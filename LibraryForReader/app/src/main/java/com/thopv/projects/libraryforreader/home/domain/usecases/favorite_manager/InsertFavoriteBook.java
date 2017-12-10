package com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

/**
 * Created by thopv on 12/2/2017.
 */
public class InsertFavoriteBook extends UseCase<InsertFavoriteBook.RequestValues, InsertFavoriteBook.ResponseValues> {
    public static final String INPUT_INVALID = "Don't have any book matching.";
    public static final String BOOK_ADREADY_INFAVORITES = "Book adready live in favorites.";
    private Repository<Long, FavoriteBook> favoriteBookRepository;
    private Repository<Long, Book> bookRepository;
    public InsertFavoriteBook(Repository<Long, FavoriteBook> favoriteBookRepository, Repository<Long, Book> bookRepository) {
        this.favoriteBookRepository = favoriteBookRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long bookId = requestValues.getBookId();

        Book book = bookRepository.findById(bookId);

        if(book == null) {
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID));
        }
        else{
            FavoriteBook oldFavoriteBook = favoriteBookRepository.findById(bookId);
            if(oldFavoriteBook == null) {
                FavoriteBook favoriteBook = new FavoriteBook(bookId);
                boolean success = favoriteBookRepository.save(favoriteBook);
                callback.onCompleted(ComplexResponse.get(success));
            }
            else{
                callback.onCompleted(ComplexResponse.fail(BOOK_ADREADY_INFAVORITES));
            }
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

    }
}
