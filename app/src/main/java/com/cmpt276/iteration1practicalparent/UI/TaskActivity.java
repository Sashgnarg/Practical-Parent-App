package com.cmpt276.iteration1practicalparent.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.Model.TaskItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.DialogueForConfigureChildren;

import java.util.ArrayList;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity implements DialogueForTask.DialogueForTaskListener {
    private ArrayList<TaskItem> taskList;

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter; //provides only the amount of items you need
    private RecyclerView.LayoutManager taskLayoutManager; //aligning items in the list

    private Button buttonInsert;

    private int taskInsertPosition; //the position where we will insert a task
    private int taskEditPosition; //the position to edit a task
    private int indexForChildTurn; //will increment to get position of child from Child list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setTitle("Tasks");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        createTaskList();
        buildTaskRecyclerView();

        setupInsertTaskButton();
    }

    public void createTaskList(){
        taskList = new ArrayList<>();
        //taskList.add(new TaskItem(R.drawable.task_image, "Task Name", "Task Description"));
    }
    public void buildTaskRecyclerView(){

        taskRecyclerView = findViewById(R.id.recyclerviewForTasks);
        taskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(this);
        taskAdapter = new TaskAdapter(taskList);

        taskRecyclerView.setLayoutManager(taskLayoutManager);
        taskRecyclerView.setAdapter(taskAdapter);

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onTaskItemClick(int position) {
                //OPEN DIALOG THAT SHOWS CHILD'S TURN AND PICTURE when clicking on a task
                taskList.get(position);
            }

            @Override
            public void onTaskDeleteClick(int position) {
                removeTask(position);
            }

            @Override
            public void onTaskEditClick(int position) {
                editTask(position);
            }
        });
    }
    //inserting a new task
    private void setupInsertTaskButton(){
        buttonInsert = findViewById(R.id.btnInsertTask);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTask();
            }
        });
    }

    public void insertTask(){
        taskInsertPosition = taskList.size();
        taskList.add(taskInsertPosition, new TaskItem(R.drawable.task_image, "Task Name", "Task Description"));
        openTaskEditDialog();
        taskAdapter.notifyItemInserted(taskInsertPosition);
        //TO DO: save data
    }

    private void removeTask(int position){
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
    private void editTask(int position){
        taskEditPosition = position;
        openTaskEditDialog();
        //TO DO: open a dialog to allow user to re-enter the task name and description
        //TO DO: sava data
    }

    public void openTaskEditDialog() {
        DialogueForTask dialogueForTask = new DialogueForTask();
        dialogueForTask.show(getSupportFragmentManager(),"Edit Task");
    }

    public void applyTaskChanges(String task_name, String task_description){
        taskList.set(taskEditPosition, new TaskItem(R.drawable.task_image, task_name, task_description));
        taskAdapter.notifyItemChanged(taskEditPosition);
        //TODO:save data
    }


}