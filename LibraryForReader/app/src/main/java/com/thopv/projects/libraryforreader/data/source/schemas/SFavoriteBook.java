package com.thopv.projects.libraryforreader.data.source.schemas;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thopv on 12/2/2017.
 */
@Entity
public class SFavoriteBook {
    @PrimaryKey
    private long bookId;

    public SFavoriteBook(long bookId) {
        this.bookId = bookId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
