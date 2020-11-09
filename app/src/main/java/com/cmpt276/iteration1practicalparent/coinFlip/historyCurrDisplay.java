package com.cmpt276.iteration1practicalparent.coinFlip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.universalFunction.UtilityFunction;

import java.util.ArrayList;

public class historyCurrDisplay extends AppCompatActivity {
    UtilityFunction utility;
    ArrayList<CoinHistoryClass> coinHistoryAll;
    ArrayList<CoinHistoryClass> currInfo;
    String face;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_current);

        utility = new UtilityFunction();
        coinHistoryAll = new ArrayList<>();
        coinHistoryAll = utility.loadCoinHistory(coinHistoryAll,this);

        // store current child selected info
        currInfo = new ArrayList<>();
        String currChildName = getIntent().getExtras().getString("param");

        for(int i = 0; i < coinHistoryAll.size(); i++){
            if (coinHistoryAll.get(i).getPickersName().equals(currChildName)){
                currInfo.add(coinHistoryAll.get(i));
            }
        }

        displayCurr();
    }

    private void displayCurr() {
        historyCurrDisplay.MyListAdapter historyAdatper = new historyCurrDisplay.MyListAdapter();

        ListView list = (ListView) findViewById(R.id.showCurr);
        list.setAdapter(historyAdatper);

    }

    public class MyListAdapter extends ArrayAdapter<CoinHistoryClass> {
        public MyListAdapter(){
            super(historyCurrDisplay.this, R.layout.history_of_all_flips, currInfo);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.history_of_all_flips, parent, false);
            }

            CoinHistoryClass history = currInfo.get(position);

            TextView display = (TextView) itemView.findViewById(R.id.itemHistory);
            /*display.setText(history.getPickersName() + " " + history.getTime().toString()
            + " " + history.getWinner() + " " + history.getFace());*/

            if (history.getFace() == 0){
                face = "tail";
            }
            else if (history.getFace() == 1){
                face = "head";
            }

            display.setText(history.getTime() + ":\n" +
                    "   " + history.getPickersName() + " chose " + face + "\n" +
                    "   The result was " + history.getWinner());

            return itemView;

        }

    }



}