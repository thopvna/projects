package test.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.SearchBooksUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchBooksUseCaseTest {

    private Repository<Integer, Book> bookRepository;
    private SearchBooksUseCase searchBooksUseCase;

    @Before
    public void setUp() throws Exception {
        bookRepository = mock(Repository.class);
        searchBooksUseCase = new SearchBooksUseCase(bookRepository);
    }
    @Test
    public void findByKeywordEmpty(){
        String keyword = "";
        searchBooksUseCase.setRequestValues(new SearchBooksUseCase.RequestValues(keyword));
        searchBooksUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), SearchBooksUseCase.KEYWORD_IS_UNAVAILABLE);
        });
        searchBooksUseCase.run();
    }
    @Test
    public void findByCriteriaAndKeywordNonEmpty_ButNotMatching(){
        String keyword = "NonEmpty";
        searchBooksUseCase.setRequestValues(new SearchBooksUseCase.RequestValues(keyword));
        searchBooksUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), SearchBooksUseCase.DON_T_HAVE_ANY_BOOK_MATCHING);
        });
        searchBooksUseCase.run();
    }
    @Test
    public void findByCriteriaAndKeywordNonEmpty_HaveMatching(){
        String keyword = "NonEmpty";
        List<Book> books = TestUtils.genBooks(5);
        when(bookRepository.find(any())).thenReturn(books);
        searchBooksUseCase.setRequestValues(new SearchBooksUseCase.RequestValues(keyword));
        searchBooksUseCase.setUseCaseCallback(response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            assertNotNull(response.getPayload().getBooks());
            assertThat(response.getPayload().getBooks()).isNotNull().isNotEmpty().isEqualTo(books);
            verify(bookRepository).find(any());
        });
        searchBooksUseCase.run();
    }
}