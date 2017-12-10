package com.thopv.projects.libraryforreader.home.presentation.presenters;

import com.thopv.projects.libraryforreader.TestUtils;
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
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by thopv on 12/3/2017.
 */
public class BookPresenterTest {
    BookContract.View view;
    BookPresenter bookPresenter;
    List<Book> books;
    UseCaseHandler useCaseHandler;
    LoadBooks loadBooks;
    SearchBooks searchBooks;
    InsertFavoriteBook insertFavoriteBook;
    InsertCartBook insertCartBook;
    RemoveFavoriteBook removeFavoriteBook;
    RemoveCartBook removeCartBook;
    LoadBookOptionsMenuStatus loadBookOptionsMenuStatus;
    private FetchBookById fetchBookById;

    @Before
    public void setUp() throws Exception {
        view = mock(BookContract.View.class);
        useCaseHandler = mock(UseCaseHandler.class);
        loadBooks = mock(LoadBooks.class);
        searchBooks = mock(SearchBooks.class);
        insertFavoriteBook = mock(InsertFavoriteBook.class);
        insertCartBook = mock(InsertCartBook.class);
        removeFavoriteBook = mock(RemoveFavoriteBook.class);
        removeCartBook = mock(RemoveCartBook.class);
        loadBookOptionsMenuStatus = mock(LoadBookOptionsMenuStatus.class);
        fetchBookById = mock(FetchBookById.class);
        bookPresenter = new BookPresenter(view, useCaseHandler, searchBooks, insertFavoriteBook, insertCartBook, removeFavoriteBook, removeCartBook, loadBookOptionsMenuStatus, loadBooks, fetchBookById);
        books = genBooks();
    }


    @Test
    public void loadEmptyBooks(){
        doAnswer(invocation ->  {
            UseCaseCallback<LoadBooks.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(LoadBooks.BOOKS_EMPTY_ERR));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        bookPresenter.loadMostSearchBooks();
        bookPresenter.loadNewestImportBooks();

        verify(view, atLeastOnce()).showError(contains(LoadBooks.BOOKS_EMPTY_ERR));
        verify(view, times(2)).showError(anyString());
    }
    @Test
    public void loadNonEmptyBooks_ButDontHaveAnyBookPayload(){
        doAnswer(invocation ->  {
            UseCaseCallback<LoadBooks.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        bookPresenter.loadMostSearchBooks();
        bookPresenter.loadNewestImportBooks();

        verify(view, times(2)).showError(any());
    }
    @Test
    public void loadNonEmptyBooks(){
        doAnswer(invocation ->  {
            UseCaseCallback<LoadBooks.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadBooks.ResponseValues(books)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        bookPresenter.loadMostSearchBooks();

        ArgumentCaptor<List> mostSearchBooksCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).showMostSearchBooks(mostSearchBooksCaptor.capture());
        verify(view).hideMostSearchProgressBar();

        List<Book> mostSearchBooksCaptorBooks = mostSearchBooksCaptor.getValue();

        assertThat(mostSearchBooksCaptorBooks)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(this.books);

        bookPresenter.loadNewestImportBooks();

        ArgumentCaptor<List> newestImportBOoksCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).showNewestImportBooks(newestImportBOoksCaptor.capture());
        verify(view).hideNewestImportProgressBar();

        List<Book> newestImprotBooksCaptorBooks = newestImportBOoksCaptor.getValue();

        assertThat(newestImprotBooksCaptorBooks)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(this.books);

    }
    @Test
    public void searchBooksWithEmptyKeyword(){
        String keyword = "";
        bookPresenter.searchBook(keyword);
        verify(view).showError(contains(BookPresenter.KEYWORD_INVALID));
    }
    @Test
    public void searchBooksWithEmptyResult(){
        String keyword = "NonEmpty";
        doAnswer(invocation -> {
            UseCaseCallback<SearchBooks.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(SearchBooks.NO_RESULT_MATCHING_ERROR));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        bookPresenter.searchBook(keyword);

        verify(view, never()).showError(BookPresenter.KEYWORD_INVALID);
        verify(view).showError(contains(SearchBooks.NO_RESULT_MATCHING_ERROR));
        verify(view).showEmptySearchResult();
    }
    @Test
    public void happySearchBook(){
        String keyword = "NonEmpty";
        List<Book> books = TestUtils.genBooks();
        doAnswer(invocation -> {
            UseCaseCallback<SearchBooks.ResponseValues> callback = invocation.getArgumentAt(2, UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new SearchBooks.ResponseValues(books)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        bookPresenter.searchBook(keyword);

        ArgumentCaptor<List> bookCaptor = ArgumentCaptor.forClass(List.class);

        verify(view, never()).showError(any());
        verify(view).showSearchResult(bookCaptor.capture(), eq(keyword));

        assertThat(bookCaptor.getValue())
                .isNotNull()
                .isNotEmpty()
                .hasSize(books.size())
                .isEqualTo(books);
    }

     List<Book> genBooks() {
        return TestUtils.genBooks();
    }

}