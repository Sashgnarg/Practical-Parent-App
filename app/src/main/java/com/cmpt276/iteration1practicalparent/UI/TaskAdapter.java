package com.cmpt276.iteration1practicalparent.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.Model.TaskItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.UtilityFunction;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<TaskItem> taskList;
    private OnItemClickListener taskListener;
    private UtilityFunction utility;
    private  ArrayList<ConfigureChildrenItem> configureChildrenItemArrayList;

    public interface OnItemClickListener {
        void onTaskItemClick(int position);
        void onTaskDeleteClick(int position);
        void onTaskEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        taskListener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        public ImageView taskImageView;
        public TextView taskNameTextView;
        public TextView taskDescriptionTextView;
        public ImageView taskDeleteImage;
        public ImageView taskEditImage;
        public TextView taskChildNameTextView;

        public TaskViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            taskImageView = itemView.findViewById(R.id.imgTask);
            taskNameTextView = itemView.findViewById(R.id.txtTaskName);
            taskDescriptionTextView = itemView.findViewById(R.id.txtTaskDescription);
            taskDeleteImage = itemView.findViewById(R.id.imgTaskDelete);
            taskEditImage = itemView.findViewById(R.id.imgTaskEdit);
            taskChildNameTextView = itemView.findViewById(R.id.txtTaskChildName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onTaskItemClick(position);
                        }
                    }
                }
            });

            taskDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onTaskDeleteClick(position);
                        }
                    }
                }
            });

            taskEditImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onTaskEditClick(position);
                        }
                    }
                }
            });

        }
    }

    public TaskAdapter(ArrayList<TaskItem> task_List, ArrayList<ConfigureChildrenItem> configureChildrenItemArrayList){
        taskList = task_List;
        this.configureChildrenItemArrayList = configureChildrenItemArrayList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        TaskViewHolder tvh = new TaskViewHolder(v, taskListener);
        return tvh;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem currentTask = taskList.get(position);
        utility = new UtilityFunction();
        holder.taskImageView.setImageResource(currentTask.getTaskImage());
        holder.taskNameTextView.setText(currentTask.getTaskName());
        holder.taskDescriptionTextView.setText(currentTask.getTaskDescription());
        if (currentTask.getIdOfChild()!= -1){
            ConfigureChildrenItem configureChildrenItem = utility.findChildForTask(currentTask.getIdOfChild(), configureChildrenItemArrayList);
            if(configureChildrenItem!=null) {
                holder.taskChildNameTextView.setText(configureChildrenItem.getmText1());
            }
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
