package main.com.jlaotsezu.library.home.views;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class HomeNavigator {
    public static final String CAP_THE_MUON = "Cấp thẻ mượn";
    public static final String BAO_TRI_THE_MUON = "Bảo trì thẻ mượn";
    public static final String DANH_SACH_REQUEST_NHAP_SACH = "Danh sách Requests nhập sách";
    public static final String YEU_CAU_NHAP_SACH = "Yêu cầu nhập sách";
    public static final String DANH_SACH_BOOKS = "Danh sách Books";
    public static final String DANH_SACH_LOAN_CHUA_TRA = "Danh sách Loan chưa trả";
    public static final String DANH_SACH_REQUESTS_MUON_SACH = "Danh sách Requests mượn sách";
    public static final String CHO_MUON_SACH = "Cho mượn sách";

    public HomeNavigator(TreeView treeView) {
        this.treeView = treeView;
        treeView.setShowRoot(false);
        treeView.setRoot(getRoot());

    }

    public void setOnContentChange(TreeClicked event) {
        treeView.setOnMouseClicked(mouse -> {
            if(mouse.getClickCount() == 1){
                TreeItem item = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                String itemValue = item.getValue().toString();
                event.onClicked(itemValue);
            }
        });
    }

    public interface TreeClicked {
        void onClicked(String value);
    }

    private TreeView treeView;

    public TreeItem<String> getRoot(){
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> borrowManager = new TreeItem<>("Quản lý mượn trả");
        TreeItem<String> bookManager = new TreeItem<>("Quản lý sách");
        TreeItem<String> borrowCardManager = new TreeItem<>("Quản lý thẻ mượn");
        /*borror manager*/
        setupBorrowManagerTree(borrowManager);
        /*Book manager*/
        setupBookManagerTree(bookManager);
        /*Borrow Card Manager*/
        setupBorrowCardManagerTree(borrowCardManager);

        root.getChildren().addAll(borrowManager, bookManager, borrowCardManager);

        return root;
    }
    private void setupBorrowCardManagerTree(TreeItem<String> borrowCardManager) {
        TreeItem<String> borrowCardIssueItem = new TreeItem<>(CAP_THE_MUON);
        TreeItem<String> borrowCardMaintainItem = new TreeItem<>(BAO_TRI_THE_MUON);
        borrowCardManager.setExpanded(true);
        borrowCardManager.getChildren().addAll(borrowCardIssueItem, borrowCardMaintainItem);
    }

    private void setupBookManagerTree(TreeItem<String> bookManager) {
        TreeItem<String> bookImportRequestsItem = new TreeItem<>(DANH_SACH_REQUEST_NHAP_SACH);
        TreeItem<String> requestImportBooksItem = new TreeItem<>(YEU_CAU_NHAP_SACH);
        TreeItem<String> books = new TreeItem<>(DANH_SACH_BOOKS);
        bookManager.setExpanded(true);
        bookManager.getChildren().addAll(bookImportRequestsItem, requestImportBooksItem, books);
    }

    private void setupBorrowManagerTree(TreeItem<String> borrowManager) {
        TreeItem<String> borrowBooksRequestsItem = new TreeItem<>(DANH_SACH_REQUESTS_MUON_SACH);
        TreeItem<String> nonReturnLoansItem = new TreeItem<>(DANH_SACH_LOAN_CHUA_TRA);
        TreeItem<String> borrowBooksItem = new TreeItem<>(CHO_MUON_SACH);
        borrowManager.setExpanded(true);
        borrowManager.getChildren().addAll(nonReturnLoansItem, borrowBooksRequestsItem, borrowBooksItem);
    }


}
