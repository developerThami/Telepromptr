package com.inc.thamsanqa.telepromptr.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
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
import com.inc.thamsanqa.telepromptr.viewmodel.ScriptViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddScriptFragment extends Fragment {

    public static final String FRAGMENT_TAG = "AddScriptFragment.TAG";
    private String scriptBody;

    @BindView(R.id.scriptText)
    EditText scriptBodyEt;

    @BindView(R.id.scriptTitle)
    EditText scriptTitleEt;


    public AddScriptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayScriptFragment.
     */
    public static AddScriptFragment newInstance(@Nullable String scriptBody) {
        AddScriptFragment fragment = new AddScriptFragment();
        fragment.scriptBody = scriptBody;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this, view);

        MainActivity activity = (MainActivity) getActivity();
        ActionBar bar = activity.getSupportActionBar();

        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(R.string.create_new_title);

        if (this.scriptBody != null)
            scriptBodyEt.setText(this.scriptBody);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                saveScript();
                return true;
            case android.R.id.home:
                goHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveScript() {

        ScriptViewModel viewModel = ViewModelProviders.of(this).get(ScriptViewModel.class);

        String title = scriptTitleEt.getText().toString();
        String body = scriptBodyEt.getText().toString();

        if (!TextUtils.isEmpty(body)) {

            title = !title.isEmpty() ? title : "Untitled";

            Script script = new Script();

            script.setTitle(title);
            script.setBody(body);
            script.setDateInMilli(System.currentTimeMillis());
            viewModel.saveNewScript(script);

            goHome();
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }

    private void goHome() {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}