package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.views;

import com.google.gson.Gson;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.supports.BorrowCardsTableView;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.contracts.MaintainBorrowCardContract;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowCardModel;
import main.com.jlaotsezu.library.support.fx.ChildrenController;
import main.com.jlaotsezu.library.support.fx.ParentController;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MaintainBorrowCardController implements MaintainBorrowCardContract.Controller, ChildrenController {
    public TextField keywordField;
    public ImageView searchButton;
    public TableView<BorrowCardModel> tableView;

    public TableColumn sttColumn;
    public TableColumn<BorrowCardModel, Integer> borrowCardIdColumn;
    public TableColumn<BorrowCardModel, String> borrowerFullNameColumn;
    public TableColumn<BorrowCardModel, String> issueTimeColumn;
    public TableColumn<BorrowCardModel, String> expireTimeColumn;
    public TableColumn<BorrowCardModel, String> statusColumn;
    public TableColumn<BorrowCardModel, String> disableTimeColumn;
    public TableColumn<BorrowCardModel, String> borrowerNameColumn;

    //private ObservableList<BorrowCardModel> tableViewBorrowCards;

    @Autowired
    Mapper<BorrowCard, BorrowCardModel> borrowCardMapper;
    @Autowired
    MaintainBorrowCardContract.Presenter maintainBorrowCardPresenter;

    private App app;
    private Intent intent;
    private User librarian;
    private BorrowCardsTableView borrowCardsTableView;

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        librarian = new Gson().fromJson(intent.getExtra("librarian"), User.class);
        initTableView();

        maintainBorrowCardPresenter.loadBorrowCards("user");
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    private Parent viewContainer;

    public Parent getViewContainer() {
        return viewContainer;
    }

    private void initTableView() {
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

        setupContextMenu();
    }

    private void setupContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem activeCardItem = new MenuItem("Active thẻ");
        MenuItem extendCardItem = new MenuItem("Gia hạn thẻ");
        MenuItem disableCardItem = new MenuItem("Vô hiệu hóa thẻ");
        MenuItem disableCardsItem = new MenuItem("Vô hiệu hóa toàn bộ thẻ của người dùng.");

        activeCardItem.setOnAction( event -> {
            BorrowCardModel model = getSelectedBorrowCardModel();
            if(model != null) {
                int borrowCardId = model.getBorrowCardId();
                DialogUtils.showConfirm("Xác nhận Active", "Bạn chắc chắn muốn active Thẻ này ?", () -> {
                    maintainBorrowCardPresenter.activeBorrowCard(borrowCardId);
                });
            }
        });
        extendCardItem.setOnAction( event -> {
            BorrowCardModel model = getSelectedBorrowCardModel();
            if(model != null) {
                int borrowCardId = model.getBorrowCardId();
                DialogUtils.showConfirm("Xác nhận Gia hạn", "Bạn chắc chắn muốn gia hạn Thẻ này ?", () -> {
                    maintainBorrowCardPresenter.extendBorrowCard(borrowCardId);
                });
            }
        });
        disableCardItem.setOnAction( event -> {
            BorrowCardModel model = getSelectedBorrowCardModel();
            if(model != null) {
                int borrowCardId = model.getBorrowCardId();
                DialogUtils.showConfirm("Xác nhận Vô hiệu hóa", "Bạn chắc chắn muốn vô hiệu hóa Thẻ này ?", () -> {
                    maintainBorrowCardPresenter.disableBorrowCard(borrowCardId);
                });
            }
        });
        disableCardsItem.setOnAction( event -> {
            BorrowCardModel model = getSelectedBorrowCardModel();
            if(model != null) {
                int borrowerId = model.getBorrowerId();
                DialogUtils.showConfirm("Xác nhận Vô hiệu hóa", "Bạn chắc chắn muốn vô hiệu hóa tất cả Thẻ của Người dùng này ?", () -> {
                    maintainBorrowCardPresenter.disableBorrowCards(borrowerId);
                });
            }
        });

        menu.getItems().addAll(activeCardItem, extendCardItem, disableCardItem, disableCardsItem);

        borrowCardsTableView.setContextMenu(menu);
    }

    public BorrowCardModel getSelectedBorrowCardModel(){
        return borrowCardsTableView.getSelectedBorrowCardModel();
    }
    public void onSearchButtonClicked(MouseEvent mouseEvent) {
        maintainBorrowCardPresenter.loadBorrowCards(keywordField.getText());
    }

    @Override
    public void showBorrowCards(List<BorrowCard> cards) {
        borrowCardsTableView.updateData(cards);
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Bảo trì thẻ mượn", message);
        maintainBorrowCardPresenter.loadBorrowCards(keywordField.getText());
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi bảo trì thẻ mượn", error);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onSearchButtonClicked(null);
        }
    }

    @Override
    public void onInjectParentController(ParentController parentController) {

    }
}
