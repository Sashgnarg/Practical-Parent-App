/*
 * show history of all coin flips
 */

package com.cmpt276.iteration1practicalparent.UI.CoinFlip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.Model.CoinHistoryClass;
import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.UtilityFunction;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;

import java.util.ArrayList;

public class HistoryAllDisplay extends AppCompatActivity {
    UtilityFunction utility;
    ArrayList<CoinHistoryClass> coinHistoryAll;
    ArrayList<ConfigureChildrenItem> childList;
    Uri currentTaskChildPicUri;
    String face;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_all);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("History for all Coin Flips");

        utility = new UtilityFunction();

        coinHistoryAll = new ArrayList<>();
        coinHistoryAll = utility.loadCoinHistory(this);
        childList = utility.loadData(this);

        displayAll();

    }

    private void displayAll() {

        RecyclerView recyclerView = findViewById(R.id.showAll);

        historyAdapter adapter = new historyAdapter(this,coinHistoryAll);
        
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

}

