package test.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.source.BorrowCardDAO;
import main.com.jlaotsezu.library.data.source.UserDAO;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
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
public class BorrowCardDAOImplTest {
    @Autowired
    BorrowCardDAO borrowCardDAO;
    @Autowired
    UserDAO userDAO;

    @Test
    public void findByUserId() throws Exception {
        BorrowCard borrowCard = prepareBorrowCard();
        User user = borrowCard.getUser();
        assertTrue(borrowCardDAO.save(borrowCard));
        assertTrue(borrowCardDAO.save(borrowCard));
        List<BorrowCard> findByUserId = borrowCardDAO.findByUserId(user.getUserId());
        assertTrue(findByUserId != null);
        assertTrue((findByUserId.size() >= 2));
        assertTrue(findByUserId.contains(borrowCard));
    }
    @Test
    public void fetchById() throws Exception {
        BorrowCard borrowCard = prepareBorrowCard();
        assertTrue(borrowCardDAO.save(borrowCard));
        BorrowCard fetchById = borrowCardDAO.fetchById(borrowCard.getBorrowCardId());
        assertEquals(fetchById, borrowCard);
    }

    @Test
    public void findByKeyword() throws Exception {
        BorrowCard borrowCard = prepareBorrowCard();
        assertTrue(borrowCardDAO.save(borrowCard));

        List<BorrowCard> findByUserName = borrowCardDAO.findByKeyword(borrowCard.getUser().getUserName());
        assertTrue(findByUserName.contains(borrowCard));

        List<BorrowCard> findByUserId = borrowCardDAO.findByKeyword(String.valueOf(borrowCard.getUser().getUserId()));
        assertTrue(findByUserId.contains(borrowCard));

        List<BorrowCard> findByBorrowCardId = borrowCardDAO.findByKeyword(String.valueOf(borrowCard.getBorrowCardId()));
        assertTrue(findByBorrowCardId.contains(borrowCard));
    }

    @Test
    public void saveAll() throws Exception {
        List<BorrowCard> borrowCards = new LinkedList<>();
        for(int i = 0 ; i < 5; i++){
            borrowCards.add(prepareBorrowCard());
        }
        assertTrue(borrowCardDAO.saveAll(borrowCards));
        List<BorrowCard> fetchAll = borrowCardDAO.fetchAll();
        assertNotNull(fetchAll);
        assertTrue(fetchAll.size() >= borrowCards.size());
        for(BorrowCard borrowCard : borrowCards){
            assertTrue(fetchAll.contains(borrowCard));
        }
    }

    @Test
    public void happySave() throws Exception {
        BorrowCard borrowCard = prepareBorrowCard();
        assertTrue(borrowCardDAO.save(borrowCard));
    }
    @Test
    public void update(){
        BorrowCard borrowCard = prepareBorrowCard();
        assertTrue(borrowCardDAO.save(borrowCard));
        borrowCard.setExpiredTime(System.currentTimeMillis());
        borrowCard.setStatus(1);
        assertTrue(borrowCardDAO.update(borrowCard));
        BorrowCard fetchById = borrowCardDAO.fetchById(borrowCard.getBorrowCardId());
        assertNotNull(fetchById);
        assertEquals(fetchById.getStatus(), borrowCard.getStatus());
        assertEquals(fetchById.getExpiredTime(), borrowCard.getExpiredTime());
    }
    @Test
    public void clearAll() throws Exception {
    }

    /**
     * Tạo và save các thành phần data của 1 borrow card trước.
     */
    private BorrowCard prepareBorrowCard(){
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        userDAO.save(borrowCard.getUser());
        return borrowCard;
    }


}