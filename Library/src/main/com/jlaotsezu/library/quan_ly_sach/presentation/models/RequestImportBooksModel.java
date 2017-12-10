package main.com.jlaotsezu.library.quan_ly_sach.presentation.models;

public class RequestImportBooksModel {
    private int bookId;
    private String bookName;
    private String authors;
    private String classification;
    private int count;

    public RequestImportBooksModel(int bookId, String bookName, String authors, String classification, int count) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authors = authors;
        this.classification = classification;
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RequestImportBooksModel){
            return bookId == ((RequestImportBooksModel) obj).getBookId();
        }
        return super.equals(obj);
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class Builder {
        private int bookIdColumn;
        private String bookNameColumn;
        private String authorsColumn;
        private String classificationColumn;
        private int countColumn;

        public Builder setBookIdColumn(int bookIdColumn) {
            this.bookIdColumn = bookIdColumn;
            return this;
        }

        public Builder setBookNameColumn(String bookNameColumn) {
            this.bookNameColumn = bookNameColumn;
            return this;
        }

        public Builder setAuthorsColumn(String authorsColumn) {
            this.authorsColumn = authorsColumn;
            return this;
        }

        public Builder setClassificationColumn(String classificationColumn) {
            this.classificationColumn = classificationColumn;
            return this;
        }

        public Builder setCountColumn(int countColumn) {
            this.countColumn = countColumn;
            return this;
        }

        public RequestImportBooksModel build() {
            return new RequestImportBooksModel(bookIdColumn, bookNameColumn, authorsColumn, classificationColumn, countColumn);
        }
    }
}
