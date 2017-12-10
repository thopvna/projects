package com.thopv.projects.libraryforreader.home.presentation.presenters;

import com.thopv.projects.libraryforreader.TestUtils;
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
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by thopv on 12/4/2017.
 */
public class CartPresenterTest {

    private CartPresenter cartPresenter;
    private UseCaseHandler useCaseHandler;
    private CartContract.View view;

    @Before
    public void setUp() throws Exception {
        view = mock(CartContract.View.class);
        InsertFavoriteBook insertFavoriteBook = mock(InsertFavoriteBook.class);
        InsertCartBook insertCartBook = mock(InsertCartBook.class);
        RemoveFavoriteBook removeFavoriteBook= mock(RemoveFavoriteBook.class);
        RemoveCartBook removeCartBook = mock(RemoveCartBook.class);
        LoadBookOptionsMenuStatus loadBookOptionsMenuStatus = mock(LoadBookOptionsMenuStatus.class);
        useCaseHandler = mock(UseCaseHandler.class);
        LoadCart loadCart = mock(LoadCart.class);
        SendBorrowRequest sendBorrowRequest = mock(SendBorrowRequest.class);
        FetchBookById fetchBookById = mock(FetchBookById.class);
        cartPresenter = new CartPresenter(view, insertFavoriteBook, insertCartBook, removeFavoriteBook, removeCartBook, loadBookOptionsMenuStatus, useCaseHandler, loadCart, sendBorrowRequest, fetchBookById);
    }

    @Test
    public void loadCartBooksButEmpty(){
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(LoadCart.CART_EMPTY_ERR));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        cartPresenter.loadCartBooks();
        verify(view).showError(contains(LoadCart.CART_EMPTY_ERR));
        verify(view).disableSendBorrowRequestButton();
        verify(view).showNoCartBooks();
    }
    @Test
    public void loadCartBooksNonEmptyButDontHaveAnyBook(){
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        cartPresenter.loadCartBooks();

        verify(view).showError(any());
        verify(view).showNoCartBooks();
        verify(view).disableSendBorrowRequestButton();
    }
    @Test
    public void loadCartBooksNonEmptyAndHaveBooks(){
        List<Book> mBOoks = TestUtils.genBooks();
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);

        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadCart.ResponseValues(mBOoks)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        cartPresenter.loadCartBooks();

        verify(view, never()).showError(any());
        verify(view, never()).showNoCartBooks();
        verify(view, never()).disableSendBorrowRequestButton();


        verify(view).hideNoCartBooks();
        verify(view).enableSendBorrowRequestButton();
        verify(view).showCartBooks(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue())
                .isNotNull()
                .isNotEmpty()
                .hasSize(mBOoks.size());
    }
    @Test
    public void sendErrorBorrowRequestThenGetErr(){
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(SendBorrowRequest.CART_EMPTY_ERR));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        cartPresenter.sendBorrowRequest();
        verify(view).showError(contains(SendBorrowRequest.CART_EMPTY_ERR));
    }
    @Test
    public void sendSuccessBorrowRequest(){
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        cartPresenter.sendBorrowRequest();

        verify(view).disableSendBorrowRequestButton();
        verify(view).showSendBorrowRequestSuccess();
        verify(view).clearCart();
        verify(view).showNoCartBooks();
    }
}