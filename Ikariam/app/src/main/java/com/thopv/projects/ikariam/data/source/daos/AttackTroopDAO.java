package com.thopv.projects.ikariam.data.source.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.home.presentation.models.ModelAttackTroop;

import java.util.List;

/**
 * Created by thopv on 11/14/2017.
 */
@Dao
public interface AttackTroopDAO {
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select unitName, amount from attacktroop where amount > 0"
    )
    LiveData<List<BaseTroop>> getAllLive();

    @Query("select * from attacktroop order by amount desc")
    List<AttackTroop> getAll();
    @Query("select * from attacktroop where amount > 0 order by amount desc")
    List<AttackTroop> getAllAvailable();

    @Delete
    int deleteAll(List<AttackTroop> troops);
    @Delete
    int delete(AttackTroop troop);

    @Query("select * from attacktroop where unitName = :unitName ")
    AttackTroop get(String unitName);

    @Update
    int updateAll(AttackTroop...troops);
    @Update
    int updateAll(List<AttackTroop> troops);
    @Update
    int update(AttackTroop attackTroop);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AttackTroop troop);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<AttackTroop> attackTroops);
}
