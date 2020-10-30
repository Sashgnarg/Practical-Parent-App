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

public class ConfigureChildren extends AppCompatActivity {
    private ArrayList<ConfigureChildrenItem> mChildrenList;

    private RecyclerView mRecyclerView;
    private AdapterForConfigureChildren mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_children);
        setTitle("Configure Children");

        buildRecyclerView();
        createExampleList();

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

    public void changeItem(int position){
        mAdapter.notifyItemChanged(position);
    }
    public void buildRecyclerView() {
        mChildrenList = new ArrayList<>();
        mChildrenList.add(new ConfigureChildrenItem(R.drawable.ic_child, "line 1", "line 2"));
        mChildrenList.add(new ConfigureChildrenItem(R.drawable.ic_child, "line 3", "line 4"));
        mChildrenList.add(new ConfigureChildrenItem(R.drawable.ic_child, "line 5", "line 6"));

    }

    public void createExampleList() {

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
                changeItem(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
}