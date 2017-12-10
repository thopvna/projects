package com.thopv.projects.libraryforreader.data.source.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.daos.AuthorDAO;
import com.thopv.projects.libraryforreader.data.source.daos.BookDAO;
import com.thopv.projects.libraryforreader.data.source.daos.ClassificationDAO;
import com.thopv.projects.libraryforreader.data.source.daos.PublisherDAO;
import com.thopv.projects.libraryforreader.home.domain.entity.Author;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.Classification;
import com.thopv.projects.libraryforreader.home.domain.entity.Publisher;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByAuthorName;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByBriefContent;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByClassificationName;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByPublisherName;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByPublisherTime;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByTitle;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
/**
 * Created by thopv on 12/2/2017.
 */
public class BookRepositoryTest {
    private Repository<Long, Book> bookRepository;
    private Random random;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, "library").build();
        bookRepository = new BookRepository(appDatabase);
        random = new Random();
    }

    @Test
    public void insert_fetchBook(){
        Book mBook = TestUtils.genBook();
        boolean success = bookRepository.save(mBook);
        assertTrue(success);

        List<Book> books = bookRepository.find(new FindBookByTitle(mBook.getTitle()));
        assertNotNull(books);
        assertTrue(books.size() > 0);

    }
    @Test
    public void insertMoreMoreBook(){
        List<Book> mBooks = TestUtils.genBooks();
        boolean success = true;
        for(Book book : mBooks) {
            boolean pieceOk = bookRepository.save(book);
            if(!pieceOk)
                success = false;
        }
        assertTrue(success);
    }
    @Test
    public void fetchMoreBooks(){
        List<Book> mBooks = TestUtils.genBooks(40);
        boolean success = true;
        for(Book mBook : mBooks){
            boolean pieceOk = bookRepository.save(mBook);
            if(!pieceOk)
                success = false;
        }
        assertTrue(success);

        List<Book> books = bookRepository.fetchAll();
        Assert.assertNotNull(books);
        for(Book book : books){
            assertNotNull(book.getClassification());
            assertNotNull(book.getAuthors());
            assertNotNull(book.getPublisher());
            assertTrue(book.getAuthors().size() > 0);
            for(Author author : book.getAuthors()){
                assertNotNull(author);
            }
        }
    }
    @Test
    public void findBookByTitle(){
        Book mBook = TestUtils.genBook();
        boolean success = bookRepository.save(mBook);
        assertTrue(success);
        List<Book> books = bookRepository.find(new FindBookByTitle(mBook.getTitle()));
        assertNotNull(books);
        assertTrue(books.size() > 0);
    }
    @Test
    public void findBookByAuthorName(){
        Book mBook = TestUtils.genBook();
        boolean success = bookRepository.save(mBook);
        assertTrue(success);
        List<Book> books = bookRepository.find(new FindBookByAuthorName(mBook.getAuthors().get(0).getAuthorName()));
        assertNotNull(books);
        assertTrue(books.size() > 0);
    }
    @Test
    public void findBookByBriefContent(){
        Book mBook = TestUtils.genBook();
        boolean success = bookRepository.save(mBook);
        assertTrue(success);
        List<Book> books = bookRepository.find(new FindBookByBriefContent(mBook.getBriefContent()));
        assertNotNull(books);
        assertTrue(books.size() > 0);
    }
    @Test
    public void findBookByClassificationName(){
        Book mBook = TestUtils.genBook();
        boolean success = bookRepository.save(mBook);
        assertTrue(success);

        List<Book> books = bookRepository.find(new FindBookByClassificationName(mBook.getClassification().getClassificationName()));
        assertNotNull(books);
        assertTrue(books.size() > 0);
    }
    @Test
    public void findBookByPublisherName(){
        Book mBook = TestUtils.genBook();
        boolean success = bookRepository.save(mBook);
        assertTrue(success);
        List<Book> books = bookRepository.find(new FindBookByPublisherName(mBook.getPublisher().getPublisherName()));
        assertNotNull(books);
        assertTrue(books.size() > 0);
    }
    @Test
    public void findBookByPublisherTime(){
        Book mBook = TestUtils.genBook();
        boolean success = bookRepository.save(mBook);
        assertTrue(success);
        List<Book> books = bookRepository.find(new FindBookByPublisherTime(String.valueOf(mBook.getPublishTime())));
        assertNotNull(books);
        assertTrue(books.size() > 0);
    }
}