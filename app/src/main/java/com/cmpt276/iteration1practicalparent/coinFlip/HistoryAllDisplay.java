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
import com.cmpt276.iteration1practicalparent.UniversalFunction.UtilityFunction;

import java.util.ArrayList;

public class HistoryAllDisplay extends AppCompatActivity {
    UtilityFunction utility;
    ArrayList<CoinHistoryClass> coinHistoryAll;
    String face;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_all);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("History for all Coin Flips");

        utility = new UtilityFunction();
        coinHistoryAll = new ArrayList<>();
        coinHistoryAll = utility.loadCoinHistory(coinHistoryAll,this);

        displayAll();

    }

    private void displayAll() {
        MyListAdapter historyAdatper = new MyListAdapter();

        ListView list = (ListView) findViewById(R.id.showAll);
        list.setAdapter(historyAdatper);

    }

    public class MyListAdapter extends ArrayAdapter<CoinHistoryClass> {
        public MyListAdapter(){
            super(HistoryAllDisplay.this, R.layout.history_of_all_flips, coinHistoryAll);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.history_of_all_flips, parent, false);
            }

            CoinHistoryClass history = coinHistoryAll.get(position);

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

    public static Intent showAll(Context c){
        Intent intent = new Intent(c, HistoryAllDisplay.class);
        return intent;
    }



}

