package com.inc.thamsanqa.telepromptr.ui;


import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;

import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;
import com.inc.thamsanqa.telepromptr.viewmodel.ScriptViewModel;

import java.util.Calendar;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScriptListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScriptListFragment extends Fragment {

    public static final String FRAGMENT_TAG = "ScriptListFragment.TAG";
    private static int CODE = 200;


    private ScriptViewModel viewModel;
    private MainActivity activity;

    public ScriptListFragment() {
        // Required empty public constructor
    }


    public static ScriptListFragment newInstance() {
        return new ScriptListFragment();
    }

    @BindView(R.id.add_new)
    FloatingActionButton addNew;

    @BindView(R.id.rv_scripts)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_script_list, container, false);

        ButterKnife.bind(this, view);

        activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle("Telepromptr");

        viewModel = ViewModelProviders.of(this).get(ScriptViewModel.class);
        final ScriptAdapter adapter = new ScriptAdapter();

        viewModel.getAllScripts().observe(this, new Observer<List<Script>>() {
            @Override
            public void onChanged(@Nullable List<Script> scripts) {
                adapter.setScripts(scripts);
            }
        });


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        //ArrayList scripts = new ArrayList<Script>();
        Calendar calendar = Calendar.getInstance();

        final Script script1 = new Script();
        script1.setDateInMilli(calendar.getTimeInMillis() - 70000);
        script1.setTitle("Video Blog script 1");
        script1.setBody("Accused of continuing to track users' movements even when they have turned off their " +
                "location history, Google is being sued in the United States for unauthorised location data collection" +
                "We have discussed the basics of the new Android Material Component - BottomAppBar as well as the new FAB " +
                "features. The BottomAppBar widget itself is not complicated to use as it extends the traditional Toolbar " +
                "but it comes with a dramatic app design change price." +
                "Part II and Part III of this BottomAppBar series is on handling navigation drawer control & action menu and the " +
                "implementation of BottomAppBar behaviors conforming to Material Design guidelines.");


        viewModel.saveNewScript(script1);

        //scripts.add(script1);


        final Script script2 = new Script();
        script2.setDateInMilli(calendar.getTimeInMillis() - 9000000);
        script2.setTitle("Script");
        script2.setBody("What is Lorem Ipsum?" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem " +
                "evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).");


        viewModel.saveNewScript(script2);

        //scripts.add(script2);
        //ScriptAdapter adapter = new ScriptAdapter(scripts);
        MainActivity activity = (MainActivity) getActivity();
        adapter.setListener(activity);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void showOptions() {

        final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.options_menu);

        Button upload = dialog.findViewById(R.id.upload);
        Button create = dialog.findViewById(R.id.create);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                browseFile();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), AddScriptActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();

    }

    private void browseFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = null;
        if (data != null) {
            uri = data.getData();
        }

        if (requestCode == CODE) {
            Toast.makeText(getContext(), uri.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
