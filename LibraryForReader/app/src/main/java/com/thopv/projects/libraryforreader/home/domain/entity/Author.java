package com.thopv.projects.libraryforreader.home.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thopv on 12/2/2017.
 */
public class Author {
    private long authorId;
    private String authorName;
    private long birthDay;
    private long deathDay;
    private String address;

    public Author(long authorId, String authorName, long birthDay, long deathDay, String address) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.birthDay = birthDay;
        this.deathDay = deathDay;
        this.address = address;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public long getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(long deathDay) {
        this.deathDay = deathDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }
}
