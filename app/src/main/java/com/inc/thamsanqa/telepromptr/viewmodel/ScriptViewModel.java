package com.inc.thamsanqa.telepromptr.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.inc.thamsanqa.telepromptr.persistance.entities.Script;
import com.inc.thamsanqa.telepromptr.persistance.repository.ScriptRepository;

import java.util.List;

public class ScriptViewModel extends AndroidViewModel {

    private ScriptRepository repository;
    private LiveData<List<Script>> allScripts;

    public ScriptViewModel(@NonNull Application application) {
        super(application);
        repository = new ScriptRepository(application);
        allScripts = repository.getAllScripts();
    }

    public LiveData<List<Script>> getAllScripts() {
        return allScripts;
    }

    public void saveNewScript(final Script script) {
        repository.insertScript(script);
    }

    public Script getScript(final int id) {
        return repository.getScript(id);
    }
}
