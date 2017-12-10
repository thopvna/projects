package com.thopv.projects.libraryforreader.home.presentation.contracts;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;

import java.util.List;

/**
 * Created by thopv on 12/3/2017.
 */

public interface BookContract {
    interface View extends BaseBookContract.View{
        void showMostSearchBooks(List<Book> books);
        void showNewestImportBooks(List<Book> books);
        void showSearchResult(List<Book> books, String keyword);
        void showEmptySearchResult();
        void showProgressDialog();
        void hideProgressDialog();
        void showMostSearchProgressBar();
        void showNewestImportProgressBar();
        void hideMostSearchProgressBar();
        void hideNewestImportProgressBar();
    }
    interface Presenter extends BaseBookContract.Presenter {
        void loadMostSearchBooks();
        void loadNewestImportBooks();
        void searchBook(String keyword);
    }

}
