package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.views;

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
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts.IssueBorrowCardContract;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowCardModel;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowerModel;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IssueBorrowCardController implements IssueBorrowCardContract.Controller, ChildrenController {
    public TextField keywordField;
    public ImageView searchButton;
    public TableView<BorrowerModel> usersTableView;
    public TableColumn sttColumn;
    public TableColumn<BorrowerModel, Integer> borrowerIdColumn;
    public TableColumn<BorrowerModel, String> borrowUserNameColumn;
    public TableColumn<BorrowerModel, String> borrowerFullNameColumn;
    public TableColumn<BorrowerModel, String> borrowerGenderColumn;
    public TableColumn<BorrowerModel, String> borrowerEmailColumn;
    public TableColumn<BorrowerModel, String> borrowerPhoneColumn;

    ObservableList<BorrowerModel> tableViewBorrowers;

    @Autowired
    Mapper<User, BorrowerModel> borrowerMapper;
    @Autowired
    Mapper<BorrowCard, BorrowCardModel> borrowCardMapper;

    @Autowired
    IssueBorrowCardContract.Presenter issueBorrowCardPresenter;

    private App app;
    private Intent intent;
    private User librarian;

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);

        initTableView();

        issueBorrowCardPresenter.findBorrower("user");
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    private Parent viewContainer;

    public Parent getViewContainer() {
        return viewContainer;
    }

    private void initTableView() {
        borrowerIdColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerId"));
        borrowUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowUserName"));
        borrowerFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerFullName"));
        borrowerGenderColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerGender"));
        borrowerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerEmail"));
        borrowerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerPhone"));

        tableViewBorrowers = FXCollections.observableArrayList();
        usersTableView.setItems(tableViewBorrowers);

        ContextMenu menu = new ContextMenu();
        MenuItem issueCardItem = new MenuItem("Cấp thẻ");
        issueCardItem.setOnAction(event -> {
            DialogUtils.showConfirm("Xác nhận cấp thẻ", "Bạn chắc chắn muốn cấp thẻ cho Người dùng này ?", this::issueBorrowCard);
        });
        menu.getItems().add(issueCardItem);

        usersTableView.setContextMenu(menu);
    }


    public void onSearchButtonClicked(MouseEvent mouseEvent) {
        issueBorrowCardPresenter.findBorrower(keywordField.getText());
    }

    @Override
    public void showBorrowers(List<User> borrowers) {
        tableViewBorrowers.clear();
        for(User borrower: borrowers){
            tableViewBorrowers.add(borrowerMapper.map(borrower));
        }
    }

    @Override
    public void showBorrowCard(BorrowCard borrowCard) {
        StringBuilder message = new StringBuilder();
        BorrowCardModel borrowCardModel = borrowCardMapper.map(borrowCard);
        message.append("Cấp thẻ thành công.\n");
        message.append("Tên người dùng: " ).append(borrowCardModel.getBorrowerFullName()).append( "\n");
        message.append("Mã số thẻ: ").append(borrowCardModel.getBorrowCardId()).append( "\n");
        message.append("Ngày cấp: ").append(borrowCardModel.getIssueTime()).append("\n");
        message.append("Ngày hết hạn: ").append(borrowCardModel.getExpireTime()).append("\n");
        showMessage(message.toString());
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Cấp thẻ mượn", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi cấp thẻ mượn", error);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onSearchButtonClicked(null);
        }
    }

    public void issueBorrowCard() {
        int borrowerId = usersTableView.getSelectionModel().getSelectedItem().getBorrowerId();
        issueBorrowCardPresenter.issueBorrowCard(borrowerId);
    }

    @Override
    public void onInjectParentController(ParentController parentController) {

    }
}
