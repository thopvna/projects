package test.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.LoadBooksUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoadBooksUseCaseTest {

    private LoadBooksUseCase loadBooksUseCase;
    private Repository<Integer, Book> bookRepository;

    @Before
    public void setUp() throws Exception {
        bookRepository = mock(Repository.class);
        loadBooksUseCase = new LoadBooksUseCase(bookRepository);
    }
    @Test
    public void load_butEmptyBooks(){
        when(bookRepository.fetchAll()).thenReturn(null);
        loadBooksUseCase.setRequestValues(new LoadBooksUseCase.RequestValues());
        loadBooksUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadBooksUseCase.DON_T_HAVE_ANY_BOOKS);
        });
        loadBooksUseCase.run();
    }
    @Test
    public void happyLoadBooks(){
        List<Book> books = TestUtils.genBooks(5);
        when(bookRepository.fetchAll()).thenReturn(books);
        loadBooksUseCase.setRequestValues(new LoadBooksUseCase.RequestValues());
        loadBooksUseCase.setUseCaseCallback(response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            assertNotNull(response.getPayload().getBooks());
            List<Book> payload = response.getPayload().getBooks();
            assertThat(payload).isNotNull().isNotEmpty().isEqualTo(books);
            verify(bookRepository).fetchAll();
        });
        loadBooksUseCase.run();
    }
}