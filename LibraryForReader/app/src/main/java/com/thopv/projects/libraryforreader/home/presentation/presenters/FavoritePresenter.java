package com.thopv.projects.libraryforreader.home.presentation.presenters;

import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.LoadFavorites;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;
import com.thopv.projects.libraryforreader.home.presentation.contracts.FavoriteContract;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

/**
 * Created by thopv on 12/3/2017.
 */

public class FavoritePresenter extends BaseBookPresenter implements FavoriteContract.Presenter {
    private FavoriteContract.View view;
    private UseCaseHandler useCaseHandler;
    private LoadFavorites loadFavorites;

    public FavoritePresenter(FavoriteContract.View view, UseCaseHandler useCaseHandler, InsertCartBook insertCartBook, RemoveFavoriteBook removeFavoriteBook, RemoveCartBook removeCartBook, LoadBookOptionsMenuStatus loadBookOptionsMenuStatus, InsertFavoriteBook insertFavoriteBook, LoadFavorites loadFavorites, FetchBookById fetchBookById) {
        super(view, useCaseHandler, insertFavoriteBook, insertCartBook, removeFavoriteBook, removeCartBook, loadBookOptionsMenuStatus, fetchBookById);
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.loadFavorites = loadFavorites;
    }

    @Override
    public void loadFavoriteBooks() {
        useCaseHandler.execute(loadFavorites, new LoadFavorites.RequestValues(), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null || response.getPayload().getBooks() == null || response.getPayload().getBooks().size() <= 0){
                    view.showError("Don't have any books in Favorite.");
                    view.showNoFavoriteBooks();
                }
                else {
                    view.hideNoFavoriteBooks();
                    view.showFavoriteBooks(response.getPayload().getBooks());
                }
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
                view.showNoFavoriteBooks();
            }
        });
    }

}
