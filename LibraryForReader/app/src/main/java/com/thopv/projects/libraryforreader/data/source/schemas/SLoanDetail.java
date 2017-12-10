package com.thopv.projects.libraryforreader.data.source.schemas;

import android.arch.persistence.room.Entity;

/**
 * Created by thopv on 12/2/2017.
 */
@Entity(primaryKeys = {"loanId", "bookId"})
public class SLoanDetail {
    private long loanId;
    private long bookId;

    public SLoanDetail(long loanId, long bookId) {
        this.loanId = loanId;
        this.bookId = bookId;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
