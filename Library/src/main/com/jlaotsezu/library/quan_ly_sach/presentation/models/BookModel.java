package main.com.jlaotsezu.library.quan_ly_sach.presentation.models;

public class BookModel {
    private int bookId;
    private String bookName;
    private String classification;
    private String authors;
    private String publisher;
    private int publishYear;

    public BookModel(int bookId, String bookName, String classification, String authors, String publisher, int publishYear) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.classification = classification;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public static class Builder {
        private int bookId;
        private String bookName;
        private String classification;
        private String authors;
        private String publisher;
        private int publishYear;

        public Builder setBookId(int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder setBookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder setClassification(String classification) {
            this.classification = classification;
            return this;
        }

        public Builder setAuthors(String authors) {
            this.authors = authors;
            return this;
        }

        public Builder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setPublishYear(int publishYear) {
            this.publishYear = publishYear;
            return this;
        }

        public BookModel build() {
            return new BookModel(bookId, bookName, classification, authors, publisher, publishYear);
        }
    }
}
