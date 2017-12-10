package com.thopv.projects.libraryforreader.home.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
public class LoadBooks extends UseCase<LoadBooks.RequestValues, LoadBooks.ResponseValues> {
    private Repository<Long, Book> bookRepository;
    public static final String BOOKS_EMPTY_ERR = "Don't have any books.";
    public LoadBooks(Repository<Long, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    synchronized protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<Book> books = bookRepository.fetchAll();
        if(books != null){
            callback.onCompleted(ComplexResponse.success(new ResponseValues(books)));
        }
        else{
            callback.onCompleted(ComplexResponse.fail(BOOKS_EMPTY_ERR));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {

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
