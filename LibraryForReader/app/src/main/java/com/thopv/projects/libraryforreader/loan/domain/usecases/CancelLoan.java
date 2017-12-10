package com.thopv.projects.libraryforreader.loan.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

/**
 * Created by thopv on 12/3/2017.
 */
public class CancelLoan extends UseCase<CancelLoan.RequestValues, CancelLoan.ResponseValues> {
    public static final String INPUT_INVALID = "Don't have any loan matching.";
    public static final String LOAN_WAS_LEND_ERR = "Loan was lend, Unable cancel.";

    public CancelLoan(Repository<Long, Loan> loanRepository) {
        this.loanRepository = loanRepository;
    }

    private Repository<Long, Loan> loanRepository;
    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long loanId = requestValues.getLoanId();
        Loan loan = loanRepository.findById(loanId);
        if(loan == null){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID));
        }
        else{
            if(loan.isWasLend()){
                callback.onCompleted(ComplexResponse.fail(LOAN_WAS_LEND_ERR));
            }
            else {
                boolean success = loanRepository.delete(loan);
                callback.onCompleted(ComplexResponse.get(success));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private long loanId;

        public long getLoanId() {
            return loanId;
        }

        public RequestValues(long loanId) {
            this.loanId = loanId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
