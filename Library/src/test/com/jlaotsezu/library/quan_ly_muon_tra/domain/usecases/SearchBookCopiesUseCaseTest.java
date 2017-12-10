package test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.SearchBookCopiesUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchBookCopiesUseCaseTest {

    private SearchBookCopiesUseCase useCase;
    private Repository<Integer, BookCopy> bookCopyRepository;

    @Before
    public void setUp() throws Exception {
        bookCopyRepository = mock(Repository.class);
        useCase = new SearchBookCopiesUseCase(bookCopyRepository);
    }
    @Test
    public void searchBookCopies_WithEmptyKeyword(){
        String keyword = "";
        useCase.setRequestValues(new SearchBookCopiesUseCase.RequestValues(keyword));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), SearchBookCopiesUseCase.INPUT_INVALID_KEYWORD_EMPTY);
        });
        useCase.run();
    }
    @Test
    public void searchBookCopies_DontHaveAnyBookCopies(){
        String keyword = "NotEmpty";
        when(bookCopyRepository.find(any())).thenReturn(null);
        useCase.setRequestValues(new SearchBookCopiesUseCase.RequestValues(keyword));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), SearchBookCopiesUseCase.DONT_HAVE_ANY_BOOKCOPY_MATCHING);
            verify(bookCopyRepository).find(any());
        });
        useCase.run();
    }
    @Test
    public void happySearchBookCopies(){
        String keyword = "NotEmpty";
        List<BookCopy> bookCopies = TestUtils.genBorrowableBookCopies(2);
        when(bookCopyRepository.find(any())).thenReturn(bookCopies);
        useCase.setRequestValues(new SearchBookCopiesUseCase.RequestValues(keyword));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(bookCopyRepository).find(any());
        });
        useCase.run();
    }
}