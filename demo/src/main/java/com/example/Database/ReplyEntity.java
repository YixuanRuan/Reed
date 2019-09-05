package com.example.Database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(collection = ("Reply"))
public class ReplyEntity {
    @Id
    private String id;
    private String replyerId; //发布人的objectId
    private String content;
    private String title;
    private Date date;
    private int type; //类型，书籍=1，影音=2，音乐=3，帖子=4
    private String placeId;  //发布的Id，可以是书籍，影音 ，帖子，音乐

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReplyerId() {
        return replyerId;
    }

    public void setReplyerId(String replyerId) {
        this.replyerId = replyerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
