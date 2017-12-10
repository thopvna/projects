package test.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.source.BookCopyDAO;
import main.com.jlaotsezu.library.data.source.BookDAO;
import main.com.jlaotsezu.library.data.source.LoanDAO;
import main.com.jlaotsezu.library.data.source.UserDAO;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import org.fest.assertions.Assertions;
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
public class LoanDAOImplTest {
    @Autowired
    LoanDAO loanDAO;
    @Autowired
    BookCopyDAO bookCopyDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    UserDAO userDAO;
    @Test
    public void happySave() throws Exception {
        Loan loan = prepareLoan();
        assertTrue(loanDAO.save(loan));
    }

    @Test
    public void findByUserId() throws Exception {
        Loan loan = prepareLoan();
        assertTrue(loanDAO.save(loan));

        List<Loan> findByUserId = loanDAO.findByUserId(loan.getBorrower().getUserId());
        assertTrue(findByUserId.contains(loan));
    }
    @Test
    public void fetchById() throws Exception {
        Loan loan = prepareLoan();
        assertTrue(loanDAO.save(loan));

        Loan fetchById = loanDAO.fetchById(loan.getLoanId());
        assertEquals(fetchById, loan);
    }
    @Test
    public void updateLoan(){
        Loan loan = prepareLoan();
        assertTrue(loanDAO.save(loan));
        loan.setFee(50000);
        loan.setReturn(true);
        assertTrue(loanDAO.update(loan));
        Loan fetchById = loanDAO.fetchById(loan.getLoanId());
        assertEquals(loan.getFee(), fetchById.getFee());
        assertEquals(loan.isReturn(), fetchById.isReturn());
    }

    /**
     * Tạo và save luôn các thành phần dữ liệu cần cho 1 loan(librarian, borrower, bookCopies)
     * @return
     */
    private Loan prepareLoan() {
        Loan loan = TestUtils.genLoan();
        User librarian = TestUtils.genLibrarian();
        User borrower = TestUtils.genBorrower();
        List<Book> books = TestUtils.genBooks(2);
        List<BookCopy> bookCopies = TestUtils.genBorrowableBookCopies(2);

        assertTrue(bookDAO.saveAll(books));
        bookCopies.get(0).setBook(books.get(0));
        bookCopies.get(1).setBook(books.get(1));
        assertTrue(bookCopyDAO.saveAll(bookCopies));
        assertTrue(userDAO.save(librarian));
        assertTrue(userDAO.save(borrower));

        loan.setLibrarian(librarian);
        loan.setBorrower(borrower);
        loan.setBookCopies(bookCopies);
        return loan;
    }
}