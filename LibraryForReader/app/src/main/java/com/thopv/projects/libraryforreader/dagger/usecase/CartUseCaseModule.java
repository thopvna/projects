package com.thopv.projects.libraryforreader.dagger.usecase;

import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.CartBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.LoadCart;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.SendBorrowRequest;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jlaotsezu on 23/11/2017.
 */
@Module
public class CartUseCaseModule {
    @Provides
    InsertCartBook insertCartBook(CartBookRepository cartBookRepository, BookRepository bookRepository){
        return new InsertCartBook(cartBookRepository, bookRepository);
    }
    @Provides
    LoadCart loadCart(CartBookRepository cartBookRepository, BookRepository bookRepository){
        return new LoadCart(cartBookRepository, bookRepository);
    }
    @Provides
    RemoveCartBook removeCartBook(CartBookRepository cartBookRepository){
        return new RemoveCartBook(cartBookRepository);
    }
    @Provides
    SendBorrowRequest sendBorrowRequest(CartBookRepository cartBookRepository, LoanRepository loanRepository){
        return new SendBorrowRequest(cartBookRepository, loanRepository);
    }
}
