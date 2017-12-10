package com.thopv.projects.libraryforreader.home.domain.entity;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
public class Book {
    private long bookId;
    private String title;
    private String briefContent;
    private long publishTime;
    private int bookCopyAmount;
    private int searchAmount;
    private long lastedImportTime;
    private Classification classification;
    private List<Author> authors;
    private Publisher publisher;

    public Book(long bookId, String title, String briefContent, long publishTime, int bookCopyAmount, int searchAmount, long lastedImportTime, Classification classification, List<Author> authors, Publisher publisher) {
        this.bookId = bookId;
        this.title = title;
        this.briefContent = briefContent;
        this.publishTime = publishTime;
        this.bookCopyAmount = bookCopyAmount;
        this.searchAmount = searchAmount;
        this.lastedImportTime = lastedImportTime;
        this.classification = classification;
        this.authors = authors;
        this.publisher = publisher;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getBookCopyAmount() {
        return bookCopyAmount;
    }

    public void setBookCopyAmount(int bookCopyAmount) {
        this.bookCopyAmount = bookCopyAmount;
    }

    public int getSearchAmount() {
        return searchAmount;
    }

    public void setSearchAmount(int searchAmount) {
        this.searchAmount = searchAmount;
    }

    public long getLastedImportTime() {
        return lastedImportTime;
    }

    public void setLastedImportTime(long lastedImportTime) {
        this.lastedImportTime = lastedImportTime;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Book)
            return ((Book) obj).getBookId() == getBookId();
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return (int) bookId;
    }

    public static class Builder {
        private long bookId;
        private String title;
        private String briefContent;
        private long publishTime;
        private int bookCopyAmount;
        private int searchAmount;
        private long lastedImportTime;
        private Classification classification;
        private List<Author> authors;
        private Publisher publisher;

        public Builder setBookId(long bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBriefContent(String briefContent) {
            this.briefContent = briefContent;
            return this;
        }

        public Builder setPublishTime(long publishTime) {
            this.publishTime = publishTime;
            return this;
        }

        public Builder setBookCopyAmount(int bookCopyAmount) {
            this.bookCopyAmount = bookCopyAmount;
            return this;
        }

        public Builder setSearchAmount(int searchAmount) {
            this.searchAmount = searchAmount;
            return this;
        }

        public Builder setLastedImportTime(long lastedImportTime) {
            this.lastedImportTime = lastedImportTime;
            return this;
        }

        public Builder setClassification(Classification classification) {
            this.classification = classification;
            return this;
        }

        public Builder setAuthors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Builder setPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Book build() {
            return new Book(bookId, title, briefContent, publishTime, bookCopyAmount, searchAmount, lastedImportTime, classification, authors, publisher);
        }
    }
}
