package com.thopv.projects.libraryforreader.data.source.repositories;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by thopv on 12/4/2017.
 */
public class CartBookRepositoryTest {

    private Repository<Long, CartBook> cartBookRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        cartBookRepository = new CartBookRepository(appDatabase);
    }
    @Test
    public void insert_fetchCartBook(){
        long longId = TestUtils.genAbsRandomLong();
        CartBook cartBook = new CartBook(longId);
        boolean success = cartBookRepository.save(cartBook);
        assertTrue(success);
        CartBook get = cartBookRepository.findById(longId);
        assertNotNull(get);
        assertEquals(get, cartBook);
    }

}