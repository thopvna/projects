package com.thopv.projects.libraryforreader.home.presentation.presenters;

import android.view.View;

import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;
import com.thopv.projects.libraryforreader.home.presentation.contracts.BaseBookContract;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

/**
 * Created by thopv on 12/3/2017.
 */

public class BaseBookPresenter implements BaseBookContract.Presenter {
    private BaseBookContract.View view;
    private UseCaseHandler useCaseHandler;
    private InsertFavoriteBook insertFavoriteBook;
    private InsertCartBook insertCartBook;
    private RemoveFavoriteBook removeFavoriteBook;
    private RemoveCartBook removeCartBook;
    private LoadBookOptionsMenuStatus loadBookOptionsMenuStatus;
    private FetchBookById fetchBookById;

    public BaseBookPresenter(BaseBookContract.View view, UseCaseHandler useCaseHandler, InsertFavoriteBook insertFavoriteBook, InsertCartBook insertCartBook, RemoveFavoriteBook removeFavoriteBook, RemoveCartBook removeCartBook, LoadBookOptionsMenuStatus loadBookOptionsMenuStatus, FetchBookById fetchBookById) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.insertFavoriteBook = insertFavoriteBook;
        this.insertCartBook = insertCartBook;
        this.removeFavoriteBook = removeFavoriteBook;
        this.removeCartBook = removeCartBook;
        this.loadBookOptionsMenuStatus = loadBookOptionsMenuStatus;
        this.fetchBookById = fetchBookById;
    }

    @Override
    public void addFavoriteBook(long bookId) {
        useCaseHandler.execute(insertFavoriteBook, new InsertFavoriteBook.RequestValues(bookId), response -> {
            if(response.isSuccess()){
                view.notifyFavoriteBookInserted(bookId);
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void addCartBook(long bookId) {
        useCaseHandler.execute(insertCartBook, new InsertCartBook.RequestValues(bookId), response -> {
            if(response.isSuccess()){
                view.notifyCartBookInserted(bookId);
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void removeFavoriteBook(long bookId) {
        useCaseHandler.execute(removeFavoriteBook, new RemoveFavoriteBook.RequestValues(bookId), response -> {
            if(response.isSuccess()){
                view.notifyFavoriteBookDeleted(bookId);
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void removeCartBook(long bookId) {
        useCaseHandler.execute(removeCartBook, new RemoveCartBook.RequestValues(bookId), response -> {
            if(response.isSuccess()){
                view.notifyCartBookDeleted(bookId);
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void loadOptionsMenuBook(long bookId, View anchor) {
        useCaseHandler.execute(loadBookOptionsMenuStatus, new LoadBookOptionsMenuStatus.RequestValues(bookId), response -> {
            if(response.isSuccess()){
                view.showOptionsMenu(response.getPayload().getBookStatus(), anchor);
            }
            else{
                view.showError("Operation failed." + response.getMessage());
            }
        });
    }

    @Override
    public void fetchBookById(long bookId) {
        useCaseHandler.execute(fetchBookById, new FetchBookById.RequestValues(bookId), response -> {
            if(response.isSuccess()){
                view.showFetchedBook(response.getPayload().getBook());
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }
}
