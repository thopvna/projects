package com.thopv.projects.libraryforreader.data.source.schemas;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
@Entity
public class SBook {
    @PrimaryKey(autoGenerate = true)
    private long bookId;
    private String title;
    private String briefContent;

    private long publishTime;
    private int bookCopyAmount;
    private int searchAmount;
    private long lastedImportTime;

    private long classificationId;
    private long publisherId;


    public SBook(long bookId, String title, String briefContent, long publishTime, int bookCopyAmount, int searchAmount, long lastedImportTime, long classificationId, long publisherId) {
        this.bookId = bookId;
        this.title = title;
        this.briefContent = briefContent;
        this.publishTime = publishTime;
        this.bookCopyAmount = bookCopyAmount;
        this.searchAmount = searchAmount;
        this.lastedImportTime = lastedImportTime;
        this.classificationId = classificationId;
        this.publisherId = publisherId;
    }
    @Ignore
    public SBook(Book book){
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.briefContent = book.getBriefContent();
        this.publishTime = book.getPublishTime();
        this.bookCopyAmount = book.getBookCopyAmount();
        this.searchAmount = book.getSearchAmount();
        this.lastedImportTime = book.getLastedImportTime();
        this.classificationId = book.getClassification().getClassificationId();
        this.publisherId = book.getPublisher().getPublisherId();
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getBookCopyAmount() {
        return bookCopyAmount;
    }

    public void setBookCopyAmount(int bookCopyAmount) {
        this.bookCopyAmount = bookCopyAmount;
    }

    public int getSearchAmount() {
        return searchAmount;
    }

    public void setSearchAmount(int searchAmount) {
        this.searchAmount = searchAmount;
    }

    public long getLastedImportTime() {
        return lastedImportTime;
    }

    public void setLastedImportTime(long lastedImportTime) {
        this.lastedImportTime = lastedImportTime;
    }

    public long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(long classificationId) {
        this.classificationId = classificationId;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }
}
