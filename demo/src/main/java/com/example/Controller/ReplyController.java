package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.BookEntity;
import com.example.Database.FilmEntity;
import com.example.Database.Posting;
import com.example.Database.ReplyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.example.StaticFunc.Static.GetCurrentTime;

@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class ReplyController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Reply}")
    private String REPLY_COLLECTION_NAME;

    /**
     * 根据用户ID和类型ID查找评论
     * @param map map中需要存在一个以"id"为键的键值对和一个以"type"为键的键值对
     * @return
     */
    @PostMapping(value = "/reply/find")
    public List<ReplyEntity> findReplyByReplyerIdAndType(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        int type = Integer.parseInt(map.get("type").toString());
        Query query = new Query(Criteria.where("replyerId").is(id).and("type").is(type));
        List<ReplyEntity> replyList = mongoTemplate.find(query, ReplyEntity.class,REPLY_COLLECTION_NAME);
        return replyList;
    }

    /**
     * 查找用户评论过的书籍影视
     * @param map "id" 用户的id
     * @return
     */
    @PostMapping(value = "/reply/findFilmAndBookByReply")
    public JSONObject findFilmAndBookByReply(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        Query bookquery = new Query(Criteria.where("replyerId").is(id).and("type").is(1));
        Query filmquery = new Query(Criteria.where("replyerId").is(id).and("type").is(2));
        List<ReplyEntity> bookreplyList = mongoTemplate.find(bookquery, ReplyEntity.class,REPLY_COLLECTION_NAME);
        List<ReplyEntity> filmreplyList = mongoTemplate.find(filmquery, ReplyEntity.class,REPLY_COLLECTION_NAME);
        JSONObject json=new JSONObject();
        ArrayList<FilmEntity> filmlist=new ArrayList<FilmEntity>();
        ArrayList<BookEntity> booklist=new ArrayList<BookEntity>();
        for(ReplyEntity it:bookreplyList)
        {
            Query query=new Query(Criteria.where("id").is(it.getPlaceId()));
            BookEntity bookEntity=mongoTemplate.findOne(query,BookEntity.class,"Book");
            booklist.add(bookEntity);
        }
        for(ReplyEntity it:filmreplyList)
        {
            Query query=new Query(Criteria.where("id").is(it.getPlaceId()));
            FilmEntity filmEntity=mongoTemplate.findOne(query,FilmEntity.class,"Film");
            filmlist.add(filmEntity);
        }
        Set<FilmEntity> filmset=new LinkedHashSet<FilmEntity>(filmlist);
        Set<BookEntity> bookset=new LinkedHashSet<BookEntity>(booklist);
        List<FilmEntity> filmafterSetlist=new ArrayList<FilmEntity>(filmset);
        List<BookEntity> bookafterSetlist=new ArrayList<BookEntity>(bookset);
        json.put("filmlist",filmafterSetlist);
        json.put("booklist",bookafterSetlist);
        return json;
    }

    /**
     * 查找用户评论过的帖子
     * @param map "id" 用户的id
     * @return
     */
    @PostMapping(value = "/reply/findPostingByReply")
    public ArrayList<Posting> findPostingByReply(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        Query query = new Query(Criteria.where("replyerId").is(id).and("type").is(4));
        List<ReplyEntity> PostreplyList = mongoTemplate.find(query, ReplyEntity.class,REPLY_COLLECTION_NAME);
        JSONObject json=new JSONObject();
        ArrayList<Posting> Postlist=new ArrayList<Posting>();
        for(ReplyEntity it:PostreplyList)
        {
            Query q=new Query(Criteria.where("id").is(it.getPlaceId()));
            Posting post=mongoTemplate.findOne(q,Posting.class,"Posting");
            Postlist.add(post);
        }
        return Postlist;
    }
    /**
     * 查找书、影、音、帖下的回复
     * @param map map中需要存在一个以"id"为键的键值对,这个id是要查找回复的书籍、影视、音乐、帖子的ID
     * @return
     */

    @PostMapping(value ="/reply/list")
    public ArrayList<JSONObject> List(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        Query query = new Query(Criteria.where("placeId").is(id));
        List<ReplyEntity> replyList = mongoTemplate.find(query, ReplyEntity.class,REPLY_COLLECTION_NAME);
        ArrayList<JSONObject> jsonlist=new ArrayList<JSONObject>();
        for(ReplyEntity it:replyList)
        {
            String postingId = it.getId();
            Query likequery = new Query(Criteria.where("postingId").is(postingId));
            long  num=mongoTemplate.count(query,"Like");
            JSONObject json=new JSONObject();
            json.put("likes",num);
            json.put("reply",it);
            jsonlist.add(json);
        }
        return jsonlist;
    }

    /**
     * 更新（暂时不用）
     * @param reply
     * @return
     */
    @PostMapping(value = "/reply/update")
    public String update(@RequestBody ReplyEntity reply) {
        mongoTemplate.save(reply);
        return reply.getId().toString();
    }

    /**
     * 添加评论
     * @param reply
     * @return Reply's ObjectId
     */
    @PostMapping(value = "/reply/add")
    public String addNewReply(@RequestBody ReplyEntity reply) {
        reply.setDate(GetCurrentTime());
        mongoTemplate.save(reply);
        return reply.getId().toString();
    }

}

