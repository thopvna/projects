package main.com.jlaotsezu.library.home.views;

import com.google.gson.Gson;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.views.BorrowBooksController;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.views.BorrowBooksRequestController;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.views.NonReturnLoansController;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.views.BookImportRequestsController;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.views.BooksController;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.views.RequestImportBooksController;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.views.IssueBorrowCardController;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.views.MaintainBorrowCardController;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.home.contracts.HomeContract;
import main.com.jlaotsezu.library.support.fx.FXController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.resources.URLProvider;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static main.com.jlaotsezu.library.home.views.HomeNavigator.*;

public class HomeController implements HomeContract.Controller, ParentController{

    public Label logoutButton;
    public TreeView navigateTreeView;
    public Label userNameView;
    public Pane contentView;

    private User librarian;
    @Autowired
    @Qualifier("homePresenter")
    HomeContract.Presenter presenter;
    private App app;
    private Intent intent;

    public void onLogoutButtonClicked(MouseEvent mouseEvent) {
        DialogUtils.showConfirm("Xác nhận đăng xuất", "Bạn chắc chắn muốn đăng xuất ?", () -> {
            Intent intent = new Intent(URLProvider.getLoginURL());
            intent.putExtra("user", new Gson().toJson(librarian));
            app.startController(intent);
        });
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Thông báo", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi", error);
    }

    private Parent viewContainer;

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);
        userNameView.setText(librarian.getUserName());
        initNavigateTree();
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }

    private void initNavigateTree() {
        HomeNavigator homeNavigator = new HomeNavigator(navigateTreeView);
        for(String name : getUrls().keySet()){
            URL url = getUrls().get(name);
            if(url != null) {
                intent.setUrl(url);
                ChildrenController controller = app.loadController(intent);
                controller.onInjectParentController(this);
                controllers.put(name, controller);
            }
        }
        homeNavigator.setOnContentChange(controllerName -> {
            ChildrenController childrenController = controllers.get(controllerName);
            if(childrenController != null) {
                contentView.getChildren().clear();
                contentView.getChildren().add(childrenController.getViewContainer());
            }
        });
    }

    private static Map<String, ChildrenController> controllers = new HashMap<>();

    @Override
    public Map<String, ChildrenController> getChilds() {
        return controllers;
    }

    @Override
    public ChildrenController getChild(String name){
        if(controllers.containsKey(name)){
            return controllers.get(name);
        }
        return null;
    }

    private static Map<String, URL> urls;

    public static Map<String, URL> getUrls(){
        if(urls == null){
            urls = new HashMap<>();
            urls.put(CAP_THE_MUON, IssueBorrowCardController.class.getResource("issue_borrow_card.fxml"));
            urls.put(BAO_TRI_THE_MUON, MaintainBorrowCardController.class.getResource("maintain_borrow_card.fxml"));

            urls.put(DANH_SACH_REQUEST_NHAP_SACH, BookImportRequestsController.class.getResource("book_import_requests.fxml"));
            urls.put(YEU_CAU_NHAP_SACH, RequestImportBooksController.class.getResource("request_import_books.fxml"));
            urls.put(DANH_SACH_BOOKS, BooksController.class.getResource("books.fxml"));

            urls.put(DANH_SACH_LOAN_CHUA_TRA, NonReturnLoansController.class.getResource("non_return_loans.fxml"));
            urls.put(DANH_SACH_REQUESTS_MUON_SACH, BorrowBooksRequestController.class.getResource("borrow_books_request.fxml"));
            urls.put(CHO_MUON_SACH, BorrowBooksController.class.getResource("borrow_books.fxml"));
        }
        return urls;
    }

    public static URL getURL(String name) {
        return getUrls().get(name);
    }
}
