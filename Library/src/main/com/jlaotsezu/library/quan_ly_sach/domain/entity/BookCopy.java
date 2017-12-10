package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;


import javax.persistence.*;

@Entity
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookCopyId;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book = null;

    @Enumerated(EnumType.STRING)
    private BookCopyType bookCopyType = BookCopyType.BORROWABLE;

    @Enumerated(EnumType.STRING)
    private BookCopyStatus bookCopyStatus = BookCopyStatus.BORROWABLE;

    private long bookCopyPrice = 0;

    public BookCopy(){

    }

    public BookCopy(Book book, BookCopyType bookCopyType, BookCopyStatus bookCopyStatus, long bookCopyPrice) {
        this.book = book;
        this.bookCopyType = bookCopyType;
        this.bookCopyStatus = bookCopyStatus;
        this.bookCopyPrice = bookCopyPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BookCopy){
            if(bookCopyId != ((BookCopy) obj).getBookCopyId()) return false;
            if(!book.equals(((BookCopy) obj).getBook())) return false;
            return true;
        }
        return super.equals(obj);
    }

    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int id) {
        this.bookCopyId = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookCopyType getBookCopyType() {
        return bookCopyType;
    }

    public void setBookCopyType(BookCopyType type) {
        this.bookCopyType = type;
    }

    public BookCopyStatus getBookCopyStatus() {
        return bookCopyStatus;
    }

    public void setBookCopyStatus(BookCopyStatus status) {
        this.bookCopyStatus = status;
    }

    public long getBookCopyPrice() {
        return bookCopyPrice;
    }

    public void setBookCopyPrice(long bookCopyPrice) {
        this.bookCopyPrice = bookCopyPrice;
    }

    public boolean borrowable() {
        return bookCopyStatus == BookCopyStatus.BORROWABLE;
    }
}
