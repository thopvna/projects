package test.com.jlaotsezu.library.quan_ly_sach.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.RequestImportBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.SearchBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.RequestImportBooksContract;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.presenters.RequestImportBooksPresenter;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.com.jlaotsezu.library.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class RequestImportBooksPresenterTest {
    @Mock
    RequestImportBooksUseCase requestImportBooksUseCase;
    @Mock
    SearchBooksUseCase searchBooksUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @Mock
    RequestImportBooksContract.Controller requestImportBooksController;
    @InjectMocks
    RequestImportBooksPresenter requestImportBooksPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void requestImportBooks_WithFailResponse(){
        String message = "any";
        int librarianId = TestUtils.genAbsInt();
        Map<Integer, Integer> booksAmount = new HashMap<>();
        doAnswer(invocation -> {
            RequestImportBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, RequestImportBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        requestImportBooksPresenter.requestImportBooks(librarianId, booksAmount);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(requestImportBooksController).showError(contains(message));
    }
    @Test
    public void happyRequestImportBooks(){
        int librarianId = TestUtils.genAbsInt();
        Map<Integer, Integer> booksAmount = new HashMap<>();
        doAnswer(invocation -> {
            RequestImportBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, RequestImportBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        requestImportBooksPresenter.requestImportBooks(librarianId, booksAmount);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(requestImportBooksController, never()).showError(any());
        verify(requestImportBooksController).showMessage(RequestImportBooksPresenter.REQUEST_IMPORT_BOOKS_SUCCESS);
        verify(requestImportBooksController).clearRequestImportBooks();
    }
    @Test
    public void searchBooks_WithFailResponse(){
        String message = "any";
        String keyword = "any";
        doAnswer(invocation-> {
            SearchBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, SearchBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        requestImportBooksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(requestImportBooksController).showError(contains(message));
    }
    @Test
    public void searchBooks_WIthEmptyPayload(){
        String keyword = "any";
        doAnswer(invocation-> {
            SearchBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, SearchBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        requestImportBooksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(requestImportBooksController).showError(RequestImportBooksPresenter.SEARCH_BOOK_USECASE_ERROR_PAYLOAD_EMTY);
    }
    @Test
    public void searchBooks_WithEmptyBooks(){
        String keyword = "any";
        doAnswer(invocation-> {
            SearchBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, SearchBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new SearchBooksUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        requestImportBooksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(requestImportBooksController).showError(RequestImportBooksPresenter.SEARCH_BOOK_USECASE_ERROR_DONT_HAVE_ANY_BOOKS);
    }
    @Test
    public void happySearchBooks(){
        String keyword = "any";
        List<Book> books = TestUtils.genBooks(5);
        doAnswer(invocation-> {
            SearchBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, SearchBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new SearchBooksUseCase.ResponseValues(books)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        requestImportBooksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(requestImportBooksController, never()).showError(RequestImportBooksPresenter.SEARCH_BOOK_USECASE_ERROR_DONT_HAVE_ANY_BOOKS);
        verify(requestImportBooksController).showSearchResult(books);
    }
}