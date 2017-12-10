package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.supports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.mappers.BorrowCardMapper;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowCardModel;

import java.util.List;

public class BorrowCardsTableView {
    public TableView<BorrowCardModel> borrowCardsTableView;

    public TableColumn sttColumn;
    public TableColumn<BorrowCardModel, Integer> borrowCardIdColumn;
    public TableColumn<BorrowCardModel, String> borrowerFullNameColumn;
    public TableColumn<BorrowCardModel, String> issueTimeColumn;
    public TableColumn<BorrowCardModel, String> expireTimeColumn;
    public TableColumn<BorrowCardModel, String> statusColumn;
    public TableColumn<BorrowCardModel, String> disableTimeColumn;
    public TableColumn<BorrowCardModel, String> borrowerNameColumn;

    private BorrowCardMapper borrowCardMapper;

    private ObservableList<BorrowCardModel> tableViewBorrowCards;
    private ContextMenu contextMenu;

    public BorrowCardsTableView(TableView<BorrowCardModel> borrowCardsTableView, TableColumn sttColumn, TableColumn<BorrowCardModel, Integer> borrowCardIdColumn, TableColumn<BorrowCardModel, String> borrowerFullNameColumn, TableColumn<BorrowCardModel, String> issueTimeColumn, TableColumn<BorrowCardModel, String> expireTimeColumn, TableColumn<BorrowCardModel, String> statusColumn, TableColumn<BorrowCardModel, String> disableTimeColumn, TableColumn<BorrowCardModel, String> borrowerNameColumn) {
        this.borrowCardsTableView = borrowCardsTableView;
        this.sttColumn = sttColumn;
        this.borrowCardIdColumn = borrowCardIdColumn;
        this.borrowerFullNameColumn = borrowerFullNameColumn;
        this.issueTimeColumn = issueTimeColumn;
        this.expireTimeColumn = expireTimeColumn;
        this.statusColumn = statusColumn;
        this.disableTimeColumn = disableTimeColumn;
        this.borrowerNameColumn = borrowerNameColumn;

        borrowCardIdColumn.setCellValueFactory(new PropertyValueFactory<>("borrowCardId"));
        borrowerFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerFullName"));
        issueTimeColumn.setCellValueFactory(new PropertyValueFactory<>("issueTime"));
        expireTimeColumn.setCellValueFactory(new PropertyValueFactory<>("expireTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        disableTimeColumn.setCellValueFactory(new PropertyValueFactory<>("disableTime"));
        borrowerNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));

        borrowCardMapper = new BorrowCardMapper();

        tableViewBorrowCards = FXCollections.observableArrayList();
        borrowCardsTableView.setItems(tableViewBorrowCards);
    }

    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
        borrowCardsTableView.setContextMenu(contextMenu);
    }

    public BorrowCardModel getSelectedBorrowCardModel() {
        return borrowCardsTableView.getSelectionModel().getSelectedItem();
    }

    public void updateData(List<BorrowCard> cards) {
        tableViewBorrowCards.clear();
        for(BorrowCard borrowCard : cards){
            tableViewBorrowCards.add(borrowCardMapper.map(borrowCard));
        }
    }

    public static class Builder {
        private TableView<BorrowCardModel> borrowCardsTableView;
        private TableColumn sttColumn;
        private TableColumn<BorrowCardModel, Integer> borrowCardIdColumn;
        private TableColumn<BorrowCardModel, String> borrowerFullNameColumn;
        private TableColumn<BorrowCardModel, String> issueTimeColumn;
        private TableColumn<BorrowCardModel, String> expireTimeColumn;
        private TableColumn<BorrowCardModel, String> statusColumn;
        private TableColumn<BorrowCardModel, String> disableTimeColumn;
        private TableColumn<BorrowCardModel, String> borrowerNameColumn;

        public Builder setTableView(TableView<BorrowCardModel> borrowCardsTableView) {
            this.borrowCardsTableView = borrowCardsTableView;
            return this;
        }

        public Builder setSttColumn(TableColumn sttColumn) {
            this.sttColumn = sttColumn;
            return this;
        }

        public Builder setBorrowCardIdColumn(TableColumn<BorrowCardModel, Integer> borrowCardIdColumn) {
            this.borrowCardIdColumn = borrowCardIdColumn;
            return this;
        }

        public Builder setBorrowerFullNameColumn(TableColumn<BorrowCardModel, String> borrowerFullNameColumn) {
            this.borrowerFullNameColumn = borrowerFullNameColumn;
            return this;
        }

        public Builder setIssueTimeColumn(TableColumn<BorrowCardModel, String> issueTimeColumn) {
            this.issueTimeColumn = issueTimeColumn;
            return this;
        }

        public Builder setExpireTimeColumn(TableColumn<BorrowCardModel, String> expireTimeColumn) {
            this.expireTimeColumn = expireTimeColumn;
            return this;
        }

        public Builder setStatusColumn(TableColumn<BorrowCardModel, String> statusColumn) {
            this.statusColumn = statusColumn;
            return this;
        }

        public Builder setDisableTimeColumn(TableColumn<BorrowCardModel, String> disableTimeColumn) {
            this.disableTimeColumn = disableTimeColumn;
            return this;
        }

        public Builder setBorrowerNameColumn(TableColumn<BorrowCardModel, String> borrowerNameColumn) {
            this.borrowerNameColumn = borrowerNameColumn;
            return this;
        }

        public BorrowCardsTableView build() {
            return new BorrowCardsTableView(borrowCardsTableView, sttColumn, borrowCardIdColumn, borrowerFullNameColumn, issueTimeColumn, expireTimeColumn, statusColumn, disableTimeColumn, borrowerNameColumn);
        }
    }
}
