package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;

import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class BookImportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookImportRequestId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BookAmountBookImportRequest", joinColumns = @JoinColumn(name = "bookImportRequestId"), inverseJoinColumns = @JoinColumn(name= "bookAmountId"))
    private List<BookAmount> booksAmounts;
    @OneToOne
    @JoinColumn(name = "userId")
    private User librarian;
    private long bornTime;
    private boolean isConfirm;
    public BookImportRequest(){

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BookImportRequest){
            if(bookImportRequestId != ((BookImportRequest) obj).getBookImportRequestId())return false;
            if(!librarian.equals(((BookImportRequest) obj).getLibrarian())) return false;
            if(booksAmounts.size() != ((BookImportRequest) obj).getBooksAmounts().size()) return false;
            for(BookAmount bookAmount : ((BookImportRequest) obj).getBooksAmounts()){
                if(!booksAmounts.contains(bookAmount)) return false;
            }
            return true;
        }
        return super.equals(obj);
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public List<BookAmount> getBooksAmounts() {
        return booksAmounts;
    }

    public void setBooksAmounts(List<BookAmount> booksAmounts) {
        this.booksAmounts = booksAmounts;
    }

    public BookImportRequest(User librarian, List<BookAmount> booksAmounts) {
        this.librarian = librarian;
        this.booksAmounts = booksAmounts;
        this.bornTime = System.currentTimeMillis();
    }

    public long getBornTime() {
        return bornTime;
    }

    public void setBornTime(long bornTime) {
        this.bornTime = bornTime;
    }

    public User getLibrarian() {
        return librarian;
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
    }

    public int getBookImportRequestId() {
        return bookImportRequestId;
    }

    public void setBookImportRequestId(int bookImportRequestId) {
        this.bookImportRequestId = bookImportRequestId;
    }

    public int getBookSum() {
        int result = 0;
        if(getBooksAmounts() != null){
            for(BookAmount bookAmount : getBooksAmounts()){
                result += bookAmount.getAmount();
            }
        }
        return result;
    }
}
