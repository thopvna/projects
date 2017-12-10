package com.thopv.projects.libraryforreader.home.domain.usecases;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.repositories.CartBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.FavoriteBookRepository;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/3/2017.
 */
public class LoadBookOptionsMenuStatusTest {
     Repository<Long, CartBook> cartBookRepository;
     Repository<Long, FavoriteBook> favoriteBookRepository;
    LoadBookOptionsMenuStatus loadBookOptionsMenuStatus;

    @Before
    public void setUp() throws Exception {
        cartBookRepository = mock(CartBookRepository.class);
        favoriteBookRepository = mock(FavoriteBookRepository.class);
        loadBookOptionsMenuStatus = new LoadBookOptionsMenuStatus(cartBookRepository, favoriteBookRepository);
    }
    @Test
    public void errorWhenBookIdInvalid(){
        long longInValid = -1;
        loadBookOptionsMenuStatus.executeUseCase(new LoadBookOptionsMenuStatus.RequestValues(longInValid), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadBookOptionsMenuStatus.INPUT_INVALID);
        });
    }
    @Test
    public void whenBookNotInCartNorFavorite(){
        long longValid = 1;
        BookStatus expected = new BookStatus(longValid, false, false);
        when(favoriteBookRepository.findById(any())).thenReturn(null);
        when(cartBookRepository.findById(any())).thenReturn(null);
        loadBookOptionsMenuStatus.executeUseCase(new LoadBookOptionsMenuStatus.RequestValues(longValid), response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            assertEquals(expected, response.getPayload().getBookStatus());
        });
    }
    @Test
    public void whenBookInCartButNotInFavorite(){
        long longValid = 1;
        BookStatus expected = new BookStatus(longValid, true, false);
        when(favoriteBookRepository.findById(any())).thenReturn(new FavoriteBook(longValid));
        when(cartBookRepository.findById(any())).thenReturn(null);
        loadBookOptionsMenuStatus.executeUseCase(new LoadBookOptionsMenuStatus.RequestValues(longValid), response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            assertEquals(expected, response.getPayload().getBookStatus());
        });
    }
    @Test
    public void whenBOokNotInCartButInFavorite(){
        long longValid = 1;
        BookStatus expected = new BookStatus(longValid, false, true);
        when(favoriteBookRepository.findById(any())).thenReturn(null);
        when(cartBookRepository.findById(any())).thenReturn(new CartBook(longValid));
        loadBookOptionsMenuStatus.executeUseCase(new LoadBookOptionsMenuStatus.RequestValues(longValid), response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            assertEquals(expected, response.getPayload().getBookStatus());
        });
    }
    @Test
    public void whenBookINCartAndFavorite(){
        long longValid = 1;
        BookStatus expected = new BookStatus(longValid, true, true);
        when(favoriteBookRepository.findById(any())).thenReturn(new FavoriteBook(longValid));
        when(cartBookRepository.findById(any())).thenReturn(new CartBook(longValid));
        loadBookOptionsMenuStatus.executeUseCase(new LoadBookOptionsMenuStatus.RequestValues(longValid), response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            assertEquals(expected, response.getPayload().getBookStatus());
        });
    }
}