package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models;

public class LoanModel {
    private int loanId;
    private int librarianId;
    private int borrowerId;
    private int bookCopyCount;
    private String librarianUserName;
    private String librarianFullName;
    private String borrowerUserName;
    private String borrowerFullName;
    private String status;
    private long fee;
    private String bornTime;
    private String expiredTime;

    public LoanModel(int loanId, int librarianId, int borrowerId, int bookCopyCount, String librarianUserName, String librarianFullName, String borrowerUserName, String borrowerFullName, String status, long fee, String bornTime, String expiredTime) {
        this.loanId = loanId;
        this.librarianId = librarianId;
        this.borrowerId = borrowerId;
        this.bookCopyCount = bookCopyCount;
        this.librarianUserName = librarianUserName;
        this.librarianFullName = librarianFullName;
        this.borrowerUserName = borrowerUserName;
        this.borrowerFullName = borrowerFullName;
        this.status = status;
        this.fee = fee;
        this.bornTime = bornTime;
        this.expiredTime = expiredTime;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LoanModel){
            return loanId == ((LoanModel) obj).getLoanId();
        }
        return super.equals(obj);
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public int getBookCopyCount() {
        return bookCopyCount;
    }

    public void setBookCopyCount(int bookCopyCount) {
        this.bookCopyCount = bookCopyCount;
    }

    public String getLibrarianUserName() {
        return librarianUserName;
    }

    public void setLibrarianUserName(String librarianUserName) {
        this.librarianUserName = librarianUserName;
    }

    public String getLibrarianFullName() {
        return librarianFullName;
    }

    public void setLibrarianFullName(String librarianFullName) {
        this.librarianFullName = librarianFullName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
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

    public static class Builder {
        private int loanId;
        private int librarianId;
        private int borrowerId;
        private int bookCopyCount;
        private String librarianUserName;
        private String librarianFullName;
        private String borrowerUserName;
        private String borrowerFullName;
        private String status;
        private long fee;
        private String bornTime;
        private String expiredTime;
        public Builder setLoanId(int loanId) {
            this.loanId = loanId;
            return this;
        }

        public Builder setLibrarianId(int librarianId) {
            this.librarianId = librarianId;
            return this;
        }

        public Builder setBorrowerId(int borrowerId) {
            this.borrowerId = borrowerId;
            return this;
        }

        public Builder setBookCopyCount(int bookCopyCount) {
            this.bookCopyCount = bookCopyCount;
            return this;
        }

        public Builder setLibrarianUserName(String librarianUserName) {
            this.librarianUserName = librarianUserName;
            return this;
        }

        public Builder setLibrarianFullName(String librarianFullName) {
            this.librarianFullName = librarianFullName;
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

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setFee(long fee) {
            this.fee = fee;
            return this;
        }

        public LoanModel build() {
            return new LoanModel(loanId, librarianId, borrowerId, bookCopyCount, librarianUserName, librarianFullName, borrowerUserName, borrowerFullName, status, fee, bornTime, expiredTime);
        }

        public Builder setBornTime(String bornTime) {
            this.bornTime = bornTime;
            return this;
        }

        public Builder setExpiredTime(String expiredTime) {
            this.expiredTime = expiredTime;
            return this;
        }
    }
}
