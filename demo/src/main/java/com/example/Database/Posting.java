package com.example.Database;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "Posting")
public class Posting {
    @Id
    private String id;
    private String title;
    private String account;
    private String content;
    private String teamId;
    private Date postTime;
    private boolean istop;
    private boolean isbest;

    public boolean isIstop() {
        return istop;
    }

    public void setIstop(boolean istop) {
        this.istop = istop;
    }

    public boolean isIsbest() {
        return isbest;
    }

    public void setIsbest(boolean isbest) {
        this.isbest = isbest;
    }

    public Posting() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @Override
    public String toString() {
        return this.id + " " +
                this.title + " " +
                this.account + " " +
                this.content + " " +
                this.postTime;
    }
}
