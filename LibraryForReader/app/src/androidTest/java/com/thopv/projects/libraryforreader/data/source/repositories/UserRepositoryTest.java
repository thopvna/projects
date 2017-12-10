package com.thopv.projects.libraryforreader.data.source.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by thopv on 12/2/2017.
 */
public class UserRepositoryTest {
    private Repository<String, User> userRepository;
    private User mUser;
    private AppDatabase appDatabase;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "library").build();
        userRepository = new UserRepository(appDatabase);

        mUser = new User("userName",
                "password",
                "Pham Van Tho",
                "thopvna@gmail.com",
                "0965 784 238",
                false);
    }
    @Test
    public void insert_fetchUser() {
        boolean success = userRepository.save(mUser);
        assertTrue(success);

        User user = userRepository.findById(mUser.getUserName());
        assertNotNull(user);
        assertEquals(user, mUser);
    }
    @Test
    public void delete_fetchUser(){
        boolean success = userRepository.delete(mUser);
        assertTrue(success);

        User user = userRepository.findById(mUser.getUserName());
        assertNull(user);
    }
}