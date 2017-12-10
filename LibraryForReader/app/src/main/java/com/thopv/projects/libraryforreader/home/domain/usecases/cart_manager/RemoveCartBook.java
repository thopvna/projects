package com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

/**
 * Created by thopv on 12/2/2017.
 */
public class RemoveCartBook extends UseCase<RemoveCartBook.RequestValues, RemoveCartBook.ResponseValues> {
    public static final String INPUT_INVALID = "Don't have any cartbook matching.";
    private Repository<Long, CartBook> cartBookRepository;

    public RemoveCartBook(Repository<Long, CartBook> cartBookRepository) {
        this.cartBookRepository = cartBookRepository;
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long bookId = requestValues.getBookid();

        CartBook oldCartBook = cartBookRepository.findById(bookId);
        if(oldCartBook == null){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID));
        }
        else{
            CartBook cartBook = new CartBook(bookId);
            boolean success = cartBookRepository.delete(cartBook);
            callback.onCompleted(ComplexResponse.get(success));
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
