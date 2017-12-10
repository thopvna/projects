package com.thopv.projects.libraryforreader.data.source.schemas;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
@Entity
public class SLoan {
    @PrimaryKey(autoGenerate = true)
    private long loanId;
    private long bornTime;
    private boolean wasLend;
    private long startTime;
    private long returnTimeExpected;
    private boolean wasReturn;
    private long returnTime;
    private long fee;

    public SLoan(long loanId, long bornTime, boolean wasLend, long startTime, long returnTimeExpected, boolean wasReturn, long returnTime, long fee) {
        this.loanId = loanId;
        this.bornTime = bornTime;
        this.wasLend = wasLend;
        this.startTime = startTime;
        this.returnTimeExpected = returnTimeExpected;
        this.wasReturn = wasReturn;
        this.returnTime = returnTime;
        this.fee = fee;
    }

    @Ignore
    public SLoan(Loan loan){
        this.loanId = loan.getLoanId();
        this.bornTime = loan.getBornTime();
        this.wasLend = loan.isWasLend();
        this.startTime = loan.getStartTime();
        this.returnTimeExpected = loan.getReturnTimeExpected();
        this.wasReturn = loan.isWasReturn();
        this.returnTime = loan.getReturnTime();
        this.fee = loan.getFee();
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
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
}
