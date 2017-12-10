package main.com.jlaotsezu.library.quan_ly_sach.presentation.views;

import com.google.gson.Gson;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.home.views.HomeNavigator;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.BooksContract;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookModel;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.supports.BooksTableView;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;

import static main.com.jlaotsezu.library.home.views.HomeNavigator.YEU_CAU_NHAP_SACH;
import static org.junit.Assert.assertFalse;

public class BooksController implements BooksContract.Controller, ChildrenController{
    public TableView<BookModel> tableView;
    public TextField keywordField;
    public ImageView searchButton;

    public TableColumn<BookModel, Integer> sttColumn;
    public TableColumn<BookModel, Integer> bookIdColumn;
    public TableColumn<BookModel, String> bookNameColumn;
    public TableColumn<BookModel, String> authorColumn;
    public TableColumn<BookModel, String> classificationColumn;
    public TableColumn<BookModel, String> publisherColumn;
    public TableColumn<BookModel, Integer> publishYearColumn;

    private BooksTableView booksTableView;

    @Autowired
    Mapper<Book, BookModel> bookMapper;

    private App app;
    private Intent intent;
    private User librarian;

    @Autowired
    BooksContract.Presenter booksPresenter;

    private Parent viewContainer;

    public Parent getViewContainer() {
        return viewContainer;
    }
    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);

        initTableView();
        booksPresenter.loadBooks();
    }

    @Override
    public void onHasMessage(Intent intent) {

    }


    private void initTableView() {
        booksTableView = new BooksTableView.Builder(tableView)
                .setSttColumn(sttColumn)
                .setBookIdColumn(bookIdColumn)
                .setBookNameColumn(bookNameColumn)
                .setAuthorsColumn(authorColumn)
                .setClassificationColumn(classificationColumn)
                .setPublisherColumn(publisherColumn)
                .setPublishYearColumn(publishYearColumn)
                .build();

        ContextMenu menu = new ContextMenu();
        MenuItem addToRequestItem = new MenuItem("Thêm vào yêu cầu nhập sách");
        addToRequestItem.setOnAction(event -> {
            ChildrenController rIB_Controller = parentController.getChild(HomeNavigator.YEU_CAU_NHAP_SACH);
            Intent intent = new Intent(null);
            intent.putExtra("books", new Gson().toJson(booksTableView.getSelectionItems()));
            rIB_Controller.onHasMessage(intent);
        });
        menu.getItems().add(addToRequestItem);
        tableView.setContextMenu(menu);
    }

    public void onSearchButtonClicked(MouseEvent mouseEvent) {
        booksPresenter.searchBooks(keywordField.getText());
    }

    @Override
    public void showBooks(List<Book> books) {
        booksTableView.updateData(books);
    }

    @Override
    public void showSearchResult(List<Book> books) {
        booksTableView.updateData(books);
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Quản lý sách", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi quản lý sách", error);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onSearchButtonClicked(null);
        }
    }
    private ParentController parentController;
    @Override
    public void onInjectParentController(ParentController parentController) {
        this.parentController = parentController;
    }
}
