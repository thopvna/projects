package com.thopv.projects.libraryforreader.home.domain.usecases;


import android.support.v4.util.ArraySet;

import com.thopv.projects.libraryforreader.data.source.Repository;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByAuthorName;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByBriefContent;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByClassificationName;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByPublisherName;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByPublisherTime;
import com.thopv.projects.libraryforreader.home.domain.specifications.FindBookByTitle;
import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;
import com.thopv.projects.libraryforreader.support.usecase.UseCase;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseCallback;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by thopv on 12/2/2017.
 */
public class SearchBooks extends UseCase<SearchBooks.RequestValues, SearchBooks.ResponseValues> {
    public static final String INPUT_INVALID_ERROR = "Keyword invalid.";
    public static final String NO_RESULT_MATCHING_ERROR = "Don't have any book matching.";
    public SearchBooks(Repository<Long, Book> bookRepository) {
        this.bookRepository = bookRepository;
    }
    private Repository<Long, Book> bookRepository;
    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        String keyword = requestValues.getKeyword();
        if(keyword == null || keyword.isEmpty()){
            callback.onCompleted(ComplexResponse.fail(INPUT_INVALID_ERROR));
            return;
        }

        List<Book> books = new LinkedList<>();
        Set<Book> setBooks = new HashSet<>();

        List<Book> booksByTitle = bookRepository.find(new FindBookByTitle(keyword));
        List<Book> booksByAuthorName = bookRepository.find(new FindBookByAuthorName(keyword));
        List<Book> booksByBriefContent = bookRepository.find(new FindBookByBriefContent(keyword));
        List<Book> booksByClassificationName = bookRepository.find(new FindBookByClassificationName(keyword));
        List<Book> booksByPublisherName = bookRepository.find(new FindBookByPublisherName(keyword));
        List<Book> booksByPublisherYear = bookRepository.find(new FindBookByPublisherTime(keyword));

        if(booksByAuthorName != null) setBooks.addAll(booksByAuthorName);
        if(booksByTitle != null) setBooks.addAll(booksByTitle);
        if(booksByBriefContent != null) setBooks.addAll(booksByBriefContent);
        if(booksByClassificationName != null) setBooks.addAll(booksByClassificationName);
        if(booksByPublisherName != null) setBooks.addAll(booksByPublisherName);
        if(booksByPublisherYear != null) setBooks.addAll(booksByPublisherYear);

        if(setBooks.isEmpty()) {
            callback.onCompleted(ComplexResponse.fail(NO_RESULT_MATCHING_ERROR));
        }
        else{
            books.addAll(setBooks);
            callback.onCompleted(ComplexResponse.success(new ResponseValues(books)));
        }
    }

    public static class RequestValues extends UseCase.RequestValues {
        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public RequestValues(String keyword) {
            this.keyword = keyword;
        }
    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<Book> books;

        public List<Book> getBooks() {
            return books;
        }

        public ResponseValues(List<Book> books) {
            this.books = books;
        }
    }
}
