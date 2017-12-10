package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity;

import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;

import javax.management.timer.Timer;
import javax.persistence.*;
import java.util.List;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;
    @OneToOne
    @JoinColumn(name = "librarianId", referencedColumnName = "userId")
    private User librarian;
    @OneToOne
    @JoinColumn(name = "borrowerId", referencedColumnName = "userId")
    private User borrower;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "LoanBookCopy", joinColumns = @JoinColumn(name="loanId"), inverseJoinColumns = @JoinColumn(name = "bookCopyId"))
    private List<BookCopy> bookCopies;

    private long bornTime;
    private long expiredTime;

    private boolean isReturn;
    private long fee;

    public Loan(){

    }

    public Loan(User librarian, User borrower, List<BookCopy> bookCopies) {
        this.librarian = librarian;
        this.borrower = borrower;
        this.bookCopies = bookCopies;
        bornTime = System.currentTimeMillis();
        expiredTime = bornTime + Timer.ONE_WEEK;
        isReturn = false;
        fee = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Loan){
            if(loanId != ((Loan) obj).getLoanId()) return false;
            if(!librarian.equals(((Loan) obj).getLibrarian())) return false;
            if(!borrower.equals(((Loan) obj).getBorrower())) return false;
            if(bookCopies.size() != ((Loan) obj).getBookCopies().size()) return false;
            for(BookCopy bookCopy : ((Loan) obj).getBookCopies()){
                if(!bookCopies.contains(bookCopy)) return false;
            }
            return true;
        }
        return super.equals(obj);
    }

    public int getBookCopyCount(){
        return bookCopies != null ? bookCopies.size() : 0;
    }

    public User getLibrarian() {
        return librarian;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
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

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getBornTime() {
        return bornTime;
    }

    public void setBornTime(long bornTime) {
        this.bornTime = bornTime;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
