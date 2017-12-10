package test.com.jlaotsezu.library.data.source.db;

import main.com.jlaotsezu.library.data.source.UserDAO;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
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
public class UserDAOImplTest {
    @Autowired
    UserDAO userDAO;
    @Test
    public void happyTestAll() throws Exception {
        User user = TestUtils.genUser();
        boolean success = userDAO.save(user);
        assertTrue(success);

        User userFromUserName = userDAO.fetchByUserName(user.getUserName());
        assertEquals(user, userFromUserName);

        User userFromUserId = userDAO.fetchById(user.getUserId());
        assertEquals(userFromUserId, user);
    }
    @Test
    public void saveNullUser(){
        boolean success = userDAO.save(null);
        assertFalse(success);
    }
    @Test
    public void fetchNonExistingUserById(){
        User user = TestUtils.genUser();

        User userById = userDAO.fetchById(user.getUserId());
        assertNull(userById);
    }
    @Test
    public void fetchNonExistingUserByName(){
        User user = TestUtils.genUser();

        User userByName = userDAO.fetchByUserName(user.getUserName());
        assertNull(userByName);
    }
    @Test
    public void findByKeyword(){
        User user = TestUtils.genUser();
        assertTrue(userDAO.save(user));

        List<User> findByUserName = userDAO.findByKeyword(user.getUserName().toLowerCase());
        assertTrue(findByUserName.contains(user));
        List<User> findByUserId = userDAO.findByKeyword(String.valueOf(user.getUserId()).toLowerCase());
        assertTrue(findByUserId.contains(user));
        List<User> findByFullName = userDAO.findByKeyword(user.getFullName().toLowerCase());
        assertTrue(findByFullName.contains(user));
    }
    @Test
    public void update(){
        User user = TestUtils.genUser();
        assertTrue(userDAO.save(user));

        user.setEmail("New Email");
        user.setFullName("New FullName");
        user.setPhone("New Phone");
        assertTrue(userDAO.update(user));

        User fetchById = userDAO.fetchById(user.getUserId());
        assertEquals(user.getEmail(), fetchById.getEmail());
        assertEquals(user.getFullName(), fetchById.getFullName());
        assertEquals(user.getPhone(), fetchById.getPhone());
    }
}