package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts;

import main.com.jlaotsezu.library.support.architecture.BasePresenter;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;

import java.util.List;

public interface NonReturnLoansContract {
    interface Controller extends BaseController {
        void showLoans(List<Loan> loans);
        void removeLoan(int loanId);
    }
    interface Presenter extends BasePresenter{
       void loadNonReturnLoans(String keyword);
       void returnBooks(int loanId, long fee);
    }
}
