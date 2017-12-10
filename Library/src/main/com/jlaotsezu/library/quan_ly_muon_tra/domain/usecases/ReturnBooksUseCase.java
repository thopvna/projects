package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopyStatus;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReturnBooksUseCase extends UseCase<ReturnBooksUseCase.RequestValues, ReturnBooksUseCase.ResponseValues> {
    public static final String DON_T_HAVE_ANY_LOANS_MATCHING = "Không có Loan nào khớp.";
    public static final String RETURN_BOOKS_FAILED_INTERNAL_DB_ERROR = "Trả sách thất bại. Lỗi cơ sở dữ liệu.";
    public static final String FEE_UNABLE_NEGATIVE = "Fee không thể là số âm.";
    private Repository<Integer, Loan> loanRepository;
    private Repository<Integer, BookCopy> bookCopyRepository;
    @Autowired
    public ReturnBooksUseCase(Repository<Integer, Loan> loanRepository, Repository<Integer, BookCopy> bookCopyRepository) {
        this.loanRepository = loanRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        int loanId = requestValues.getLoanId();
        long fee = requestValues.getFee();

        Loan loan = loanRepository.fetchById(loanId);
        if(fee < 0){
            callback.onCompleted(ComplexResponse.fail(FEE_UNABLE_NEGATIVE));
        }
        else if(loan == null){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_LOANS_MATCHING));
        }
        else{
            List<BookCopy> bookCopies = loan.getBookCopies();
            for(BookCopy bookCopy : bookCopies){
                bookCopy.setBookCopyStatus(BookCopyStatus.BORROWABLE);
            }
            loan.setReturn(true);
            loan.setFee(fee);

            boolean success = bookCopyRepository.updateAll(bookCopies) && loanRepository.update(loan);

            if(success){
                callback.onCompleted(ComplexResponse.success());
            }
            else{
                callback.onCompleted(ComplexResponse.fail(RETURN_BOOKS_FAILED_INTERNAL_DB_ERROR));
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private int loanId;
        private long fee;

        public RequestValues(int loanId, long fee) {
            this.loanId = loanId;
            this.fee = fee;
        }

        public int getLoanId() {
            return loanId;
        }

        public long getFee() {
            return fee;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}