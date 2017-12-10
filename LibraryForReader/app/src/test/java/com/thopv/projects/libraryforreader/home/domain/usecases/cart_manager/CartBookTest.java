package com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.CartBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/3/2017.
 */
public class CartBookTest {
    Repository<Long, CartBook> cartBookRepository;
    Repository<Long, Book> bookRepository;
    Repository<Long, Loan> loanRepository;
    InsertCartBook insertCartBook;
    LoadCart loadCart;
    RemoveCartBook removeCartBook;
    SendBorrowRequest sendBorrowRequest;

    @Before
    public void setUp() throws Exception {
        cartBookRepository = mock(CartBookRepository.class);
        bookRepository = mock(BookRepository.class);
        loanRepository = mock(LoanRepository.class);

        insertCartBook = new InsertCartBook(cartBookRepository, bookRepository);
        loadCart = new LoadCart(cartBookRepository, bookRepository);
        removeCartBook = new RemoveCartBook(cartBookRepository);
        sendBorrowRequest = new SendBorrowRequest(cartBookRepository, loanRepository);
    }
    @Test
    public void insertCartBookInvalid(){
        long longId = TestUtils.genAbsRandomLong();
        when(bookRepository.findById(longId)).thenReturn(null);

        insertCartBook.executeUseCase(new InsertCartBook.RequestValues(longId), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), InsertCartBook.INPUT_INVALID);
        });
    }
    @Test
    public void insertExistingCartBook(){
        Book book = TestUtils.genBook();
        long longId = book.getBookId();
        CartBook cartBook = new CartBook(longId);

        when(bookRepository.findById(any())).thenReturn(book);
        when(cartBookRepository.findById(any())).thenReturn(cartBook);

        insertCartBook.executeUseCase(new InsertCartBook.RequestValues(longId), response -> {
            assertFalse(response.getMessage(), response.isSuccess());
            assertEquals(response.getMessage(), InsertCartBook.BOOK_ADREADY_INCART);
        });
    }
    @Test
    public void insertNewCartBook(){
        Book mbook = TestUtils.genBook();
        long bookId = mbook.getBookId();
        Assert.assertNotNull(mbook);

        when(bookRepository.findById(any())).thenReturn(mbook);
        when(cartBookRepository.findById(any())).thenReturn(null);
        when(cartBookRepository.save(any())).thenReturn(true);

        insertCartBook.executeUseCase(new InsertCartBook.RequestValues(bookId), response -> {
            assertTrue(response.getMessage(), response.isSuccess());
        });
    }
    @Test
    public void removeNotExistingCartBookThenGetErr(){
        long longId = TestUtils.genAbsRandomLong();
        when(cartBookRepository.findById(longId)).thenReturn(null);
        removeCartBook.executeUseCase(new RemoveCartBook.RequestValues(longId), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), RemoveCartBook.INPUT_INVALID);
        });
    }
    @Test
    public void removeExistingCartBook(){
        long longId = TestUtils.genAbsRandomLong();
        CartBook cartBook = new CartBook(longId);

        when(cartBookRepository.findById(longId)).thenReturn(cartBook);
        when(cartBookRepository.delete(cartBook)).thenReturn(true);

        removeCartBook.executeUseCase(new RemoveCartBook.RequestValues(longId), response -> {
            assertTrue(response.getMessage(), response.isSuccess());
        });

    }
    @Test
    public void sendBorrowRequestEmpty_DontHaveAnyCartBook_ThenGetErr(){
        when(cartBookRepository.fetchAll()).thenReturn(null);
        sendBorrowRequest.executeUseCase(new SendBorrowRequest.RequestValues(), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), SendBorrowRequest.CART_EMPTY_ERR);
        });
    }
    @Test
    public void sendBorrowRequestNonEmptyThenCartBookEmpty(){
        List<CartBook> cartBooks = new LinkedList<>();

        cartBooks.add(new CartBook(TestUtils.genAbsRandomLong()));
        cartBooks.add(new CartBook(TestUtils.genAbsRandomLong()));
        cartBooks.add(new CartBook(TestUtils.genAbsRandomLong()));

        when(cartBookRepository.fetchAll()).thenReturn(cartBooks);

        sendBorrowRequest.executeUseCase(new SendBorrowRequest.RequestValues(), response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(loanRepository).save(any());
            verify(cartBookRepository).clearAll();
        });
    }
}