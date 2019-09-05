package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.StaticFunc.Static.GetCurrentTime;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class TeamController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Team}")
    private String TEAM_COLLECTION_NAME;

    private List<Team> HobbyTeam = new ArrayList<Team>();
    private List<Team> EmotionTeam = new ArrayList<Team>();
    private List<Team> MovieTeam = new ArrayList<Team>();
    private List<Team> LiteratureTeam = new ArrayList<Team>();
    private List<Team> AcademicTeam = new ArrayList<Team>();
    private List<Team> LifeTeam = new ArrayList<Team>();

    /**
     * 创建一个新的队伍
     * @param map 至少需要"introduction"的字段，"teamName"的字段，"account"字段,"tags"字段
     * @return team's ObjectId Str
     */
    @PostMapping(value = "/Team/Add")
    public String CreateTeam(@RequestBody Map<String,Object> map) {
        String introduction = map.get("introduction").toString();
        String teamName = map.get("teamName").toString();
        Date createTime = GetCurrentTime();
        String account = map.get("account").toString();
        String temp = map.get("tags").toString();
        temp = temp.replaceAll(" ","");
        String[] tags = temp.substring(1,temp.length()-1).split(",");

        Map<String,Integer> memberMap = new HashMap<String,Integer>();
        memberMap.put(account,1);

        Team team = new Team();
        team.setIntroduction(introduction);
        team.setTeamName(teamName);
        team.setCreateTime(createTime);
        team.setTeamMember(memberMap);
        team.setTags(tags);
        mongoTemplate.save(team);
        return team.getId().toString();
    }

    /**
     * 退出一个队伍
     * @param map userid,teamid
     * @return team's ObjectId Str
     */
    @PostMapping(value = "/Team/out")
    public String outTeam(@RequestBody Map<String,String> map) {
        String userid=map.get("account");
        String teamid=map.get("teamid");
        if(IsAccountJoinTeam(userid,teamid)==false)
            return "user not in team!";
        getOutOfTeam(userid,teamid);
        return "success";
    }

    public void getOutOfTeam(String account,String id)
    {
        Team team=mongoTemplate.findById(id,Team.class);
        Map<String, Integer> map = team.getTeamMember();
        map.remove(account);
        team.setTeamMember(map);
        mongoTemplate.save(team);
    }
    /**
     * 查找对应id的队伍
     * @param map 至少需要包含一个"id"字段
     * @return Team
     */
    @PostMapping(value = "/Team/FindOne")
    public Team FindOneById(@RequestBody Map<String,Object> map) {
        String teamId = map.get("id").toString();
        return mongoTemplate.findById(teamId,Team.class);
    }

    /**
     * 返回数据库中包含的所有的团队信息
     * @return List<Team>
     */
    @PostMapping(value = "/Team/FindAll")
    public List<Team> FindAllTeam() {
        return mongoTemplate.findAll(Team.class);
    }


    /**
     * 加入队伍
     * @param map 需要有"account"字段和"power"字段(1,2,3)和"id"字段
     * @return
     * 返回1，表示加入成功
     * 返回0，表示加入失败
     */
    @PostMapping(value = "/Team/Join")
    public int JoinTeam(@RequestBody Map<String,Object> map) {
        String account = map.get("account").toString();
        Integer power = Integer.parseInt(map.get("power").toString());
        String teamId = map.get("id").toString();
        if (IsAccountJoinTeam(account, teamId))
            return 0;
        Team team = mongoTemplate.findById(teamId,Team.class,TEAM_COLLECTION_NAME);
        Map<String,Integer> memberMap = team.getTeamMember();
        memberMap.put(account,power);
        team.setTeamMember(memberMap);
        mongoTemplate.save(team);
        return 1;
    }

    /**
     * 找到带有Hobby标签的小组
     * @return List<Team>
     */
    @PostMapping(value = "/Team/FindHobby")
    public List<Team> FindHobby() {
        this.UpdateTeamList();
        return this.HobbyTeam;
    }

    /**
     * 找到带有Emotion标签的小组
     * @return List<Team>
     */
    @PostMapping(value = "/Team/FindEmotion")
    public List<Team> FindEmotion() {
        this.UpdateTeamList();
        return this.EmotionTeam;
    }

    /**
     * 找到带有Movie标签的小组
     * @return List<Team>
     */
    @PostMapping(value = "/Team/FindMovie")
    public List<Team> FindMovie() {
        this.UpdateTeamList();
        return this.MovieTeam;
    }

    /**
     * 找到带有Literature标签的小组
     * @return List<Team>
     */
    @PostMapping(value = "/Team/FindLiterature")
    public List<Team> FindLiterature() {
        this.UpdateTeamList();
        return this.LiteratureTeam;
    }

    /**
     * 找到带有Academic标签的小组
     * @return List<Team>
     */
    @PostMapping(value = "/Team/FindAcademic")
    public List<Team> FindAcademic() {
        this.UpdateTeamList();
        return this.AcademicTeam;
    }

    /**
     * 通过用户id获取参加的队伍
     * @param map "id"->用户account
     * @return
     */
    @PostMapping(value = "/Team/findteambyuser")
    public List<JSONObject> FindAcademic(@RequestBody Map<String,String> map) {
        String id=map.get("id").toString();
        return getTeamByUserId(id);
    }
    /**
     * 找到带有Life标签的小组
     * @return List<Team>
     */
    @PostMapping(value = "/Team/FindLife")
    public List<Team> FindLife() {
        this.UpdateTeamList();
        return this.LifeTeam;
    }

	@PostMapping(value = "/Team/IsJoin")
	public boolean IsJoin(@RequestBody Map<String,Object> map) {
		String teamId = map.get("id").toString();
		String account = map.get("account").toString();
		return IsAccountJoinTeam(account,teamId);
	}

    /**
     * 判断该账户是否加入了该组织
     * @param account
     * @param teamId
     * @return boolean
     */
    public boolean IsAccountJoinTeam(String account, String teamId) {
        Map<String,Integer> memberMap = GetTeamMemberById(teamId);
		if (memberMap == null)
			return false;
        Integer power = memberMap.get(account);
        return power != null;
    }

    /**
     * 更新TeamList
     */
    public void UpdateTeamList() {
        List<Team> teamList = mongoTemplate.findAll(Team.class);
        this.HobbyTeam.clear();
        this.EmotionTeam.clear();
        this.MovieTeam.clear();
        this.LiteratureTeam.clear();
        this.AcademicTeam.clear();
        this.LifeTeam.clear();
        for(int i=0;i<teamList.size();++i) {
            String[] tagList = teamList.get(i).getTags();
            for (int j=0;j<tagList.length;++j) {
                if (tagList[j].equals("兴趣"))
                    HobbyTeam.add(teamList.get(i));
                if (tagList[j].equals("情感"))
                    EmotionTeam.add(teamList.get(i));
                if (tagList[j].equals("电影"))
                    MovieTeam.add(teamList.get(i));
                if (tagList[j].equals("文学"))
                    LiteratureTeam.add(teamList.get(i));
                if (tagList[j].equals("生活"))
                    LifeTeam.add(teamList.get(i));
            }
        }
    }

    /**
     * 通过ID得到TeamMember
     * @param teamId
     * @return Map
     */
    public Map<String,Integer> GetTeamMemberById(String teamId) {
        Team team = mongoTemplate.findById(teamId,Team.class,TEAM_COLLECTION_NAME);
		if (team == null)
			return null;
		else
			return team.getTeamMember();
    }

    /**
     * 查找用户加入的所有小组
     * @param id 用户account
     * @return
     */
    public ArrayList<JSONObject> getTeamByUserId(String id)
    {
        List<Team> allteam=mongoTemplate.findAll(Team.class);
        ArrayList<JSONObject> ret=new ArrayList<JSONObject>();
        for(Team it:allteam)
        {
            JSONObject json=new JSONObject();
            Map<String, Integer> mems = it.getTeamMember();
            if(mems.get(id)!=null)
            {
                json.put("team",it);
                json.put("power",mems.get(id));
                ret.add(json);
            }
        }
        return ret;
    }
}
