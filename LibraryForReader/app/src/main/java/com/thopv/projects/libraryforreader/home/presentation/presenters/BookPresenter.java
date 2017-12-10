package com.thopv.projects.libraryforreader.home.presentation.presenters;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBooks;
import com.thopv.projects.libraryforreader.home.domain.usecases.SearchBooks;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;
import com.thopv.projects.libraryforreader.home.presentation.contracts.BookContract;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import java.util.Collections;
import java.util.List;

/**
 * Created by thopv on 12/3/2017.
 */

public class BookPresenter extends BaseBookPresenter implements BookContract.Presenter {
    public static final String KEYWORD_INVALID = "Keyword invalid.";
    private BookContract.View view;
    private UseCaseHandler useCaseHandler;
    private LoadBooks loadBooks;
    private SearchBooks searchBooks;

    public BookPresenter(BookContract.View view, UseCaseHandler useCaseHandler, SearchBooks searchBooks, InsertFavoriteBook insertFavoriteBook, InsertCartBook insertCartBook, RemoveFavoriteBook removeFavoriteBook, RemoveCartBook removeCartBook, LoadBookOptionsMenuStatus loadBookOptionsMenuStatus, LoadBooks loadBooks, FetchBookById fetchBookById) {
        super(view, useCaseHandler, insertFavoriteBook, insertCartBook, removeFavoriteBook, removeCartBook , loadBookOptionsMenuStatus, fetchBookById);
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.loadBooks = loadBooks;
        this.searchBooks = searchBooks;
    }

    @Override
    public void loadMostSearchBooks() {
        useCaseHandler.execute(loadBooks, new LoadBooks.RequestValues(), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null || response.getPayload().getBooks() == null || response.getPayload().getBooks().size() <= 0){
                    view.showError("Don't have any books.");
                }
                else {
                    List<Book> books = response.getPayload().getBooks();
                    sortBookBySearchAmount(books);
                    view.showMostSearchBooks(books);
                    view.hideMostSearchProgressBar();
                }
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    private void sortBookBySearchAmount(List<Book> books) {
        Collections.sort(books, (book1, book2) -> {
            if(book1.getSearchAmount() > book2.getSearchAmount())
                return 1;
            else if(book1.getSearchAmount() == book2.getSearchAmount())
                return 0;
            return -1;
        });
    }

    @Override
    public void loadNewestImportBooks() {
        useCaseHandler.execute(loadBooks, new LoadBooks.RequestValues(), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null || response.getPayload().getBooks() == null || response.getPayload().getBooks().size() <= 0){
                    view.showError("Don't have any books.");
                }
                else {
                    List<Book> books = response.getPayload().getBooks();
                    sortBOokByLastedImportTime(books);
                    view.showNewestImportBooks(books);
                    view.hideNewestImportProgressBar();
                }
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    private void sortBOokByLastedImportTime(List<Book> books) {
        Collections.sort(books, (book1, book2) -> {
            if(book1.getLastedImportTime() > book2.getLastedImportTime())
                return 1;
            else if(book1.getLastedImportTime() == book2.getLastedImportTime())
                return 0;
            return -1;
        });
    }

    @Override
    public void searchBook(String keyword) {
        if(keyword.isEmpty()){
            view.showError(KEYWORD_INVALID);
        }
        else{
            view.showProgressDialog();
            useCaseHandler.execute(searchBooks, new SearchBooks.RequestValues(keyword), response -> {
                view.hideProgressDialog();
                if(response.isSuccess()){
                    view.showSearchResult(response.getPayload().getBooks(), keyword);
                }
                else{
                    view.showError("Operation failed. " + response.getMessage());
                    view.showEmptySearchResult();
                }
            });
        }
    }
}
