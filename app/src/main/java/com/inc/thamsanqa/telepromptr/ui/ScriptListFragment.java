package com.inc.thamsanqa.telepromptr.ui;


import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;

import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;
import com.inc.thamsanqa.telepromptr.viewmodel.ScriptViewModel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    OnAddScriptListener listener;

    interface OnAddScriptListener {
        void createNewScript(String scriptBody);
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
        activity.getSupportActionBar().setTitle(R.string.app_tile);

        viewModel = ViewModelProviders.of(this).get(ScriptViewModel.class);
        final ScriptAdapter adapter = new ScriptAdapter();

        viewModel.getAllScripts().observe(this, new Observer<List<Script>>() {
            @Override
            public void onChanged(@Nullable List<Script> scripts) {
                adapter.setScripts(scripts);
                MainActivity activity = (MainActivity) getActivity();
                adapter.setListener(activity);

                recyclerView.setLayoutManager(
                        new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        });


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        MainActivity activity = (MainActivity) getActivity();
        adapter.setListener(activity);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setListener(OnAddScriptListener listener) {
        this.listener = listener;
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
                listener.createNewScript(null);
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

        String dataText;
        Uri uri;
        if (requestCode == CODE) {
            if (data != null) {
                uri = data.getData();
                try {
                    dataText = readTextFromUri(uri);
                    listener.createNewScript(dataText);
                    Log.d(FRAGMENT_TAG, dataText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
