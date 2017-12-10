package main.com.jlaotsezu.library.data.source;

import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;

import java.util.List;

public interface BookImportRequestDAO {
    List<BookImportRequest> fetchAll();
    BookImportRequest fetchById(int id);
    boolean saveAll(List<BookImportRequest> bookImportRequests);
    boolean save(BookImportRequest bookImportRequest);

    boolean update(BookImportRequest bookImportRequest);

    boolean updateAll(List<BookImportRequest> bookImportRequests);

    void clearAll();
    List<BookImportRequest> fetchByLibrarianId(int librarianId);
    boolean delete(int id);
}
