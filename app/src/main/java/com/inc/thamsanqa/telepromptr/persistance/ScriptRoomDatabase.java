package com.inc.thamsanqa.telepromptr.persistance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.inc.thamsanqa.telepromptr.persistance.dao.ScriptDao;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;

@Database(entities = {Script.class}, version = 1, exportSchema = false)
public abstract class ScriptRoomDatabase extends RoomDatabase {

    public abstract ScriptDao scriptDao();

    private static ScriptRoomDatabase INSTANCE;

    public static ScriptRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (ScriptRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ScriptRoomDatabase.class, "script_database")
                        .build();
            }
        }
        return INSTANCE;
    }

}