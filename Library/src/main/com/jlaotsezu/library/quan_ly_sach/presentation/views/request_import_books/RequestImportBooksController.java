package main.com.jlaotsezu.library.quan_ly_sach.presentation.views;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.home.views.HomeNavigator;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.presenters.RequestImportBooksPresenter;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.RequestImportBooksContract;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookModel;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.RequestImportBooksModel;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.supports.BooksSelectionDialogController;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.supports.IntegerEditingCell;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.resources.URLProvider;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RequestImportBooksController implements RequestImportBooksContract.Controller, ChildrenController{
    public TextField keywordField;
    public ImageView searchButton;
    public Button confirmButton;
    public TableView<RequestImportBooksModel> booksTableView;

    public TableColumn sttColumn;
    public TableColumn<RequestImportBooksModel, Integer> bookIdColumn;
    public TableColumn<RequestImportBooksModel, String> bookNameColumn;
    public TableColumn<RequestImportBooksModel, String> authorsColumn;
    public TableColumn<RequestImportBooksModel, String> classificationColumn;
    public TableColumn<RequestImportBooksModel, Integer> countColumn;

    @Autowired
    Mapper<BookModel, RequestImportBooksModel> requestImportBooksMapper;
    @Autowired
    RequestImportBooksContract.Presenter requestImportBooksPresenter;

    private App app;
    private Intent intent;
    private User librarian;
    private ObservableList<RequestImportBooksModel> tableViewBookModels;

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);
        initTableView();
    }

    @Override
    public void onHasMessage(Intent intent) {
        if(intent.getExtra("books") != null){
            TypeToken<List<BookModel>> token = new TypeToken<List<BookModel>>(){};
            List<BookModel> books = new Gson().fromJson(intent.getExtra("books"), token.getType());
            addRequestImportBook(books);
        }
    }

    private Parent viewContainer;

    public Parent getViewContainer() {
        return viewContainer;
    }
    private void initTableView() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        countColumn.setCellFactory(param -> new IntegerEditingCell<>((row, newValue) -> {
            tableViewBookModels.get(row).setCount(newValue);
        }));
        tableViewBookModels = FXCollections.observableArrayList();
        booksTableView.setItems(tableViewBookModels);
    }


    public void onSearchButtonClicked(MouseEvent mouseEvent) {
        requestImportBooksPresenter.searchBooks(keywordField.getText());
    }

    public void onConfirmButtonClicked(ActionEvent actionEvent) {
        Map<Integer, Integer> booksAmount = new HashMap<>();
        for(RequestImportBooksModel model : tableViewBookModels){
            if(model.getCount() > 0)
                booksAmount.put(model.getBookId(), model.getCount());
        }
        requestImportBooksPresenter.requestImportBooks(librarian.getUserId(), booksAmount);
    }
    private void addRequestImportBook(List<BookModel> books){
        for(BookModel bookModel : books){
            RequestImportBooksModel model = requestImportBooksMapper.map(bookModel);
            if(!tableViewBookModels.contains(model))
                tableViewBookModels.add(model);
        }
    }
    @Override
    public void showSearchResult(List<Book> books) {
        Intent intent = new Intent(URLProvider.getBooksSelectionURL());
        intent.putExtra("books", new Gson().toJson(books));
        BooksSelectionDialogController controller = app.loadController(intent);
        app.showDialog("Lựa chọn sách", controller.getViewContainer(), () -> {
            addRequestImportBook(controller.getSelectedItems());
        });
    }

    @Override
    public void clearRequestImportBooks() {
        tableViewBookModels.clear();
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Yêu cầu nhập sách", message);
        notifyForBookImportRequestsController(message);
    }

    private void notifyForBookImportRequestsController(String message) {
        if(message.equalsIgnoreCase(RequestImportBooksPresenter.REQUEST_IMPORT_BOOKS_SUCCESS)){
            ChildrenController bookImportRequestsController = parentController.getChild(HomeNavigator.DANH_SACH_REQUEST_NHAP_SACH);
            Intent intent = new Intent();
            intent.putExtra("requestsChange", "true");
            bookImportRequestsController.onHasMessage(intent);
        }
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi yêu cầu nhập sách", error);
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
