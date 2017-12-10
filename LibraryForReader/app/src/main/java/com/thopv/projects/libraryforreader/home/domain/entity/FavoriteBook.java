package com.thopv.projects.libraryforreader.home.domain.entity;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class FavoriteBook {
    private long bookId;

    public FavoriteBook(long bookId) {
        this.bookId = bookId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FavoriteBook){
            return ((FavoriteBook) obj).getBookId() == getBookId();
        }
        return super.equals(obj);
    }
}
