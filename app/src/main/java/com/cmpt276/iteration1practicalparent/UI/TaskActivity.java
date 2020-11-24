package com.cmpt276.iteration1practicalparent.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.Model.TaskItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.UtilityFunction;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Console;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class TaskActivity extends AppCompatActivity implements DialogueForTask.DialogueForTaskListener {
    public static final String LIST_OF_TASKS = "list of tasks";
    private ArrayList<TaskItem> taskList;
    private ConfigureChildrenItem currentIteml;
    private ArrayList<ConfigureChildrenItem> childrenList;
    UtilityFunction utility;

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter; //provides only the amount of items you need
    private RecyclerView.LayoutManager taskLayoutManager; //aligning items in the list

    private Button buttonInsert;

    private int taskEditPosition; //the position to edit/insert a task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //code tutorial from: https://www.youtube.com/playlist?list=PLrnPJCHvNZuBtTYUuc5Pyo4V7xZ2HNtf4
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setTitle("Tasks");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        loadData();
        buildTaskRecyclerView();
        setupInsertTaskButton();
    }
    public void buildTaskRecyclerView(){

        taskRecyclerView = findViewById(R.id.recyclerviewForTasks);
        taskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(this);
        taskAdapter = new TaskAdapter(taskList, childrenList);

        taskRecyclerView.setLayoutManager(taskLayoutManager);
        taskRecyclerView.setAdapter(taskAdapter);

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onTaskItemClick(int position) {
                //OPEN DIALOG THAT SHOWS CHILD'S TURN AND PICTURE when clicking on a task
                TaskItem clickedTask = taskList.get(position);
                ConfigureChildrenItem configureChildrenItem = utility.findChildForTask(clickedTask.getIdOfChild(), childrenList);
                if (configureChildrenItem != null){
                    openTaskPopUpWithChild(position, clickedTask);
                }
                else{
                    openTaskPopUpWithNoChild(position, clickedTask);
                }
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
        taskEditPosition = taskList.size();
        ConfigureChildrenItem childToInsert;
        int indexOfChild;
        if (!childrenList.isEmpty()){
            childToInsert = childrenList.get(0);
            indexOfChild = 0;
            taskList.add(taskEditPosition, new TaskItem(R.drawable.task_image, "", "", childToInsert.getIdOfChild(), indexOfChild));
        }
        else{
            indexOfChild = -1;
            taskList.add(taskEditPosition, new TaskItem(R.drawable.task_image, "", "", -1 , indexOfChild));
        }
        openTaskEditDialog();
        taskAdapter.notifyItemInserted(taskEditPosition);
        saveData();
    }

    private void removeTask(int position){
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
        saveData();
    }
    private void editTask(int position){
        taskEditPosition = position;
        openTaskEditDialog();
        saveData();
    }

    public void openTaskPopUpWithChild(int position, TaskItem clickedTask){
        // popup screen that shows task description and child picture+name
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.task_pop_up_dialog,null);

        String taskName = clickedTask.getTaskName();

        ConfigureChildrenItem configureChildrenItem = findChildForTask(clickedTask.getIdOfChild());

        Uri currentTaskChildPicUri = Uri.parse(configureChildrenItem.getImageResource());
        String currentTaskChildName = configureChildrenItem.getmText1();
        int indexOfChildForTask = clickedTask.getIndexOfChildForTask();


        //setting the task name in our dialog pop up
        TextView txtTaskName = view.findViewById(R.id.txtTaskNameDialog);
        txtTaskName.setText(taskName);

        //setting the image and Child name for this task in our dialog pop up
        ImageView imgChild = view.findViewById(R.id.imgTaskChildDialog);
        imgChild.setImageURI(currentTaskChildPicUri);
        TextView txtChildName = view.findViewById(R.id.txtTaskChildNameDialog);
        txtChildName.setText(currentTaskChildName);

        //alert dialog setting
        builder.setCancelable(true);
        builder.setView(view);
        builder.setPositiveButton("Child Completed Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //making sure there are more children in the list
                //if there is, the next child gets their turn to do this task
                if (indexOfChildForTask < childrenList.size()-1){
                    clickedTask.setIndexOfChildForTask(indexOfChildForTask + 1);
                    ConfigureChildrenItem nextChild = childrenList.get(indexOfChildForTask + 1);
                    clickedTask.setChildForTask(nextChild.getIdOfChild());
                    taskAdapter.notifyItemChanged(position);
                }
                //if not we restart indexOfChildForTask to 0
                else {
                    if (!childrenList.isEmpty()) {
                        clickedTask.setIndexOfChildForTask(0);
                        clickedTask.setChildForTask(childrenList.get(0).getIdOfChild());
                        taskAdapter.notifyItemChanged(position);
                    }
                }
                saveData();
            }
        });
        builder.setNegativeButton("Return", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog pop = builder.create();
        pop.show();
    }

    private ConfigureChildrenItem findChildForTask(int idOfChild) {

        for(ConfigureChildrenItem configureChildrenItem : childrenList){
            if (configureChildrenItem.getIdOfChild() == idOfChild)
            {
                return configureChildrenItem;
            }
        }
        return null;
    }

    //if there are no children associated with the task, we display this pop-up instead
    public void openTaskPopUpWithNoChild(int position, TaskItem clickedTask){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.task_pop_up_dialog,null);

        String taskName = clickedTask.getTaskName();

        //setting the task name in our dialog pop up
        TextView txtTaskName = view.findViewById(R.id.txtTaskNameDialog);
        txtTaskName.setText(taskName);

        TextView txtChildName = view.findViewById(R.id.txtTaskChildNameDialog);
        txtChildName.setText("No child for this task (list empty)");

        //alert dialog setting
        builder.setCancelable(true);
        builder.setView(view);

        builder.setNegativeButton("Return", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog pop = builder.create();
        pop.show();
    }

    public void openTaskEditDialog() {
        DialogueForTask dialogueForTask = new DialogueForTask();
        dialogueForTask.show(getSupportFragmentManager(),"Edit Task");
    }

    public void applyTaskChanges(String task_name, String task_description){
        TaskItem currentItem = taskList.get(taskEditPosition);
        taskList.set(taskEditPosition, new TaskItem(R.drawable.task_image, task_name, task_description, currentItem.getIdOfChild(), currentItem.getIndexOfChildForTask()));
        taskAdapter.notifyItemChanged(taskEditPosition);
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString(LIST_OF_TASKS, json);
        editor.apply();
    }

    private void loadData() {
        utility = new UtilityFunction();
        //load config children
        childrenList = utility.loadData(this);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_OF_TASKS, null);
        Type type = new TypeToken<ArrayList<TaskItem>>(){}.getType();
        taskList = gson.fromJson(json, type);

        if(taskList == null){
            taskList = new ArrayList<>();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        //taskAdapter.notifyDataSetChanged();
        for (TaskItem task: taskList){
            if (task.getIndexOfChildForTask() == -1){
                if (!childrenList.isEmpty()){
                    task.setIndexOfChildForTask(0);
                    task.setChildForTask(childrenList.get(0).getIdOfChild());
                    taskAdapter.notifyItemChanged(0);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (TaskItem task: taskList){
            if (task.getIndexOfChildForTask() == -1){
                if (!childrenList.isEmpty()){
                    task.setIndexOfChildForTask(0);
                    task.setChildForTask(childrenList.get(0).getIdOfChild());
                    taskAdapter.notifyItemChanged(0);
                }
            }
        }
    }
}