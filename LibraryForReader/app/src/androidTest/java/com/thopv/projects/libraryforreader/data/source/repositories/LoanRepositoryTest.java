package com.thopv.projects.libraryforreader.data.source.repositories;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Author;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.Classification;
import com.thopv.projects.libraryforreader.home.domain.entity.Publisher;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

/**
 * Created by thopv on 12/2/2017.
 */
public class LoanRepositoryTest {
    private Repository<Long, Loan> loanRepository;
    private Repository<Long, Book> bookRepository;
    private Random random;
    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        loanRepository = new LoanRepository(appDatabase);
        bookRepository = new BookRepository(appDatabase);
        random = new Random();
    }
    @Test
    public void insert_fetchLoan(){
        Loan mLoan = TestUtils.genLoan();
        loanRepository.save(mLoan);
        List<Loan> loans = loanRepository.fetchAll();
        assertNotNull(loans);
        assertTrue(loans.size() > 0);
        assertTrue(loansContainLoan(loans, mLoan));
    }
    @Test
    public void insertMoreLoans(){
        loanRepository.clearAll();
        List<Loan> mLoans = TestUtils.genLoans(20);
        boolean success = true;

        List<Book> books = bookRepository.fetchAll();
        List<Long> bookIds = new LinkedList<>();
        for(Book book : books){
            bookIds.add(book.getBookId());
        }

        for(Loan mLoan : mLoans){
            mLoan.setBookIds(bookIds);
            boolean piece = loanRepository.save(mLoan);
            if(!piece)
                success = false;
        }
        assertTrue(success);
    }
    public boolean loansContainLoan(List<Loan> loans, Loan mLoan){
        for(Loan loan : loans){
            if(loan.getBornTime() == mLoan.getBornTime()){
                return true;
            }
        }
        return false;
    }
}