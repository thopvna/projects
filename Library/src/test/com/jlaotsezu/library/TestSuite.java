package test.com.jlaotsezu.library;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.com.jlaotsezu.library.data.source.db.*;
import test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.*;
import test.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters.BorrowBooksPresenterTest;
import test.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters.BorrowBooksRequestsPresenterTest;
import test.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters.NonReturnLoansPresenterTest;
import test.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.usecases.LoginUseCaseTest;
import test.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.presenters.LoginPresenterTest;
import test.com.jlaotsezu.library.quan_ly_sach.domain.usecases.*;
import test.com.jlaotsezu.library.quan_ly_sach.presentation.presenters.BookImportRequestsPresenterTest;
import test.com.jlaotsezu.library.quan_ly_sach.presentation.presenters.BooksPresenterTest;
import test.com.jlaotsezu.library.quan_ly_sach.presentation.presenters.RequestImportBooksPresenterTest;
import test.com.jlaotsezu.library.quan_ly_the_muon.domain.usecases.*;
import test.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters.IssueBorrowCardPresenterTest;
import test.com.jlaotsezu.library.quan_ly_the_muon.presentation.presenters.MaintainBorrowCardPresenterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
//      Data Source DB
        BookCopyDAOImplTest.class,
        BookDAOImplTest.class,
        BookImportRequestDAOImplTest.class,
        BorrowBooksRequestDAOImplTest.class,
        BorrowCardDAOImplTest.class,
        LoanDAOImplTest.class,
        UserDAOImplTest.class,
//
        ConfirmBorrowBooksRequestsUseCaseTest.class,
        FetchBookCopyUseCaseTest.class,
        FetchBorrowBooksRequestsUseCaseTest.class,
        LoadNonReturnLoansUseCaseTest.class,
        NewMannerBorrowBooksUseCaseTest.class,
        ReturnBooksUseCaseTest.class,
        SearchBookCopiesUseCaseTest.class,
//
        BorrowBooksPresenterTest.class,
        BorrowBooksRequestsPresenterTest.class,
        NonReturnLoansPresenterTest.class,
//
        LoginUseCaseTest.class,
//
        LoginPresenterTest.class,
//
        CancelBookImportRequestUseCaseTest.class,
        LoadBookImportRequestsUseCaseTest.class,
        LoadBooksUseCaseTest.class,
        RequestImportBooksUseCaseTest.class,
        SearchBooksUseCaseTest.class,
//
        BookImportRequestsPresenterTest.class,
        BooksPresenterTest.class,
        RequestImportBooksPresenterTest.class,
//
        ActiveBorrowCardUseCaseTest.class,
        DisableBorrowCardsUseCaseTest.class,
        DisableBorrowCardUseCaseTest.class,
        ExtendBorrowCardUseCaseTest.class,
        FetchBorrowCardsUseCaseTest.class,
        FindBorrowerUseCaseTest.class,
        IssueBorrowCardUseCaseTest.class,
//
        IssueBorrowCardPresenterTest.class,
        MaintainBorrowCardPresenterTest.class
})
public class TestSuite {

}
