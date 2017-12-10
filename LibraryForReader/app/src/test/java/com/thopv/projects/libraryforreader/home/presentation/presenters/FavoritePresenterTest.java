package com.thopv.projects.libraryforreader.home.presentation.presenters;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.LoadFavorites;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;
import com.thopv.projects.libraryforreader.home.presentation.contracts.FavoriteContract;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.LoadFavorites.FAVORITES_EMPTY_ERR;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by thopv on 12/4/2017.
 */
public class FavoritePresenterTest {

    private FavoritePresenter favoritePresenter;
    private FavoriteContract.View view;
    private UseCaseHandler useCaseHandler;

    @Before
    public void setUp() throws Exception {
        view = mock(FavoriteContract.View.class);
        useCaseHandler = mock(UseCaseHandler.class);
        InsertCartBook insertCartBook = mock(InsertCartBook.class);
        RemoveFavoriteBook removeFavoriteBook = mock(RemoveFavoriteBook.class);
        RemoveCartBook removeCartBook = mock(RemoveCartBook.class);
        LoadBookOptionsMenuStatus loadBookOptionsMenuStatus = mock(LoadBookOptionsMenuStatus.class);
        InsertFavoriteBook insertFavoriteBook = mock(InsertFavoriteBook.class);
        LoadFavorites loadFavorites = mock(LoadFavorites.class);
        FetchBookById fetchBookById = mock(FetchBookById.class);

        favoritePresenter = new FavoritePresenter(
                view, useCaseHandler, insertCartBook, removeFavoriteBook, removeCartBook, loadBookOptionsMenuStatus, insertFavoriteBook, loadFavorites, fetchBookById
         );
    }
    @Test
    public void loadEmptyFavorite(){
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(FAVORITES_EMPTY_ERR));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        favoritePresenter.loadFavoriteBooks();

        verify(view).showError(contains(FAVORITES_EMPTY_ERR));
        verify(view).showNoFavoriteBooks();
    }
    @Test
    public void loadNonEmptyFavorite_ButDontHaveAnyFavoriteBooks(){
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        favoritePresenter.loadFavoriteBooks();

        verify(view).showError(any());
        verify(view).showNoFavoriteBooks();
    }
    @Test
    public void loadNonEmptyFavorite_AndHaveBooks(){
        List<Book> books = TestUtils.genBooks();
        doAnswer(invocation -> {
            UseCaseCallback callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadFavorites.ResponseValues(books)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        favoritePresenter.loadFavoriteBooks();

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).showFavoriteBooks(argumentCaptor.capture());
        verify(view).hideNoFavoriteBooks();
        assertThat(argumentCaptor.getValue()).isNotNull().isNotEmpty().hasSize(books.size());

    }

}