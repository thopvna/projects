package test.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.RequestImportBooksUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RequestImportBooksUseCaseTest {

    private RequestImportBooksUseCase usecase;
    private Repository<Integer, BookImportRequest> bookImportRequestRepository;
    private Repository<Integer, Book> bookRepository;
    private Repository<Integer, User> userRepository;

    @Before
    public void setUp() throws Exception {
        bookImportRequestRepository = mock(Repository.class);
        bookRepository = mock(Repository.class);
        userRepository = mock(Repository.class);
        usecase = new RequestImportBooksUseCase(bookImportRequestRepository, bookRepository, userRepository);
    }
    @Test
    public void sendRequestWithNonExistingLibrarian(){
        User user = TestUtils.genUser();
        when(userRepository.fetchById(user.getUserId())).thenReturn(null);
        usecase.setRequestValues(new RequestImportBooksUseCase.RequestValues(null, user.getUserId()));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), RequestImportBooksUseCase.DON_T_HAVE_ANY_LIBRARIAN_MATCHING);
        });
        usecase.run();
    }
    @Test
    public void sendRequestWithExisting_ButIsNotLibrarian(){
        User user = TestUtils.genUser();
        when(userRepository.fetchById(user.getUserId())).thenReturn(user);
        usecase.setRequestValues(new RequestImportBooksUseCase.RequestValues(null, user.getUserId()));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), RequestImportBooksUseCase.USER_WITH_LIBRIAN_ID_IS_NOT_A_LIBRARIAN);
        });
        usecase.run();
    }
    @Test
    public void sendEmptyBooksRequestImport(){
        User librarian = TestUtils.genLibrarian();
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);
        usecase.setRequestValues(new RequestImportBooksUseCase.RequestValues(null, librarian.getUserId()));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), RequestImportBooksUseCase.INPUT_INVALID_BOOK_AMOUNT_INVALID);
        });
        usecase.run();
    }
    @Test
    public void sendNonEmptyBooksRequestImport_ButSomeBookNonExisting(){
        User librarian = TestUtils.genLibrarian();
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);

        Book book = TestUtils.genBook();
        Map<Integer, Integer> booksAmount = new HashMap<>();
        booksAmount.put(book.getBookId(), TestUtils.genAbsInt());

        when(bookRepository.fetchById(book.getBookId())).thenReturn(null);

        usecase.setRequestValues(new RequestImportBooksUseCase.RequestValues(booksAmount, librarian.getUserId()));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), RequestImportBooksUseCase.SOME_BOOKS_DON_T_EXISTING_UNABLE_REQUEST_IMPORT_NON_EXISTING_BOOKS);
            verify(bookRepository).fetchById(book.getBookId());
        });
        usecase.run();
    }
    @Test
    public void sendNonEmptyBooksRequestImport_BooksExistingButHaveDbErr(){
        User librarian = TestUtils.genLibrarian();
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);

        Book book = TestUtils.genBook();
        Map<Integer, Integer> booksAmount = new HashMap<>();
        booksAmount.put(book.getBookId(), TestUtils.genAbsInt());

        when(bookRepository.fetchById(book.getBookId())).thenReturn(book);
        when(bookImportRequestRepository.save(any())).thenReturn(false);

        usecase.setRequestValues(new RequestImportBooksUseCase.RequestValues(booksAmount, librarian.getUserId()));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), RequestImportBooksUseCase.REQUEST_IMPORT_BOOKS_FAILED_DB_ERROR);
            verify(bookRepository).fetchById(book.getBookId());
            verify(bookImportRequestRepository).save(any());
        });
        usecase.run();
    }
    @Test
    public void sendNonEmptyBooksRequestImport_BooksExisting_Happy(){
        User librarian = TestUtils.genLibrarian();
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);

        Book book = TestUtils.genBook();
        Map<Integer, Integer> booksAmount = new HashMap<>();
        booksAmount.put(book.getBookId(), TestUtils.genAbsInt());

        when(bookRepository.fetchById(book.getBookId())).thenReturn(book);
        when(bookImportRequestRepository.save(any())).thenReturn(true);

        usecase.setRequestValues(new RequestImportBooksUseCase.RequestValues(booksAmount, librarian.getUserId()));
        usecase.setUseCaseCallback(response -> {
            assertTrue(response.isSuccess());
            verify(bookRepository).fetchById(book.getBookId());
            verify(bookImportRequestRepository).save(any());
        });
        usecase.run();
    }
}