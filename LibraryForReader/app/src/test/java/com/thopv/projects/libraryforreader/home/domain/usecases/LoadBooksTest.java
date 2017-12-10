package com.thopv.projects.libraryforreader.home.domain.usecases;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/3/2017.
 */
public class LoadBooksTest {

    LoadBooks loadBooks;
    Repository<Long, Book> bookRepository;
    @Before
    public void setUp() throws Exception {
        bookRepository = mock(BookRepository.class);
        loadBooks = new LoadBooks(bookRepository);
    }
    @Test
    public void errorWhenBOokRepositoryEmpty(){
        when(bookRepository.fetchAll()).thenReturn(null);

        loadBooks.executeUseCase(new LoadBooks.RequestValues(), response -> {
            assertFalse(response.isSuccess());
        });
    }
    @Test
    public void happyWhenBookRepositoryNonEmpty(){
        Book anyOtherBook = TestUtils.genBook();
        List<Book> expectBooks = TestUtils.genBooks();
        when(bookRepository.fetchAll()).thenReturn(expectBooks);

        loadBooks.executeUseCase(new LoadBooks.RequestValues(), response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            List<Book> payload = response.getPayload().getBooks();
            assertThat(payload)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(expectBooks.size())
                    .excludes(anyOtherBook)
            ;
        });
    }

}