package com.thopv.projects.libraryforreader.data.source.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thopv.projects.libraryforreader.data.source.schemas.SLoan;
import com.thopv.projects.libraryforreader.data.source.schemas.SLoanDetail;

import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */
@Dao
public interface LoanDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SLoan sLoan);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertDetails(List<SLoanDetail> sLoanDetails);

    @Update
    int update(SLoan sLoan);
    @Update
    int updateDetails(List<SLoanDetail> sLoanDetails);

    @Delete
    int delete(SLoan sLoan);
    @Delete
    int deleteDetails(List<SLoanDetail> sLoanDetails);

    @Query("delete from sloan where loanId = :loanId")
    int delete(long loanId);
    @Query("delete from sloandetail where loanId = :loanId")
    int deleteDetailsOf(long loanId);

    @Query("select * from sloan order by bornTime desc")
    List<SLoan> fetchAll();
    @Query("select * from sloandetail where loanId = :loanId")
    List<SLoanDetail> fetchDetailsOf(long loanId);

    @Query("select * from sloan where loanId = :loanId")
    SLoan fetchById(Long loanId);

    @Query("delete from sloan")
    int clearAll();
    @Query("delete from sloandetail")
    int clearAllDetail();
}
