package com.example.Database;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Film")
public class FilmEntity {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;
    private String filmName;
    private String director;
    private String[] screenplay;
    private String[] starring;
    private String[] tags;
    private String country;
    private String[] language;
    private String year;
    private String filmTime;
    private String[] AKA;
    private String intro;

    private String posterImg;
    private int looked;
    private int like;
    private int dislike;
    private String score;

    public String getFilmName() {
        return filmName;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(this ==obj)
            return true;
        if(!(obj instanceof FilmEntity))
            return false;
        FilmEntity film=(FilmEntity) obj;
        return  this.getId().equals(film.getId());
    }
    @Override
    public int hashCode()
    {
        return 1;
    }
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getScreenplay() {
        return screenplay;
    }

    public void setScreenplay(String[] screenplay) {
        this.screenplay = screenplay;
    }

    public String[] getStarring() {
        return starring;
    }

    public void setStarring(String[] starring) {
        this.starring = starring;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String[] getLanguage() {
        return language;
    }

    public void setLanguage(String[] language) {
        this.language = language;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(String filmTime) {
        this.filmTime = filmTime;
    }

    public String[] getAKA() {
        return AKA;
    }

    public void setAKA(String[] AKA) {
        this.AKA = AKA;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public int getLooked() {
        return looked;
    }

    public void setLooked(int looked) {
        this.looked = looked;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
