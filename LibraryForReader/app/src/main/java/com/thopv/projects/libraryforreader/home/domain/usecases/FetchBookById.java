package com.thopv.projects.libraryforreader.home.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

/**
 * Created by thopv on 12/4/2017.
 */
public class FetchBookById extends UseCase<FetchBookById.RequestValues, FetchBookById.ResponseValues> {
    public static final String DON_T_HAVE_ANY_BOOK_MATCHING = "Don't have any book matching.";
    private Repository<Long, Book> bookRepository;

    public FetchBookById(Repository<Long, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long bookId = requestValues.getBookId();

        Book book = bookRepository.findById(bookId);

        if(book == null){
            callback.onCompleted(ComplexResponse.fail(DON_T_HAVE_ANY_BOOK_MATCHING));
        }
        else{
            callback.onCompleted(ComplexResponse.success(new ResponseValues(book)));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private long bookId;

        public long getBookId() {
            return bookId;
        }

        public RequestValues(long bookId) {
            this.bookId = bookId;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private Book book;

        public Book getBook() {
            return book;
        }

        public ResponseValues(Book book) {
            this.book = book;
        }
    }
}
