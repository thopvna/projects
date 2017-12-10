package main.com.jlaotsezu.library.data.repositories;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.data.Specification;
import main.com.jlaotsezu.library.data.source.BookDAO;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.specifications.FindBookByKeyword;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookRepository implements Repository<Integer, Book> {
    @Autowired
    BookDAO bookDAO;
    @Override
    public boolean save(Book book) {
        return bookDAO.save(book);
    }

    @Override
    public boolean saveAll(List<Book> books) {
        return bookDAO.saveAll(books);
    }
    @Override
    public boolean update(Book book) {
        return bookDAO.update(book);
    }

    @Override
    public boolean updateAll(List<Book> books) {
        return bookDAO.updateAll(books);
    }

    @Override
    public boolean delete(Book book) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(Book book) {
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
    public Book fetchById(Integer integer) {
        int id = integer;
        return bookDAO.fetchById(id);
    }

    @Override
    public List<Book> fetchAll() {
        return bookDAO.fetchAll();
    }

    @Override
    public List<Book> find(Specification specification) {
        if(specification instanceof FindBookByKeyword){
            String keyword = ((FindBookByKeyword) specification).getKeyword();
            return bookDAO.searchByKeyword(keyword);
        }
        throw new UnsupportedOperationException();
    }
}
