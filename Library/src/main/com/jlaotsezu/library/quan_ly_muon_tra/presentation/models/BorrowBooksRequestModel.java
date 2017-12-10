package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models;

import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;

import java.util.List;

public class BorrowBooksRequestModel {
    public int requestId;
    private int borrowerId;
    private String borrowerUserName;
    private String borrowerFullName;
    private List<BookCopy> bookCopies;
    private int bookCount;
    private String bornTime;
    private String acceptTime;
    private String confirmTime;
    private String expiredTime;
    private String status;

    public BorrowBooksRequestModel(int requestId, int borrowerId, String borrowerUserName, String borrowerFullName, List<BookCopy> bookCopies, String bornTime, String acceptTime, String confirmTime, String expiredTime, String status) {
        this.requestId = requestId;
        this.borrowerId = borrowerId;
        this.borrowerUserName = borrowerUserName;
        this.borrowerFullName = borrowerFullName;
        this.bookCopies = bookCopies;
        this.bookCount = bookCopies != null ? bookCopies.size() : 0;
        this.bornTime = bornTime;
        this.acceptTime = acceptTime;
        this.confirmTime = confirmTime;
        this.expiredTime = expiredTime;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BorrowBooksRequestModel){
            return requestId == ((BorrowBooksRequestModel) obj).getRequestId();
        }
        return super.equals(obj);
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowerUserName() {
        return borrowerUserName;
    }

    public void setBorrowerUserName(String borrowerUserName) {
        this.borrowerUserName = borrowerUserName;
    }

    public String getBorrowerFullName() {
        return borrowerFullName;
    }

    public void setBorrowerFullName(String borrowerFullName) {
        this.borrowerFullName = borrowerFullName;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public String getBornTime() {
        return bornTime;
    }

    public void setBornTime(String bornTime) {
        this.bornTime = bornTime;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public static class Builder {
        private int requestId;
        private int borrowerId;
        private String borrowerUserName;
        private String borrowerFullName;
        private List<BookCopy> bookCopies;
        private String acceptTime;
        private String bornTime;
        private String confirmTime;
        private String expiredTime;
        private String status;

        public Builder setRequestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder setBorrowerId(int borrowerId) {
            this.borrowerId = borrowerId;
            return this;
        }

        public Builder setBorrowerUserName(String borrowerUserName) {
            this.borrowerUserName = borrowerUserName;
            return this;
        }

        public Builder setBorrowerFullName(String borrowerFullName) {
            this.borrowerFullName = borrowerFullName;
            return this;
        }

        public Builder setBookCopies(List<BookCopy> bookCopies) {
            this.bookCopies = bookCopies;
            return this;
        }

        public Builder setBornTime(String bornTime) {
            this.bornTime = bornTime;
            return this;
        }

        public Builder setExpiredTime(String expiredTime) {
            this.expiredTime = expiredTime;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public BorrowBooksRequestModel build() {
            return new BorrowBooksRequestModel(requestId, borrowerId, borrowerUserName, borrowerFullName, bookCopies, bornTime, acceptTime, confirmTime, expiredTime, status);
        }

        public Builder setAcceptTime(String acceptTime) {
            this.acceptTime = acceptTime;
            return this;
        }

        public Builder setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
            return this;
        }
    }
}
