package com.cmpt276.iteration1practicalparent.coinFlip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.cmpt276.iteration1practicalparent.ConfigureChildren.AdapterForConfigureChildren;
import com.cmpt276.iteration1practicalparent.ConfigureChildren.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.universalFunction.ButtonFunctions;
import com.cmpt276.iteration1practicalparent.universalFunction.Global;
import com.cmpt276.iteration1practicalparent.universalFunction.UtilityFunction;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.cmpt276.iteration1practicalparent.universalFunction.Global.LIST_CHILDREN_HISTORY;
import static com.cmpt276.iteration1practicalparent.universalFunction.Global.LIST_OF_CHILDREN;
import static com.cmpt276.iteration1practicalparent.universalFunction.Global.SHAREDPREFERENCE_FOR_CHILDREN;

public class CoinFlipMain extends AppCompatActivity {

    ButtonFunctions buttonF;
    UtilityFunction utility;
    private TextView coinText, currentChildren, coinFlipWinnerText;
    private ArrayList<ConfigureChildrenItem> mChildrenList;

    private String childrenName, winner;
    private static int selection;
    ArrayList<CoinHistoryClass> coinHistory;

    private int coinFace;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Coin Flip");

        //initial functions
        utility = new UtilityFunction();
        coinHistory = new ArrayList<>();
        initialLayout();
    }
    public void initialLayout(){
        //initial layout
        currentChildren = (TextView)findViewById(R.id.current_children);

        coinText = (TextView)findViewById(R.id.coin_text);
        coinFlipWinnerText = (TextView)findViewById(R.id.coin_flip_winner_text);
        Button flipButton = (Button)findViewById(R.id.flip_button);

        setFlipButton(flipButton,coinText);

        mChildrenList = utility.loadData(mChildrenList,this);
        coinHistory = utility.loadCoinHistory(coinHistory,this);
        popUpChildren(this);

    }

    public void popUpChildren(Context context){
        // popup screen
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.coin_picker_list,null);

        //alert dialog setting
        builder.setCancelable(true);
        builder.setView(view);
        AlertDialog pop = builder.create();

        createChildrenList(view,pop);
        pop.show();
    }
    public void popUpMsg(String msg, Context context){
        // popup screen base on popup_dialog.xml
        // change popup_dialog.xml for more effect
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.popup_dialog,null);

        TextView text = (TextView)view.findViewById(R.id.message);
        text.setText(msg);

        builder.setCancelable(true);
        builder.setView(view);
        builder.setPositiveButton("Tail", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                selection = Global.TAIL;
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Head", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                selection = Global.HEAD;
                dialog.cancel();
            }
        });
        AlertDialog pop = builder.create();
        pop.show();
    }
    private void createChildrenList(View view, AlertDialog dialog){
        //recycle layout
        TextView textView = (TextView)view.findViewById(R.id.coin_picker_text);
        RecyclerView mRecyclerView = view.findViewById(R.id.coin_picker_recyc_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        AdapterForConfigureChildren mAdapter = new AdapterForConfigureChildren(mChildrenList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterForConfigureChildren.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                childrenName = mChildrenList.get(position).getmText1();
                updateText();
                dialog.dismiss();
                popUpMsg("Pick Your Face", CoinFlipMain.this);
            }
            @Override
            public void onDeleteClick(int position) {}
            @Override
            public void onEditClick(int position) {}
        });
    }
    private void updateText(){
        currentChildren.setText(childrenName);
    }
    private void saveHistory(){

        if (selection ==  coinFace){
            winner = "WIN";
            coinFlipWinnerText.setText(R.string.win);
        }
        else{
            winner = "LOSE";
            coinFlipWinnerText.setText(R.string.lose);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(Global.CHILDREN_HISTORY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Date currentTime = Calendar.getInstance().getTime();
        coinHistory.add(new CoinHistoryClass(childrenName,currentTime,coinFace,winner));
        String json = gson.toJson(coinHistory);
        editor.putString(Global.LIST_CHILDREN_HISTORY, json);
        editor.commit();
    }

    public void setFlipButton(Button button,TextView coinText) {
        // setup Flip Button and then update textView everyClick
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                coinFace = utility.randomTwoFace();

                coinText.setText(""+coinFace);
                saveHistory();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}