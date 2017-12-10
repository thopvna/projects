package com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager;

import android.util.Log;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

/**
 * Created by thopv on 12/2/2017.
 */
public class InsertCartBook extends UseCase<InsertCartBook.RequestValues, InsertCartBook.ResponseValues> {
    public static final String INPUT_INVALID = "Don't have any book matching.";
    public static final String BOOK_ADREADY_INCART = "Book adreadly live in Cart.";
    private static final String INSERT_FAIL_ERR = "Insert cart book fail.";
    private Repository<Long, CartBook> cartBookRepository;
    private Repository<Long, Book> bookRepository;
    public InsertCartBook(Repository<Long, CartBook> cartBookRepository, Repository<Long, Book> bookRepository) {
        this.cartBookRepository = cartBookRepository;
        this.bookRepository = bookRepository;
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long bookId = requestValues.getBookid();

        Book book = bookRepository.findById(bookId);

        if(book == null){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID));
        }
        else{
            CartBook oldCartBook = cartBookRepository.findById(bookId);
            if(oldCartBook != null) {
                callback.onCompleted(ComplexResponse.fail(BOOK_ADREADY_INCART));
            }
            else{
                CartBook cartBook = new CartBook(bookId);
                boolean success = cartBookRepository.save(cartBook);
                if(success){
                    callback.onCompleted(ComplexResponse.success());
                }
                else{
                    callback.onCompleted(ComplexResponse.fail(INSERT_FAIL_ERR));
                }
            }
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private long bookid;

        public long getBookid() {
            return bookid;
        }

        public RequestValues(long bookid) {
            this.bookid = bookid;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
