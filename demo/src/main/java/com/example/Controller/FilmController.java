package com.example.Controller;

import com.example.Database.FilmEntity;
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

import java.util.List;
import java.util.Map;

@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class FilmController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Film}")
    private String FILM_COLLECTION_NAME;

    /**
     * 查找图书
     * @param map 同book
     * @return
     */
    @PostMapping(value = "/film/find")
    public FilmEntity findById(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        Query query = new Query().addCriteria(Criteria.where("id").is(id));
        FilmEntity film=mongoTemplate.findOne(query, FilmEntity.class,FILM_COLLECTION_NAME);
        return film;
    }

    /**
     * 返回所有电影信息
     * @return
     */
    @PostMapping(value = "/film/findAll")
    public List<FilmEntity> findAll() {
        Query query = new Query();
        return mongoTemplate.findAll(FilmEntity.class);
    }
    /**
     * 更新图书
     * @param film
     * @return
     */
    @PostMapping(value = "/film/update")
    public String update(@RequestBody FilmEntity film) {
        mongoTemplate.save(film);
        return film.getId().toString();
    }

    /**
     * 添加书籍
     * @param film
     * @return
     */
    @PostMapping(value = "/film/add")
    public String addNewFilm(@RequestBody FilmEntity film) {
        film.setScore("50");
        mongoTemplate.save(film);
        return film.getId().toString();
    }

    /**
     * 添加所有电影
     * @param film
     * @return
     */
    @PostMapping(value = "/film/addAll")
    public String addAlls(@RequestBody FilmEntity film[]) {
        int size=film.length;
        for(FilmEntity f:film) {
            f.setScore("50");
            mongoTemplate.insert(f);
        }
        return film.length+" have been added.";
    }
    /**
     * 计算评分
     * @param map
     * @return
     */


    /**
     * 打分评价
     * @param map map中需要存在一个以"id"为键的键值对和一个以"grade"为键的键值对,0是踩，1是赞,"account"用户
     */
    @PostMapping(value = "/film/grade")//给评分
    public String gradeById(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        String account=map.get("account").toString();
        if(isLikeByAccountAndPosting(account,id)==true)
            return "like fail";
        Query query = new Query().addCriteria(Criteria.where("id").is(id));
        FilmEntity film=mongoTemplate.findOne(query, FilmEntity.class);
        String grade=map.get("grade").toString();
        if(Integer.parseInt(grade)==0)
            film.setDislike(film.getDislike()+1);
        else if(Integer.parseInt(grade)==1)
            film.setLike(film.getLike()+1);

        double score= film.getLike()+0.0/(film.getLike()+film.getDislike()+0.0);
        score=score*100;
        int scoreInt= (int) score;
        String SCORE=String.valueOf(scoreInt/2);
        film.setScore(SCORE);
        Like like=new Like();
        like.setType(2);
        like.setPostingId(id);
        like.setAccount(account);
        mongoTemplate.save(like);
        mongoTemplate.save(film);
        return "success";
    }
    public   boolean isLikeByAccountAndPosting(String account, String postingId) {
        Query query = new Query(Criteria.where("account").is(account).and("postingId").is(postingId));
        if(mongoTemplate.findOne(query,Like.class)==null)
            return false;
        return true;
    }
}

