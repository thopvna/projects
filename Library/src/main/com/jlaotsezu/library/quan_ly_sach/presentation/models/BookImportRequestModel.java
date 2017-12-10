package main.com.jlaotsezu.library.quan_ly_sach.presentation.models;

public class BookImportRequestModel {
    private int requestId;
    private int librarianId;
    private int bookSum;
    private String librarianName;
    private String bornTime;
    private String status; //confirm or non confirm

    public BookImportRequestModel(int requestId, int librarianId, int bookSum, String librarianName, String bornTime, String status) {
        this.requestId = requestId;
        this.librarianId = librarianId;
        this.bookSum = bookSum;
        this.librarianName = librarianName;
        this.bornTime = bornTime;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public int getBookSum() {
        return bookSum;
    }

    public void setBookSum(int bookSum) {
        this.bookSum = bookSum;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public void setLibrarianName(String librarianName) {
        this.librarianName = librarianName;
    }

    public String getBornTime() {
        return bornTime;
    }

    public void setBornTime(String bornTime) {
        this.bornTime = bornTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BookImportRequestModel){
            return ((BookImportRequestModel) obj).getRequestId() == getRequestId();
        }
        return super.equals(obj);
    }

    public static class Builder {
        private int requestId;
        private int librarianId;
        private int bookSum;
        private String librarianName;
        private String bornTime;
        private String status;

        public Builder setRequestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder setLibrarianId(int librarianId) {
            this.librarianId = librarianId;
            return this;
        }

        public Builder setBookSum(int bookSum) {
            this.bookSum = bookSum;
            return this;
        }

        public Builder setLibrarianName(String librarianName) {
            this.librarianName = librarianName;
            return this;
        }

        public Builder setBornTime(String bornTime) {
            this.bornTime = bornTime;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public BookImportRequestModel build() {
            return new BookImportRequestModel(requestId, librarianId, bookSum, librarianName, bornTime, status);
        }
    }
}
