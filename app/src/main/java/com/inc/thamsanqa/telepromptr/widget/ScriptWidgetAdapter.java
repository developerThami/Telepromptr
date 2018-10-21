package com.inc.thamsanqa.telepromptr.widget;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;
import com.inc.thamsanqa.telepromptr.persistance.repository.ScriptRepository;
import com.inc.thamsanqa.telepromptr.ui.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class ScriptWidgetAdapter implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    private List<Script> scriptsList = new ArrayList<>();


    public ScriptWidgetAdapter(final Context context) {
        this.context = context;
    }

    private void populateScripts() {

        ScriptRepository repository = new ScriptRepository(context);
        repository.getAllScripts().observeForever(new Observer<List<Script>>() {
            @Override
            public void onChanged(@Nullable List<Script> scripts) {
                scriptsList.addAll(scripts);
                Log.d("####", "" + scriptsList.size());
            }
        });

        Script script = new Script();
        script.setTitle("roccky");
        script.setDateInMilli(System.currentTimeMillis() - 100);
        script.setBody("kjashdjhkjajc ajidhlsjhiudh jashldcajs auishcilabnd");

        scriptsList.add(script);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        populateScripts();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return scriptsList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.script_remote_item);

        Script script = scriptsList.get(i);
        remoteView.setTextViewText(R.id.timeRemote, TimeUtil.timeAgo(script.getDateInMilli() - 1000));
        remoteView.setTextViewText(R.id.title, script.getTitle());
        remoteView.setTextViewText(R.id.body, script.getBody());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int i) {
        return scriptsList.get(i).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
