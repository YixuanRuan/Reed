package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.Team;
import com.example.Database.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.StaticFunc.Static.GetCurrentTime;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.User}")
    private String USER_COLLECTION_NAME;

    /**
     * 返回数据库中包含特定account的用户信息
     * @param map 至少需要包含一个"account"
     * @return User
     */
    @PostMapping(value = "/User/FindOne")
    public User FindOneUser(@RequestBody Map<String, Object> map) {
        String account = map.get("account").toString();
        return QueryUserByAccount(account);
    }

    /**
     * 判断是否是超级用户
     * @param map 用户"account"
     * @return boolean
     */
    @PostMapping(value = "/User/issupper")
    public boolean isSuper(@RequestBody Map<String, Object> map) {
        String account = map.get("account").toString();
        User user=QueryUserByAccount(account);
        return user.isSuperuser();
    }
    /**
     * 返回数据库中包含的所有用户信息
     * @return List<User>
     */
    @PostMapping(value = "/User/FindAll")
    public Object FindAllUser() {
        List<User> userList = mongoTemplate.findAll(User.class);
        return userList;
    }

    /**
     * 添加用户
     * @param user 至少需要一个"account"字段和一个"password"字段
     * @return 返回是否创建成功的信息
     * 返回0表示该账户已存在
     * 返回1表示账户创建成功
     */
    @PostMapping(value = "/User/Add")
    public int AddUser(@RequestBody User user) {
        if (!IsAccountExist(user.getAccount())) {
            user.setJoinTime(GetCurrentTime());
            user.setSuperuser(false);
            mongoTemplate.save(user);
        }
        else
            return 0;
        return 1;
    }

    /**
     * 用户登录
     * @param user 至少需要一个"account"字段和一个"password"字段
     * @return Object
     * 如果用户不存在，则返回0
     * 如果密码错误，则返回-1
     * 登录成功，返回用户信息
     */
    @PostMapping(value = "/User/Login")
    public Object Login(@RequestBody User user) {
		JSONObject jsonObject = new JSONObject();
        if (!IsAccountExist(user.getAccount()))
            return 0;
        else {
            User temp = QueryUserByAccount(user.getAccount());
			if (temp.getPassword().equals(user.getPassword())){
				jsonObject.put("user",temp);
				String account = temp.getAccount();
				List<Team> teamList = mongoTemplate.findAll(Team.class);
				List<Team> res = new ArrayList<Team>();
				for(int i=0;i<teamList.size();++i) {
					Map<String, Integer> memberMap = teamList.get(i).getTeamMember();
					if(memberMap.containsKey(account))
						res.add(teamList.get(i));
                }
                jsonObject.put("team",res);
                return jsonObject;
			}
            else
                return -1;
        }
    }

    /**
     * 修改用户密码
     * @param user 至少需要一个"account"字段和一个"password"字段
     * @return int
     * 如果密码未提供，则返回2
     * 剩余参数返回见函数ModifyUserMulti
     */
    @PostMapping(value = "/User/ModifyPassword")
    public int ModifyUserPassword(@RequestBody User user) {
        String account = user.getAccount();
        String password = user.getPassword();
        if (password == null)
            return 2;
        return ModifyUserMulti(user);
    }


    /**
     * 修改用户名
     * @param user 至少需要一个"account"字段和一个"username"字段
     * @return int
     * 如果username未提供，则返回2
     * 剩余参数返回见函数ModifyUserMulti
     */
    @PostMapping(value = "/User/ModifyUserName")
    public int ModifyUserUserName(@RequestBody User user) {
        String account = user.getAccount();
        String username = user.getUsername();
        if (username == null)
            return 2;
        return ModifyUserMulti(user);
    }

    /**
     * 修改个人介绍
     * @param user 至少需要一个"account"字段和一个"introduction"字段
     * @return int
     * 如果introduction未提供，则返回2
     * 剩余参数返回见函数ModifyUserMulti
     */
    @PostMapping(value = "/User/ModifyIntroduction")
    public int ModifyUserIntroduction(@RequestBody User user) {
        String account = user.getAccount();
        String introduction = user.getIntroduction();
        if (introduction == null)
            return 2;
        return ModifyUserMulti(user);
    }

    /**
     * 修改性别
     * @param user 至少需要一个"account"字段和一个"gender"字段
     * @return int
     * 如果gender未提供，则返回2
     * 剩余参数返回见函数ModifyUserMulti
     */
    @PostMapping(value = "/User/ModifyGender")
    public int ModifyUserGender(@RequestBody User user) {
        String account = user.getAccount();
        String gender = user.getGender();
        if (gender == null)
            return 2;
        return ModifyUserMulti(user);
    }

    /**
     * 修改用户信息
     * @param user 至少需要一个"account"字段，剩余的则是需要修改什么就添加什么字段
     * @return int
     * 如果account未提供，返回0
     * 如果account不存在，返回-1
     * 如果修改成功，返回1
     */
    public int ModifyUserMulti(User user) {
        String account = user.getAccount();
        if (account == null)
            return 0;
        if (!IsAccountExist(account))
            return -1;
        Query query = new Query(Criteria.where("account").is(account));
        String password = user.getPassword();
        if (password != null) {
            Update update = new Update().set("password",password);
            mongoTemplate.updateFirst(query,update,USER_COLLECTION_NAME);
        }
        String username = user.getUsername();
        if(username != null) {
            Update update = new Update().set("username",username);
            mongoTemplate.updateFirst(query,update,USER_COLLECTION_NAME);
        }
        String introduction = user.getIntroduction();
        if(introduction != null) {
            Update update = new Update().set("introduction",introduction);
            mongoTemplate.updateFirst(query,update,USER_COLLECTION_NAME);
        }
        String gender = user.getGender();
        if(gender != null) {
            Update update = new Update().set("gender",gender);
            mongoTemplate.updateFirst(query,update,USER_COLLECTION_NAME);
        }
        return 1;
    }

    /**
     * 判断用户是否存在
     * @param Account String
     * @return 返回布尔值
     */
    public boolean IsAccountExist(String Account) {
        User user = QueryUserByAccount(Account);
        if (user == null)
            return false;
        else
            return true;
    }

    /**
     * 根据account查找用户
     * @param Account String
     * @return 返回用户信息，如果不存在返回null
     */
    public User QueryUserByAccount(String Account) {
        Query query = new Query(Criteria.where("account").is(Account));
        return mongoTemplate.findOne(query,User.class,USER_COLLECTION_NAME);
    }
}
