package com.thopv.projects.libraryforreader.loan.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
public class LoadLoans extends UseCase<LoadLoans.RequestValues, LoadLoans.ResponseValues> {
    public static final String LOANS_EMPTY_ERR = "Don't have any loans.";
    private Repository<Long, Loan> loanRepository;

    public LoadLoans(Repository<Long, Loan> loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<Loan> loans = loanRepository.fetchAll();

        if(loans != null){
            callback.onCompleted(ComplexResponse.success(new ResponseValues(loans)));
        }
        else{
            callback.onCompleted(ComplexResponse.fail("Don't have any loans."));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {

    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<Loan> loans;

        public ResponseValues(List<Loan> loans) {
            this.loans = loans;
        }

        public List<Loan> getLoans() {
            return loans;
        }
    }
}
