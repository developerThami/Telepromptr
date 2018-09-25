package com.inc.thamsanqa.telepromptr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ScriptAdapter.OnScriptSelectedListener, PlayScriptFragment.PlayScriptListener {

    @BindView(R.id.toolbar)
    Toolbar bar;

    private static final String SCRIPT_KEY = "Script.key";
    Script script;

    boolean isPreviewMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(bar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, ScriptListFragment.newInstance())
                    .addToBackStack(ScriptListFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void scriptSelected(Script script) {
        this.script = script;
        PlayScriptFragment fragment = PlayScriptFragment.newInstance(script);
        fragment.setListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(PlayScriptFragment.FRAGMENT_TAG)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SCRIPT_KEY, script);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onPlayScript(Script script) {
        isPreviewMode = true;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PlayFragment.newInstance(script))
                .addToBackStack(PlayFragment.FRAGMENT_TAG)
                .commit();
    }



//    scriptViewModel = ViewModelProviders.of(this).get(ScriptViewModel.class);
//    script = scriptViewModel.getScript(getIntent().getIntExtra("id",0));
//
//    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//    int currentOrientation = getResources().getConfiguration().orientation;
//
//        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) { //todo add check to check
}