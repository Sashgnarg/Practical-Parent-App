/*
 * show history of all coin flips
 */

package com.cmpt276.iteration1practicalparent.UI.CoinFlip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
        MyListAdapter historyAdatper = new MyListAdapter();

        ListView list = (ListView) findViewById(R.id.showAll);
        list.setAdapter(historyAdatper);

    }

    public class MyListAdapter extends ArrayAdapter<CoinHistoryClass> {
        public MyListAdapter(){
            super(HistoryAllDisplay.this, R.layout.history_all, coinHistoryAll);

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null){
                //itemView = getLayoutInflater().inflate(R.layout.history_of_all_flips, parent, false);
                itemView = getLayoutInflater().inflate(R.layout.history_all, parent, false);
            }
            CoinHistoryClass history = coinHistoryAll.get(position);

            //TextView display = (TextView) itemView.findViewById(R.id.itemHistory);
            TextView display = (TextView) itemView.findViewById(R.id.historyAllTxtView);
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

            ImageView childImg = findViewById(R.id.childImgHistoryAll);

            ConfigureChildrenItem tempChild = IDChecker(history);

            if (tempChild != null) {
                currentTaskChildPicUri = Uri.parse(tempChild.getImageResource());
                try{
                    childImg.setImageURI(currentTaskChildPicUri);
                }catch (NullPointerException exception){
                    Log.d("check: 2 ", currentTaskChildPicUri.toString());
                }
            }
            return itemView;
        }

    }
    public ConfigureChildrenItem IDChecker(CoinHistoryClass history){
        for (ConfigureChildrenItem child : childList){
            if (child.getIdOfChild() == history.getChild().getIdOfChild()){
                return child;
            }
        }
        return null;
    }
}

