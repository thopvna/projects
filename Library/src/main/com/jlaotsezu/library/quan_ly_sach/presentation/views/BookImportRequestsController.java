package main.com.jlaotsezu.library.quan_ly_sach.presentation.views;

import com.google.gson.Gson;
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
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.contracts.BookImportRequestsContract;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookImportRequestModel;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookImportRequestsController implements BookImportRequestsContract.Controller, ChildrenController {
    public Button requestImportBooksButton;
    public TableView<BookImportRequestModel> bookImportRequestsTableView;
    public TextField keywordField;
    public ImageView seachButton;

    public TableColumn sttColumn;

    public TableColumn<BookImportRequestModel, Integer> requestIdColumn;
    public TableColumn<BookImportRequestModel, Integer> librarianIdColumn;
    public TableColumn<BookImportRequestModel, String> librarianNameColumn;
    public TableColumn<BookImportRequestModel, String> bookSumColumn;
    public TableColumn<BookImportRequestModel, String> bornTimeColumn;
    public TableColumn<BookImportRequestModel, String> statusColumn;

    @Autowired
    Mapper<BookImportRequest, BookImportRequestModel> bookImportRequestMapper;

    @Autowired
    BookImportRequestsContract.Presenter bookImportRequestsPresenter;

    private App app;
    private Intent intent;
    private User librarian;
    private ObservableList<BookImportRequestModel> tableViewBookImportRequests;
    private Parent viewContainer;

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);
        initTableView();
        keywordField.setText(String.valueOf(librarian.getUserId()));
        bookImportRequestsPresenter.loadBookImportRequests(librarian.getUserId());
    }

    @Override
    public void onHasMessage(Intent intent) {
        if(intent.getExtra("requestsChange") != null){
            bookImportRequestsPresenter.loadBookImportRequests(librarian.getUserId());
        }
    }

    public Parent getViewContainer() {
        return viewContainer;
    }

    private void initTableView() {
        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        librarianIdColumn.setCellValueFactory(new PropertyValueFactory<>("librarianId"));
        librarianNameColumn.setCellValueFactory(new PropertyValueFactory<>("librarianName"));
        bookSumColumn.setCellValueFactory(new PropertyValueFactory<>("bookSum"));
        bornTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bornTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableViewBookImportRequests = FXCollections.observableArrayList();
        bookImportRequestsTableView.setItems(tableViewBookImportRequests);

        ContextMenu menu = new ContextMenu();
        MenuItem cancelItem = new MenuItem("Hủy bỏ yêu cầu");
        cancelItem.setOnAction(event -> {
            DialogUtils.showConfirm("Xác nhận hủy bỏ yêu cầu", "Bạn chắc chắn muốn hủy bỏ yêu cầu này ?", () -> {
                BookImportRequestModel model = getSelectedBorrowImportRequest();
                bookImportRequestsPresenter.cancelRequest(model.getRequestId());
            });
        });
        menu.getItems().add(cancelItem);
        bookImportRequestsTableView.setContextMenu(menu);
    }
    private BookImportRequestModel getSelectedBorrowImportRequest(){
        return bookImportRequestsTableView.getSelectionModel().getSelectedItem();
    }
    @Override
    public void showBookImportRequests(List<BookImportRequest> requests) {
        tableViewBookImportRequests.clear();
        for(BookImportRequest request : requests){
            tableViewBookImportRequests.add(bookImportRequestMapper.map(request));
        }
    }

    @Override
    public void removeBookImportRequest(int requestId) {
        BookImportRequestModel model = new BookImportRequestModel.Builder()
                .setRequestId(requestId)
                .build();
        tableViewBookImportRequests.remove(model);
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Book Import Requests", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi", error);
    }

    public void onRequestImportBooksButtonClicked(ActionEvent actionEvent) {
        //TODO: Navigate sang request import books.
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            onSearchButtonClicked(null);
        }
    }

    public void onSearchButtonClicked(MouseEvent mouseEvent) {
        try {
            bookImportRequestsPresenter.loadBookImportRequests(Integer.parseInt(keywordField.getText()));
        }
        catch (Exception e){
            showError("Chỉ hỗ trợ tìm kiếm bằng ID thủ thư.");
        }
    }

    @Override
    public void onInjectParentController(ParentController parentController) {

    }
}
