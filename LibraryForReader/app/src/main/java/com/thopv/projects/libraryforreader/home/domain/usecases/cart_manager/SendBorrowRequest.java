package com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import junit.framework.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
public class SendBorrowRequest extends UseCase<SendBorrowRequest.RequestValues, SendBorrowRequest.ResponseValues> {
    public static final String CART_EMPTY_ERR = "Don't have any book in cart";
    private Repository<Long, CartBook> cartBookRepository;
    private Repository<Long, Loan> loanRepository;

    public SendBorrowRequest(Repository<Long, CartBook> cartBookRepository, Repository<Long, Loan> loanRepository) {
        this.cartBookRepository = cartBookRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<CartBook> cartBooks = cartBookRepository.fetchAll();
        if(cartBooks == null || cartBooks.size() <= 0){
            callback.onCompleted(ComplexResponse.fail(CART_EMPTY_ERR));
            return;
        }
        List<Long> bookIds = new LinkedList<>();
        for(CartBook cartBook : cartBooks){
            bookIds.add(cartBook.getBookId());
        }
        Loan loan = new Loan.Builder().setBookIds(bookIds).setBornTime(System.currentTimeMillis()).build();

        loanRepository.runInTransacstion(() -> {
            boolean success = loanRepository.save(loan) && cartBookRepository.clearAll();
            if(success)
                loanRepository.commitTransaction();
            callback.onCompleted(ComplexResponse.get(success));
        });
    }

    public static class RequestValues extends UseCase.RequestValues {

    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
