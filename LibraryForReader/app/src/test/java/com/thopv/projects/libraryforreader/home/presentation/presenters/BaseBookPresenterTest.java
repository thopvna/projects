package com.thopv.projects.libraryforreader.home.presentation.presenters;

import android.view.View;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;
import com.thopv.projects.libraryforreader.home.presentation.contracts.BaseBookContract;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Created by thopv on 12/3/2017.
 */
public class BaseBookPresenterTest {
    BaseBookPresenter baseBookPresenter;
    BaseBookContract.View view;
    UseCaseHandler useCaseHandler;
    InsertFavoriteBook insertFavoriteBook;
    InsertCartBook insertCartBook;
    RemoveFavoriteBook removeFavoriteBook;
    RemoveCartBook removeCartBook;
    LoadBookOptionsMenuStatus loadBookOptionsMenuStatus;
    FetchBookById fetchBookById;
    @Before
    public void setUp() throws Exception {
        view = mock(BaseBookContract.View.class);
        useCaseHandler = mock(UseCaseHandler.class);
        insertFavoriteBook = mock(InsertFavoriteBook.class);
        insertCartBook = mock(InsertCartBook.class);
        removeFavoriteBook = mock(RemoveFavoriteBook.class);
        removeCartBook = mock(RemoveCartBook.class);
        fetchBookById = mock(FetchBookById.class);
        loadBookOptionsMenuStatus = mock(LoadBookOptionsMenuStatus.class);
        baseBookPresenter = new BaseBookPresenter(view, useCaseHandler, insertFavoriteBook, insertCartBook, removeFavoriteBook, removeCartBook, loadBookOptionsMenuStatus, fetchBookById);
    }

    @Test
    public void showErrWhenInsertCartBookFailed(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail("Whatever"));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.addCartBook(anyLong);

        verify(view).showError(any());
    }
    @Test
    public void notifyChangeWhenInsertCartBookSuccess(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.addCartBook(anyLong);

        verify(view).notifyCartBookInserted(anyLong);
    }

    @Test
    public void showErrorWhenDeleteCartFailed(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail("Whatever"));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.removeCartBook(anyLong);

        verify(view).showError(any());
    }
    @Test
    public void notifyChangeWhenDeleteCartBookSuccess(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.removeCartBook(anyLong);

        verify(view).notifyCartBookDeleted(anyLong);
    }
    ////
    @Test
    public void showErrWhenInsertFavoriteBookFailed(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail("Whatever"));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.addFavoriteBook(anyLong);

        verify(view).showError(any());
    }
    @Test
    public void notifyChangeWhenInsertFavoriteBookSuccess(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.addFavoriteBook(anyLong);

        verify(view).notifyFavoriteBookInserted(anyLong);
    }

    @Test
    public void showErrorWhenDeleteFavoriteFailed(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail("Whatever"));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.removeFavoriteBook(anyLong);

        verify(view).showError(any());
    }
    @Test
    public void notifyChangeWhenDeleteFavoriteBookSuccess(){
        long anyLong = TestUtils.genAbsRandomLong();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.removeFavoriteBook(anyLong);

        verify(view).notifyFavoriteBookDeleted(anyLong);
    }
    @Test
    public void showErrWhenFetchBookStatusFailed(){
        long anyLong = TestUtils.genAbsRandomLong();
        View view = mock(View.class);
        doAnswer(invocation -> {
            UseCaseCallback useCaseCallback = invocation.getArgumentAt(2, UseCaseCallback.class);
            useCaseCallback.onCompleted(ComplexResponse.fail("Whateveer"));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.loadOptionsMenuBook(anyLong, view);

        verify(this.view).showError(any());
    }
    @Test
    public void showOptionsMenuWhenLoadStatusSuccss(){
        long anyLong = TestUtils.genAbsRandomLong();
        View view = mock(View.class);
        BookStatus bookStatus = new BookStatus(anyLong, false, false);
        doAnswer(invocation -> {
            UseCaseCallback useCaseCallback = invocation.getArgumentAt(2, UseCaseCallback.class);
            useCaseCallback.onCompleted(ComplexResponse.success(new LoadBookOptionsMenuStatus.ResponseValues(bookStatus)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        baseBookPresenter.loadOptionsMenuBook(anyLong, view);

        verify(this.view).showOptionsMenu(bookStatus, view);
    }
}