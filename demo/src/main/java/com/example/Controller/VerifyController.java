package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.PhoneEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;



import java.util.Map;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController

public class VerifyController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Phone}")
    private String PHONE_COLLECTION_NAME;
    String sid = "9ac2025e6582924e3fbfdb17cb753ad4";
    // sid
    String token = "ffbca7f9ef4522fa13a73d14a37ec54e";
    // token
    String appid = "a301c7ecd2e34551b3a11fa559de56dd";
    // appid
    String teplateid = "497865";
    //teplateid

    /**
     * 给手机发验证码
     * @param map "phone"是手机号
     * @return 错误讯息或者验证码
     */
    @RequestMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String send(@RequestBody Map<String,String> map) {

        String mobile = map.get("phone");
        removeByPhone(mobile);//首先删除之前所有验证码
        String randomcode=getRandom();
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://open.ucpaas.com/ol/sms/sendsms";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject Httpmap= new JSONObject();
        Httpmap.put("sid", sid);
        Httpmap.put("token", token);
        Httpmap.put("appid", appid);
        Httpmap.put("templateid", teplateid);
        Httpmap.put("mobile", mobile);
        Httpmap.put("param",randomcode);
        JSONObject json= restTemplate.postForEntity(url,Httpmap,JSONObject.class).getBody();
        String result = json.get("msg").toString();
        System.out.println(json.toString());
        if(!result.equals("OK"))
            return result;
        PhoneEntity phone= new PhoneEntity();
        phone.setMobile(mobile);
        phone.setVerifycode(randomcode);
        phone.setIsused(0);
        return randomcode;

    }
    public void removeByPhone(String phonenum)
    {
        Query query=new Query(Criteria.where("mobile").is(phonenum));
        mongoTemplate.remove(query,PHONE_COLLECTION_NAME);
    }
    public String getRandom()
    {
        return  String.valueOf((int)((Math.random()*9+1)*1000));
    }

    /**
     * 验证验证码是否正确
     * @param map "phone"和"code"
     * @return
     */
    @PostMapping(value = "/verify")
    public String verifyCode(@RequestBody Map<String,String> map)
    {
        String phoneNum=map.get("phone");
        String code=map.get("code");
        Query query=new Query(Criteria.where("mobile").is( phoneNum));
        PhoneEntity phone =mongoTemplate.findOne(query,PhoneEntity.class,PHONE_COLLECTION_NAME);
        if(phone==null)
            return "phone should have no code";
        if(phone.getVerifycode().equals(code))
        {
            return "success";
        }
        else
            return "error code";
    }
}
