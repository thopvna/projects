package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.views;


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
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.BorrowBooksContract;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.mappers.BookCopyMapper;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.mappers.LoanMapper;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.BookCopyModel;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.LoanModel;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.supports.BorrowCardsSelectionController;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowCardModel;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.resources.URLProvider;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

public class BorrowBooksController implements BorrowBooksContract.Controller, ChildrenController{
    public TableColumn sttColumn;
    public TableColumn<BookCopyModel, Integer> bookCopyIdColumn;
    public TableColumn<BookCopyModel, Integer> bookIdColumn;
    public TableColumn<BookCopyModel, String> bookNameColumn;
    public TableColumn<BookCopyModel, String> authorsColumn;
    public TableColumn<BookCopyModel, String> classificationColumn;
    public TableColumn<BookCopyModel, String> publisherColumn;
    public TableColumn<BookCopyModel, Integer> publishYearColumn;
    public TableColumn<BookCopyModel, String> bookCopyTypeColumn;
    public TableColumn<BookCopyModel, String> bookCopyStatusColumn;

    public Button confirmButton;
    public TableView<BookCopyModel> tableView;
    public TextField bookCopyKeywordField;
    public ImageView bookCopySearchButton;
    public ImageView cardSearchButton;
    public TextField cardKeywordField;
    public Label cardIdLabel;

    @Autowired
    BorrowBooksContract.Presenter borrowBooksPresenter;
    @Autowired
    BookCopyMapper bookCopyMapper;
    @Autowired
    LoanMapper loanMapper;
    private ObservableList<BookCopyModel> bookCopyModels;
    private Parent viewContainer;
    private Intent intent;
    private App app;
    private User librarian;

    @Override
    public void onInjectParentController(ParentController parentController) {

    }

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        this.librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);
        initTableView();

    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }

    private void initTableView(){
        bookCopyIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookCopyId"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        bookCopyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bookCopyType"));
        bookCopyStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bookCopyStatus"));

        bookCopyModels = FXCollections.observableArrayList();
        tableView.setItems(bookCopyModels);

        ContextMenu menu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Loại bỏ BookCopy");
        menu.getItems().add(removeItem);
        removeItem.setOnAction(event -> {
            BookCopyModel model = getSelectedModel();
            bookCopyModels.remove(model);
        });
        tableView.setContextMenu(menu);
    }
    public BookCopyModel getSelectedModel(){
        return tableView.getSelectionModel().getSelectedItem();
    }
    public void onConfirmButtonClicked(ActionEvent actionEvent) {
        try {
            int cardId = Integer.parseInt(cardIdLabel.getText());
            List<Integer> bookCopyIds = new LinkedList<>();
            for (BookCopyModel model : bookCopyModels) {
                bookCopyIds.add(model.getBookCopyId());
            }
            borrowBooksPresenter.borrowBooks(librarian.getUserId(), cardId, bookCopyIds);
        }
        catch (Exception e){
            showError("Vui lòng chọn thẻ mượn trước khi cho mượn.");
        }
    }

    @Override
    public void showBookCopy(BookCopy bookCopy) {
        BookCopyModel model = bookCopyMapper.map(bookCopy);
        if(!bookCopyModels.contains(model))
            bookCopyModels.add(model);
        bookCopyKeywordField.clear();
    }

    @Override
    public void showLoan(Loan loan) {
        //TODO: create loan mapper and show message
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
    public void showCards(List<BorrowCard> cards) {
        //TODO: create cards dialog for choose card.
        Intent intent = new Intent(URLProvider.getBorrowCardsSelectionViewURL());
        intent.putExtra("cards", new Gson().toJson(cards));
        BorrowCardsSelectionController controller = app.loadController(intent);
        app.showDialog("Lựa chọn Thẻ mượn", controller.getViewContainer(), () -> {
            BorrowCardModel model = controller.getSelectedItem();
            cardIdLabel.setText(String.valueOf(model.getBorrowCardId()));
            cardKeywordField.clear();
            cardIdLabel.requestFocus();
        });
    }

    @Override
    public void clearViews() {
        cardKeywordField.clear();
        bookCopyKeywordField.clear();
        cardIdLabel.setText("Chưa xác định");
        bookCopyModels.clear();
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Cho mượn sách", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi cho mượn sách", error);
    }


    @Override
    public void onHasMessage(Intent intent) {

    }

    public void onBookCopyKeywordFieldKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onBookCopySearchButtonClicked(null);
        }
    }

    public void onBookCopySearchButtonClicked(MouseEvent event) {
        try {
            borrowBooksPresenter.fetchBookCopy(Integer.parseInt(bookCopyKeywordField.getText()));
        }
        catch (Exception e){
            showError("Chỉ hỗ trợ tìm kiếm BookCopy bằng ID.");
        }
    }

    public void onCardKeywordFieldKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onCardSearchButtonClicked(null);
        }
    }

    public void onCardSearchButtonClicked(MouseEvent event) {
        borrowBooksPresenter.fetchBorrowCards(cardKeywordField.getText());
    }
}
