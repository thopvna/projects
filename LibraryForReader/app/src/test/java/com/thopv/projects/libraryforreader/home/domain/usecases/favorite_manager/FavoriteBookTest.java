package com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.FavoriteBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/3/2017.
 */
public class FavoriteBookTest {
    Repository<Long, FavoriteBook> favoriteBookRepository;
    Repository<Long, Book> bookRepository;
    Repository<Long, Loan> loanRepository;
    InsertFavoriteBook insertFavoriteBook;
    LoadFavorites loadFavorites;
    RemoveFavoriteBook removeFavoriteBook;

    @Before
    public void setUp() throws Exception {
        favoriteBookRepository = mock(FavoriteBookRepository.class);
        bookRepository = mock(BookRepository.class);
        loanRepository = mock(LoanRepository.class);
        insertFavoriteBook = new InsertFavoriteBook(favoriteBookRepository, bookRepository);
        loadFavorites = new LoadFavorites(favoriteBookRepository, bookRepository);
        removeFavoriteBook = new RemoveFavoriteBook(favoriteBookRepository);
    }
    @Test
    public void errorWhenLoadEmptyFavorites(){
        when(favoriteBookRepository.fetchAll()).thenReturn(null);
        loadFavorites.executeUseCase(new LoadFavorites.RequestValues(), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadFavorites.FAVORITES_EMPTY_ERR);
        });
    }
    @Test
    public void happyWhenLoadFavoritesNonEmpty(){
        List<FavoriteBook> favoriteBooks = new LinkedList<>();
        favoriteBooks.add(new FavoriteBook(TestUtils.genAbsRandomLong()));
        favoriteBooks.add(new FavoriteBook(TestUtils.genAbsRandomLong()));
        favoriteBooks.add(new FavoriteBook(TestUtils.genAbsRandomLong()));

        FavoriteBook anotherFavoriteBook =  new FavoriteBook(TestUtils.genAbsRandomLong());

        when(favoriteBookRepository.fetchAll()).thenReturn(favoriteBooks);
        loadFavorites.executeUseCase(new LoadFavorites.RequestValues(), response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            assertThat(favoriteBooks)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(favoriteBooks.size())
                    .excludes(anotherFavoriteBook);
        });
    }

    @Test
    public void insertFavoriteBookInvalid(){
        long longId = TestUtils.genAbsRandomLong();
        when(bookRepository.findById(longId)).thenReturn(null);
        
        insertFavoriteBook.executeUseCase(new InsertFavoriteBook.RequestValues(longId), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), InsertFavoriteBook.INPUT_INVALID);
        });
    }
    @Test
    public void insertExistingFavoriteBook(){
        Book book = TestUtils.genBook();
        long longId = book.getBookId();
        when(bookRepository.findById(longId)).thenReturn(book);
        when(favoriteBookRepository.findById(longId)).thenReturn(new FavoriteBook(longId));
        insertFavoriteBook.executeUseCase(new InsertFavoriteBook.RequestValues(longId), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), InsertFavoriteBook.BOOK_ADREADY_INFAVORITES);
        });
    }
    @Test
    public void insertNewFavoriteBook(){
        Book book = TestUtils.genBook();
        long longId = book.getBookId();
        when(bookRepository.findById(longId)).thenReturn(book);
        when(favoriteBookRepository.findById(longId)).thenReturn(null);
        when(favoriteBookRepository.save(any())).thenReturn(true);
        insertFavoriteBook.executeUseCase(new InsertFavoriteBook.RequestValues(longId), response -> {
            assertTrue(response.getMessage(), response.isSuccess());

        });
    }
    @Test
    public void removeNotExistingFavoriteBookThenGetErr(){
        long longId = TestUtils.genAbsRandomLong();
        when(favoriteBookRepository.findById(longId)).thenReturn(null);
        removeFavoriteBook.executeUseCase(new RemoveFavoriteBook.RequestValues(longId), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), RemoveFavoriteBook.INPUT_INVALID);
        });
    }
    @Test
    public void removeExistingFavoriteBook(){
        long longId = TestUtils.genAbsRandomLong();
        when(favoriteBookRepository.findById(longId)).thenReturn(new FavoriteBook(longId));
        when(favoriteBookRepository.delete(any())).thenReturn(true);
        removeFavoriteBook.executeUseCase(new RemoveFavoriteBook.RequestValues(longId), response -> {
            assertTrue(response.getMessage(), response.isSuccess());
        });
    }
}