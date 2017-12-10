package com.thopv.projects.libraryforreader.loan.domain.usecases;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/4/2017.
 */
public class LoadLoanDetailsTest {

    private Repository<Long, Loan> loanRepository;
    private Repository<Long, Book> bookRepository;
    private LoadLoanDetails loadLoanDetails;

    @Before
    public void setUp() throws Exception {
        loanRepository = mock(LoanRepository.class);
        bookRepository = mock(BookRepository.class);
        loadLoanDetails = new LoadLoanDetails(loanRepository, bookRepository);
    }
    @Test
    public void loadDetailsOfLoanNonExisting(){
        long loanId = TestUtils.genAbsRandomLong();
        when(loanRepository.findById(loanId)).thenReturn(null);
        loadLoanDetails.executeUseCase(new LoadLoanDetails.RequestValues(loanId), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadLoanDetails.INPUT_INVALID);
        });
    }
    @Test
    public void loadDetailsLoanExistingButBookInLoanIsNotAvailable(){
        Loan mLoan = TestUtils.genLoan();
        long loanId = mLoan.getLoanId();
        when(loanRepository.findById(loanId)).thenReturn(mLoan);
        when(bookRepository.findById(any())).thenReturn(null);

        loadLoanDetails.executeUseCase(new LoadLoanDetails.RequestValues(loanId), response -> {
            assertFalse(response.isSuccess());
            assertTrue(response.getMessage().contains(LoadLoanDetails.SOME_BOOK_NOT_AVAILABLE));
        });
    }
    @Test
    public void happyLoadDetailsLoanExisting(){
        Loan mLoan = TestUtils.genLoan();
        Book mBook = TestUtils.genBook();
        long loanId = mLoan.getLoanId();
        when(loanRepository.findById(loanId)).thenReturn(mLoan);
        when(bookRepository.findById(any())).thenReturn(mBook);

        loadLoanDetails.executeUseCase(new LoadLoanDetails.RequestValues(loanId), response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            assertNotNull(response.getPayload());
            List<Book> books = response.getPayload().getBooks();
            assertTrue(books.contains(mBook));
        });
    }
}