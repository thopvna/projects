package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models;

public class BookCopyModel {
    private int bookCopyId;
    private int bookId;
    private String bookCopyStatus;
    private String bookCopyType;
    private String bookName;
    private String classification;
    private String authors;
    private String publisher;
    private int publishYear;

    public BookCopyModel(int bookCopyId, int bookId, String bookCopyStatus, String bookCopyType, String bookName, String classification, String authors, String publisher, int publishYear) {
        this.bookCopyId = bookCopyId;
        this.bookId = bookId;
        this.bookCopyStatus = bookCopyStatus;
        this.bookCopyType = bookCopyType;
        this.bookName = bookName;
        this.classification = classification;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BookCopyModel){
            return getBookCopyId() == ((BookCopyModel) obj).getBookCopyId();
        }
        return super.equals(obj);
    }

    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookCopyStatus() {
        return bookCopyStatus;
    }

    public void setBookCopyStatus(String bookCopyStatus) {
        this.bookCopyStatus = bookCopyStatus;
    }

    public String getBookCopyType() {
        return bookCopyType;
    }

    public void setBookCopyType(String bookCopyType) {
        this.bookCopyType = bookCopyType;
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
        private int bookCopyId;
        private int bookId;
        private String bookCopyStatus;
        private String bookCopyType;
        private String bookName;
        private String classification;
        private String authors;
        private String publisher;
        private int publishYear;

        public Builder setBookCopyId(int bookCopyId) {
            this.bookCopyId = bookCopyId;
            return this;
        }

        public Builder setBookId(int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder setBookCopyStatus(String bookCopyStatus) {
            this.bookCopyStatus = bookCopyStatus;
            return this;
        }

        public Builder setBookCopyType(String bookCopyType) {
            this.bookCopyType = bookCopyType;
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

        public BookCopyModel build() {
            return new BookCopyModel(bookCopyId, bookId, bookCopyStatus, bookCopyType, bookName, classification, authors, publisher, publishYear);
        }
    }
}
