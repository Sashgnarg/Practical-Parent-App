package com.cmpt276.iteration1practicalparent.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cmpt276.iteration1practicalparent.Model.TaskItem;
import com.cmpt276.iteration1practicalparent.R;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView taskRecyclerView;
    private RecyclerView.Adapter taskAdapter; //provides only the amount of items you need
    private RecyclerView.LayoutManager taskLayoutManager; //aligning items in the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ArrayList<TaskItem> taskList = new ArrayList<>();
        taskList.add(new TaskItem(R.drawable.task_image, "Task Name", "Task Description"));


    }
}