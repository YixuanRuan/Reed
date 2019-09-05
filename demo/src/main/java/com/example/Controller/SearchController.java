package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Pattern;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController

public class SearchController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Value("${com.example.mongodb.collection.Book}")
    private String BOOK_COLLECTION_NAME;
    @Value("${com.example.mongodb.collection.Team}")
    private String TEAM_COLLECTION_NAME;
    @Value("${com.example.mongodb.collection.Film}")
    private String FILM_COLLECTION_NAME;

    /**
     * 模糊查找
     * @param map 以"name"为名的要查找的关键字
     * @return 返回和关键字匹配的书籍、影视、还有小组
     */
    @PostMapping(value = "/search/find")
    public Map<String,List<?>> List(@RequestBody Map<String,Object> map) {
        String name = map.get("name").toString();
        Pattern pattern = Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
        Query bookquery = new Query(Criteria.where("bookName").regex(pattern));
        Query filmquery = new Query(Criteria.where("filmName").regex(pattern));
        Query teamquery = new Query(Criteria.where("teamName").regex(pattern));
        List<BookEntity> bookList = mongoTemplate.find(bookquery, BookEntity.class,BOOK_COLLECTION_NAME);
        List<FilmEntity> filmList = mongoTemplate.find(filmquery, FilmEntity.class,FILM_COLLECTION_NAME);
        List<Team> teamList = mongoTemplate.find(teamquery, Team.class,TEAM_COLLECTION_NAME);
        HashMap<String,List<?>> retmap= new HashMap<String,List<?>>();
        retmap.put("book",bookList);
        retmap.put("film",filmList);
        retmap.put("team",teamList);
        return retmap;
    }


    /**
     * 某个电影、书籍、帖子点赞数最高的评论
     * @param map "id"->前端给出电影、书籍、帖子的id
     * @return
     */
    @PostMapping(value = "/search/likebestReply")
    public Object filmsLikeBest(@RequestBody Map<String,Object> map)
    {

        Aggregation agg = Aggregation.newAggregation(

                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("postingId", "account", "likes"),
                // 第二步：sql where 语句筛选符合条件的记录
                   Aggregation.match(Criteria.where("type").is(3)),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("postingId").count().as("likes"),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC,"likes"),
                // 第五步：数量(分页)
                // Aggregation.limit(Integer.parseInt(map.get("pagesCount").toString())),
                // 第刘步：重新挑选字段
                Aggregation.project("postingId","likes")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "Like", JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        for(JSONObject it:mappedResults)
        {
            String ReplyId=it.get("_id").toString();
            String Id=map.get("id").toString();
            Query query=new Query(Criteria.where("id").is(ReplyId));
            ReplyEntity reply=mongoTemplate.findOne(query, ReplyEntity.class,"Reply");
            it.put("reply",reply);
            if(reply.getPlaceId().equals(Id)) {
                it.put("reply", reply);
                return it;
            }
        }
        Query query=new Query(Criteria.where("placeId").is(map.get("id").toString()));
        JSONObject json=new JSONObject();
        ReplyEntity replyEntity=mongoTemplate.findOne(query,ReplyEntity.class,"Reply");
        json.put("reply",replyEntity);
        json.put("likes","0");
        return json;
    }
    /**
     * 按点赞数排序 (电影）
     * @param map 前端给pagesCount，展示的个数
     * @return
     */
    @PostMapping(value = "/search/filmlikelist")
    public List<?>  filmsortByLike(@RequestBody Map<String,Object> map)
    {

        Aggregation agg = Aggregation.newAggregation(

                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("postingId", "account", "likes"),
                // 第二步：sql where 语句筛选符合条件的记录
          //  Aggregation.match(Criteria.where("userId").is(map.get("userId"))),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("postingId").count().as("likes"),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC,"likes"),
                // 第五步：数量(分页)
                Aggregation.limit(Integer.parseInt(map.get("pagesCount").toString())),
                // 第刘步：重新挑选字段
                Aggregation.project("postingId","likes")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "Like", JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        Iterator<JSONObject> it =mappedResults.iterator();
        ArrayList<FilmEntity> list=new ArrayList<>();
        while(it.hasNext())
        {
            JSONObject js=it.next();
            Query query=new Query(Criteria.where("id").is(js.get("_id").toString()));
            FilmEntity film=mongoTemplate.findOne(query,FilmEntity.class,"Film");
            if(film!=null)
                list.add(film);
        }
        List<FilmEntity> all=mongoTemplate.findAll(FilmEntity.class);
        for(FilmEntity its :all)
        {
            if(!list.contains(its))
                list.add(its);
        }
        if(list.size()==0)
            return mongoTemplate.findAll(FilmEntity.class);
        return list;
    }

    /**
     * 按点赞数排序 (书籍）
     * @param map 前端给pagesCount，展示的个数
     * @return
     */
    @PostMapping(value = "/search/booklikelist")
    public List<?>  booksortByLike(@RequestBody Map<String,Object> map)
    {

        Aggregation agg = Aggregation.newAggregation(

                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("postingId", "account", "likes"),
                // 第二步：sql where 语句筛选符合条件的记录
                //  Aggregation.match(Criteria.where("userId").is(map.get("userId"))),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("postingId").count().as("likes"),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC,"likes"),
                // 第五步：数量(分页)
                Aggregation.limit(Integer.parseInt(map.get("pagesCount").toString())),
                // 第刘步：重新挑选字段
                Aggregation.project("postingId","likes")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "Like", JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        List<BookEntity> list=new ArrayList<>();
        for(JSONObject it:mappedResults)
        {
            Query query=new Query(Criteria.where("id").is(it.get("_id").toString()));

            BookEntity book=mongoTemplate.findOne(query,BookEntity.class,"Book");
            list.add(book);
        }
        List<BookEntity> all=mongoTemplate.findAll(BookEntity.class);
        for(BookEntity it :all)
        {
            if(!list.contains(it))
                list.add(it);
        }
        if(mappedResults.size()==0)
            return mongoTemplate.findAll(BookEntity.class);
        return list;
    }
    /**
     * 按评论数排序 (书籍）
     * @param map 前端给pagesCount，展示的个数
     * @return
     */

    @PostMapping(value = "/search/bookreplylist")
    public List<?>  booksortByReply(@RequestBody Map<String,Object> map)
    {
        Aggregation agg = Aggregation.newAggregation(

                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("placeId", "replyerId", "replies"),
                // 第二步：sql where 语句筛选符合条件的记录
                  Aggregation.match(Criteria.where("type").is(1)),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("placeId").count().as("replies"),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC,"replies"),
                // 第五步：数量(分页)
                Aggregation.limit(Integer.parseInt(map.get("pagesCount").toString())),
                // 第刘步：重新挑选字段
                Aggregation.project("placeId","replies")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "Reply", JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        List<BookEntity> list =new ArrayList<BookEntity>();
        for(JSONObject it:mappedResults)
        {
            Query query=new Query(Criteria.where("id").is(it.get("_id").toString()));
            BookEntity book=mongoTemplate.findOne(query,BookEntity.class,"BookE");
            list.add(book);
        }
        List<BookEntity> all=mongoTemplate.findAll(BookEntity.class);
        for(BookEntity it :all)
        {
            if(!list.contains(it))
                list.add(it);
        }
        if(mappedResults.size()==0)
        {
            return all;
        }
        return list;
    }
    /**
     * 按评论数排序 (电影）
     * @param map 前端给pagesCount，展示的个数
     * @return
     */
    @PostMapping(value = "/search/filmreplylist")
    public List<?>  filmsortByReply(@RequestBody Map<String,Object> map)
    {
        Aggregation agg = Aggregation.newAggregation(

                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("placeId", "replyerId", "replies"),
                // 第二步：sql where 语句筛选符合条件的记录
                Aggregation.match(Criteria.where("type").is(2)),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("placeId").count().as("replies"),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC,"replies"),
                // 第五步：数量(分页)
                Aggregation.limit(Integer.parseInt(map.get("pagesCount").toString())),
                // 第刘步：重新挑选字段
                Aggregation.project("placeId","replies")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "Reply", JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        List<FilmEntity> list =new ArrayList<FilmEntity>();
        for(JSONObject it:mappedResults)
        {
            Query query=new Query(Criteria.where("id").is(it.get("_id").toString()));
            FilmEntity film=mongoTemplate.findOne(query,FilmEntity.class,"Film");
            list.add(film);
        }
        List<FilmEntity> all=mongoTemplate.findAll(FilmEntity.class);
        for(FilmEntity it :all)
        {
            if(!list.contains(it))
                list.add(it);
        }
        if(mappedResults.size()==0)
        {
            return all;
        }
        return list;
    }
    /**
     * 按时间排序 (书籍）
     * @param map，前端给pagesCount，展示的个数
     * @return
     */
    @PostMapping(value = "/search/booktimelist")
    public List<?>  booksortByTime(@RequestBody Map<String,Object> map)
    {

            Query query=new Query();
            query.limit(Integer.parseInt(map.get("pagesCount").toString()));
            query.with(new Sort(Sort.Direction.DESC,"publishingYear"));
            List<BookEntity> book =mongoTemplate.find(query,BookEntity.class,"Book");
            return book;
    }
    /**
     * 按时间排序 (电影）
     * @param map 前端给pagesCount，展示的个数
     * @return
     */
    @PostMapping(value = "/search/filmtimelist")
    public List<?>  filmsortByTime(@RequestBody Map<String,Object> map)
    {

        Query query=new Query();
        query.limit(Integer.parseInt(map.get("pagesCount").toString()));
        query.with(new Sort(Sort.Direction.DESC,"year"));
        List<FilmEntity> film =mongoTemplate.find(query,FilmEntity.class,"Film");
        return film;
    }

    /**
     * 点赞最多的电影（图书）评论列表
     * @param map 前端给pagesCount，展示的个数
     * @return 按点赞数降序的电影评论列表,同时带filmid
     */
    @PostMapping(value = "/search/likestfilmreplylist")
    public List<?>  likestFilmReplyList(@RequestBody Map<String,Object> map)
    {

        Aggregation agg = Aggregation.newAggregation(

                // 第一步：挑选所需的字段，类似select *，*所代表的字段内容
                Aggregation.project("postingId", "account", "likes"),
                // 第二步：sql where 语句筛选符合条件的记录
                Aggregation.match(Criteria.where("type").is(2)),
                // 第三步：分组条件，设置分组字段
                Aggregation.group("postingId").count().as("likes"),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC,"likes"),
                // 第五步：数量(分页)
                Aggregation.limit(Integer.parseInt(map.get("pagesCount").toString())),
                // 第刘步：重新挑选字段
                Aggregation.project("postingId","likes")

        );

        AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "Like", JSONObject.class);
        List<JSONObject> mappedResults = results.getMappedResults();
        for(JSONObject it:mappedResults)
        {
            Query query=new Query(Criteria.where("id").is(it.get("_id").toString()));
            ReplyEntity reply=mongoTemplate.findOne(query,ReplyEntity.class,"Reply");
            it.put("reply",reply);
            it.put("filmid",reply.getPlaceId());
        }
        return mappedResults;
    }
}