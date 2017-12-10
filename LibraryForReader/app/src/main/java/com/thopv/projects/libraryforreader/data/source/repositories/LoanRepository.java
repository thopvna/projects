package com.thopv.projects.libraryforreader.data.source.repositories;

import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.Specification;
import com.thopv.projects.libraryforreader.data.source.daos.LoanDAO;
import com.thopv.projects.libraryforreader.data.source.schemas.SLoan;
import com.thopv.projects.libraryforreader.data.source.schemas.SLoanDetail;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class LoanRepository implements Repository<Long, Loan> {
    private AppDatabase appDatabase;
    private LoanDAO loanDAO;
    public LoanRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        loanDAO = appDatabase.getLoanDAO();
    }

    @Override
    public boolean save(Loan loan) {
        SLoan sloan = new SLoan(loan);
        long loanId = loanDAO.insert(sloan);
        if(loanId <= 0) return false;

        List<SLoanDetail> sLoanDetails = getLoanDetails(loan, loanId);
        return loanDAO.insertDetails(sLoanDetails).length == sLoanDetails.size();
    }
    private List<SLoanDetail> getLoanDetails(Loan loan, long loanId){
        List<SLoanDetail> sLoanDetails = new LinkedList<>();
        for(long bookId : loan.getBookIds()){
            SLoanDetail sLoanDetail = new SLoanDetail(loanId, bookId);
            sLoanDetails.add(sLoanDetail);
        }
        return sLoanDetails;
    }
    @Override
    public boolean delete(Loan loan) {
        return loanDAO.delete(loan.getLoanId()) > 0 && loanDAO.deleteDetailsOf(loan.getLoanId()) > 0;
    }

    @Override
    public boolean clearAll() {
        return loanDAO.clearAll() > 0 && loanDAO.clearAllDetail() > 0;
    }

    @Override
    public List<Loan> fetchAll() {
        List<SLoan> sLoans = loanDAO.fetchAll();
        return getLoans(sLoans);
    }

    @Override
    public List<Loan> find(Specification specification) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Loan findById(Long aLong) {
        return getLoan(loanDAO.fetchById(aLong));
    }

    @Override
    public void commitTransaction() {
        appDatabase.setTransactionSuccessful();
    }

    @Override
    public void runInTransacstion(Runnable runnable) {
        appDatabase.beginTransaction();

        runnable.run();

        appDatabase.endTransaction();
    }

    private List<Loan> getLoans(List<SLoan> sLoans){
        List<Loan> loans = new LinkedList<>();
        for(SLoan sLoan : sLoans){
            loans.add(getLoan(sLoan));
        }
        return loans;
    }
    private Loan getLoan(SLoan sLoan){
        List<SLoanDetail> sLoanDetails = loanDAO.fetchDetailsOf(sLoan.getLoanId());
        List<Long> bookIds = new LinkedList<>();
        for(SLoanDetail sLoanDetail : sLoanDetails){
            bookIds.add(sLoanDetail.getBookId());
        }
        return buildLoan(sLoan, bookIds);
    }

    private Loan buildLoan(SLoan sLoan, List<Long> bookIds) {
        return new Loan.Builder()
                .setBookIds(bookIds)
                .setBornTime(sLoan.getBornTime())
                .setFee(sLoan.getFee())
                .setLoanId(sLoan.getLoanId())
                .setReturnTime(sLoan.getReturnTime())
                .setReturnTimeExpected(sLoan.getReturnTimeExpected())
                .setStartTime(sLoan.getStartTime())
                .setWasLend(sLoan.isWasLend())
                .setWasReturn(sLoan.isWasReturn())
                .build();
    }

}
