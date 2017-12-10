package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId = 0;
    private String bookTitle = null;
    private String bookBriefContent = null;
    @OneToOne()
    @JoinColumn(name = "classificationId")
    private Classification bookClassification = null;

    @OneToOne()
    @JoinColumn(name="publisherId")
    private Publisher bookPublisher = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="BookAuthor", joinColumns = @JoinColumn(name="bookId"), inverseJoinColumns = @JoinColumn(name="authorId"))
    private List<Author> bookAuthors = null;

    private int bookPublishYear;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Book){
            if(bookId != ((Book) obj).getBookId()) return false;
            if(!bookClassification.equals(((Book) obj).getBookClassification())) return false;
            if(!bookPublisher.equals(((Book) obj).getBookPublisher())) return false;
            if(bookAuthors.size() != ((Book) obj).getBookAuthors().size()) return false;
            for(Author author : ((Book) obj).getBookAuthors()){
                if(!bookAuthors.contains(author))return false;
            }
            return true;
        }
        return super.equals(obj);
    }

    public static class Builder{
        private int id;
        private String title;
        private String briefContent;
        private Publisher publisher;
        private List<Author> author;
        private Classification classification;
        private int publishYear;
        public Book build(){
            Book book = new Book();
            book.setBookId(id);
            book.setBookTitle(title);
            book.setBookPublisher(publisher);
            book.setBookClassification(classification);
            book.setBookBriefContent(briefContent);
            book.setBookPublishYear(publishYear);
            book.setBookAuthors(author);
            return book;
        }
        @Deprecated
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder briefcontent(String briefContent) {
            this.briefContent = briefContent;
            return this;
        }

        public Builder publisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder authors(List<Author> author) {
            this.author = author;
            return this;
        }

        public Builder classification(Classification classification) {
            this.classification = classification;
            return this;
        }
        public Builder publishYear(int year){
            this.publishYear = year;
            return this;
        }
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int id) {
        this.bookId = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String title) {
        this.bookTitle = title;
    }

    public String getBookBriefContent() {
        return bookBriefContent;
    }

    public void setBookBriefContent(String briefContent) {
        this.bookBriefContent = briefContent;
    }

    public Publisher getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(Publisher publisher) {
        this.bookPublisher = publisher;
    }


    public List<Author> getBookAuthors() {
        return bookAuthors;
    }

    public int getBookPublishYear() {
        return bookPublishYear;
    }

    public void setBookPublishYear(int publishYear) {
        this.bookPublishYear = publishYear;
    }

    public void setBookAuthors(List<Author> authors) {
        this.bookAuthors = authors;
    }

    public Classification getBookClassification() {
        return bookClassification;
    }

    public void setBookClassification(Classification classification) {
        this.bookClassification = classification;
    }

}
