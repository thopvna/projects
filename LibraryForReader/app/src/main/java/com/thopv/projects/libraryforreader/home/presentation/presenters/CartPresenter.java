package com.thopv.projects.libraryforreader.home.presentation.presenters;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.LoadCart;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.SendBorrowRequest;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;
import com.thopv.projects.libraryforreader.home.presentation.contracts.CartContract;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import junit.framework.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/3/2017.
 */

public class CartPresenter extends BaseBookPresenter implements CartContract.Presenter {
    private CartContract.View view;
    private UseCaseHandler useCaseHandler;
    private LoadCart loadCart;
    private SendBorrowRequest sendBorrowRequest;

    public CartPresenter(CartContract.View view, InsertFavoriteBook insertFavoriteBook, InsertCartBook insertCartBook, RemoveFavoriteBook removeFavoriteBook, RemoveCartBook removeCartBook, LoadBookOptionsMenuStatus loadBookOptionsMenuStatus, UseCaseHandler useCaseHandler, LoadCart loadCart, SendBorrowRequest sendBorrowRequest, FetchBookById fetchBookById) {
        super(view, useCaseHandler, insertFavoriteBook, insertCartBook, removeFavoriteBook, removeCartBook, loadBookOptionsMenuStatus, fetchBookById);
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.loadCart = loadCart;
        this.sendBorrowRequest = sendBorrowRequest;
    }

    @Override
    public void loadCartBooks() {
        useCaseHandler.execute(loadCart, new LoadCart.RequestValues(), response -> {
            if (response.isSuccess()) {
                if(response.getPayload() == null || response.getPayload().getBooks() == null || response.getPayload().getBooks().size() == 0){
                    view.showNoCartBooks();
                    view.showError("Don't have any books in Cart.");
                    view.disableSendBorrowRequestButton();
                }
                else {
                    List<Book> books = response.getPayload().getBooks();
                    view.hideNoCartBooks();
                    view.showCartBooks(books);
                    view.enableSendBorrowRequestButton();
                }
            } else {
                view.showError("Operation failed. " + response.getMessage());
                view.showNoCartBooks();
                view.disableSendBorrowRequestButton();
            }
        });
    }

    @Override
    public void sendBorrowRequest() {
        useCaseHandler.execute(sendBorrowRequest, new SendBorrowRequest.RequestValues(), response -> {
            if (response.isSuccess()) {
                view.clearCart();
                view.showNoCartBooks();
                view.showSendBorrowRequestSuccess();
                view.disableSendBorrowRequestButton();
            } else {
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }
}
