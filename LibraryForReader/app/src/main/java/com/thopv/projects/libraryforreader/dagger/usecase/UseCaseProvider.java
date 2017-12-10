package com.thopv.projects.libraryforreader.dagger.usecase;

import android.content.Context;

import com.thopv.projects.libraryforreader.dagger.repository.DaggerRepositoryComponent;
import com.thopv.projects.libraryforreader.dagger.repository.RepositoryComponent;
import com.thopv.projects.libraryforreader.dagger.repository.RepositoryModule;
import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBooks;
import com.thopv.projects.libraryforreader.home.domain.usecases.SearchBooks;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.InsertCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.LoadCart;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.RemoveCartBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.SendBorrowRequest;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.LoadFavorites;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;
import com.thopv.projects.libraryforreader.loan.domain.usecases.CancelLoan;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoanDetails;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoans;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Login;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Register;

import dagger.Component;

/**
 * Created by jlaotsezu on 23/11/2017.
 */
@UseCaseScope
@Component(modules = {UserUseCaseModule.class, BookUseCaseModule.class, CartUseCaseModule.class, FavoriteUseCaseModule.class, LoanUseCaseModule.class}, dependencies = {RepositoryComponent.class})
public interface UseCaseProvider {
    Login getLogin();
    Register getRegister();

    LoadLoans getLoadLoans();
    CancelLoan getCancelLoan();
    LoadLoanDetails getLoadLoanDetails();

    LoadBooks getLoadBooks();
    SearchBooks getSearchBooks();
    LoadBookOptionsMenuStatus getLoadBookOptionsMenuStatus();
    FetchBookById getFetchBOokById();

    InsertCartBook getInsertCartBook();
    LoadCart getLoadCart();
    RemoveCartBook getRemoveCartBook();
    SendBorrowRequest getSendBorrowRequest();

    InsertFavoriteBook getInsertFavoriteBook();
    LoadFavorites getLoadFavorites();
    RemoveFavoriteBook getRemoveFavoriteBook();


    static UseCaseProvider getInstance(Context context){
        RepositoryComponent repositoryComponent = DaggerRepositoryComponent.builder()
                .repositoryModule(new RepositoryModule(context))
                .build();
        return DaggerUseCaseProvider.builder()
                .repositoryComponent(repositoryComponent)
                .build();
    }
}
