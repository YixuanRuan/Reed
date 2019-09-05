package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.Posting;
import com.example.Database.ReplyEntity;
import com.example.Database.ReportEntity;
import com.example.Database.Team;
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
public class ReportController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${com.example.mongodb.collection.Report}")
    private String Report_COLLECTION_NAME;

    /**
     * 添加举报内容
     * @param reportEntity  前端需要在json中填写完整的ReoportEntity中的条目
     * @return  返回ObjectId
     */
    @PostMapping(value = "/report/add")
    public String addReport(@RequestBody ReportEntity reportEntity) {
        reportEntity.setDate(GetCurrentTime());
        return mongoTemplate.insert(reportEntity).toString();
    }

    /**
     *删除举报
     * @param map 需要删除的举报的ObjectId,JSON中以"id"命名
     * @return
     */
    @PostMapping(value = "/report/delete")
    public String deleteReport(@RequestBody Map<String, String> map) {
        String id=map.get("id");
        Query query=new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query,ReportEntity.class,"Report").toString();
    }

    /**
     * 拉取举报列表
     * @return 返回一个举报的list,有对帖子的举报和对回复的举报
     */
    @PostMapping(value = "/report/list")
    public ArrayList<JSONObject> deleteReport() {
        List<ReportEntity> list = mongoTemplate.findAll(ReportEntity.class);
        ArrayList<JSONObject> jsonlist=new ArrayList<JSONObject>();
        for(ReportEntity it:list)
        {
            int type=it.getType();
            String id= it.getReportedId();
            Query query=new Query(Criteria.where("id").is(id));
            JSONObject json=new JSONObject();
            json.put("report",it);
            if(type==0) //是帖子
            {
                Posting posting=mongoTemplate.findOne(query,Posting.class,"Posting");
                Query teamq=new Query(Criteria.where("id").is(posting.getTeamId()));
                Team team=mongoTemplate.findOne(teamq,Team.class,"Team");
                json.put("Team",team);
                json.put("Posting",posting);
            }
            else if(type==1) //是回复
            {
                ReplyEntity ReplyEntity=mongoTemplate.findOne(query,ReplyEntity.class,"Reply");
                json.put("Reply",ReplyEntity);
            }
            jsonlist.add(json);
        }
        return jsonlist;
    }
}
