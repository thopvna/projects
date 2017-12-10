package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.views;


import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.BorrowBooksRequestsContract;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.BorrowBooksRequestModel;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.LoanModel;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.presenters.BorrowBooksRequestsPresenter;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookModel;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BorrowBooksRequestController implements ChildrenController, BorrowBooksRequestsContract.Controller{

    public TableView<BorrowBooksRequestModel> tableView;
    public TableColumn<BorrowBooksRequestModel, Integer> sttColumn;
    public TableColumn<BorrowBooksRequestModel, Integer> requestIdColumn;
    public TableColumn<BorrowBooksRequestModel, String> borrowerUserNameColumn;
    public TableColumn<BorrowBooksRequestModel, String> borrowerFullNameColumn;
    public TableColumn<BorrowBooksRequestModel, Integer> bookCountColumn;
    public TableColumn<BorrowBooksRequestModel, String> bornTimeColumn;
    public TableColumn<BorrowBooksRequestModel, String> confirmTimeColumn;
    public TableColumn<BorrowBooksRequestModel, String> expiredTimeColumn;
    public TableColumn<BorrowBooksRequestModel, String> statusColumn;


    public TextField keywordField;
    public ImageView searchButton;

    @Autowired
    Mapper<BorrowBooksRequest, BorrowBooksRequestModel> borrowBooksRequestMapper;
    @Autowired
    Mapper<Loan, LoanModel> loanMapper;
    @Autowired
    Mapper<Book, BookModel> bookMapper;
    @Autowired
    BorrowBooksRequestsPresenter borrowBooksRequestsPresenter;

    private ObservableList<BorrowBooksRequestModel> requestModels;
    private App app;
    private Intent intent;
    private Parent viewContainer;
    private ParentController parentController;
    private User librarian;


    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;

        librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);
        initTableView();
        initMenuContext();

        borrowBooksRequestsPresenter.fetchBorrowBooksRequests("user");
    }

    private void initMenuContext() {
        ContextMenu menu = new ContextMenu();

        MenuItem detailItem = new MenuItem("Xem chi tiết");
        detailItem.setOnAction(event -> {
            BorrowBooksRequestModel model = getSelectedItem();
            if(model != null){
                showRequestDetail(model);
            }
        });
        MenuItem confirmItem = new MenuItem("Xác nhận lấy sách");
        confirmItem.setOnAction(event -> {
            BorrowBooksRequestModel model = getSelectedItem();
            if(model != null){
                DialogUtils.showNumberInputDialog("Xác nhận lấy sách", "Vui lòng nhập ID Thẻ mượn của người mượn", 0, (value) -> {
                    borrowBooksRequestsPresenter.confirmBorrowBooksRequest(model.getRequestId(), librarian.getUserId(), Math.toIntExact(value));
                });
            }
        });
        menu.getItems().addAll(detailItem, confirmItem);

        tableView.setContextMenu(menu);
    }

    private void showRequestDetail(BorrowBooksRequestModel requestModel) {
        StringBuilder message = new StringBuilder("Thông tin các cuốn sách.\n");
        for(BookCopy bookCopy : requestModel.getBookCopies()){
            BookModel bookModel = bookMapper.map(bookCopy.getBook());
            message.append("BookCopyID: ").append(bookCopy.getBookCopyId())
                    .append(", BookID: ").append(bookModel.getBookId())
                    .append(", Tên sách: ").append(bookModel.getBookName())
                    .append(", Tác giả: ").append(bookModel.getAuthors())
                    .append(", Thể loại: ").append(bookModel.getClassification())
                    .append(", NhàXB: ").append(bookModel.getPublisher())
                    .append(", NămXB: ").append(bookModel.getPublishYear())
                    .append("\n");
        }
        DialogUtils.showMessage("Chi tiết yêu cầu mượn sách", message.toString());
    }

    private void initTableView(){
        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        borrowerUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerUserName"));
        borrowerFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerFullName"));
        bookCountColumn.setCellValueFactory(new PropertyValueFactory<>("bookCount"));
        bornTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bornTime"));
        confirmTimeColumn.setCellValueFactory(new PropertyValueFactory<>("confirmTime"));
        expiredTimeColumn.setCellValueFactory(new PropertyValueFactory<>("expiredTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        requestModels = FXCollections.observableArrayList();
        tableView.setItems(requestModels);
    }

    private BorrowBooksRequestModel getSelectedItem(){
        return tableView.getSelectionModel().getSelectedItem();
    }

    public void onSearchButtonClicked(MouseEvent event) {
        borrowBooksRequestsPresenter.fetchBorrowBooksRequests(keywordField.getText());
    }

    public void onKeywordFieldKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onSearchButtonClicked(null);
        }
    }


    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Danh sách yêu cầu mượn sách", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi danh sách yêu cầu mượn sách", error);
    }

    @Override
    public void onInjectParentController(ParentController parentController) {
        this.parentController = parentController;
    }


    @Override
    public void onHasMessage(Intent intent) {

    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }

    @Override
    public void showBorrowBooksRequests(List<BorrowBooksRequest> requests) {
        requestModels.clear();
        for(BorrowBooksRequest request : requests){
            requestModels.add(borrowBooksRequestMapper.map(request));
        }
    }

    @Override
    public void showLoan(Loan loan) {
        LoanModel loanModel = loanMapper.map(loan);
        showMessage("Cho mượn sách thành công. \n" +
                "Mã loan: " + loanModel.getLoanId() + "\n" +
                "Tên thủ thư: " + loanModel.getLibrarianFullName()  + "\n" +
                "Tên người mượn: " + loanModel.getBorrowerFullName()  + "\n" +
                "Tổng số Cuốn: " + loanModel.getBookCopyCount()  + "\n" +
                "Thời hạn trả: " + loanModel.getExpiredTime()  + "\n" +
                "");
    }

    @Override
    public void updateRequest(BorrowBooksRequest request) {
        BorrowBooksRequestModel model = borrowBooksRequestMapper.map(request);
        if(requestModels.contains(model))
            requestModels.set(requestModels.indexOf(model), model);
    }
}
