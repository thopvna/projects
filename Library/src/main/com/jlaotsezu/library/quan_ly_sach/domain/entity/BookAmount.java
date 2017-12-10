package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;


import javax.persistence.*;

@Entity
public class BookAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookAmountId;
    @OneToOne
    @JoinColumn(name="bookId")
    private Book book;
    private int amount;

    public BookAmount(){

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BookAmount){
            return bookAmountId == ((BookAmount) obj).getBookAmountId()
                    && book.equals(((BookAmount) obj).getBook());
        }
        return super.equals(obj);
    }

    public BookAmount(Book book, int amount) {
        this.book = book;
        this.amount = amount;
    }

    public int getBookAmountId() {
        return bookAmountId;
    }

    public void setBookAmountId(int bookAmountId) {
        this.bookAmountId = bookAmountId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
