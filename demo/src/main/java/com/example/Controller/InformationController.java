package com.example.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Database.Information;
import com.example.Database.Posting;
import com.example.Database.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.StaticFunc.Static.GetCurrentTime;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class InformationController {
    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Information}")
    private String INFORMATION_COLLECTION_NAME;

    /**
     * 添加消息通知
     * @param map 至少要有一个postingId的字段
     * @return
     */
    @PostMapping(value = "/Information/Add")
    public void AddInformation(@RequestBody Map<String,Object> map) {
        String postingId = map.get("postingId").toString();
		Query query = new Query(Criteria.where("id").is(postingId)); ;
        String account = mongoTemplate.findOne(query,Posting.class,"Posting").getAccount();

        Information information = new Information();
        information.setSendTime(GetCurrentTime());
        information.setPostingId(postingId);
        information.setReceiverAccount(account);
        information.setRead(false);

        mongoTemplate.save(information);
    }

    /**
     * 设置已读
     * @param map "id"是要设置成已读的未读信息
     */
    @PostMapping(value = "/Information/setread")
    public void setRead(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        Query query=new Query(Criteria.where("id").is(id));
        Information inf = mongoTemplate.findOne(query, Information.class,INFORMATION_COLLECTION_NAME);
        inf.setRead(false);
        mongoTemplate.save(inf);
    }
    /**
     * 获得消息通知
     * @param map 至少需要一个"account"字段
     * @return List<Information>
     */
    @PostMapping(value = "/Information/Get")
    public JSONArray GetInformationByAccount(@RequestBody Map<String,Object> map) {
        String account = map.get("account").toString();
        List<Information> informationList = mongoTemplate.find(new Query(Criteria.where("receiverAccount").is(account)),Information.class,INFORMATION_COLLECTION_NAME);
        JSONArray jsonArray = new JSONArray();
        for(Information it:informationList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("information", it);
            Query query=new Query(Criteria.where("id").is(it.getPostingId()));
            String teamId = mongoTemplate.findOne(query,Posting.class,"Posting").getTeamId();
            jsonObject.put("teamName",mongoTemplate.findOne(new Query(Criteria.where("id").is(teamId)),Team.class,"Team").getTeamName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    /**
     * 删除消息通知
     * @param map 至少需要一个"id"字段
     */
    @PostMapping(value = "/Information/Del")
    public void DelInformation(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
		Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,Information.class,INFORMATION_COLLECTION_NAME);
    }
}


















