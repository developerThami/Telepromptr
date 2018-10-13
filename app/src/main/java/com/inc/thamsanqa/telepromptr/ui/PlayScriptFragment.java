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
import android.widget.ScrollView;
import android.widget.TextView;

import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayScriptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayScriptFragment extends Fragment {

    public static final String FRAGMENT_TAG = "PlayScriptFragment.TAG";

    private PlayScriptListener listener;

    interface PlayScriptListener{
        void onPlayScript(Script script);
    }

    TextView scriptView;
    ScrollView scrollView;

    private static final String ARG_PARAM1 = "param1";

    private Script script;
    private MainActivity activity;

    public PlayScriptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param script Parameter 1.
     * @return A new instance of fragment PlayScriptFragment.
     */
    public static PlayScriptFragment newInstance(Script script) {
        PlayScriptFragment fragment = new PlayScriptFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, script);
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
        View view = inflater.inflate(R.layout.fragment_play_script, container, false);

        scriptView = view.findViewById(R.id.scriptTextView);
        scrollView = view.findViewById(R.id.scriptScrollView);

        script = getArguments().getParcelable(ARG_PARAM1);

        activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        return view;
    }

    public void setListener(PlayScriptListener listener) {
        this.listener = listener;
    }

    private void initViews() {
        script = getArguments().getParcelable(ARG_PARAM1);
        activity.getSupportActionBar().setTitle(script.getTitle());
        scriptView.setText(script.getBody());
    }

    public void scroll() {
        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.smoothScrollTo(0, scriptView.getBottom() / 2);
            }
        }, 1000);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_preview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_play:
                listener.onPlayScript(script);
                return true;
            case android.R.id.home:
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                activity.getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
