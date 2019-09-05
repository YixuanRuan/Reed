package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.CommentReply;
import com.example.Database.ReplyEntity;
import com.example.Database.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.StaticFunc.Static.GetCurrentTime;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class CommentReplyController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${com.example.mongodb.collection.CommentReply}")
    private String COMMENTREPLY_COLLECTION_NAME;

    /**
     *
     * @param map "要查找回复的那条回复id
     * @return
     */
    @PostMapping(value = "/commentreply/get")
    public CommentReply getCommentReply(@RequestBody Map<String,String> map) {
        String replyid=map.get("id");
        Query query=new Query(Criteria.where("replyId").is(replyid));
        return mongoTemplate.findOne(query, CommentReply.class,COMMENTREPLY_COLLECTION_NAME);
    }
    /**
     *查找二级评论
     * @param map 二级评论的"id"
     * @return
     */
    @PostMapping(value = "/commentreply/find")
    public CommentReply findCommentReply(@RequestBody Map<String,String> map) {
        String replyid=map.get("id");
        return mongoTemplate.findById(replyid,CommentReply.class);
    }

    /**
     * 根据书影贴id返回所有二级评论
     * @param map 书影贴id->"id"
     * @return
     */
    @PostMapping(value = "/commentreply/findByMAB")
    public ArrayList<CommentReply> findByMAB(@RequestBody Map<String,String> map) {
        String movieorbookid=map.get("id");
        Query query=new Query(Criteria.where("placeId").is(movieorbookid));
        List<ReplyEntity> list = mongoTemplate.find(query, ReplyEntity.class, "Reply");
        ArrayList<CommentReply> ctlist=new ArrayList<>();
        for(ReplyEntity it :list)
        {
            CommentReply commentReply=findCRBysecid(it.getId());
            if(commentReply!=null)
                ctlist.add(commentReply);
        }
        return ctlist;
    }

    public CommentReply findCRBysecid(String replyid)
    {
        Query query=new Query(Criteria.where("replyId").is(replyid));
        return mongoTemplate.findOne(query,CommentReply.class,COMMENTREPLY_COLLECTION_NAME);
    }
    /**
     *由二级评论找一级评论的内容
     * @param map 二级评论的"id"
     * @return
     */
    @PostMapping(value = "/commentreply/sectoFir")
    public JSONObject secondfindFirstReply(@RequestBody Map<String,String> map) {
        String secid=map.get("id");
        CommentReply sec=mongoTemplate.findById(secid,CommentReply.class);
        String firid=sec.getReplyId();
        ReplyEntity reply=mongoTemplate.findById(firid,ReplyEntity.class);
        if(reply==null)
            return null;
        JSONObject json=new JSONObject();
        json.put("reply",reply);
        String userid=reply.getReplyerId();
        Query query=new Query(Criteria.where("account").is(userid));
        User user=mongoTemplate.findOne(query,User.class,"User");
        json.put("username",user.getAccount());
        json.put("account",user.getAccount());
        return json;
    }
    /**
     * 删除某个二级回复
     * @param map
     * @return
     */
    @PostMapping(value = "/commentreply/delete")
    public String delete(@RequestBody Map<String,String> map) {
        String id=map.get("id");
        CommentReply commentReply=mongoTemplate.findById(id,CommentReply.class);
        if(commentReply==null)
            return "no such commentreply";
        mongoTemplate.remove(commentReply);
        return "succuss";
    }
    /**
     *
     * @param commentReply
     *     private String userId; //发布人的objectId
     *     private String content;
     *     private String title;
     *     private String replyId
     * @return 返回添加结果，另外一条评论仅能有一条回复，多了会报错
     */
    @PostMapping(value = "/commentreply/add")
    public String getCommentReply(@RequestBody CommentReply commentReply) {
        String replyid=commentReply.getReplyId();
        if(isReplied(replyid)==true)
            return "only one Commentreply each reply!";
        commentReply.setDate(GetCurrentTime());
        mongoTemplate.insert(commentReply);
        return "success";
    }
    public boolean isReplied(String id)
    {
        Query query=new Query(Criteria.where("replyId").is(id));
        CommentReply commentReply=mongoTemplate.findOne(query, CommentReply.class,COMMENTREPLY_COLLECTION_NAME);
        if(commentReply==null)
            return false;
        return true;
    }

}
