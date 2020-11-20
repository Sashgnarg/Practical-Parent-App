package com.cmpt276.iteration1practicalparent.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmpt276.iteration1practicalparent.Model.TaskItem;
import com.cmpt276.iteration1practicalparent.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<TaskItem> taskList;

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        public ImageView taskImageView;
        public TextView taskNameTextView;
        public TextView taskDescriptionTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskImageView = itemView.findViewById(R.id.imgTask);
            taskNameTextView = itemView.findViewById(R.id.txtTaskName);
            taskDescriptionTextView = itemView.findViewById(R.id.txtTaskDescription);
        }
    }

    public TaskAdapter(ArrayList<TaskItem> task_List){
        taskList = task_List;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        TaskViewHolder tvh = new TaskViewHolder(v);
        return tvh;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem currentTask = taskList.get(position);

        holder.taskImageView.setImageResource(currentTask.getTaskImage());
        holder.taskNameTextView.setText(currentTask.getTaskName());
        holder.taskDescriptionTextView.setText(currentTask.getTaskDescription());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
