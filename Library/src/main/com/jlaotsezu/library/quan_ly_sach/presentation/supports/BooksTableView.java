package main.com.jlaotsezu.library.quan_ly_sach.presentation.supports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.mappers.BookMapper;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookModel;

import java.util.LinkedList;
import java.util.List;

public class BooksTableView {
    public TableColumn sttColumn;
    private TableColumn<BookModel, Integer> bookIdColumn;
    private TableColumn<BookModel, String> bookNameColumn;
    private TableColumn<BookModel, String> authorsColumn;
    private TableColumn<BookModel, String> classificationColumn;
    private TableColumn<BookModel, String> publisherColumn;
    private TableColumn<BookModel, Integer> publishYearColumn;
    private TableView<BookModel> booksTableView;
    private ObservableList<BookModel> bookModels;

    private Mapper<Book, BookModel> bookMapper;

    public BooksTableView(TableColumn sttColumn, TableColumn<BookModel, Integer> bookIdColumn, TableColumn<BookModel, String> bookNameColumn, TableColumn<BookModel, String> authorsColumn, TableColumn<BookModel, String> classificationColumn, TableColumn<BookModel, String> publisherColumn, TableColumn<BookModel, Integer> publishYearColumn, TableView<BookModel> booksTableView) {
        this.sttColumn = sttColumn;
        this.bookIdColumn = bookIdColumn;
        this.bookNameColumn = bookNameColumn;
        this.authorsColumn = authorsColumn;
        this.classificationColumn = classificationColumn;
        this.publisherColumn = publisherColumn;
        this.publishYearColumn = publishYearColumn;
        this.booksTableView = booksTableView;
        this.booksTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bookMapper = new BookMapper();
        setup();
    }

    private void setup() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publishYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));

        bookModels = FXCollections.observableArrayList();
        booksTableView.setItems(bookModels);
    }

    public void updateData(List<Book> books){
        bookModels.clear();
        for(Book book : books){
            bookModels.add(bookMapper.map(book));
        }
    }

    public List<BookModel> getSelectionItems(){
        List<BookModel> bookModels = new LinkedList<>();
        bookModels.addAll(booksTableView.getSelectionModel().getSelectedItems());
        return bookModels;
    }

    public static class Builder {
        private TableColumn sttColumn;
        private TableColumn<BookModel, Integer> bookIdColumn;
        private TableColumn<BookModel, String> bookNameColumn;
        private TableColumn<BookModel, String> authorsColumn;
        private TableColumn<BookModel, String> classificationColumn;
        private TableColumn<BookModel, String> publisherColumn;
        private TableColumn<BookModel, Integer> publishYearColumn;
        private TableView<BookModel> booksTableView;

        public Builder(TableView<BookModel> tableView){
            this.booksTableView = tableView;
        }

        public Builder setSttColumn(TableColumn sttColumn) {
            this.sttColumn = sttColumn;
            return this;
        }

        public Builder setBookIdColumn(TableColumn<BookModel, Integer> bookIdColumn) {
            this.bookIdColumn = bookIdColumn;
            return this;
        }

        public Builder setBookNameColumn(TableColumn<BookModel, String> bookNameColumn) {
            this.bookNameColumn = bookNameColumn;
            return this;
        }

        public Builder setAuthorsColumn(TableColumn<BookModel, String> authorsColumn) {
            this.authorsColumn = authorsColumn;
            return this;
        }

        public Builder setClassificationColumn(TableColumn<BookModel, String> classificationColumn) {
            this.classificationColumn = classificationColumn;
            return this;
        }

        public Builder setPublisherColumn(TableColumn<BookModel, String> publisherColumn) {
            this.publisherColumn = publisherColumn;
            return this;
        }

        public Builder setPublishYearColumn(TableColumn<BookModel, Integer> publishYearColumn) {
            this.publishYearColumn = publishYearColumn;
            return this;
        }

        public BooksTableView build() {
            return new BooksTableView(sttColumn, bookIdColumn, bookNameColumn, authorsColumn, classificationColumn, publisherColumn, publishYearColumn, booksTableView);
        }
    }
}
