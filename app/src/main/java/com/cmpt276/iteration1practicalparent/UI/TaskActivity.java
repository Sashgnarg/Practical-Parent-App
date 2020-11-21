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

import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.ButtonFunctions;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.Global;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.UtilityFunction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity implements DialogueForTask.DialogueForTaskListener {
    private ArrayList<TaskItem> taskList;
    private ArrayList<ConfigureChildrenItem> mChildrenList;
    private ArrayList<ConfigureChildrenItem> newChildrenList;

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

        initializeTaskData();
        buildTaskRecyclerView();
        setupInsertTaskButton();
    }

    public void initializeTaskData(){
        taskList = new ArrayList<>();
        //ISSUE WITH LOADING CHILDREN: not letting me load the data using utility function..
        //load config children
        //mChildrenList = utility.loadData(this);
        newChildrenList = new ArrayList<ConfigureChildrenItem>();
        newChildrenList.add(new ConfigureChildrenItem(R.drawable.ic_child, "John", "age7"));
        newChildrenList.add(new ConfigureChildrenItem(R.drawable.ic_child, "Emily", "age7"));
        newChildrenList.add(new ConfigureChildrenItem(R.drawable.ic_child, "Silvana", "age7"));

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
                openTaskPopUp();
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
        ConfigureChildrenItem childToInsert;
        int indexOfChild;
        if (!newChildrenList.isEmpty()){
            childToInsert = newChildrenList.get(0);
            indexOfChild = 0;
        }
        else{
            childToInsert = null;
            indexOfChild = -1;
        }
        taskList.add(taskInsertPosition, new TaskItem(R.drawable.task_image, "Task Name", "Task Description", childToInsert, indexOfChild));
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

    public void openTaskPopUp(){

    }

    public void openTaskEditDialog() {
        DialogueForTask dialogueForTask = new DialogueForTask();
        dialogueForTask.show(getSupportFragmentManager(),"Edit Task");
    }

    public void applyTaskChanges(String task_name, String task_description){
        TaskItem currentItem = taskList.get(taskEditPosition);
        taskList.set(taskEditPosition, new TaskItem(R.drawable.task_image, task_name, task_description, currentItem.getChildForTask(), currentItem.getIndexOfChildForTask()));
        taskAdapter.notifyItemChanged(taskEditPosition);
        //TODO:save data
    }


}