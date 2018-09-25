package com.inc.thamsanqa.telepromptr.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddScriptFragment extends Fragment {

    public static final String FRAGMENT_TAG = "PlayScriptFragment.TAG";


    @BindView(R.id.scriptText)
    EditText scriptView;

    private static final String ARG_PARAM1 = "param1";

    private Script script;

    public AddScriptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param script Parameter 1.
     * @return A new instance of fragment PlayScriptFragment.
     */
    public static PlayFragment newInstance(Script script) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        //args.putParcelable(ARG_PARAM1, script);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setHasOptionsMenu(true);

        if (getArguments() != null) {
            script = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add) {
            saveScript();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveScript() {
        Toast.makeText(getContext() , "Saved", Toast.LENGTH_SHORT).show();
    }
}