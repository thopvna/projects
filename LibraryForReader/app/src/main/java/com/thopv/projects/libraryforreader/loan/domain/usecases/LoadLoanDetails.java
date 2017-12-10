package com.thopv.projects.libraryforreader.loan.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/4/2017.
 */
public class LoadLoanDetails extends UseCase<LoadLoanDetails.RequestValues, LoadLoanDetails.ResponseValues> {
    public static final String INPUT_INVALID = "Don't have any loan matching.";
    public static final String SOME_BOOK_NOT_AVAILABLE = "Some book is not available.";
    public static final String LOAN_EMPTY_ERR = "Don't have any book in Loan ?";
    private Repository<Long, Loan> loanRepository;
    private Repository<Long, Book> bookRepository;

    public LoadLoanDetails(Repository<Long, Loan> loanRepository, Repository<Long, Book> bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long loanId = requestValues.getLoanId();
        Loan loan = loanRepository.findById(loanId);
        if(loan == null){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID));
        }
        else{
            List<Book> books = new LinkedList<>();
            List<Long> bookIds = loan.getBookIds();

            if(bookIds == null || bookIds.size() == 0){
                callback.onCompleted(ComplexResponse.fail(LOAN_EMPTY_ERR));
            }
            else{
                for(long bookId : bookIds){
                    Book book = bookRepository.findById(bookId);
                    if(book != null) {
                        books.add(book);
                    }
                    else{
                        callback.onCompleted(ComplexResponse.fail(SOME_BOOK_NOT_AVAILABLE + bookId));
                        return;
                    }
                }
                callback.onCompleted(ComplexResponse.success(new ResponseValues(books)));
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
        private List<Book> books;

        public ResponseValues(List<Book> books) {
            this.books = books;
        }

        public List<Book> getBooks() {
            return books;
        }
    }
}
