package com.example.karan.auto_attendance;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Karan on 14-03-2018.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context cxt;
    private List<Student> studentList;
    private int count = 0;

    public StudentAdapter(Context cxt, List<Student> studentList) {
        this.cxt = cxt;
        this.studentList = studentList;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cxt);
        View view = inflater.inflate(R.layout.student_card, null);
        StudentViewHolder holder = new StudentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.name.setText(student.getName());
        holder.id.setText(student.getId());
        holder.presence.setText(student.getPresence());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView name, id, presence;
        RelativeLayout relativeLayout;

        public StudentViewHolder(View itemView) {
            super(itemView);
            count++;
            relativeLayout = itemView.findViewById(R.id.relative);
            if(count%2 != 0) {
//                relativeLayout.setBackgroundColor(Color.YELLOW);
            }
            else {
                relativeLayout.setBackgroundColor(Color.LTGRAY);
            }

            name = itemView.findViewById(R.id.nameLabel);
            id = itemView.findViewById(R.id.idLabel);
            presence = itemView.findViewById(R.id.presentLabel);
        }
    }
}
