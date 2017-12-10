package main.com.jlaotsezu.library.quan_ly_sach.presentation.mappers;

import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookImportRequestModel;
import main.com.jlaotsezu.library.support.utils.DateUtils;

public class BookImportRequestMapper implements Mapper<BookImportRequest, BookImportRequestModel> {

    @Override
    public BookImportRequestModel map(BookImportRequest bookImportRequest) {
        return new BookImportRequestModel.Builder()
                .setRequestId(bookImportRequest.getBookImportRequestId())
                .setLibrarianId(bookImportRequest.getLibrarian().getUserId())
                .setLibrarianName(bookImportRequest.getLibrarian().getFullName())
                .setBookSum(bookImportRequest.getBookSum())
                .setBornTime(DateUtils.getBasicDate(bookImportRequest.getBornTime()))
                .setStatus(bookImportRequest.isConfirm() ? "Xác nhận" : "Chờ xử lý")
                .build();
    }
}
