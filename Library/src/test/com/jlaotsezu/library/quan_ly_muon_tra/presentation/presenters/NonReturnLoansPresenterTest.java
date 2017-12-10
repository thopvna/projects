package test.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.LoadNonReturnLoansUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.ReturnBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.NonReturnLoansContract;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters.NonReturnLoansPresenter;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class NonReturnLoansPresenterTest {
    @Mock
    LoadNonReturnLoansUseCase loadNonReturnLoansUseCase;
    @Mock
    ReturnBooksUseCase returnBooksUseCase;
    @Mock
    UseCaseHandler useCaseHandler;
    @Mock
    NonReturnLoansContract.Controller nonReturnLoansController;
    @InjectMocks
    NonReturnLoansPresenter nonReturnLoansPresenter;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void loadNonReturnLoans_WithFailResponse(){
        String message = "any";
        String keyword = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        nonReturnLoansPresenter.loadNonReturnLoans(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(nonReturnLoansController).showError(contains(message));
    }
    @Test
    public void loadNoNReturnLoans_WithEmptyPayload(){
        String keyword = "any";

        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        nonReturnLoansPresenter.loadNonReturnLoans(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(nonReturnLoansController).showError(NonReturnLoansPresenter.LOAD_NONRETURN_LOANS_ERROR_PAYLOAD_EMPTY);
    }
    @Test
    public void loadNoNReturnLoans_WithEmptyLoans(){
        String keyword = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadNonReturnLoansUseCase.ResponseValues(null)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        nonReturnLoansPresenter.loadNonReturnLoans(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(nonReturnLoansController).showError(NonReturnLoansPresenter.LOAD_NONRETURN_LOANS_ERROR_LOANS_EMPTY);
    }
    @Test
    public void happyLoadNonReturnLoans(){
        String keyword = "any";
        List<Loan> loans = TestUtils.genLoans(5);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new LoadNonReturnLoansUseCase.ResponseValues(loans)));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        nonReturnLoansPresenter.loadNonReturnLoans(keyword);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(nonReturnLoansController, never()).showError(NonReturnLoansPresenter.LOAD_NONRETURN_LOANS_ERROR_LOANS_EMPTY);
        verify(nonReturnLoansController).showLoans(loans);
    }
    @Test
    public void returnBooks_WithFailResponse(){
        int loanId = TestUtils.genAbsInt();
        long fee = TestUtils.genAbsLong();
        String message = "any";
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        nonReturnLoansPresenter.returnBooks(loanId, fee);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(nonReturnLoansController).showError(contains(message));
    }
    @Test
    public void happyReturnBooks(){
        int loanId = TestUtils.genAbsInt();
        long fee = TestUtils.genAbsLong();
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success());
            return null;
        }).when(useCaseHandler).execute(any(), any(), any());

        nonReturnLoansPresenter.returnBooks(loanId, fee);

        verify(useCaseHandler).execute(any(), any(), any());
        verify(nonReturnLoansController).showMessage(NonReturnLoansPresenter.RETURN_BOOKS_SUCCESS);
        verify(nonReturnLoansController).removeLoan(loanId);
    }
}