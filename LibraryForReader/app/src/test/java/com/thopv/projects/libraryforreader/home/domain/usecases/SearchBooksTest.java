package com.thopv.projects.libraryforreader.home.domain.usecases;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/3/2017.
 */
public class SearchBooksTest {
    SearchBooks searchBooks;
    Repository<Long, Book> bookRepository;

    @Before
    public void setUp() throws Exception {
        bookRepository = mock(BookRepository.class);
        searchBooks = new SearchBooks(bookRepository);
    }
    @Test
    public void errorWhenKeywordEmpty(){
        String keyword = "";
        searchBooks.executeUseCase(new SearchBooks.RequestValues(keyword), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), SearchBooks.INPUT_INVALID_ERROR);
        });
    }
    @Test
    public void errorWhenDontHaveAnyMatching(){
        String any = "NonEmpty";
        when(bookRepository.find(Matchers.any())).thenReturn(null);
        searchBooks.executeUseCase(new SearchBooks.RequestValues(any), response ->{
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), SearchBooks.NO_RESULT_MATCHING_ERROR);
        });
    }
    @Test
    public void happyWhenHaveSomeMaching(){
        String any = "NonEmpty";
        List<Book> books = TestUtils.genBooks();
        Book anyOtherBookNotInResult = TestUtils.genBook();
        when(bookRepository.find(Matchers.any())).thenReturn(books);
        searchBooks.executeUseCase(new SearchBooks.RequestValues(any), response -> {
            assertTrue(response.isSuccess());
            List<Book> payload = response.getPayload().getBooks();
            assertThat(payload)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(books.size())
                    .excludes(anyOtherBookNotInResult);
        });
    }
}