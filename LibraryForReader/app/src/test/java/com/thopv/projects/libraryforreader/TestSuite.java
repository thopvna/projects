package com.thopv.projects.libraryforreader;


import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatusTest;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBooksTest;
import com.thopv.projects.libraryforreader.home.domain.usecases.SearchBooksTest;
import com.thopv.projects.libraryforreader.home.domain.usecases.cart_manager.CartBookTest;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.FavoriteBookTest;
import com.thopv.projects.libraryforreader.home.presentation.presenters.BaseBookPresenterTest;
import com.thopv.projects.libraryforreader.home.presentation.presenters.BookPresenterTest;
import com.thopv.projects.libraryforreader.home.presentation.presenters.CartPresenterTest;
import com.thopv.projects.libraryforreader.home.presentation.presenters.FavoritePresenterTest;
import com.thopv.projects.libraryforreader.loan.domain.usecases.CancelLoanTest;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoanDetailsTest;
import com.thopv.projects.libraryforreader.loan.domain.usecases.LoadLoansTest;
import com.thopv.projects.libraryforreader.welcome.presentation.presenters.LoginPresenterTest;
import com.thopv.projects.libraryforreader.welcome.presentation.presenters.RegisterPresenterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by thopv on 12/3/2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CartBookTest.class,
        FavoriteBookTest.class,
        LoadBookOptionsMenuStatusTest.class,
        LoadBooksTest.class,
        SearchBooksTest.class,
        BaseBookPresenterTest.class,
        BookPresenterTest.class,
        CancelLoanTest.class,
        LoadLoansTest.class,
        LoginPresenterTest.class,
        RegisterPresenterTest.class,
        CartPresenterTest.class,
        FavoritePresenterTest.class,
        LoadLoanDetailsTest.class
})
public class TestSuite {

}
