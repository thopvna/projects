package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.mappers;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.LoanModel;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.support.utils.DateUtils;

public class LoanMapper implements Mapper<Loan, LoanModel> {
    @Override
    public LoanModel map(Loan loan) {
        return new LoanModel.Builder()
                .setLoanId(loan.getLoanId())
                .setBookCopyCount(loan.getBookCopyCount())
                .setBorrowerId(loan.getBorrower().getUserId())
                .setBorrowerFullName(loan.getBorrower().getFullName())
                .setBorrowerUserName(loan.getBorrower().getUserName())
                .setLibrarianId(loan.getLibrarian().getUserId())
                .setLibrarianUserName(loan.getLibrarian().getUserName())
                .setLibrarianFullName(loan.getLibrarian().getFullName())
                .setBornTime(loan.getBornTime() > 0 ? DateUtils.getBasicDate(loan.getBornTime()) : "--/--/----")
                .setExpiredTime(loan.getExpiredTime() > 0 ? DateUtils.getBasicDate(loan.getExpiredTime()) : "--/--/----")
                .setFee(loan.getFee())
                .setStatus(loan.isReturn() ? "Đã trả" : "Chưa trả")
                .build();
    }
}
