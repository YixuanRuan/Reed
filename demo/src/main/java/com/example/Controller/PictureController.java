package com.example.Controller;

import com.example.Database.PosterPicture;
import com.example.Database.ProfilePicture;
import com.example.Database.RecyclePicEntity;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
@CrossOrigin(allowCredentials="true",maxAge = 3600)
@RestController
public class PictureController {

    @Value("${com.example.mongodb.collection.PosterPicture}")
    private String POSTER_PICTURE_COLLECTION_NAME;
    @Value("${com.example.mongodb.collection.RecyclePicEntity}")
    private String RECYCLE_PICTURE_COLLECTION_NAME;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取图片
     * @param id id是图片的id
     * @return
     */
    @GetMapping(value = "/PosterPicture/MovieAccount/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] FindPictureById(@PathVariable String id){
        byte[] data = null;
        Query query = new Query(Criteria.where("srcId").is(id));
        ProfilePicture file = mongoTemplate.findOne(query, ProfilePicture.class,POSTER_PICTURE_COLLECTION_NAME);
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }

    /**
     * 添加图片
     * @param file 图片参数设为"file"
     * @param id "id"是某个电影、帖子、书籍的id
     * @return
     */
    @PostMapping(value ="/img/add")
    @ResponseBody
    public String uploadImage(@RequestParam(value = "image") MultipartFile file, @RequestParam(value = "id") String id) {
        if(file.isEmpty())
            return "Empty";
        String fileName = file.getOriginalFilename();
        System.err.println(id);
        String picId = "";
        try {
            PosterPicture picture = new PosterPicture();
            picture.setSrcId(id);
            picture.setName(fileName);
            picture.setCreatedTime(new Date());
            picture.setContent(new Binary(file.getBytes()));
            picture.setContentType(file.getContentType());
            picture.setSize(file.getSize());
            mongoTemplate.save(picture);
            picId = picture.getId().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picId;
    }

    @PostMapping(value ="/img/filmimageadd")
    @ResponseBody
    public String filmImageadd(@RequestParam(value = "image") MultipartFile file) {
        if(file.isEmpty())
            return "Empty";
        String fileName = file.getOriginalFilename().split("\\.")[0];
        System.err.println(fileName);
        String picId = "";
        try {
            PosterPicture picture = new PosterPicture();
            picture.setSrcId(fileName);
            picture.setName(fileName);
            picture.setCreatedTime(new Date());
            picture.setContent(new Binary(file.getBytes()));
            picture.setContentType(file.getContentType());
            picture.setSize(file.getSize());
            mongoTemplate.save(picture);
            picId = picture.getId().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
    /**
     * 添加轮播图
     * @param file
     * @return
     */
    @PostMapping(value ="/img/addrecyclepic")
    @ResponseBody
    public String addRecyclePic(@RequestParam(value = "image") MultipartFile file)
    {
        if(file.isEmpty())
            return "Empty";
        String fileName = file.getOriginalFilename();
        String picId = "";
        try {
            RecyclePicEntity picture = new RecyclePicEntity();
            picture.setName(fileName);
            picture.setCreatedTime(new Date());
            picture.setContent(new Binary(file.getBytes()));
            picture.setContentType(file.getContentType());
            picture.setSize(file.getSize());
            mongoTemplate.save(picture);
            picId = picture.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picId;
    }

    /**
     * 删除pic
     * @param id 要删除的picid
     * @return
     */
    @PostMapping(value ="/img/deleterecyclepic")
    @ResponseBody
    public String deleteRecyclePic(@RequestParam(value = "id") String id)
    {
        Query query=new Query(Criteria.where("id").is(id));
        DeleteResult result=mongoTemplate.remove(query,RecyclePicEntity.class,RECYCLE_PICTURE_COLLECTION_NAME);
        return result.toString();
    }

    /**
     * 拉取所有轮播图
     * @return 所有轮播图
     */
    @GetMapping(value ="/img/recyclepiclist")
    @ResponseBody
    public List<RecyclePicEntity> pullRecyclePic()
    {
        return mongoTemplate.findAll(RecyclePicEntity.class,RECYCLE_PICTURE_COLLECTION_NAME);
    }
}
