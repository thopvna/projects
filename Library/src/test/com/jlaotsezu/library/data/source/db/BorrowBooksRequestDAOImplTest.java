package test.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.source.BookCopyDAO;
import main.com.jlaotsezu.library.data.source.BookDAO;
import main.com.jlaotsezu.library.data.source.BorrowBooksRequestDAO;
import main.com.jlaotsezu.library.data.source.UserDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
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
public class BorrowBooksRequestDAOImplTest {
    @Autowired
    BorrowBooksRequestDAO borrowBooksRequestDAO;
    @Autowired
    BookCopyDAO bookCopyDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    BookDAO bookDAO;

    @Test
    public void findByUserId() throws Exception {
        BorrowBooksRequest borrowBooksRequest = prepareBorrowBooksRequest();
        assertTrue(borrowBooksRequestDAO.save(borrowBooksRequest));
        List<BorrowBooksRequest> findByUserId = borrowBooksRequestDAO.findByUserId(borrowBooksRequest.getBorrower().getUserId());
        assertNotNull(findByUserId);
        assertTrue(findByUserId.contains(borrowBooksRequest));
    }

    @Test
    public void fetchAllNonConfirm() throws Exception {
        List<BorrowBooksRequest> borrowBooksRequests = new LinkedList<>();
        for(int i =0; i < 5; i++){
            BorrowBooksRequest borrowBooksRequest = prepareBorrowBooksRequest();
            borrowBooksRequest.setConfirm(false);
            borrowBooksRequests.add(borrowBooksRequest);
        }
        assertTrue(borrowBooksRequestDAO.saveAll(borrowBooksRequests));
        List<BorrowBooksRequest> fetchAllNonConfirm = borrowBooksRequestDAO.fetchAllNonConfirm();
        assertTrue(fetchAllNonConfirm.size() >= borrowBooksRequests.size());
        for(BorrowBooksRequest request : borrowBooksRequests){
            assertTrue(fetchAllNonConfirm.contains(request));
        }
    }
    @Test
    public void fetchById() throws Exception {
        BorrowBooksRequest borrowBooksRequest = prepareBorrowBooksRequest();
        assertTrue(borrowBooksRequestDAO.save(borrowBooksRequest));
        BorrowBooksRequest fetchById = borrowBooksRequestDAO.fetchById(borrowBooksRequest.getBorrowBooksRequestId());
        assertEquals(borrowBooksRequest, fetchById);
    }
    @Test
    public void happySave() throws Exception {
        BorrowBooksRequest borrowBooksRequest = prepareBorrowBooksRequest();
        assertTrue(borrowBooksRequestDAO.save(borrowBooksRequest));
    }

    @Test
    public void update(){
        BorrowBooksRequest borrowBooksRequest = prepareBorrowBooksRequest();
        assertTrue(borrowBooksRequestDAO.save(borrowBooksRequest));

        borrowBooksRequest.setConfirm(true);
        assertTrue(borrowBooksRequestDAO.update(borrowBooksRequest));

        BorrowBooksRequest fetchById = borrowBooksRequestDAO.fetchById(borrowBooksRequest.getBorrowBooksRequestId());
        assertEquals(borrowBooksRequest.isConfirm(), fetchById.isConfirm());
    }
    @Test
    public void happySaveAll() throws Exception {
        List<BorrowBooksRequest> borrowBooksRequests = new LinkedList<>();
        for(int i = 0; i < 5; i++){
            borrowBooksRequests.add(prepareBorrowBooksRequest());
        }
        assertTrue(borrowBooksRequestDAO.saveAll(borrowBooksRequests));
    }

    @Test
    public void clearAll() throws Exception {
    }

    /**
     * Tạo và save các thành phần dữ liệu của một borrow books request
     * @return
     */
    private BorrowBooksRequest prepareBorrowBooksRequest(){
        BorrowBooksRequest borrowBooksRequest = TestUtils.genBorrowBookRequest();
        for(BookCopy bookCopy : borrowBooksRequest.getBookCopies()){
            bookDAO.save(bookCopy.getBook());
        }
        bookCopyDAO.saveAll(borrowBooksRequest.getBookCopies());
        userDAO.save(borrowBooksRequest.getBorrower());
        return borrowBooksRequest;
    }
}