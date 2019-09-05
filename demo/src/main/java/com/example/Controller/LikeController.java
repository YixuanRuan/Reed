package com.example.Controller;

import com.example.Database.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.example.StaticFunc.Static.GetCurrentTime;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class LikeController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Like}")
    private String LIKE_COLLECTION_NAME;

    /**
     * 点赞/取消点赞
     * @param map 至少需要一个"account"字段和一个"postingId"字段还有一个"type" 1是图书，2是电影，3是评论，4是帖子
     * @return int
     * 如果成功取消点赞，返回0
     * 如果点赞成功，返回1
     */
    @PostMapping(value = "/Like/ChangeStatus")
    public int ChangeLikeStatus(@RequestBody Map<String,Object> map) {
        String account = map.get("account").toString();
        int type=Integer.parseInt(map.get("type").toString());
        String postingId = map.get("postingId").toString();
        if (IsAccountLikePosting(account,postingId)) {
            RemoveLike(account, postingId);
            return 0;
        }
        Like like = new Like();
        like.setAccount(account);
        like.setType(type);
        like.setPostingId(postingId);
        like.setLikeTime(GetCurrentTime());
        mongoTemplate.save(like);
        return 1;
    }

    /**
     * 根据postingId统计点赞总数
     * @param map 至少需要一个"postingId"字段
     * @return Long
     * 统计的点赞总数
     */
    @PostMapping(value = "/Like/CountNum")
    public Long CountLikeNum(@RequestBody Map<String, Object> map) {
        String postingId = map.get("postingId").toString();
        Query query = new Query(Criteria.where("postingId").is(postingId));
        return mongoTemplate.count(query,LIKE_COLLECTION_NAME);
    }

    /**
     * 判断该账户是否点赞了该帖子
     * @param map 至少需要一个"account"字段和一个"postingId"字段
     * @return boolean
     */
    @PostMapping(value = "/Like/IsLike")
    public boolean IsLike(@RequestBody Map<String, Object> map) {
        return IsAccountLikePosting(map.get("account").toString(),map.get("postingId").toString());
    }


    /**
     * 取消点赞
     * @param account
     * @param postingId
     */
    public void RemoveLike(String account, String postingId) {
        Query query = new Query(Criteria.where("account").is(account).and("postingId").is(postingId));
        mongoTemplate.remove(query,Like.class,LIKE_COLLECTION_NAME);
    }

    /**
     * 判断该账户是否点赞了该帖子
     * @param account 用户账户
     * @param postingId 帖子Id
     * @return 布尔值
     */
    public  boolean IsAccountLikePosting(String account, String postingId) {
        return FindLikeByAccountAndPosting(account, postingId) != null;
    }

    /**
     * 通过用户账户和帖子Id查找数据库
     * @param account 用户账户
     * @param postingId 帖子Id
     * @return Like
     */
    public Like FindLikeByAccountAndPosting(String account, String postingId) {
        Query query = new Query(Criteria.where("account").is(account).and("postingId").is(postingId));
        return mongoTemplate.findOne(query,Like.class);
    }


}
