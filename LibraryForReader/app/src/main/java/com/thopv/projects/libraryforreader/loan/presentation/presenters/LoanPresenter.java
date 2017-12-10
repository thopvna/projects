package com.thopv.projects.libraryforreader.loan.presentation.presenters;

import com.thopv.projects.libraryforreader.loan.domain.usecases.CancelLoan;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoanDetails;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoans;
import com.thopv.projects.libraryforreader.loan.presentation.contracts.LoanContract;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

/**
 * Created by thopv on 12/3/2017.
 */

public class LoanPresenter implements LoanContract.Presenter {
    private LoanContract.View view;
    private UseCaseHandler useCaseHandler;
    private CancelLoan cancelLoan;
    private LoadLoans loadLoans;
    private LoadLoanDetails loadLoanDetails;
    public LoanPresenter(LoanContract.View view, UseCaseHandler useCaseHandler, CancelLoan cancelLoan, LoadLoans loadLoans, LoadLoanDetails loadLoanDetails) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.cancelLoan = cancelLoan;
        this.loadLoans = loadLoans;
        this.loadLoanDetails = loadLoanDetails;
    }

    @Override
    public void loadLoans() {
        useCaseHandler.execute(loadLoans, new LoadLoans.RequestValues(), response -> {
            if(response.isSuccess()){
                view.showLoans(response.getPayload().getLoans());
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void cancelLoan(long loanId) {
        useCaseHandler.execute(cancelLoan, new CancelLoan.RequestValues(loanId), response -> {
            if(response.isSuccess()){
                view.showMessage("Cancel Loan success");
                loadLoans();
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void loadDetails(long loanId) {
        useCaseHandler.execute(loadLoanDetails, new LoadLoanDetails.RequestValues(loanId), response -> {
            if(response.isSuccess()){
                view.showLoanDetails(response.getPayload().getBooks());
            }
            else{
                view.showError("Operation failed. " + response.getMessage());
            }
        });
    }

}
