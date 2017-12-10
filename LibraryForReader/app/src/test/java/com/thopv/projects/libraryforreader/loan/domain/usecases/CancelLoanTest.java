package com.thopv.projects.libraryforreader.loan.domain.usecases;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/3/2017.
 */
public class CancelLoanTest {

    CancelLoan cancelLoan;
    LoanRepository loanRepository;

    @Before
    public void setUp() throws Exception {
        loanRepository = mock(LoanRepository.class);
        cancelLoan = new CancelLoan(loanRepository);
    }
    @Test
    public void cancelNonExistingLoan(){
        long loanId = TestUtils.genAbsRandomLong();
        when(loanRepository.findById(loanId)).thenReturn(null);
        cancelLoan.executeUseCase(new CancelLoan.RequestValues(loanId), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), CancelLoan.INPUT_INVALID);
        });
    }
    @Test
    public void cancelWasLendLoanThenGetErr(){
        Loan loan = TestUtils.genLoan();
        loan.setWasLend(true);
        when(loanRepository.findById(loan.getLoanId())).thenReturn(loan);
        cancelLoan.executeUseCase(new CancelLoan.RequestValues(loan.getLoanId()), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), CancelLoan.LOAN_WAS_LEND_ERR);
        });
    }
    @Test
    public void cancelWasNotLendLoan(){
        Loan loan = TestUtils.genLoan();
        loan.setWasLend(false);
        when(loanRepository.findById(loan.getLoanId())).thenReturn(loan);
        when(loanRepository.delete(any())).thenReturn(true);

        cancelLoan.executeUseCase(new CancelLoan.RequestValues(loan.getLoanId()), response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(loanRepository).delete(eq(loan));
        });
    }
}