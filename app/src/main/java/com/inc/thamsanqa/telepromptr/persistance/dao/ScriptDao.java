package com.inc.thamsanqa.telepromptr.persistance.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.inc.thamsanqa.telepromptr.persistance.entities.Script;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ScriptDao {

    @Insert(onConflict = REPLACE)
    void insert(Script script);

    @Query("DELETE FROM script")
    void deleteAll();

    @Query("SELECT * from script ORDER BY date_created_milliseconds ASC")
    LiveData<List<Script>> getAllScripts();

    @Query("SELECT * from script WHERE id = :id")
    Script getScript(int id);
}
