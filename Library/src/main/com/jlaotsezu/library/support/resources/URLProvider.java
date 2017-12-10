package main.com.jlaotsezu.library.support.resources;

import main.com.jlaotsezu.library.home.views.HomeController;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.supports.BorrowCardsSelectionController;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.views.LoginController;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.presentation.views.RegisterController;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.supports.BooksSelectionDialogController;

import java.net.URL;

public class URLProvider {
    private static URL loginViewURL = LoginController.class.getResource("login.fxml");
    private static URL registerViewURL = RegisterController.class.getResource("register.fxml");
    private static URL homeViewURL = HomeController.class.getResource("home.fxml");
    private static URL booksSelectionViewURL = BooksSelectionDialogController.class.getResource("books_selection_dialog.fxml");
    private static URL borrowCardsSelectionViewURL= BorrowCardsSelectionController.class.getResource("borrow_card_selection.fxml");
    public static URL getLoginURL(){
        return loginViewURL;
    }
    public static URL getRegisterURL(){
        return registerViewURL;
    }
    public static URL getHomeURL(){
        return homeViewURL;
    }

    public static URL getBooksSelectionURL() {
        return booksSelectionViewURL;
    }

    public static URL getBorrowCardsSelectionViewURL() {
        return borrowCardsSelectionViewURL;
    }
}
