package com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
public class LoadCart extends UseCase<LoadCart.RequestValues, LoadCart.ResponseValues> {
    public static final String CART_EMPTY_ERR = "Don't have any books in cart.";

    public LoadCart(Repository<Long, CartBook> cartBookRepository, Repository<Long, Book> bookRepository) {
        this.cartBookRepository = cartBookRepository;
        this.bookRepository = bookRepository;
    }

    private Repository<Long, CartBook> cartBookRepository;
    private Repository<Long, Book> bookRepository;

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<CartBook> cartBooks = cartBookRepository.fetchAll();
        List<Book> books = new LinkedList<>();
        if(cartBooks != null){
            for(CartBook cartBook : cartBooks){
                Book book = bookRepository.findById(cartBook.getBookId());
                if(book != null){
                    books.add(book);
                }
            }
            callback.onCompleted(ComplexResponse.success(new ResponseValues(books)));
        }
        else{
            callback.onCompleted(ComplexResponse.fail(CART_EMPTY_ERR));
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
