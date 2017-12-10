package com.thopv.projects.libraryforreader.loan.domain.entity;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class Loan {
    private long loanId;
    private List<Long> bookIds;
    private long bornTime;
    private boolean wasLend;
    private long startTime;
    private long returnTimeExpected;
    private boolean wasReturn;
    private long returnTime;
    private long fee;

    public Loan(long loanId, List<Long> bookIds, long bornTime, boolean wasLend, long startTime, long returnTimeExpected, boolean wasReturn, long returnTime, long fee) {
        this.loanId = loanId;
        this.bookIds = bookIds;
        this.bornTime = bornTime;
        this.wasLend = wasLend;
        this.startTime = startTime;
        this.returnTimeExpected = returnTimeExpected;
        this.wasReturn = wasReturn;
        this.returnTime = returnTime;
        this.fee = fee;
    }

    public Loan(List<Long> bookIds, long bornTime) {
        this.bookIds = bookIds;
        this.bornTime = bornTime;
        wasLend = false;
        startTime = 0;
        returnTimeExpected = 0;
        wasReturn = false;
        returnTime = 0;
        fee = 0;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }

    public long getBornTime() {
        return bornTime;
    }

    public void setBornTime(long bornTime) {
        this.bornTime = bornTime;
    }

    public boolean isWasLend() {
        return wasLend;
    }

    public void setWasLend(boolean wasLend) {
        this.wasLend = wasLend;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getReturnTimeExpected() {
        return returnTimeExpected;
    }

    public void setReturnTimeExpected(long returnTimeExpected) {
        this.returnTimeExpected = returnTimeExpected;
    }

    public boolean isWasReturn() {
        return wasReturn;
    }

    public void setWasReturn(boolean wasReturn) {
        this.wasReturn = wasReturn;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Loan)
            return getLoanId() == ((Loan) obj).getLoanId() ;
        return super.equals(obj);
    }

    public static class Builder {
        private long loanId;
        private List<Long> bookIds;
        private long bornTime;
        private boolean wasLend;
        private long startTime;
        private long returnTimeExpected;
        private boolean wasReturn;
        private long returnTime;
        private long fee;

        public Builder setLoanId(long loanId) {
            this.loanId = loanId;
            return this;
        }

        public Builder setBookIds(List<Long> bookIds) {
            this.bookIds = bookIds;
            return this;
        }

        public Builder setBornTime(long bornTime) {
            this.bornTime = bornTime;
            return this;
        }

        public Builder setWasLend(boolean wasLend) {
            this.wasLend = wasLend;
            return this;
        }

        public Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setReturnTimeExpected(long returnTimeExpected) {
            this.returnTimeExpected = returnTimeExpected;
            return this;
        }

        public Builder setWasReturn(boolean wasReturn) {
            this.wasReturn = wasReturn;
            return this;
        }

        public Builder setReturnTime(long returnTime) {
            this.returnTime = returnTime;
            return this;
        }

        public Builder setFee(long fee) {
            this.fee = fee;
            return this;
        }

        public Loan build() {
            return new Loan(loanId, bookIds, bornTime, wasLend, startTime, returnTimeExpected, wasReturn, returnTime, fee);
        }
    }
}
