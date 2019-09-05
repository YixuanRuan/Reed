package com.example.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Database.BookEntity;
import com.example.Database.FilmEntity;
import com.example.Database.Posting;
import com.example.Database.ViewHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.StaticFunc.Static.GetCurrentTime;

@RestController
public class ViewHistoryController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${com.example.mongodb.collection.ViewHistory}")
    private String VIEW_HISTORY_COLLECTION_NAME;

    @PostMapping(value = "/ViewHistory/AddMovie")
    public JSONObject AddMovie(@RequestBody Map<String,Object> map) {
        JSONObject jsonObject = new JSONObject();
        String id = map.get("id").toString();
        String account = map.get("account").toString();

        Query query = new Query(Criteria.where("id").is(id));
        FilmEntity filmEntity = mongoTemplate.findOne(query,FilmEntity.class,"Film");

        if (IsViewed(id)) {
            UpdateViewTime(id);
        } else {
            ViewHistory viewHistory = new ViewHistory();
            viewHistory.setType(2);
            viewHistory.setViewTime(GetCurrentTime());
            viewHistory.setViewId(id);
            viewHistory.setAccount(account);
            mongoTemplate.save(viewHistory);
        }

        jsonObject.put("id",id);
        jsonObject.put("filmname",filmEntity.getFilmName());

        return jsonObject;
    }

    @PostMapping(value = "/ViewHistory/AddBook")
    public JSONObject AddBook(@RequestBody Map<String,Object> map) {
        JSONObject jsonObject = new JSONObject();
        String id = map.get("id").toString();
        String account = map.get("account").toString();

        Query query = new Query(Criteria.where("id").is(id));
        BookEntity bookEntity = mongoTemplate.findOne(query,BookEntity.class,"Book");

        if (IsViewed(id)) {
            UpdateViewTime(id);
        } else {
            ViewHistory viewHistory = new ViewHistory();
            viewHistory.setType(1);
            viewHistory.setViewTime(GetCurrentTime());
            viewHistory.setViewId(id);
            viewHistory.setAccount(account);
            mongoTemplate.save(viewHistory);
        }

        jsonObject.put("id",id);
        jsonObject.put("bookname",bookEntity.getBookName());

        return jsonObject;
    }

    @PostMapping(value = "/ViewHistory/AddPosting")
    public JSONObject AddPosting(@RequestBody Map<String,Object> map) {
        JSONObject jsonObject = new JSONObject();
        String id = map.get("id").toString();
        String account = map.get("account").toString();

        Query query = new Query(Criteria.where("id").is(id));
        Posting posting = mongoTemplate.findOne(query,Posting.class,"Posting");

        if (IsViewed(id)) {
            UpdateViewTime(id);
        } else {
            ViewHistory viewHistory = new ViewHistory();
            viewHistory.setType(4);
            viewHistory.setViewTime(GetCurrentTime());
            viewHistory.setViewId(id);
            viewHistory.setAccount(account);
            mongoTemplate.save(viewHistory);
        }

        jsonObject.put("id",id);
        jsonObject.put("postingtitle",posting.getTitle());

        return jsonObject;
    }

    @PostMapping(value = "/ViewHistory/DelAll")
    public void DelAll() {
        mongoTemplate.remove(new Query(),ViewHistory.class,VIEW_HISTORY_COLLECTION_NAME);
    }

    @PostMapping(value = "/ViewHistory/Get")
    public JSONArray GetViewHistory(@RequestBody Map<String,Object> map) {
        String account = map.get("account").toString();
        Sort sort = new Sort(Sort.Direction.DESC, "viewTime");
        Query queryMovie = new Query(Criteria.where("account").is(account).and("type").is(2)); //movie
        Query queryBook = new Query(Criteria.where("account").is(account).and("type").is(1)); //Book
        Query queryPosting = new Query(Criteria.where("account").is(account).and("type").is(4)); //Posting

        List<ViewHistory> viewHistoryListMovie = mongoTemplate.find(queryMovie.with(sort).limit(1000), ViewHistory.class);
        List<ViewHistory> viewHistoryListBook = mongoTemplate.find(queryBook.with(sort).limit(1000), ViewHistory.class);
        List<ViewHistory> viewHistoryListPosting = mongoTemplate.find(queryPosting.with(sort).limit(1000), ViewHistory.class);

        JSONArray jsonArrayTotal = new JSONArray();

        List<JSONObject> jsonArrayMovie = new ArrayList<JSONObject>();
        List<JSONObject> jsonArrayBook = new ArrayList<JSONObject>();
        List<JSONObject> jsonArrayPosting = new ArrayList<JSONObject>();

        if (viewHistoryListMovie !=null) {
            for (int i = 0; i < viewHistoryListMovie.size(); ++i) {
                String id = viewHistoryListMovie.get(i).getViewId();
                Query query = new Query(Criteria.where("id").is(id));
                String filmName = mongoTemplate.findOne(query, FilmEntity.class, "Film").getFilmName();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("filmname", filmName);
                jsonObject.put("id", id);
                jsonArrayMovie.add(jsonObject);
            }
        }


        if (viewHistoryListBook != null) {
            for (int i = 0; i < viewHistoryListBook.size(); ++i) {
                String id = viewHistoryListBook.get(i).getViewId();
                Query query = new Query(Criteria.where("id").is(id));
                String bookName = mongoTemplate.findOne(query, BookEntity.class, "Book").getBookName();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bookname", bookName);
                jsonObject.put("id", id);
                jsonArrayBook.add(jsonObject);
            }
        }

        if (viewHistoryListPosting != null) {
            for (int i = 0; i < viewHistoryListPosting.size(); ++i) {
                String id = viewHistoryListPosting.get(i).getViewId();
                Query query = new Query(Criteria.where("id").is(id));
                String titleName = mongoTemplate.findOne(query, Posting.class, "Posting").getTitle();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("postingtitle", titleName);
                jsonObject.put("id", id);
                jsonArrayPosting.add(jsonObject);
            }
        }
        jsonArrayTotal.add(jsonArrayMovie);
        jsonArrayTotal.add(jsonArrayBook);
        jsonArrayTotal.add(jsonArrayPosting);
        return jsonArrayTotal;
    }

    public boolean IsViewed(String id) {
        Query query = new Query(Criteria.where("viewId").is(id));
        ViewHistory viewHistory = mongoTemplate.findOne(query,ViewHistory.class,VIEW_HISTORY_COLLECTION_NAME);
        return viewHistory != null;
    }

    public ViewHistory UpdateViewTime(String id) {
        Query query = new Query(Criteria.where("viewId").is(id));
        ViewHistory viewHistory = mongoTemplate.findOne(query,ViewHistory.class,VIEW_HISTORY_COLLECTION_NAME);
        viewHistory.setViewTime(GetCurrentTime());
        mongoTemplate.save(viewHistory);
        return viewHistory;
    }
}
