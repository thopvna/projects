package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models;

public class BorrowCardModel {
    private int borrowCardId;
    private String borrowerName;
    private String borrowerFullName;
    private int borrowerId;
    private String issueTime;
    private String expireTime;
    private String status;
    private String disableTime;

    public BorrowCardModel(int borrowCardId, String borrowerName, String borrowerFullName, int borrowerId, String issueTime, String expireTime, String status, String disableTime) {
        this.borrowCardId = borrowCardId;
        this.borrowerName = borrowerName;
        this.borrowerFullName = borrowerFullName;
        this.borrowerId = borrowerId;
        this.issueTime = issueTime;
        this.expireTime = expireTime;
        this.status = status;
        this.disableTime = disableTime;
    }

    public int getBorrowCardId() {
        return borrowCardId;
    }

    public void setBorrowCardId(int borrowCardId) {
        this.borrowCardId = borrowCardId;
    }

    public String getBorrowerFullName() {
        return borrowerFullName;
    }

    public void setBorrowerFullName(String borrowerFullName) {
        this.borrowerFullName = borrowerFullName;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(String disableTime) {
        this.disableTime = disableTime;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public static class Builder {
        private int borrowCardId;
        private String borrowerName;
        private String borrowerFullName;
        private int borrowerId;
        private String issueTime;
        private String expireTime;
        private String status;
        private String disableTime;

        public Builder setBorrowCardId(int borrowCardId) {
            this.borrowCardId = borrowCardId;
            return this;
        }

        public Builder setBorrowerFullName(String borrowerFullName) {
            this.borrowerFullName = borrowerFullName;
            return this;
        }

        public Builder setIssueTime(String issueTime) {
            this.issueTime = issueTime;
            return this;
        }

        public Builder setExpireTime(String expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setDisableTime(String disableTime) {
            this.disableTime = disableTime;
            return this;
        }

        public BorrowCardModel build() {
            return new BorrowCardModel(borrowCardId, borrowerName, borrowerFullName, borrowerId, issueTime, expireTime, status, disableTime);
        }

        public Builder setBorrowerId(int borrowerId) {
            this.borrowerId = borrowerId;
            return this;
        }

        public Builder setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
            return this;
        }
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }
}
