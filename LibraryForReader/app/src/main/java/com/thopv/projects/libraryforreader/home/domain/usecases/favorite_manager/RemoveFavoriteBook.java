package com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

/**
 * Created by thopv on 12/2/2017.
 */
public class RemoveFavoriteBook extends UseCase<RemoveFavoriteBook.RequestValues, RemoveFavoriteBook.ResponseValues> {
    public static final String INPUT_INVALID = "Don't have any favorite book matching.";
    private Repository<Long, FavoriteBook> favoriteBookRepository;

    public RemoveFavoriteBook(Repository<Long, FavoriteBook> favoriteBookRepository) {
        this.favoriteBookRepository = favoriteBookRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long bookId = requestValues.getBookid();

        FavoriteBook oldFavoriteBook = favoriteBookRepository.findById(bookId);

        if(oldFavoriteBook != null) {
            boolean success = favoriteBookRepository.delete(oldFavoriteBook);
            callback.onCompleted(ComplexResponse.get(success));
        }
        else{
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID));
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
