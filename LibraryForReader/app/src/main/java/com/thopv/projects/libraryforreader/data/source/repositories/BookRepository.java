package com.thopv.projects.libraryforreader.data.source.repositories;

import android.support.annotation.NonNull;

import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.data.source.Specification;
import com.thopv.projects.libraryforreader.data.source.daos.AuthorDAO;
import com.thopv.projects.libraryforreader.data.source.daos.BookDAO;
import com.thopv.projects.libraryforreader.data.source.daos.ClassificationDAO;
import com.thopv.projects.libraryforreader.data.source.daos.PublisherDAO;
import com.thopv.projects.libraryforreader.data.source.mappers.AuthorMapper;
import com.thopv.projects.libraryforreader.data.source.mappers.ClassificationMapper;
import com.thopv.projects.libraryforreader.data.source.mappers.ListAuthorMapper;
import com.thopv.projects.libraryforreader.data.source.mappers.PublisherMapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SAuthor;
import com.thopv.projects.libraryforreader.data.source.schemas.SBookAuthorDetail;
import com.thopv.projects.libraryforreader.data.source.schemas.SBook;
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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class BookRepository implements Repository<Long, Book> {
    private AppDatabase appDatabase;
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private ClassificationDAO classificationDAO;
    private PublisherDAO publisherDAO;
    private ListAuthorMapper listAuthorMapper;
    private AuthorMapper authorMapper;
    private ClassificationMapper classificationMapper;
    private PublisherMapper publisherMapper;
    public BookRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        bookDAO = appDatabase.getBookDAO();
        authorDAO = appDatabase.getAuthorDAO();
        classificationDAO = appDatabase.getClassificationDAO();
        publisherDAO = appDatabase.getPublisherDAO();

        listAuthorMapper = new ListAuthorMapper();
        authorMapper = new AuthorMapper();
        classificationMapper = new ClassificationMapper();
        publisherMapper = new PublisherMapper();
    }

    @Override
    public boolean save(Book book) {
        List<Author> authors = book.getAuthors();
        Classification classification = book.getClassification();
        Publisher publisher = book.getPublisher();
        //TODO: save other ?
        if (!saveAuthors(authors)) return false;
        if (!saveClassification(classification)) return false;
        if (!savePublisher(publisher)) return false;
        //TODO: save book ?
        SBook sBook = new SBook(book);
        long bookId = bookDAO.insert(sBook);
        if(bookId <= 0)return false;
        List<SBookAuthorDetail> authorDetails = getAuthorDetails(authors, bookId);
        return bookDAO.insertDetails(authorDetails).length == authorDetails.size();
    }

    private boolean savePublisher(Publisher publisher) {
        long publisherId = publisherDAO.insert(publisherMapper.map(publisher));
        if(publisherId > 0){
            publisher.setPublisherId(publisherId);
            return true;
        }
        return false;
    }

    private boolean saveClassification(Classification classification) {
        long classificationId = classificationDAO.insert(classificationMapper.map(classification));
        if(classificationId > 0){
            classification.setClassificationId(classificationId);
            return true;
        }
        return false;
    }

    private boolean saveAuthors(List<Author> authors) {
        for(Author author : authors){
            long authorID = authorDAO.insert(authorMapper.map(author));
            author.setAuthorId(authorID);
            if(authorID <= 0)
                return false;
        }
        return true;
    }

    @NonNull
    private List<SBookAuthorDetail> getAuthorDetails(List<Author> authors, long bookId) {
        List<SBookAuthorDetail> authorDetails = new LinkedList<>();
        for(Author author : authors){
            authorDetails.add(new SBookAuthorDetail(bookId, author.getAuthorId()));
        }
        return authorDetails;
    }

    @Override
    public boolean delete(Book book) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public boolean clearAll() {
        return bookDAO.clearAllBook() > 0 && bookDAO.clearAllDetail() > 0;
    }

    @Override
    public List<Book> fetchAll() {
        List<SBook> sBooks = bookDAO.fetchAll();
        return getBooks(sBooks);
    }

    @Override
    public List<Book> find(Specification specification) {
        if(specification instanceof FindBookByTitle){
            return getBooks(bookDAO.findByTitle("%" + ((FindBookByTitle) specification).getKeyword() + "%"));
        }
        else
        if(specification instanceof FindBookByAuthorName){
            return getBooks(bookDAO.findByAuthorName("%" + ((FindBookByAuthorName) specification).getKeyword()+ "%"));
        }
        else
        if(specification instanceof FindBookByBriefContent){
            return getBooks(bookDAO.findByBriefContent("%" + ((FindBookByBriefContent) specification).getKeyword()+ "%"));
        }
        else
        if(specification instanceof FindBookByClassificationName){
            return getBooks(bookDAO.findByClassificationName("%" + ((FindBookByClassificationName) specification).getKeyword()+ "%"));
        }
        else
        if(specification instanceof FindBookByPublisherName){
            return getBooks(bookDAO.findByPublisherName("%" + ((FindBookByPublisherName) specification).getKeyword()+ "%"));
        }
        else
        if(specification instanceof FindBookByPublisherTime){
            return getBooks(bookDAO.findByPublisherTime("%" + ((FindBookByPublisherTime) specification).getKeyword()+ "%"));
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Book findById(Long aLong) {
        SBook sBook = bookDAO.fetchById(aLong);
        return getBook(sBook);
    }

    @Override
    public void commitTransaction() {
        appDatabase.setTransactionSuccessful();
    }

    @Override
    public void runInTransacstion(Runnable runnable) {
        appDatabase.beginTransaction();;
        runnable.run();
        appDatabase.endTransaction();
    }


    private List<Book> getBooks(List<SBook> sBooks){
        if(sBooks == null) return null;
        List<Book> books = new LinkedList<>();
        for(SBook sBook : sBooks){
            books.add(getBook(sBook));
        }
        return books;
    }
    private Book getBook(SBook sBook){
        List<Author> authors = getAuthors(sBook);
        Classification classification = classificationDAO.fetchById(sBook.getClassificationId());
        Publisher publisher = publisherDAO.fetchById(sBook.getPublisherId());

        return buildBook(sBook, authors, classification, publisher);
    }
    private Book buildBook(SBook sBook, List<Author> authors, Classification classification, Publisher publisher) {
        return new Book.Builder()
                .setBookId(sBook.getBookId())
                .setTitle(sBook.getTitle())
                .setBriefContent(sBook.getBriefContent())
                .setClassification(classification)
                .setPublisher(publisher)
                .setAuthors(authors)
                .setPublishTime(sBook.getPublishTime())
                .setSearchAmount(sBook.getSearchAmount())
                .setLastedImportTime(sBook.getLastedImportTime())
                .setBookCopyAmount(sBook.getBookCopyAmount())
                .build();
    }

    @NonNull
    private List<Author> getAuthors(SBook sBook) {
        List<Author> authors = new LinkedList<>();
        List<SBookAuthorDetail> sBookAuthorDetails = bookDAO.fetchAllOf(sBook.getBookId());
        for(SBookAuthorDetail sBookAuthorDetail : sBookAuthorDetails){
            Author author = authorDAO.fetchById(sBookAuthorDetail.getAuthorId());
            if(author != null) authors.add(author);
        }
        return authors;
    }
}
