package main.com.jlaotsezu.library.data.source;

import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;

import java.util.List;

public interface BookCopyDAO {
    boolean save(BookCopy bookCopy);

    boolean update(BookCopy bookCopy);

    boolean updateAll(List<BookCopy> bookCopies);

    List<BookCopy> fetchAll();
    BookCopy fetchById(int id);
    boolean saveAll(List<BookCopy> bookCopies);
    void clearAll();

}
