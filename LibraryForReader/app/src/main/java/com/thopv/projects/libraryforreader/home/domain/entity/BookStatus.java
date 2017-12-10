package com.thopv.projects.libraryforreader.home.domain.entity;

/**
 * Created by thopv on 12/3/2017.
 */

public class BookStatus {
    private long bookId;
    private boolean inCart;
    private boolean inFavorite;

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }


    public void setInFavorite(boolean inFavorite) {
        this.inFavorite = inFavorite;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public BookStatus(long bookId) {
        this.bookId = bookId;
    }
    public BookStatus(long bookId, boolean inCart, boolean inFavorite) {
        this.bookId = bookId;
        this.inCart = inCart;
        this.inFavorite = inFavorite;
    }

    public boolean isInCart() {
        return inCart;
    }

    public boolean isInFavorite() {
        return inFavorite;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BookStatus){
            return getBookId() == ((BookStatus) obj).getBookId();
        }
        return super.equals(obj);
    }
}
