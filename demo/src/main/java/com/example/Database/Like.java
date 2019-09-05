package com.example.Database;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "Like")
public class Like {
    @Id
    private String id;
    private String account;
    private String postingId;
    private Date likeTime;
    private int type; //类型，书籍=1，影音=2，音乐=3，帖子=4,回复是5

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Like() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPostingId() {
        return postingId;
    }

    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    @Override
    public String toString() {
        return this.id + " " +
                this.account + " " +
                this.postingId + " " +
                this.likeTime;
    }
}