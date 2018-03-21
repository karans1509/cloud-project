package com.example.karan.auto_attendance;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Karan on 12-03-2018.
 */

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeekViewHolder> {
    private Context cxt;
    private List<Week> weeksList;

    public WeeksAdapter(Context cxt, List<Week> weeksList) {
        this.cxt = cxt;
        this.weeksList = weeksList;
    }

    @Override
    public WeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cxt);
        View view = inflater.inflate(R.layout.week_card, null);
        WeekViewHolder holder = new WeekViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeekViewHolder holder, int position) {
        Week week = weeksList.get(position);
        holder.weekNum.setText(week.getWeek());
        holder.weekDate.setText(week.getDate());
    }

    @Override
    public int getItemCount() {
        return weeksList.size();
    }

    class WeekViewHolder extends RecyclerView.ViewHolder {
        public TextView weekNum;
        public TextView weekDate;

        public WeekViewHolder(final View itemView) {
            super(itemView);
            weekNum = (TextView)itemView.findViewById(R.id.weekLabel);
            weekDate = (TextView)itemView.findViewById(R.id.dateLabel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(cxt, DetailsActivity.class);
                    intent.putExtra("number", weekNum.getText().toString());
                    cxt.startActivity(intent);
//                    Snackbar snackbar = Snackbar.make(itemView, "You clicked"+weekNum.getText().toString(), Snackbar.LENGTH_LONG);
//                    snackbar.show();
                }
            });
        }
    }

}
