package test.com.jlaotsezu.library.quan_ly_sach.presentation.presenters;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.CancelBookImportRequestUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.LoadBookImportRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.BookImportRequestsContract;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.presenters.BookImportRequestsPresenter;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class BookImportRequestsPresenterTest {

    @Mock
    LoadBookImportRequestsUseCase loadBookImportRequestsUseCase;
    @Mock
    CancelBookImportRequestUseCase cancelBookImportRequestUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @Mock
    BookImportRequestsContract.Controller bookImportRequestsController;

    @InjectMocks
    BookImportRequestsPresenter bookImportRequestsPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void loadBookImportRequests_WithFailResponse(){
        int librarianId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            LoadBookImportRequestsUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBookImportRequestsUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        bookImportRequestsPresenter.loadBookImportRequests(librarianId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(bookImportRequestsController).showError(contains(message));
    }
    @Test
    public void loadBookImportRequests_WithEmptyPayload(){
        int librarianId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            LoadBookImportRequestsUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBookImportRequestsUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        bookImportRequestsPresenter.loadBookImportRequests(librarianId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(bookImportRequestsController).showError(BookImportRequestsPresenter.LOAD_BOOK_IMPORT_REQUESTS_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void loadBookImportRequests_WithEmptyRequests(){
        int librarianId = TestUtils.genAbsInt();
        doAnswer(invocation -> {
            LoadBookImportRequestsUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBookImportRequestsUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadBookImportRequestsUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        bookImportRequestsPresenter.loadBookImportRequests(librarianId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(bookImportRequestsController).showError(BookImportRequestsPresenter.LOAD_BOOK_IMPORT_REQUESTS_ERROR_DONT_HAVE_ANY_REQUESTS);
    }
    @Test
    public void happyLoadBookImportRequests(){
        int librarianId = TestUtils.genAbsInt();
        List<BookImportRequest> requests = TestUtils.genBookImportRequests(2);
        doAnswer(invocation -> {
            LoadBookImportRequestsUseCase.UseCaseCallback callback = invocation.getArgumentAt(2, LoadBookImportRequestsUseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadBookImportRequestsUseCase.ResponseValues(requests)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());
        bookImportRequestsPresenter.loadBookImportRequests(librarianId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(bookImportRequestsController, never()).showError(BookImportRequestsPresenter.LOAD_BOOK_IMPORT_REQUESTS_ERROR_DONT_HAVE_ANY_REQUESTS);
        verify(bookImportRequestsController).showBookImportRequests(requests);
    }
    @Test
    public void cancelBookImportRequest_WithFailResponse(){
        int librarianId = TestUtils.genAbsInt();
        int requestId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        bookImportRequestsPresenter.cancelRequest(requestId);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(bookImportRequestsController).showError(contains(message));
    }
    @Test
    public void happyCancelBookImportRequest(){
        int requestId = TestUtils.genAbsInt();
        String message = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        bookImportRequestsPresenter.cancelRequest(requestId);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(bookImportRequestsController, never()).showError(contains(message));
        verify(bookImportRequestsController).showMessage(BookImportRequestsPresenter.CANCEL_REQUEST_SUCCESS);
    }
}