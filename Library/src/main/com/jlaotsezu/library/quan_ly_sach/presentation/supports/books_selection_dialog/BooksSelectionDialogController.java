package main.com.jlaotsezu.library.quan_ly_sach.presentation.supports;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.support.architecture.BaseController;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookModel;
import main.com.jlaotsezu.library.support.utils.DialogUtils;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class BooksSelectionDialogController implements BaseController{

    public TableColumn sttColumn;
    public TableColumn<BookModel, Integer> bookIdColumn;
    public TableColumn<BookModel, String> bookNameColumn;
    public TableColumn<BookModel, String> authorsColumn;
    public TableColumn<BookModel, String> classificationColumn;
    public TableColumn<BookModel, String> publisherColumn;
    public TableColumn<BookModel, Integer> publishYearColumn;
    public TableView<BookModel> tableView;

    private BooksTableView booksTableView;
    private App app;
    private Intent intent;
    private List<Book> books;
    private Parent viewContainer;

    private void initTableView() {
        booksTableView = new BooksTableView.Builder(tableView)
                .setSttColumn(sttColumn)
                .setBookIdColumn(bookIdColumn)
                .setBookNameColumn(bookNameColumn)
                .setAuthorsColumn(authorsColumn)
                .setClassificationColumn(classificationColumn)
                .setPublisherColumn(publisherColumn)
                .setPublishYearColumn(publishYearColumn)
                .build();
    }
    public void showBooks(List<Book> books){
        booksTableView.updateData(books);
    }

    @Override
    public void onInjectContext(App app, Intent intent, Parent viewContainer) {
        this.app = app;
        this.intent = intent;
        this.viewContainer = viewContainer;
        TypeToken<List<Book>> token = new TypeToken<List<Book>>(){};
        books = new Gson().fromJson(intent.getExtra("books"), token.getType());

        initTableView();

        showBooks(books);
    }

    @Override
    public void onHasMessage(Intent intent) {

    }

    public List<BookModel> getSelectedItems(){
        return booksTableView.getSelectionItems();
    }

    @Override
    public void showMessage(String message) {
        DialogUtils.showMessage("Lựa chọn sách", message);
    }

    @Override
    public void showError(String error) {
        DialogUtils.showMessage("Lỗi lựa chọn sách", error);
    }

    @Override
    public Parent getViewContainer() {
        return viewContainer;
    }
}
