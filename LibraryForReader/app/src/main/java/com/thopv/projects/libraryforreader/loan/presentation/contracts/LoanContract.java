package com.thopv.projects.libraryforreader.loan.presentation.contracts;

import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import java.util.List;

/**
 * Created by thopv on 12/3/2017.
 */

public interface LoanContract {
    interface View{
        void showError(String err);
        void showMessage(String message);
        void showLoans(List<Loan> loans);
        void showLoanDetails(List<Book> books);
    }
    interface Presenter{
        void loadLoans();
        void cancelLoan(long loanId);
        void loadDetails(long loanId);
    }
}
