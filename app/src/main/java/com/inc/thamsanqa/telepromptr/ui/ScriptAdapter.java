package com.inc.thamsanqa.telepromptr.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inc.thamsanqa.telepromptr.R;
import com.inc.thamsanqa.telepromptr.persistance.entities.Script;

import java.util.Calendar;
import java.util.List;


public class ScriptAdapter extends RecyclerView.Adapter<ScriptAdapter.ScriptViewHolder> {

    Context context;
    List<Script> scripts;

    private OnScriptSelectedListener listener;

    interface OnScriptSelectedListener {
        void scriptSelected(Script script);
    }

    public ScriptAdapter() {
    }

    public void setScripts(List<Script> scripts) {
        this.scripts = scripts;
        notifyDataSetChanged();
    }

    public void setListener(OnScriptSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        return scripts.get(position).getId();
    }

    @Override
    public ScriptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.script_item, parent, false);
        final ScriptViewHolder vh = new ScriptViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.scriptSelected(scripts.get(vh.getAdapterPosition()));
            }
        });
        return vh;
    }


    @Override
    public void onBindViewHolder(ScriptViewHolder holder, int position) {
        holder.bind(scripts.get(position));
    }

    @Override
    public int getItemCount() {

        if(scripts != null){
            return scripts.size();
        }

        return 0;
    }

    class ScriptViewHolder extends RecyclerView.ViewHolder {

        TextView timeView;
        TextView titleView;
        TextView bodyView;

        ScriptViewHolder(View view) {
            super(view);

            timeView = view.findViewById(R.id.time);
            titleView = view.findViewById(R.id.title);
            bodyView = view.findViewById(R.id.body);
        }

        void bind(Script script) {
            timeView.setText(timeAgo(script.getDateInMilli()));
            titleView.setText(script.getTitle());
            bodyView.setText(script.getBody());
        }

        String timeAgo(long pastDate) {

            Calendar current = Calendar.getInstance();

            long diff = current.getTimeInMillis() - pastDate;
            long diffSeconds = diff / 1000;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            String time = null;
            if (diffDays > 0) {
                if (diffDays == 1) {
                    time = diffDays + "day ago ";
                } else {
                    time = diffDays + "days ago ";
                }
            } else {
                if (diffHours > 0) {
                    if (diffHours == 1) {
                        time = diffHours + "hr ago";
                    } else {
                        time = diffHours + "hrs ago";
                    }
                } else {
                    if (diffMinutes > 0) {
                        if (diffMinutes == 1) {
                            time = diffMinutes + "min ago";
                        } else {
                            time = diffMinutes + "mins ago";
                        }
                    } else {
                        if (diffSeconds > 0) {
                            time = diffSeconds + "secs ago";
                        }
                    }

                }

            }

            return time;
        }

    }
}


