package com.example.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Database.Collection;
import com.example.Database.FilmEntity;
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
public class CollectionController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Collection}")
    private String  COLLECTION_COLLECTION_NAME;

    @PostMapping(value = "/Collection/Add")
    public void AddList(@RequestBody Map<String,Object> map) {
		Collection collection = new Collection();
		collection.setAccount(map.get("account").toString());
		collection.setCollectionTime(GetCurrentTime());
		collection.setPostingId(map.get("id").toString());
        AddCollection(collection);
    }

	@PostMapping(value="/Collection/Del")
	public void DelByAccount(@RequestBody Map<String,Object> map) {
		RemoveAllByAccount(map.get("account").toString());
	}

    @PostMapping(value = "/Collection/Get")
    public JSONArray GetCollection(@RequestBody Map<String,Object> map) {
        String account = map.get("account").toString();
        List<Collection> collectionList = mongoTemplate.find(new Query(Criteria.where("account").is(account)),Collection.class,COLLECTION_COLLECTION_NAME);
        JSONArray jsonArray = new JSONArray();
        for (int i=0;i<collectionList.size();++i) {
            String postingId = collectionList.get(i).getPostingId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account",account);
            jsonObject.put("id",postingId);
            jsonObject.put("score",mongoTemplate.findOne(new Query(Criteria.where("id").is(postingId)), FilmEntity.class,"Film").getScore());
            jsonObject.put("filmName",mongoTemplate.findOne(new Query(Criteria.where("id").is(postingId)),FilmEntity.class,"Film").getFilmName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    /**
     * 添加收藏/取消收藏
     * @param map 至少需要一个"account"字段和"postingId"字段
     * @return int
     * 成功取消收藏，返回0
     * 成功收藏，返回1
     */
    @PostMapping(value = "/Collection/ChangeStatus")
    public int ChangeCollectionStatus(@RequestBody Map<String,Object> map) {
        String account = map.get("account").toString();
        String postingId = map.get("postingId").toString();
        if (IsAccountCollectPosting(account,postingId)) {
            RemoveCollection(account, postingId);
            return 0;
        }
        Collection collection = new Collection();
        collection.setAccount(account);
        collection.setPostingId(postingId);
        collection.setCollectionTime(GetCurrentTime());
        mongoTemplate.save(collection);
        return 1;
    }

    /**
     * 根据postingId统计收藏数
     * @param map 至少需要一个"postingId"字段
     * @return 统计收藏数
     */
    @PostMapping(value = "/Collection/CountNum")
    public Long CountCollectionNum(@RequestBody Map<String, Object> map) {
        String postingId = map.get("postingId").toString();
        Query query = new Query(Criteria.where("postingId").is(postingId));
        return mongoTemplate.count(query,COLLECTION_COLLECTION_NAME);
    }

    /**
     * 取消收藏
     * @param account 用户账号
     * @param postingId 帖子Id
     */
    public void RemoveCollection(String account, String postingId) {
        Query query = new Query(Criteria.where("account").is(account).and("postingId").is(postingId));
        mongoTemplate.remove(query,Collection.class,COLLECTION_COLLECTION_NAME);
    }

    public void RemoveAllByAccount(String account) {
        mongoTemplate.remove(new Query(Criteria.where("account").is(account)),Collection.class,COLLECTION_COLLECTION_NAME);
    }

    public void AddCollection(Collection map){
        mongoTemplate.insert(map);
    }

    /**
     * 判断该账户是否收藏了该帖子
     * @param account 用户账号
     * @param postingId 帖子Id
     * @return boolean
     * 如果收藏了，返回true
     * 如果没有收藏，返回false
     */
    public boolean IsAccountCollectPosting(String account, String postingId) {
        return FindCollectionByAccountAndPosting(account, postingId) != null;
    }

    /**
     * 通过用户账号和帖子Id查找数据库
     * @param account 用户账号
     * @param postingId 帖子Id
     * @return 返回一个collection
     */
    public Collection FindCollectionByAccountAndPosting(String account, String postingId) {
        Query query = new Query(Criteria.where("account").is(account).and("postingId").is(postingId));
        return mongoTemplate.findOne(query,Collection.class,COLLECTION_COLLECTION_NAME);
    }
}
