package com.cmpt276.iteration1practicalparent.coinFlip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.cmpt276.iteration1practicalparent.ConfigureChildren.AdapterForConfigureChildren;
import com.cmpt276.iteration1practicalparent.ConfigureChildren.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.universalFunction.ButtonFunctions;
import com.cmpt276.iteration1practicalparent.universalFunction.Global;
import com.cmpt276.iteration1practicalparent.universalFunction.UtilityFunction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CoinFlipMain extends AppCompatActivity {


    ButtonFunctions buttonF;
    UtilityFunction utility;
    private TextView currentChildren, coinFlipResultText;
    private ArrayList<ConfigureChildrenItem> mChildrenList;

    private String childrenName, winner;
    private int selection,coinFace;
    ArrayList<CoinHistoryClass> coinHistory;

    private Button historyAllBtn;
    private Button historyCurrBtn;


    // hello i am andrew
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

        historyAllBtn = (Button) findViewById(R.id.viewAllHistory);
        historyAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = historyAllDisplay.showAll(CoinFlipMain.this);
                startActivity(intent);
            }
        });

        historyCurrBtn = (Button) findViewById(R.id.viewCurrHistory);
        historyCurrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoinFlipMain.this, historyCurrDisplay.class);
                intent.putExtra("param", childrenName);
                startActivity(intent);
            }
        });



    }
    public void initialLayout(){
        //initial layout
        currentChildren = (TextView)findViewById(R.id.current_children);

        //coinText = (TextView)findViewById(R.id.coin_text);
        coinFlipResultText = (TextView)findViewById(R.id.coin_flip_result_text);
        Button flipButton = (Button)findViewById(R.id.flip_button);
        //hi

        setFlipButton(flipButton);

        mChildrenList = utility.loadData(mChildrenList,this);
        coinHistory = utility.loadCoinHistory(coinHistory,this);

        if (!mChildrenList.isEmpty()){ //if there is config children
            popUpChildren(this);
        }
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
        String coinResult;
        if (coinFace == 1){
            coinResult = "Heads";
        }
        else {
            coinResult = "Tails";
        }
        coinFlipResultText.setText(coinResult);

        if (selection ==  coinFace){
            winner = "WIN";
            //coinFlipWinnerText.setText(R.string.win);
        }
        else if(childrenName == null){
            winner = "";
        }
        else{
            winner = "LOSE";
            //coinFlipWinnerText.setText(R.string.lose);
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

    public void setFlipButton(Button button) {
        // setup Flip Button and then update imgView everyClick
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                coinFace = utility.randomTwoFace();
                flipCoin(coinFace);
                MediaPlayer coinSound = MediaPlayer.create(CoinFlipMain.this, R.raw.coin_flip_sound);
                coinSound.start();
                if (!mChildrenList.isEmpty()){ //if there is config children
                    saveHistory();
                }
            }
        });
    }

    //code tutorial: https://www.youtube.com/watch?v=eoPRhXoIOWA
    private void flipCoin(int coinFace){
        ImageView coin = (ImageView) findViewById(R.id.imgCoin);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(500);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //if coin was heads:
                if (coinFace == 1){
                    coin.setImageResource(R.drawable.heads);
                }
                else {
                    coin.setImageResource(R.drawable.tails);
                }
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(500);
                fadeIn.setFillAfter(true);
                coin.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        coin.startAnimation(fadeOut);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}