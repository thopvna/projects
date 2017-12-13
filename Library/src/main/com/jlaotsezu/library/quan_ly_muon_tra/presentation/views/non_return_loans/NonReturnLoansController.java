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
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.contracts.NonReturnLoansContract;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.mappers.LoanMapper;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.LoanModel;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NonReturnLoansController implements NonReturnLoansContract.Controller, ChildrenController{

    public TextField keywordField;
    public ImageView searchButton;
    public TableColumn<LoanModel, Integer> sttColumn;
    public TableColumn<LoanModel, Integer> loanIdColumn;
    public TableColumn<LoanModel, String> librarianUserNameColumn;
    public TableColumn<LoanModel, String> librarianFullNameColumn;
    public TableColumn<LoanModel, String> borrowerUserNameColumn;
    public TableColumn<LoanModel, String> borrowerFullNameColumn;
    public TableColumn<LoanModel, String> bornTimeColumn;
    public TableColumn<LoanModel, String> expiredTimeColumn;
    public TableColumn<LoanModel, String> statusColumn;
    public TableColumn<LoanModel, Long> feeColumn;

    public TableView<LoanModel> tableView;
    private App app;
    private Intent intent;
    private Parent viewContainer;

    @Autowired
    LoanMapper loanMapper;
    @Autowired
    NonReturnLoansContract.Presenter nonReturnLoansPresenter;
    private ObservableList<LoanModel> loanModels;
    private User librarian;
    private ParentController parentController;

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        this.librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);

        initTableView();
        initContextMenu();

        nonReturnLoansPresenter.loadNonReturnLoans("user");
    }

    private void initContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem returnBooksItem = new MenuItem("Xác nhận trả.");
        returnBooksItem.setOnAction(event -> {
            LoanModel model = getSelectedItem();
            if(model != null) {
                int loanId = model.getLoanId();
                DialogUtils.showNumberInputDialog("Xác nhận trả", "Nhập phí mượn sách", 0, value -> {
                    nonReturnLoansPresenter.returnBooks(loanId, value);
                });
            }
            else{
                showError("Vui lòng chọn Loan trước khi xác nhận trả.");
            }
        });
        menu.getItems().add(returnBooksItem);

        tableView.setContextMenu(menu);
    }

    private LoanModel getSelectedItem(){
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void initTableView(){
        loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        librarianUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("librarianUserName"));
        librarianFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("librarianFullName"));
        borrowerUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerUserName"));
        borrowerFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerFullName"));
        bornTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bornTime"));
        expiredTimeColumn.setCellValueFactory(new PropertyValueFactory<>("expiredTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loanModels = FXCollections.observableArrayList();
        tableView.setItems(loanModels);
    }

    public void onKeywordFieldKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onSearchButtonClicked(null);
        }
    }

    public void onSearchButtonClicked(MouseEvent event) {
        nonReturnLoansPresenter.loadNonReturnLoans(keywordField.getText());
    }

    @Override
    public void showLoans(List<Loan> loans) {
        loanModels.clear();
        for(Loan loan : loans){
            loanModels.add(loanMapper.map(loan));
        }
    }

    @Override
    public void removeLoan(int loanId) {
        LoanModel model = new LoanModel.Builder()
                .setLoanId(loanId)
                .build();
        loanModels.remove(model);
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Trả sách", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi trả sách", error);
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }

    @Override
    public void onInjectParentController(ParentController parentController) {
        this.parentController = parentController;
    }
}
