package com.inc.thamsanqa.telepromptr.ui;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {

    public static final String FRAGMENT_TAG = "PlayFragment.TAG";


    TextView scriptView;
    ScrollView scrollView;
    ImageButton controlButton;

    private static final String ARG_PARAM1 = "param1";

    private Script script;
    private MainActivity activity;
    private TextView progressTextView;

    int scrollTo = 100;

    private boolean isPlaying = false;
    private CardView controlsView;
    private SeekBar seekBar;
    private View view;
    private boolean hasReachedBottom;

    public PlayFragment() {
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
        args.putParcelable(ARG_PARAM1, script);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            script = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play, container, false);

        scriptView = view.findViewById(R.id.scriptTextView);
        scrollView = view.findViewById(R.id.scriptScrollView);
        controlButton = view.findViewById(R.id.controlButton);

        seekBar = view.findViewById(R.id.speedSeekBar);
        progressTextView = view.findViewById(R.id.speedValue);

        controlsView = view.findViewById(R.id.controls);

        initViews();


        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    controlButton.setImageResource(R.drawable.ic_pause);
//                    pause();
                } else {
                    controlButton.setImageResource(R.drawable.ic_play);
                    scroll();
                }
                isPlaying = !isPlaying;

                if (hasReachedBottom) {
                    showDoneScrollingMessage();
                }
            }
        });


        scriptView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                showControls();
            }
        });

        return view;
    }

    private void initViews() {

        activity = (MainActivity) getActivity();
        activity.getSupportActionBar().hide();

        scriptView.setText(script.getBody());
        scriptView.setTextSize(45);
//        scriptBodyEt.setTextSize(60);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    updateSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        hasReachedBottom = scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollView.getScrollY());

                        if (!hasReachedBottom && isPlaying) {
                            scroll();
                        }

                        if(scrollView.getChildAt(0).getBottom() == (scrollView.getHeight() + scrollView.getScrollY())){
                            showDoneScrollingMessage();
                        }

                    }
                });

    }

    private void showDoneScrollingMessage() {
        Toast.makeText(getContext() , "Script done", Toast.LENGTH_LONG).show();
    }

    private void updateSpeed(int progress) {
        progressTextView.setText(progress + "");
        //scriptBodyEt.getLineCount()
    }

    public void scroll() {
        hideControls();
        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.smoothScrollTo(0, scrollTo);
            }
        }, 100);
        scrollTo += 10;
    }

    private void hideControls() {

        scrollView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        controlsView.setVisibility(View.INVISIBLE);
    }

    private void showControls() {
        controlsView.setVisibility(View.VISIBLE);
    }

//    public void scroll() {
//        dialog.hide();
//        scrollView.postDelayed(new Runnable() {
//            public void run() {
//                scrollView.smoothScrollTo(0, scriptBodyEt.getBottom() / 2);
//            }
//        }, 1000);
//    }
}
