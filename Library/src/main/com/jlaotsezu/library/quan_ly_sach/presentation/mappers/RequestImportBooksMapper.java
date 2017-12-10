package main.com.jlaotsezu.library.quan_ly_sach.presentation.mappers;

import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookModel;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.RequestImportBooksModel;

public class RequestImportBooksMapper implements Mapper<BookModel, RequestImportBooksModel> {
    @Override
    public RequestImportBooksModel map(BookModel book) {
        return new RequestImportBooksModel.Builder()
                .setBookIdColumn(book.getBookId())
                .setBookNameColumn(book.getBookName())
                .setAuthorsColumn(book.getAuthors())
                .setClassificationColumn(book.getClassification())
                .setCountColumn(0)
                .build();
    }
}
