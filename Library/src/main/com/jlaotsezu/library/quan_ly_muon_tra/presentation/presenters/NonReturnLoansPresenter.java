package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.LoadNonReturnLoansUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.ReturnBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.NonReturnLoansContract;
import main.com.jlaotsezu.library.support.architecture.UseCaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class NonReturnLoansPresenter implements NonReturnLoansContract.Presenter {
    public static final String RETURN_BOOKS_SUCCESS = "Return books thành công.";
    public static final String LOAD_NONRETURN_LOANS_ERROR_LOANS_EMPTY = "Lỗi Load NonReturn Loans Usecase. Loans is empty.";
    public static final String LOAD_NONRETURN_LOANS_ERROR_PAYLOAD_EMPTY = "Lỗi Load NonReturn Loans Usecase. Payload is empty.";
    @Autowired
    LoadNonReturnLoansUseCase loadNonReturnLoansUseCase;
    @Autowired
    ReturnBooksUseCase returnBooksUseCase;
    @Autowired
    UseCaseHandler useCaseHandler;
    @Autowired
    NonReturnLoansContract.Controller nonReturnLoansController;
    @Override
    public void loadNonReturnLoans(String keyword) {
        useCaseHandler.execute(loadNonReturnLoansUseCase, new LoadNonReturnLoansUseCase.RequestValues(keyword), response -> {
            if(response.isSuccess()){
                if(response.getPayload() == null){
                    nonReturnLoansController.showError(LOAD_NONRETURN_LOANS_ERROR_PAYLOAD_EMPTY);
                }
                else{
                    List<Loan> loans = response.getPayload().getLoans();
                    if(loans == null || loans.size() == 0){
                        nonReturnLoansController.showError(LOAD_NONRETURN_LOANS_ERROR_LOANS_EMPTY);
                    }
                    else{
                        nonReturnLoansController.showLoans(loans);
                    }
                }
            }
            else{
                nonReturnLoansController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void returnBooks(int loanId, long fee) {
        useCaseHandler.execute(returnBooksUseCase, new ReturnBooksUseCase.RequestValues(loanId, fee), response -> {
            if(response.isSuccess()){
                nonReturnLoansController.showMessage(RETURN_BOOKS_SUCCESS);
                nonReturnLoansController.removeLoan(loanId);
            }

            else{
                nonReturnLoansController.showError("Thao tác thất bại. " + response.getMessage());
            }
        });
    }

    @Override
    public void start() {

    }
}
