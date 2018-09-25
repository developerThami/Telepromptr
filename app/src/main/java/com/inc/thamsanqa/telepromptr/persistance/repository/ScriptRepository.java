package com.inc.thamsanqa.telepromptr.persistance.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.inc.thamsanqa.telepromptr.persistance.ScriptRoomDatabase;
import com.inc.thamsanqa.telepromptr.persistance.dao.ScriptDao;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;

import java.util.List;

public class ScriptRepository {

    private enum ScriptOperation {
        READ_SCRIPT,
        WRITE_SCRIPT
    }

    private ScriptDao mDao;
    private Script script;

    public ScriptRepository(Context context) {
        mDao = ScriptRoomDatabase.getDatabase(context).scriptDao();
    }

    public void insertScript(Script script) {
        new ScriptAsync().setDao(mDao).setScript(script).execute(ScriptOperation.WRITE_SCRIPT);
    }

    private void setScript(Script script) {
        this.script = script;
    }

    public Script getScript(int id) {
        ScriptAsync async = new ScriptAsync();
        async.setDao(mDao).setId(id).execute(ScriptOperation.READ_SCRIPT);
        return async.getScript();
    }

    public LiveData<List<Script>> getAllScripts() {
        return mDao.getAllScripts();
    }

    public void deleteAll() {
        mDao.deleteAll();
    }

    private class ScriptAsync extends AsyncTask<ScriptOperation, Void, Void> {

        private ScriptDao dao;
        private int id;
        private Script script;

        public ScriptAsync() {
        }

        public ScriptAsync setId(int id) {
            this.id = id;
            return this;
        }

        public ScriptAsync setDao(ScriptDao dao) {
            this.dao = dao;
            return this;
        }

        public ScriptAsync setScript(Script script) {
            this.script = script;
            return this;
        }

        public Script getScript() {
            return this.script;
        }

        @Override
        protected Void doInBackground(ScriptOperation... scriptOperations) {
            switch (scriptOperations[0]) {
                case READ_SCRIPT:
                    setScript(dao.getScript(id));
                    break;
                case WRITE_SCRIPT:
                    dao.insert(script);
                    break;

            }
            return null;
        }
    }
}
