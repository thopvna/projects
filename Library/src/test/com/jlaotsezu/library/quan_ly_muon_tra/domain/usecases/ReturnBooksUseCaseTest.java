package test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.ReturnBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReturnBooksUseCaseTest {

    private ReturnBooksUseCase returnBooksUseCase;
    private Repository<Integer, BookCopy> bookCopyRepository;
    private Repository<Integer, Loan> loanRepository;

    @Before
    public void setUp() throws Exception {
        loanRepository = mock(Repository.class);
        bookCopyRepository = mock(Repository.class);
        returnBooksUseCase = new ReturnBooksUseCase(loanRepository, bookCopyRepository);
    }
    @Test
    public void returnBooksOfNonExistingLoan(){
        Loan loan = TestUtils.genLoan();
        int fee = 0;
        when(loanRepository.fetchById(loan.getLoanId())).thenReturn(null);

        returnBooksUseCase.setRequestValues(new ReturnBooksUseCase.RequestValues(loan.getLoanId(), fee));
        returnBooksUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ReturnBooksUseCase.DON_T_HAVE_ANY_LOANS_MATCHING);
            verify(loanRepository).fetchById(loan.getLoanId());
        });
        returnBooksUseCase.run();
    }
    @Test
    public void returnBOoksWithNegativeFee(){
        Loan loan = TestUtils.genLoan();
        int fee = -1;
        when(loanRepository.fetchById(loan.getLoanId())).thenReturn(loan);
        returnBooksUseCase.setRequestValues(new ReturnBooksUseCase.RequestValues(loan.getLoanId(), fee));
        returnBooksUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ReturnBooksUseCase.FEE_UNABLE_NEGATIVE);
        });
        returnBooksUseCase.run();
    }
    @Test
    public void returnBookOfExistingLoan_FeePositive_ButUpdateLoanStatusFailed(){
        Loan loan = TestUtils.genLoan();
        int fee = 1;
        when(loanRepository.fetchById(loan.getLoanId())).thenReturn(loan);
        when(loanRepository.update(any())).thenReturn(false);
        when(bookCopyRepository.updateAll(any())).thenReturn(true);

        returnBooksUseCase.setRequestValues(new ReturnBooksUseCase.RequestValues(loan.getLoanId(), fee));
        returnBooksUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ReturnBooksUseCase.RETURN_BOOKS_FAILED_INTERNAL_DB_ERROR);
            verify(loanRepository).update(any());
            verify(bookCopyRepository).updateAll(any());
        });
        returnBooksUseCase.run();
    }
    @Test
    public void returnBookOfExistingLoan_ButUpdateBookCopiesStatusFailed(){
        Loan loan = TestUtils.genLoan();
        int fee = 1;
        when(loanRepository.fetchById(loan.getLoanId())).thenReturn(loan);
        when(loanRepository.save(any())).thenReturn(true);
        when(bookCopyRepository.updateAll(any())).thenReturn(false);

        returnBooksUseCase.setRequestValues(new ReturnBooksUseCase.RequestValues(loan.getLoanId(), fee));
        returnBooksUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ReturnBooksUseCase.RETURN_BOOKS_FAILED_INTERNAL_DB_ERROR);
            verify(bookCopyRepository).updateAll(any());
        });
        returnBooksUseCase.run();
    }
    @Test
    public void returnBookOfExistingLoan_HappyUpdate(){
        Loan loan = TestUtils.genLoan();
        int fee = 1;
        when(loanRepository.fetchById(loan.getLoanId())).thenReturn(loan);
        when(loanRepository.update(any())).thenReturn(true);
        when(bookCopyRepository.updateAll(any())).thenReturn(true);

        returnBooksUseCase.setRequestValues(new ReturnBooksUseCase.RequestValues(loan.getLoanId(), fee));
        returnBooksUseCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(bookCopyRepository).updateAll(any());
            verify(loanRepository).update(any());
        });
        returnBooksUseCase.run();
    }
}