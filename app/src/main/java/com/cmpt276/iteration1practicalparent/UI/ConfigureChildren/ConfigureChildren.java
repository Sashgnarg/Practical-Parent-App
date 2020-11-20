package com.cmpt276.iteration1practicalparent.UI.ConfigureChildren;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/*
Builds recycler view
sets up insert button
saves/loads/creates the array list of children
responsible for adding/removing/deleting items from the arraylist and updating the recycler view
opens dialogue when insert or edit item is clicked
 */

public class ConfigureChildren extends AppCompatActivity
        implements DialogueForConfigureChildren.DialogueForConfigureChildrenListener, DialogueForSelectImageOrPicture.DialogueForSelectImageOrTakePictureListener {
    public static final String LIST_OF_CHILDREN = "list of children";
    private ArrayList<ConfigureChildrenItem> mChildrenList;

    private RecyclerView mRecyclerView;
    private AdapterForConfigureChildren mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;

    private int editPosition;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_children);
        setTitle("Configure Children");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
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
        editPosition = mChildrenList.size();
        getDefaultImageForChild();

        mChildrenList.add(editPosition, new ConfigureChildrenItem(getDefaultImageForChild(), "Edit Name", "Edit details"));
        openEditDialog();
        mAdapter.notifyItemInserted(editPosition);
        saveData();
    }

    private String getDefaultImageForChild() {
        context = ConfigureChildren.this;
        context.getApplicationContext();
        Resources res = context.getResources();
        String mImageResource = ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(R.drawable.ic_child)
                + '/' + res.getResourceTypeName(R.drawable.ic_child)
                + '/' + res.getResourceEntryName(R.drawable.ic_child);
        return mImageResource;
    }

    public void removeItem(int position) {
        mChildrenList.remove(position);
        mAdapter.notifyItemRemoved(position);
        saveData();
    }

    private void editItem(int position) {
        editPosition = position;
        openEditDialog();
        saveData();
    }

    private void editPhoto(int position) {
        editPosition = position;
        openEditPhotoDialog();
        saveData();
    }

    private void openEditPhotoDialog() {
        DialogueForSelectImageOrPicture dialogueForSelectImageOrPicture = new DialogueForSelectImageOrPicture();
        dialogueForSelectImageOrPicture.show(getSupportFragmentManager(), "Edit Photo");
    }

    public void openEditDialog() {
        DialogueForConfigureChildren dialogueForConfigureChildren = new DialogueForConfigureChildren();
        dialogueForConfigureChildren.show(getSupportFragmentManager(), "Edit Child");
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
            public void onEditClick(int position) {
                editItem(position);
            }

            @Override
            public void onChildPhotoClick(int position) {
                editPhoto(position);
            }

        });
    }

    @Override
    public void applyChanges(String name, String addition_info) {
        ConfigureChildrenItem configureChildrenItem = mChildrenList.get(editPosition);
        configureChildrenItem.setmText1(name);
        configureChildrenItem.setmText2(addition_info);
        mChildrenList.set(editPosition, configureChildrenItem);
        mAdapter.notifyItemChanged(editPosition);
        saveData();
    }


    @Override
    public void applyChangesForPhoto(String uri) {
        ConfigureChildrenItem configureChildrenItem = mChildrenList.get(editPosition);
        configureChildrenItem.setmImageResource(uri);
        mChildrenList.set(editPosition, configureChildrenItem);
        mAdapter.notifyItemChanged(editPosition);
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mChildrenList);
        editor.putString(LIST_OF_CHILDREN, json);
        editor.apply();
    }

    private void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_OF_CHILDREN, null);
        Type type = new TypeToken<ArrayList<ConfigureChildrenItem>>() {
        }.getType();
        mChildrenList = gson.fromJson(json, type);

        if (mChildrenList == null) {
            mChildrenList = new ArrayList<>();
        }
    }
}