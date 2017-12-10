package main.com.jlaotsezu.library.data.source;

import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;

import java.util.List;

public interface BookDAO {
    boolean save(Book book);

    boolean saveAll(List<Book> books);

    boolean update(Book book);

    boolean updateAll(List<Book> books);

    List<Book> fetchAll();

    Book fetchById(int id);
    void clearAll();

    List<Book> searchByKeyword(String keyword);
}
