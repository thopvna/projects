package test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.FetchBookCopyUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FetchBookCopyUseCaseTest {

    private FetchBookCopyUseCase useCase;
    private Repository<Integer, BookCopy> bookCopyRepository;

    @Before
    public void setUp() throws Exception {
        bookCopyRepository = mock(Repository.class);
        useCase = new FetchBookCopyUseCase(bookCopyRepository);
    }
    @Test
    public void fetchNonExistingBookCopy(){
        int bookCopyId = TestUtils.genAbsInt();
        when(bookCopyRepository.fetchById(bookCopyId)).thenReturn(null);
        useCase.setRequestValues(new FetchBookCopyUseCase.RequestValues(bookCopyId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FetchBookCopyUseCase.DONT_HAVE_ANY_BOOKCOPY_MATCHING);
            verify(bookCopyRepository).fetchById(bookCopyId);
        });
    }
    @Test
    public void happyFetchBookCopy(){
        BookCopy bookCopy = TestUtils.genBorrowableBookCopy();
        when(bookCopyRepository.fetchById(bookCopy.getBookCopyId())).thenReturn(bookCopy);
        useCase.setRequestValues(new FetchBookCopyUseCase.RequestValues(bookCopy.getBookCopyId()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(bookCopyRepository).fetchById(bookCopy.getBookCopyId());
        });
    }
}