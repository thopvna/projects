package com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
public class LoadFavorites extends UseCase<LoadFavorites.RequestValues, LoadFavorites.ResponseValues> {
    public final static String FAVORITES_EMPTY_ERR = "Don't have any favorite books.";
    private Repository<Long, FavoriteBook> favoriteBookRepository;
    private Repository<Long, Book> bookRepository;

    public LoadFavorites(Repository<Long, FavoriteBook> favoriteBookRepository, Repository<Long, Book> bookRepository) {
        this.favoriteBookRepository = favoriteBookRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<FavoriteBook> favoriteBooks = favoriteBookRepository.fetchAll();
        List<Book> books = new LinkedList<>();
        if(favoriteBooks != null){
            for(FavoriteBook favoriteBook : favoriteBooks){
                Book book = bookRepository.findById(favoriteBook.getBookId());
                if(book != null){
                    books.add(book);
                }
            }
            callback.onCompleted(ComplexResponse.success(new ResponseValues(books)));
        }
        else{
            callback.onCompleted(ComplexResponse.fail(FAVORITES_EMPTY_ERR));
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
