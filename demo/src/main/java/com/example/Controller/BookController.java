package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.BookEntity;
import com.example.Database.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class BookController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${com.example.mongodb.collection.Book}")
    private String BOOK_COLLECTION_NAME;

    /**
     * 查找图书
     * @param map map中存在一个以"id"为键的键值对,用于查询对应图书
     * @return book实体类
     */
    @PostMapping(value = "/book/find")
    public BookEntity findById(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, BookEntity.class,BOOK_COLLECTION_NAME);
    }

    /**
     * 修改图书信息
     * @param book 保存了修改过的图书信息
     * @return book's ObjectId
     */
    @PostMapping(value = "/book/update")
    public String update(@RequestBody BookEntity book) {
        mongoTemplate.save(book);
        return book.getId().toString();
    }


    /**
     * 添加图书
     * @param book
     * BookEntity 中除了id和score不需要添加数据，剩下的都需要添加数据
     * 日期可以直接使用YYYY-MM-DD的格式
     * @return book's ObjectId
     */
    @PostMapping(value = "/book/add")
    public String addNewBook(@RequestBody BookEntity book) {
        book.setScore("50");
        mongoTemplate.save(book);
        return book.getId().toString();
    }

    /**
     * 添加所有电影
     * @param
     * @return
     */
    @PostMapping(value = "/book/addAll")
    public String addAlls(@RequestBody BookEntity book[]) {
        for(BookEntity f:book) {
            f.setScore("50");
            mongoTemplate.insert(f);
        }
        return book.length+" have been added.";
    }

    /**
     * 给图书评分
     * @param map map中需要存在一个以"id"为键的键值对和一个以"grade"为键的键值对,0是踩，1是赞,"account"用户
     */
    @PostMapping(value = "/book/grade")
    public String gradeById(@RequestBody Map<String,Object> map) {
        String id = map.get("id").toString();
        String account=map.get("account").toString();
        if(isLikeByAccountAndPosting(account,id)==true)
            return "like fail";
        Query query = new Query().addCriteria(Criteria.where("id").is(id));
        BookEntity book=mongoTemplate.findOne(query, BookEntity.class);
        String grade=map.get("grade").toString();
        if(Integer.parseInt(grade)==0)
            book.setDislike(book.getDislike()+1);
        else if(Integer.parseInt(grade)==1)
            book.setLike(book.getLike()+1);
        double score= book.getLike()+0.0/(book.getLike()+book.getDislike()+0.0);
        score=score*100;
        int scoreInt= (int) score;
        String SCORE=String.valueOf(scoreInt/2);
        book.setScore(SCORE);
        Like like=new Like();
        like.setPostingId(id);
        like.setType(1);
        like.setAccount(account);
        mongoTemplate.save(like);
        mongoTemplate.save(book);
        return "success";
    }

    /**
     *
     * @param map "account"-用户id,"id"-书籍影视id
     * @return
     */
    @PostMapping(value = "/isgrade")
    public boolean isgradeById(@RequestBody Map<String,String> map)
    {
        String account =map.get("account");
        String likeid =map.get("id");
        return isLikeByAccountAndPosting(account,likeid);
    }
    public   boolean isLikeByAccountAndPosting(String account, String postingId) {
        Query query = new Query(Criteria.where("account").is(account).and("postingId").is(postingId));
        if(mongoTemplate.findOne(query,Like.class)==null)
            return false;
        return true;
    }
}

