package test.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.source.BookCopyDAO;
import main.com.jlaotsezu.library.data.source.BookDAO;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopyStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.com.jlaotsezu.library.TestUtils;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/config/spring-config.xml")
public class BookCopyDAOImplTest {
    @Autowired
    BookCopyDAO bookCopyDAO;
    @Autowired
    BookDAO bookDAO;
    @Test
    public void save() throws Exception {
        BookCopy bookCopy = prepareBookCopy();
        assertTrue(bookCopyDAO.save(bookCopy));
    }

    @Test
    public void fetchAll() throws Exception {
        List<BookCopy> bookCopies = new LinkedList<>();
        for(int i =0; i < 5; i++){
            bookCopies.add(prepareBookCopy());
        }
        assertTrue(bookCopyDAO.saveAll(bookCopies));
        List<BookCopy> fetchAll = bookCopyDAO.fetchAll();
        for(BookCopy bookCopy : bookCopies){
            assertTrue(fetchAll.contains(bookCopy));
        }
    }

    @Test
    public void fetchById() throws Exception {
        BookCopy bookCopy = prepareBookCopy();
        assertTrue(bookCopyDAO.save(bookCopy));
        BookCopy fetchById = bookCopyDAO.fetchById(bookCopy.getBookCopyId());
        assertEquals(bookCopy, fetchById);
    }
    @Test
    public void update(){
        BookCopy bookCopy = prepareBookCopy();
        assertTrue(bookCopyDAO.save(bookCopy));

        bookCopy.setBookCopyStatus(BookCopyStatus.BORROWED);
        assertTrue(bookCopyDAO.update(bookCopy));

        BookCopy fetchById = bookCopyDAO.fetchById(bookCopy.getBookCopyId());
        assertEquals(bookCopy.getBookCopyStatus(), fetchById.getBookCopyStatus());
    }
    @Test
    public void saveAll() throws Exception {
        List<BookCopy> bookCopies = new LinkedList<>();
        for(int i =0; i < 5; i++){
            bookCopies.add(prepareBookCopy());
        }
        assertTrue(bookCopyDAO.saveAll(bookCopies));
    }
    @Test
    public void clearAll() throws Exception {
    }

    /**
     * Thêm cách thành phần dữ liệu cho book copy(book, ...)
     * @return
     */
    private BookCopy prepareBookCopy(){
        BookCopy bookCopy = TestUtils.genBorrowableBookCopy();
        bookDAO.save(bookCopy.getBook());
        return bookCopy;
    }
}