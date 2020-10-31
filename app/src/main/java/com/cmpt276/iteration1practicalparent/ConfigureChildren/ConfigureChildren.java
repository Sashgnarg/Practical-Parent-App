package com.cmpt276.iteration1practicalparent.ConfigureChildren;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmpt276.iteration1practicalparent.R;

import java.util.ArrayList;

public class ConfigureChildren extends AppCompatActivity implements DialogueForConfigureChildren.DialogueForConfigureChildrenListener{
    private ArrayList<ConfigureChildrenItem> mChildrenList;

    private RecyclerView mRecyclerView;
    private AdapterForConfigureChildren mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;

    private int editPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_children);
        setTitle("Configure Children");

        buildRecyclerView();
        createChildrenList();

        setButtons();
    }


    private void setButtons() {
        buttonInsert = findViewById(R.id.button_insert);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });

    }

    public void insertItem() {
        mChildrenList.add(mAdapter.getItemCount(), new ConfigureChildrenItem(R.drawable.ic_child, "Edit Name", "Edit details"));
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
    }
    public void removeItem(int position) {
        mChildrenList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void editItem(int position) {
        openEditDialog();
        editPosition = position;
    }

    public void openEditDialog() {
        DialogueForConfigureChildren dialogueForConfigureChildren = new DialogueForConfigureChildren();
        dialogueForConfigureChildren.show(getSupportFragmentManager(),"Edit Child");
    }

    public void buildRecyclerView() {
        mChildrenList = new ArrayList<>();
    }

    public void createChildrenList() {

        mRecyclerView = findViewById(R.id.recycler_configure_children);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterForConfigureChildren(mChildrenList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterForConfigureChildren.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mChildrenList.get(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onEditClick(int position) { editItem(position); }
        });
    }

    @Override
    public void applyChanges(String name, String addition_info) {
        mChildrenList.set(editPosition, new ConfigureChildrenItem(R.drawable.ic_child, name, addition_info));
        mAdapter.notifyItemChanged(editPosition);
    }
}