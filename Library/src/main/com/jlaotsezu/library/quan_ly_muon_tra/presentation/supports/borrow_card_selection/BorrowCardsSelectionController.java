package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.supports;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowCardModel;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.supports.BorrowCardsTableView;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.utils.DialogUtils;

import java.util.List;

public class BorrowCardsSelectionController implements BaseController{
    public TableView<BorrowCardModel> tableView;
    public TableColumn sttColumn;
    public TableColumn<BorrowCardModel, Integer> borrowCardIdColumn;
    public TableColumn<BorrowCardModel, String> borrowerNameColumn;
    public TableColumn<BorrowCardModel, String> borrowerFullNameColumn;
    public TableColumn<BorrowCardModel, String> issueTimeColumn;
    public TableColumn<BorrowCardModel, String> expireTimeColumn;
    public TableColumn<BorrowCardModel, String> statusColumn;
    public TableColumn<BorrowCardModel, String> disableTimeColumn;
    private App app;
    private Intent intent;
    private Parent viewContainer;
    private List<BorrowCard> cards;
    private BorrowCardsTableView borrowCardsTableView;


    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Lựa chọn Thẻ mượn", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi lựa chọn Thẻ mượn", error);
    }

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        TypeToken<List<BorrowCard>> typeToken = new TypeToken<List<BorrowCard>>(){};
        cards = new Gson().fromJson(intent.getExtra("cards"), typeToken.getType());

        borrowCardsTableView = new BorrowCardsTableView.Builder()
                .setBorrowCardIdColumn(borrowCardIdColumn)
                .setTableView(tableView)
                .setBorrowerFullNameColumn(borrowerFullNameColumn)
                .setBorrowerNameColumn(borrowerNameColumn)
                .setDisableTimeColumn(disableTimeColumn)
                .setExpireTimeColumn(expireTimeColumn)
                .setIssueTimeColumn(issueTimeColumn)
                .setStatusColumn(statusColumn)
                .setSttColumn(sttColumn)
                .build();

        borrowCardsTableView.updateData(cards);
    }

    public BorrowCardModel getSelectedItem(){
        return borrowCardsTableView.getSelectedBorrowCardModel();
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }
}
