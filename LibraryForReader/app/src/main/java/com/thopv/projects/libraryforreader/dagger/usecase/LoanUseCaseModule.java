package com.thopv.projects.libraryforreader.dagger.usecase;

import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.loan.domain.usecases.CancelLoan;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoanDetails;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoans;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thopv on 12/2/2017.
 */
@Module
public class LoanUseCaseModule {
    @Provides
    LoadLoans loadLoans(LoanRepository loanRepository){
        return new LoadLoans(loanRepository);
    }
    @Provides
    CancelLoan cancelLoan(LoanRepository loanRepository){
        return new CancelLoan(loanRepository);
    }
    @Provides
    LoadLoanDetails loadLoanDetails(LoanRepository loanRepository, BookRepository bookRepository){
        return new LoadLoanDetails(loanRepository, bookRepository);
    }
}
