package com.thopv.projects.libraryforreader.data.source.schemas;

import android.arch.persistence.room.Entity;

/**
 * Created by thopv on 12/2/2017.
 */
@Entity(primaryKeys = {"bookId", "authorId"})
public class SBookAuthorDetail {
    private long bookId;
    private long authorId;

    public SBookAuthorDetail(long bookId, long authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
