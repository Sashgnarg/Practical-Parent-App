/*
 * shows the selected child's coin flip history
 */


package com.cmpt276.iteration1practicalparent.UI.CoinFlip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.Model.CoinHistoryClass;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.UtilityFunction;

import java.util.ArrayList;

public class HistoryCurrDisplay extends AppCompatActivity {
    UtilityFunction utility;
    ArrayList<CoinHistoryClass> coinHistoryAll;
    ArrayList<CoinHistoryClass> currInfo;
    String face;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_current);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("History for Current Child");

        utility = new UtilityFunction();
        coinHistoryAll = new ArrayList<>();
        coinHistoryAll = utility.loadCoinHistory(this);

        // store current child selected info
        currInfo = new ArrayList<>();
        String currChildName = getIntent().getExtras().getString("param");

        for(int i = 0; i < coinHistoryAll.size(); i++){
            if (coinHistoryAll.get(i).getPickersName()!= null && currChildName!=null && coinHistoryAll.get(i).getPickersName().equals(currChildName)){
                currInfo.add(coinHistoryAll.get(i));
            }
        }
        displayCurr();
    }

    private void displayCurr() {
        RecyclerView recyclerView = findViewById(R.id.showCurr);
        historyAdapter adapter = new historyAdapter(this,currInfo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}