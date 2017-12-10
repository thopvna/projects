package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models;

public class BorrowerModel {
    private int borrowerId;
    private String borrowUserName;
    private String borrowerFullName;
    private String borrowerGender;
    private String borrowerEmail;
    private String borrowerPhone;

    public BorrowerModel(int borrowerId, String borrowUserName, String borrowerFullName, String borrowerGender, String borrowerEmail, String borrowerPhone) {
        this.borrowerId = borrowerId;
        this.borrowUserName = borrowUserName;
        this.borrowerFullName = borrowerFullName;
        this.borrowerGender = borrowerGender;
        this.borrowerEmail = borrowerEmail;
        this.borrowerPhone = borrowerPhone;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getBorrowerFullName() {
        return borrowerFullName;
    }

    public void setBorrowerFullName(String borrowerFullName) {
        this.borrowerFullName = borrowerFullName;
    }

    public String getBorrowerGender() {
        return borrowerGender;
    }

    public void setBorrowerGender(String borrowerGender) {
        this.borrowerGender = borrowerGender;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
    }

    public String getBorrowerPhone() {
        return borrowerPhone;
    }

    public void setBorrowerPhone(String borrowerPhone) {
        this.borrowerPhone = borrowerPhone;
    }

    public static class Builder {
        private int borrowerId;
        private String borrowUserName;
        private String borrowerFullName;
        private String borrowerGender;
        private String borrowerEmail;
        private String borrowerPhone;

        public Builder setBorrowerId(int borrowerId) {
            this.borrowerId = borrowerId;
            return this;
        }

        public Builder setBorrowUserName(String borrowUserName) {
            this.borrowUserName = borrowUserName;
            return this;
        }

        public Builder setBorrowerFullName(String borrowerFullName) {
            this.borrowerFullName = borrowerFullName;
            return this;
        }

        public Builder setBorrowerGender(String borrowerGender) {
            this.borrowerGender = borrowerGender;
            return this;
        }

        public Builder setBorrowerEmail(String borrowerEmail) {
            this.borrowerEmail = borrowerEmail;
            return this;
        }

        public Builder setBorrowerPhone(String borrowerPhone) {
            this.borrowerPhone = borrowerPhone;
            return this;
        }

        public BorrowerModel build() {
            return new BorrowerModel(borrowerId, borrowUserName, borrowerFullName, borrowerGender, borrowerEmail, borrowerPhone);
        }
    }
}
