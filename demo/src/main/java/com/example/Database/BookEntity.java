package com.example.Database;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.print.Book;
import java.util.Date;
@Document(collection = "Book")
public class BookEntity{
    @Id
    private String id;
    private String bookName;
    private String author;
    private String publisher;
    private String chupin;
    private String subtitle;
    private String originalTitle;
    private Date publishingYear;
    private String isbn;
    private String price;
    private String translator;
    private String intro;
    private int like;
    private int dislike;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(this ==obj)
            return true;
        if(!(obj instanceof BookEntity))
            return false;
        BookEntity book=(BookEntity) obj;
        return  this.getId().equals(book.getId());
    }
    @Override
    public int hashCode()
    {
        return 1;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getChupin() {
        return chupin;
    }

    public void setChupin(String chupin) {
        this.chupin = chupin;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Date getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Date publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

}
