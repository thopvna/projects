package com.thopv.projects.libraryforreader.home.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

/**
 * Created by thopv on 12/3/2017.
 */
public class LoadBookOptionsMenuStatus extends UseCase<LoadBookOptionsMenuStatus.RequestValues, LoadBookOptionsMenuStatus.ResponseValues> {
    public static final String INPUT_INVALID = "Input invalid.";
    private Repository<Long, CartBook> cartBookRepository;
    private Repository<Long, FavoriteBook> favoriteBookRepository;

    public LoadBookOptionsMenuStatus(Repository<Long, CartBook> cartBookRepository, Repository<Long, FavoriteBook> favoriteBookRepository) {
        this.cartBookRepository = cartBookRepository;
        this.favoriteBookRepository = favoriteBookRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        long bookId = requestValues.getBookId();

        if(bookId < 0){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID));
            return;
        }

        FavoriteBook favoriteBook = favoriteBookRepository.findById(bookId);
        CartBook cartBook = cartBookRepository.findById(bookId);

        BookStatus bookStatus = new BookStatus(bookId);

        bookStatus.setInFavorite(favoriteBook != null);
        bookStatus.setInCart(cartBook != null);

        callback.onCompleted(ComplexResponse.success(new ResponseValues(bookStatus)));
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
        private BookStatus bookStatus;

        public ResponseValues(BookStatus bookStatus) {
            this.bookStatus = bookStatus;
        }

        public BookStatus getBookStatus() {
            return bookStatus;
        }
    }
}
