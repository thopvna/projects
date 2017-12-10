package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity;

import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.management.timer.Timer;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class BorrowBooksRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowBooksRequestId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User borrower;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "BookCopyBorrowBooksRequest", joinColumns = @JoinColumn(name = "borrowBooksRequestId"), inverseJoinColumns = @JoinColumn(name = "bookCopyId"))
    private List<BookCopy> bookCopies;

    private long bornTime;
    private long acceptTime;
    private long confirmTime;
    private long expiredTime;

    private boolean isAccept;
    private boolean isConfirm;

    public BorrowBooksRequest(){

    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BorrowBooksRequest){
            if(borrowBooksRequestId != ((BorrowBooksRequest) obj).getBorrowBooksRequestId()) return false;
            if(!borrower.equals(((BorrowBooksRequest) obj).getBorrower())) return false;
            if(bookCopies.size() != ((BorrowBooksRequest) obj).getBookCopies().size()) return false;
            for(BookCopy book : ((BorrowBooksRequest) obj).getBookCopies()){
                if(!bookCopies.contains(book)) return false;
            }
            return true;
        }
        return super.equals(obj);
    }

    public BorrowBooksRequest(User borrower, List<BookCopy> bookCopies) {
        this.borrower = borrower;
        this.bookCopies = bookCopies;
        this.isConfirm = false;
        this.isAccept = false;
        this.bornTime = System.currentTimeMillis();
    }

    public int getBorrowBooksRequestId() {
        return borrowBooksRequestId;
    }

    public void setBorrowBooksRequestId(int borrowBooksRequestId) {
        this.borrowBooksRequestId = borrowBooksRequestId;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }
    public List<Integer> getBookCopyIds(){
        List<Integer> bookCopyIds = new LinkedList<>();
        if(bookCopies != null){
            for(BookCopy bookCopy : bookCopies){
                bookCopyIds.add(bookCopy.getBookCopyId());
            }
        }
        return bookCopyIds;
    }

    public void setBookCopies(List<BookCopy> books) {
        this.bookCopies = books;
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public long getBornTime() {
        return bornTime;
    }

    public void setBornTime(long bornTime) {
        this.bornTime = bornTime;
    }
//    xac nhan lay sach
    public void confirm() {
        isConfirm = true;
        confirmTime = System.currentTimeMillis();
    }

    public long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public boolean isExpired() {
        return expiredTime > System.currentTimeMillis();
    }

    public long getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(long acceptTime) {
        this.acceptTime = acceptTime;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }
    public void accept(){
        isAccept = true;
        acceptTime = System.currentTimeMillis();
        expiredTime = acceptTime + Timer.ONE_DAY * 2;
    }
}
