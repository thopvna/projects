package com.thopv.projects.libraryforreader.home.presentation.contracts;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;

/**
 * Created by thopv on 12/3/2017.
 */

public interface BaseBookContract {
    interface View{
        void showMessage(String message);
        void showError(String error);
        void showOptionsMenu(BookStatus bookStatus, android.view.View view);
        void notifyFavoriteBookInserted(long bookId);
        void notifyFavoriteBookDeleted(long bookId);
        void notifyCartBookInserted(long bookId);
        void notifyCartBookDeleted(long bookId);
        void showFetchedBook(Book book);
    }

    interface Presenter{
        void addFavoriteBook(long bookId);
        void addCartBook(long bookId);
        void removeFavoriteBook(long bookId);
        void removeCartBook(long bookId);
        void loadOptionsMenuBook(long bookId, android.view.View view);
        void fetchBookById(long bookId);
    }
}
