package test.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.ConfirmBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.FetchBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.LoadAllBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.BorrowBooksRequestsContract;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters.BorrowBooksRequestsPresenter;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BorrowBooksRequestsPresenterTest {
    @Mock
    BorrowBooksRequestsContract.Controller borrowBooksRequestsController;
    @Mock
    LoadAllBorrowBooksRequestsUseCase loadAllBorrowBooksRequestsUseCase;
    @Mock
    FetchBorrowBooksRequestsUseCase fetchBorrowBooksRequestsUseCase;
    @Mock
    ConfirmBorrowBooksRequestsUseCase confirmBorrowBooksRequestsUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @InjectMocks
    BorrowBooksRequestsPresenter borrowBooksRequestsPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchBorrowBooksRequests_WithFailResponse(){
        String keyword = "any";
        String message = "any";

        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.fetchBorrowBooksRequests(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController).showError(contains(message));
    }

    @Test
    public void fetchBorrowBooksRequests_WithEmptyPayload(){
        String keyword = "any";

        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.fetchBorrowBooksRequests(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController).showError(BorrowBooksRequestsPresenter.FETCH_BORROW_BOOKS_REQUETS_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void fetchBorrowBooksRequests_WIthEmptyRequests(){
        String keyword = "any";

        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBorrowBooksRequestsUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.fetchBorrowBooksRequests(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController).showError(BorrowBooksRequestsPresenter.FETCH_BORROW_BOOKS_REQUETS_ERROR_REQUESTS_EMPTY);

    }
    @Test
    public void happyFetchBorrowBooksRequests(){
        String keyword = "any";
        List<BorrowBooksRequest> requests = TestUtils.genBorrowBookRequests(5);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new FetchBorrowBooksRequestsUseCase.ResponseValues(requests)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.fetchBorrowBooksRequests(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController, never()).showError(BorrowBooksRequestsPresenter.FETCH_BORROW_BOOKS_REQUETS_ERROR_REQUESTS_EMPTY);
        verify(borrowBooksRequestsController).showBorrowBooksRequests(requests);
    }
    @Test
    public void confirmBorrowBooksRequest_WithFailResponse(){
        int requestId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        int borrowCardId = TestUtils.genAbsInt();
        String message = "any";

        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.confirmBorrowBooksRequest(requestId, librarianId, borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController).showError(contains(message));
    }
    @Test
    public void confirmBorrowBooksRequest_WithEmptyPayload(){
        int requestId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        int borrowCardId = TestUtils.genAbsInt();

        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.confirmBorrowBooksRequest(requestId, librarianId, borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController).showError(BorrowBooksRequestsPresenter.CONFIRM_BORROW_BOOKS_REQUEST_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void confirmBorrowBooksRequest_WithNullLoan(){
        int requestId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        int borrowCardId = TestUtils.genAbsInt();

        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new ConfirmBorrowBooksRequestsUseCase.ResponseValues(null, request)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.confirmBorrowBooksRequest(requestId, librarianId, borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController).showError(BorrowBooksRequestsPresenter.CONFIRM_BORROW_BOOKS_REQUEST_ERROR_LOAN_NULL);
    }
    @Test
    public void confirmBorrowBooksRequest_WithNullRequestInResponse(){
        int requestId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        int borrowCardId = TestUtils.genAbsInt();

        Loan loan = TestUtils.genLoan();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new ConfirmBorrowBooksRequestsUseCase.ResponseValues(loan, null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.confirmBorrowBooksRequest(requestId, librarianId, borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController).showError(BorrowBooksRequestsPresenter.CONFIRM_BORROW_BOOKS_REQUEST_ERROR_REQUEST_NULL);
    }
    @Test
    public void happyConfirmBorrowBooksRequest(){
        int requestId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        int borrowCardId = TestUtils.genAbsInt();

        Loan loan = TestUtils.genLoan();
        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new ConfirmBorrowBooksRequestsUseCase.ResponseValues(loan, request)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        borrowBooksRequestsPresenter.confirmBorrowBooksRequest(requestId, librarianId, borrowCardId);
        verify(useCaseHandler).execute(any(), any(), any());
        verify(borrowBooksRequestsController, never()).showError(BorrowBooksRequestsPresenter.CONFIRM_BORROW_BOOKS_REQUEST_ERROR_REQUEST_NULL);
        verify(borrowBooksRequestsController).showLoan(loan);
        verify(borrowBooksRequestsController).updateRequest(request);
    }
}