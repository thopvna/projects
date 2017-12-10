package test.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.source.BookDAO;
import main.com.jlaotsezu.library.data.source.BookImportRequestDAO;
import main.com.jlaotsezu.library.data.source.UserDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookAmount;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
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
public class BookImportRequestDAOImplTest {
    @Autowired
    BookImportRequestDAO bookImportRequestDAO;

    @Autowired
    BookDAO bookDAO;
    @Test
    public void fetchAll() throws Exception {
        List<BookImportRequest> requests = new LinkedList<>();
        for(int i =0; i < 5; i++){
            requests.add(prepareBookImportRequest());
        }
        assertTrue(bookImportRequestDAO.saveAll(requests));
        List<BookImportRequest> fetchALl = bookImportRequestDAO.fetchAll();
        for(BookImportRequest request : requests){
            assertTrue(fetchALl.contains(request));
        }
    }

    @Test
    public void fetchById() throws Exception {
        BookImportRequest request = prepareBookImportRequest();
        assertTrue(bookImportRequestDAO.save(request));

        BookImportRequest fetchById = bookImportRequestDAO.fetchById(request.getBookImportRequestId());
        assertEquals(request, fetchById);
    }

    @Test
    public void saveAll() throws Exception {
        List<BookImportRequest> requests = new LinkedList<>();
        for(int i = 0; i < 5; i++){
            requests.add(prepareBookImportRequest());
        }
        assertTrue(bookImportRequestDAO.saveAll(requests));
    }

    @Test
    public void save() throws Exception {
        BookImportRequest request = prepareBookImportRequest();
        assertTrue(bookImportRequestDAO.save(request));
    }
    @Test
    public void delete(){
        BookImportRequest request = prepareBookImportRequest();
        assertTrue(bookImportRequestDAO.save(request));
        assertTrue(bookImportRequestDAO.delete(request.getBookImportRequestId()));
        BookImportRequest fetchById = bookImportRequestDAO.fetchById(request.getBookImportRequestId());
        assertNull(fetchById);
        Book book = bookDAO.fetchById(request.getBooksAmounts().get(0).getBook().getBookId());
        assertNotNull(book);
    }
    @Test
    public void clearAll() throws Exception {
    }
    private BookImportRequest prepareBookImportRequest(){
        BookImportRequest bookImportRequest = TestUtils.genBookImportRequest();
        for(BookAmount bookAmount : bookImportRequest.getBooksAmounts()){
            bookDAO.save(bookAmount.getBook());
        }
        userDAO.save(bookImportRequest.getLibrarian());
        return bookImportRequest;
    }
    @Autowired
    UserDAO userDAO;

}