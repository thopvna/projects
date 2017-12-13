package com.thopv.projects.ikariam.users.daos;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.schema.users.User;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

/**
 * Created by jlaotsezu on 25/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {
    private UserDAO userDAO;
    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        userDAO = appDatabase.getUserDAO();
    }
    @Test
    public void testUserDAO(){
        Assert.assertNotNull(userDAO);
        User mUser = new User.Builder()
                .setFullName("Pham Van Tho")
                .setPassword("1")
                .setUserName("a" + new Random().nextInt())
                .build();
        boolean insertResponse = userDAO.insert(mUser) > 0;
        Assert.assertTrue(insertResponse);

        User user = userDAO.get(mUser.getUserName(), mUser.getPassword());
        Assert.assertNotNull(user);

        user.setParty(0);
        boolean updateResponse = userDAO.update(user) > 0;
        Assert.assertTrue(updateResponse);

        User newUser = userDAO.get(user.getUserName(), user.getPassword());
        Assert.assertEquals(newUser.getParty(), user.getParty());
    }
}