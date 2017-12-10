package com.thopv.projects.libraryforreader.data.source.schemas;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thopv on 12/2/2017.
 */
@Entity
public class SPublisher {
    @PrimaryKey(autoGenerate = true)
    private long publisherId;
    private String publisherName;
    private String address;

    public SPublisher(long publisherId, String publisherName, String address) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.address = address;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
