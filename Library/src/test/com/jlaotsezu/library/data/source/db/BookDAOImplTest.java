package test.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.source.BookDAO;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/config/spring-config.xml")
public class BookDAOImplTest {
    @Autowired
    BookDAO bookDAO;
    @Test
    public void save() throws Exception {
        Book book = TestUtils.genBook();
        assertTrue(bookDAO.save(book));
    }

    @Test
    public void saveAll() throws Exception {
        List<Book> books = TestUtils.genBooks(5);
        assertTrue(bookDAO.saveAll(books));
    }

    @Test
    public void fetchAll() throws Exception {
        List<Book> books = TestUtils.genBooks(5);
        assertTrue(bookDAO.saveAll(books));

        List<Book> fetchAll = bookDAO.fetchAll();
        assertNotNull(fetchAll);
        for(Book book : books){
            assertTrue(fetchAll.contains(book));
        }
        for(int i =0; i < fetchAll.size(); i++){
            System.out.println("I Id: " + fetchAll.get(i).getBookId());
        }
    }

    @Test
    public void fetchById() throws Exception {
        Book book = TestUtils.genBook();
        assertTrue(bookDAO.save(book));

        Book fetchById = bookDAO.fetchById(book.getBookId());
        assertEquals(fetchById, book);
    }

    @Test
    public void clearAll() throws Exception {

    }
    @Test
    public void search(){
        Book book = TestUtils.genBook();
        assertTrue(bookDAO.save(book));

        List<Book> searchByTitle = bookDAO.searchByKeyword(book.getBookTitle());
        assertNotNull(searchByTitle);
        assertTrue(searchByTitle.size() > 0);
        assertTrue(searchByTitle.contains(book));

        List<Book> searchByBriefContent = bookDAO.searchByKeyword(book.getBookBriefContent());
        assertNotNull(searchByBriefContent);
        assertTrue(searchByBriefContent.contains(book));

        List<Book> searchByClassificationName = bookDAO.searchByKeyword(book.getBookClassification().getClassificationName());
        assertNotNull(searchByClassificationName);
        assertTrue(searchByClassificationName.contains(book));

        List<Book> searchByPublisherYear = bookDAO.searchByKeyword(String.valueOf(book.getBookPublishYear()));
        assertNotNull(searchByPublisherYear);
        assertTrue(searchByPublisherYear.contains(book));

        List<Book> searchByPublisher = bookDAO.searchByKeyword(book.getBookPublisher().getPublisherName());
        assertNotNull(searchByPublisher);
        assertTrue(searchByPublisher.contains(book));

        List<Book> searchByAuthor = bookDAO.searchByKeyword(book.getBookAuthors().get(0).getAuthorName());
        assertNotNull(searchByAuthor);
        assertTrue(searchByAuthor.contains(book));
    }
}