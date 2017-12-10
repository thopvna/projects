package com.thopv.projects.libraryforreader.home.presentation.contracts;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;

import java.util.List;

/**
 * Created by thopv on 12/3/2017.
 */

public interface FavoriteContract {
    interface View extends BaseBookContract.View{
        void showFavoriteBooks(List<Book> books);
        void showNoFavoriteBooks();
        void hideNoFavoriteBooks();
    }
    interface Presenter extends BaseBookContract.Presenter{
        void loadFavoriteBooks();
    }
}
