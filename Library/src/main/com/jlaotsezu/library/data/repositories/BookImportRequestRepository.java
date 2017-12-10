package main.com.jlaotsezu.library.data.repositories;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.data.source.BookImportRequestDAO;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.specifications.FindBookImportRequestByLibrarianId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookImportRequestRepository implements Repository<Integer, BookImportRequest> {
    @Autowired
    BookImportRequestDAO bookImportRequestDAO;
    @Override
    public boolean save(BookImportRequest bookImportRequest) {
        return bookImportRequestDAO.save(bookImportRequest);
    }

    @Override
    public boolean saveAll(List<BookImportRequest> bookImportRequests) {
        return bookImportRequestDAO.saveAll(bookImportRequests);
    }
    @Override
    public boolean update(BookImportRequest bookImportRequest) {
        return bookImportRequestDAO.update(bookImportRequest);
    }

    @Override
    public boolean updateAll(List<BookImportRequest> bookImportRequests) {
        return bookImportRequestDAO.updateAll(bookImportRequests);
    }

    @Override
    public boolean delete(BookImportRequest bookImportRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(BookImportRequest bookImportRequest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Integer integer) {
        int id = integer;
        return bookImportRequestDAO.delete(id);
    }

    @Override
    public boolean deleteAllById(List<Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookImportRequest fetchById(Integer integer) {
        int id = integer;
        return bookImportRequestDAO.fetchById(id);
    }

    @Override
    public List<BookImportRequest> fetchAll() {
        return bookImportRequestDAO.fetchAll();
    }

    @Override
    public List<BookImportRequest> find(Specification specification) {
        if(specification instanceof FindBookImportRequestByLibrarianId){
            return bookImportRequestDAO.fetchByLibrarianId(((FindBookImportRequestByLibrarianId) specification).getLibrarianId());
        }
        throw new UnsupportedOperationException();
    }
}
