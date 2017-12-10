package main.com.jlaotsezu.library.data.repositories;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.data.source.BookCopyDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindBookCopiesByKeyword;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookCopyRepository implements Repository<Integer, BookCopy> {
    @Autowired
    BookCopyDAO bookCopyDAO;
    @Override
    public boolean save(BookCopy bookCopy) {
        return bookCopyDAO.save(bookCopy);
    }

    @Override
    public boolean saveAll(List<BookCopy> bookCopies) {
        return bookCopyDAO.saveAll(bookCopies);
    }

    @Override
    public boolean update(BookCopy bookCopy) {
        return bookCopyDAO.update(bookCopy);
    }

    @Override
    public boolean updateAll(List<BookCopy> bookCopies) {
        return bookCopyDAO.updateAll(bookCopies);
    }

    @Override
    public boolean delete(BookCopy bookCopy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(BookCopy bookCopy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAllById(List<Integer> integers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookCopy fetchById(Integer integer) {
        int id = integer;
        return bookCopyDAO.fetchById(id);
    }

    @Override
    public List<BookCopy> fetchAll() {
        return bookCopyDAO.fetchAll();
    }

    @Override
    public List<BookCopy> find(Specification specification) {
        if(specification instanceof FindBookCopiesByKeyword){

        }
        throw new UnsupportedOperationException();
    }
}
