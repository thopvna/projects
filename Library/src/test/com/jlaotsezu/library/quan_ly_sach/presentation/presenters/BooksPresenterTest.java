package test.com.jlaotsezu.library.quan_ly_sach.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.LoadBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.SearchBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.BooksContract;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.presenters.BooksPresenter;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BooksPresenterTest {
    @Mock
    LoadBooksUseCase loadBooksUseCase;
    @Mock
    SearchBooksUseCase searchBooksUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @Mock
    BooksContract.Controller booksController;
    @InjectMocks
    BooksPresenter booksPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void loadBooks_WithFailResponse(){
        String message = "any";
        doAnswer(invocation-> {
            LoadBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        booksPresenter.loadBooks();

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController).showError(contains(message));
    }
    @Test
    public void loadBooks_WithEmptyPayload(){
        doAnswer(invocation-> {
            LoadBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        booksPresenter.loadBooks();

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController).showError(BooksPresenter.LOAD_BOOKS_USECASE_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void loadBooks_WithEmptyBooks(){
        doAnswer(invocation-> {
            LoadBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadBooksUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        booksPresenter.loadBooks();

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController).showError(BooksPresenter.LOAD_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOK);
    }
    @Test
    public void happyLoadBooks(){
        List<Book> books = TestUtils.genBooks(5);
        doAnswer(invocation-> {
            LoadBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadBooksUseCase.ResponseValues(books)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        booksPresenter.loadBooks();

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController, never()).showError(BooksPresenter.LOAD_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOK);
        verify(booksController).showBooks(books);
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

        booksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController).showError(contains(message));
    }
    @Test
    public void searchBooks_WIthEmptyPayload(){
        String keyword = "any";
        doAnswer(invocation-> {
            SearchBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, SearchBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        booksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController).showError(BooksPresenter.SEARCH_BOOKS_USECASE_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void searchBooks_WithEmptyBooks(){
        String keyword = "any";
        doAnswer(invocation-> {
            SearchBooksUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, SearchBooksUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new SearchBooksUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        booksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController).showError(BooksPresenter.SEARCH_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOKS);
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

        booksPresenter.searchBooks(keyword);

        verify(useCaseHandler).execute(any(),any(),any());
        verify(booksController, never()).showError(BooksPresenter.SEARCH_BOOKS_USECASE_ERROR_DONT_HAVE_ANY_BOOKS);
        verify(booksController).showSearchResult(books);
    }
}