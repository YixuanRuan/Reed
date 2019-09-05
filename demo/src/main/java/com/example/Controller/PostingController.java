package com.example.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Database.*;
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
import static com.example.StaticFunc.Static.Md2Txt;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class PostingController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Posting}")
    private String POSTING_COLLECTION_NAME;

    /**
     * 发布帖子
     * @param posting
     * @return String posting的ID
     */
    @PostMapping(value = "/Posting/Add")
    public String AddPosting(@RequestBody Posting posting) {
        posting.setPostTime(GetCurrentTime());
        mongoTemplate.save(posting);
        return posting.getId();
    }

    @PostMapping(value = "/Posting/FindOne")
    public JSONObject FindOne(@RequestBody Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        Posting posting = FindPostingById(map.get("id").toString());
        jsonObject.put("posting",posting);
        jsonObject.put("teamId",GetTeamIdByPostingId(posting.getId()));
        jsonObject.put("teamName",GetTeamNameByPostingId(posting.getId()));
        return jsonObject;
    }

    /**
     * 得到小组信息
     * @param map 至少需要一个"account"字段
     * @return JSONArray
     */
    @PostMapping(value = "/MyGroupNotification")
    public JSONArray GetMyGroupNotification(@RequestBody Map<String,Object> map) {
        JSONArray jsonArray = new JSONArray();
        String account = map.get("account").toString();

        List<Team> teamList = GetTeamByUserAccount(account);
        List<Posting> postingList = new ArrayList<Posting>();
        for(int i=0;i<teamList.size();++i) {
            List<Posting> temp = GetPostingsByTeam(teamList.get(i).getId());
            for(int j=0;j<temp.size();++j)
                postingList.add(temp.get(j));
        }

        for(int i=0;i<postingList.size();++i) {
			String postingId = postingList.get(i).getId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account",postingList.get(i).getAccount());
            jsonObject.put("username",GetUserNameByAccount(account));
            jsonObject.put("teamId",GetTeamIdByPostingId(postingList.get(i).getId()));
            jsonObject.put("teamName",GetTeamNameByPostingId(postingList.get(i).getId()));
			jsonObject.put("postingId", postingId);
			jsonObject.put("likeNum", GetLikeNumByPostingId(postingId));
			jsonObject.put("replyNum", GetReplyNumByPostingId(postingId));
            jsonObject.put("title",postingList.get(i).getTitle());
            jsonObject.put("content",postingList.get(i).getContent());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 搜索用户帖子发布历史
     * @param map 至少需要一个"account"字段
     * @return List<Posting>
     */
    @PostMapping(value = "/Posting/SearchHistory")
    public List<Posting> SearchPostingHistory(@RequestBody Map<String,Object> map) {
        List<Posting> postingList = mongoTemplate.find(new Query(Criteria.where("account").is(map.get("account").toString())),Posting.class,POSTING_COLLECTION_NAME);
		for(int i=0;i<postingList.size();++i) {
			Posting posting = postingList.get(i);
			posting.setContent(Md2Txt(posting.getContent()));
		}
		return postingList;
    }

    @PostMapping(value = "/Posting/GetPostingSortByLike")
    public JSONArray GetPostingSortByLike(@RequestBody Map<String,Object> map) {
        int num = Integer.parseInt(map.get("num").toString());
        List<Posting> postingList = mongoTemplate.findAll(Posting.class);
        Map<Posting,Long> mapList = new HashMap<Posting,Long>();
        for(int i=0;i<postingList.size();++i)
            mapList.put(postingList.get(i),GetLikeNumByPostingId(postingList.get(i).getId()));
        Map<Posting,Long> sortMap = sortMapByValue(mapList);

        int temp=0;
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Posting, Long> entry : sortMap.entrySet()) {
            temp++;
            if (temp > num)
                break;
            JSONObject jsonObject = new JSONObject();
            Posting posting = entry.getKey();
            jsonObject.put("id",posting.getId());
            jsonObject.put("account",posting.getAccount());
            jsonObject.put("username",GetUserNameByAccount(posting.getAccount()));
            jsonObject.put("teamId",GetTeamIdByPostingId(posting.getId()));
            jsonObject.put("teamName",GetTeamNameByPostingId(posting.getId()));
            jsonObject.put("title",posting.getTitle());
            jsonObject.put("content",Md2Txt(posting.getContent()));
            jsonObject.put("likeNum",entry.getValue());
            jsonObject.put("replyNum", GetReplyNumByPostingId(posting.getId()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


	@PostMapping(value = "/Posting/Del")
	public void PostingDel(@RequestBody Map<String,Object> map) {
		String id = map.get("postingId").toString();
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Posting.class, POSTING_COLLECTION_NAME);
	}

    /**
     * 通过id找到帖子
     * @param id
     * @return Posting
     */
    public Posting FindPostingById(String id) {
        return mongoTemplate.findById(id,Posting.class);
    }

    /**
     * 通过用户账号得到所在队伍
     * @param account
     * @return List<Team>
     */

    /**
     * 设置置顶
     * @param map "id"
     */
    @PostMapping(value = "/Posting/settop")
    public void setTop(@RequestBody Map<String,String> map)
    {
        String id=map.get("id");
        Posting post=mongoTemplate.findById(id,Posting.class);
        post.setIstop(true);

    }

    /**
     * 设置加精
     * @param map ”id“
     */
    @PostMapping(value = "/Posting/setbest")
    public void setBest(@RequestBody Map<String,String> map)
    {
        String id=map.get("id");
        Posting post=mongoTemplate.findById(id,Posting.class);
        post.setIsbest(true);

    }
    public List<Team> GetTeamByUserAccount(String account) {
        List<Team> teamList = mongoTemplate.findAll(Team.class);
        List<Team> res = new ArrayList<Team>();
        for(int i=0;i<teamList.size();++i) {
            Map<String, Integer> memberMap = teamList.get(i).getTeamMember();
            if(memberMap.containsKey(account))
                res.add(teamList.get(i));
        }
        return res;
    }

    /**
     * 通过队伍id得到
     * @param teamId
     * @return List<Posting>
     */
	@PostMapping(value="/Posting/GetPostingsByTeamId")
    public JSONArray GetPostingsByTeamId(@RequestBody Map<String,Object> map) {
		String teamId = map.get("teamId").toString();
        Query query = new Query(Criteria.where("teamId").is(teamId));
        List<Posting> postingList = mongoTemplate.find(query,Posting.class);
		JSONArray jsonArray = new JSONArray();
		for (int i=0;i<postingList.size();++i) {
			JSONObject jsonObject = new JSONObject();
			String postingId = postingList.get(i).getId();
			jsonObject.put("posting", postingList.get(i));
			jsonObject.put("likeNum", GetLikeNumByPostingId(postingId));
			jsonObject.put("replyNum", GetReplyNumByPostingId(postingId));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
    }

	public List<Posting> GetPostingsByTeam(String teamId) {
        Query query = new Query(Criteria.where("teamId").is(teamId));
        return mongoTemplate.find(query,Posting.class);
	}

    /**
     * 通过"account"得到UserName
     * @param account
     * @return String
     */
    public String GetUserNameByAccount(String account) {
        Query query = new Query(Criteria.where("account").is(account));
        return mongoTemplate.findOne(query, User.class).getUsername();
    }

    /**
     * 通过PostingId得到TeamId
     * @param postingId
     * @return String
     */
    public String GetTeamIdByPostingId(String postingId) {
        Query query = new Query(Criteria.where("id").is(postingId));
        return mongoTemplate.findOne(query,Posting.class).getTeamId();
    }

    /**
     * 通过PostingId得到TeamName
     * @param postingId
     * @return String
     */
    public String GetTeamNameByPostingId(String postingId) {
        Query query = new Query(Criteria.where("id").is(postingId));
        String teamId = mongoTemplate.findOne(query,Posting.class).getTeamId();
        query = new Query(Criteria.where("id").is(teamId));
        return mongoTemplate.findOne(query,Team.class).getTeamName();
    }

    public long GetLikeNumByPostingId(String postingId) {
        return mongoTemplate.count(new Query(Criteria.where("postingId").is(postingId)), Like.class,"Like");
    }

    public Long GetReplyNumByPostingId(String postingId) {
        return mongoTemplate.count(new Query(Criteria.where("placeId").is(postingId).and("type").is(4)), ReplyEntity.class,"Reply");

    }

    public static Map<Posting, Long> sortMapByValue(Map<Posting, Long> map) {
        Map<Posting, Long> sortedMap = new LinkedHashMap<Posting, Long>();
        List<Map.Entry<Posting, Long>> lists = new ArrayList<Map.Entry<Posting, Long>>(map.entrySet());
        Collections.sort(lists, new Comparator<Map.Entry<Posting, Long>>() {
            public int compare(Map.Entry<Posting, Long> o1, Map.Entry<Posting, Long> o2) {
                double q1 = o1.getValue();
                double q2 = o2.getValue();
                double p = q2 - q1;
                if (p > 0) {
                    return 1;
                } else if (p == 0) {
                    return 0;
                } else
                    return -1;
            }
        });
        if (lists.size() >= 10) {
            for (Map.Entry<Posting, Long> set : lists.subList(0, 10)) {
                sortedMap.put(set.getKey(), set.getValue());
            }
        } else {
            for (Map.Entry<Posting, Long> set : lists) {
                sortedMap.put(set.getKey(), set.getValue());
            }
        }
        return sortedMap;
    }

}





