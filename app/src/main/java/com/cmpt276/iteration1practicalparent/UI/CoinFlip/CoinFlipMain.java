package com.cmpt276.iteration1practicalparent.UI.CoinFlip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
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


import com.cmpt276.iteration1practicalparent.Model.CoinHistoryClass;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.AdapterForConfigureChildren;
import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.ButtonFunctions;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.Global;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.UtilityFunction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CoinFlipMain extends AppCompatActivity {

    ButtonFunctions buttonF;
    UtilityFunction utility;
    private TextView currentChildTextV, coinFlipResultText,nextChildTextV, currentFaceV;
    private ArrayList<ConfigureChildrenItem> mChildrenList;

    private String winner;
    private ConfigureChildrenItem currentChild, previousChild, nextChild;
    private int selection,coinFace;
    private ImageView childImage;
    ArrayList<CoinHistoryClass> coinHistory;

    private Button historyAllBtn;
    private Button historyCurrBtn;


    // coin Flip Main class:
    // doing the coin flip and save to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.coin_flip);

        //initial functions
        utility = new UtilityFunction();
        buttonF = new ButtonFunctions();

        coinHistory = new ArrayList<>();
        initialLayout();


        historyAllBtn = (Button) findViewById(R.id.viewAllHistory);
        buttonF.setChangeActivity(historyAllBtn,CoinFlipMain.this,HistoryAllDisplay.class,false);
        historyCurrBtn = (Button) findViewById(R.id.viewCurrHistory);
        historyCurrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoinFlipMain.this, HistoryCurrDisplay.class);
                if (currentChild!=null){
                    intent.putExtra("param", currentChild.getmText1());
                }
                else{
                    intent.putExtra("param", ""); //prevent error when passing value
                                                               //when there is no children selected
                }
                startActivity(intent);
            }
        });
    }
    private void initialLayout(){
        //initial layout
        currentChildTextV = (TextView)findViewById(R.id.current_children);
        nextChildTextV = (TextView)findViewById(R.id.new_child_text);
        currentFaceV = (TextView)findViewById(R.id.coin_current_face);

        childImage = (ImageView)findViewById(R.id.child_image_view);
        childImage.setImageResource(R.drawable.ic_child); //default image

        //coinText = (TextView)findViewById(R.id.coin_text);
        coinFlipResultText = (TextView)findViewById(R.id.coin_flip_result_text);
        Button flipButton = (Button)findViewById(R.id.flip_button);

        setFlipButton(flipButton); //button to flip coin
        setSwitchChildButton();// button to switch child
        setSwitchFaceButton(); // button to pick new face


        //load config children
        mChildrenList = utility.loadData(this);
        //load coin history
        coinHistory = utility.loadCoinHistory(this);

        if (!mChildrenList.isEmpty()){ //if there is config children
            popUpChildren(this);
        }
    }

    public void popUpChildren(Context context){
        // popup screen that shows a list of children
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.coin_picker_list,null);

        //alert dialog setting
        builder.setCancelable(true);
        builder.setView(view);
        builder.setPositiveButton("pick no child", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currentChild = null;
                nextChild = mChildrenList.get(0);
                updateUI();
            }
        });
        AlertDialog pop = builder.create();
        createChildrenList(view,pop);
        pop.show();
    }
    public void PickFaceMsg(String msg, Context context){
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
                setFaceText();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Head", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                selection = Global.HEAD;
                setFaceText();
                dialog.cancel();
            }
        });
        AlertDialog pop = builder.create();
        pop.show();
    }
    private void setFaceText(){
        if (currentChild!=null){
            if (selection == Global.HEAD){
                currentFaceV.setText(R.string.picked_head_text);
            }
            else{
                currentFaceV.setText(R.string.picked_tail_text);
            }
        }else{
            currentFaceV.setText("");
        }
    }
    private void createChildrenList(View view, AlertDialog dialog){
        //recycle layout for popup
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
                if (currentChild!=null){
                    previousChild = currentChild;
                }
                nextChild = mChildrenList.get(position);
                updateUI();
                currentChild = mChildrenList.get(position);
                setChildInQuene(); //update the children list
                dialog.dismiss();
                PickFaceMsg("Pick Your Face", CoinFlipMain.this);
            }
            @Override
            public void onDeleteClick(int position) {}
            @Override
            public void onEditClick(int position) {}

            @Override
            public void onChildPhotoClick(int position) {}
        });
    }
    private void updateUI(){
        if (currentChild!= null){
            currentChildTextV.setText(currentChild.getmText1());
            nextChildTextV.setText("Next:  "+nextChild.getmText1());//next Child
            Uri uri = Uri.parse(currentChild.getImageResource());
            childImage.setImageURI(uri);

        }
        else{
            currentChildTextV.setText("");
            if (nextChild!=null){
                nextChildTextV.setText("Next:  "+nextChild.getmText1());//next Child
            }
            childImage.setImageResource(R.drawable.ic_child);
        }
        setFaceText();
    }
    private void saveHistory(){

        if (selection ==  coinFace){
            winner = "WIN";
        }
        else{
            winner = "LOSE";
        }
        SharedPreferences sharedPreferences = getSharedPreferences(Global.CHILDREN_HISTORY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Date currentTime = Calendar.getInstance().getTime();
        coinHistory.add(new CoinHistoryClass(currentChild.getmText1(),currentTime,coinFace,winner));
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
                if (coinFace == 1){
                    coinFlipResultText.setText(R.string.head);
                }
                else {
                    coinFlipResultText.setText(R.string.tail);
                }
                MediaPlayer coinSound = MediaPlayer.create(CoinFlipMain.this, R.raw.coin_flip_sound);
                coinSound.start();
                if (!mChildrenList.isEmpty()){
                    if (currentChild != null){ //if there is config children
                        saveHistory();
                        updateUI();
                        previousChild = currentChild;
                        currentChild = mChildrenList.get(1);
                        setChildInQuene();
                    }
                    else{
                        updateUI();
                        previousChild = currentChild;
                        setFaceText();
                        setChildInQuene();
                    }
                }
            }
        });
    }
    private void setSwitchChildButton(){
       Button switchChildButton = (Button)findViewById(R.id.switch_child_button);
       switchChildButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (!mChildrenList.isEmpty()){ //if there is config children
                   popUpChildren(CoinFlipMain.this);
               }
               else{
                   utility.popUpMsg("please add a children before you select",CoinFlipMain.this);
               }
           }
       });
    }
    private void setChildInQuene(){
        ArrayList<ConfigureChildrenItem> tempChildItem = new ArrayList<>();
        if (currentChild!=null){ // if a current child is picked
                tempChildItem.add(currentChild);
                mChildrenList.remove(currentChild);
        }
        if (previousChild != null){ //if there is a previous child
                mChildrenList.remove(previousChild);
        }
        int childListLength = mChildrenList.size();
        for (int i = 0;i<childListLength;i++){
            tempChildItem.add(mChildrenList.get(i));
        }
        if (previousChild != null){
            tempChildItem.add(previousChild);
        }
        mChildrenList = tempChildItem; //save back
        nextChild = mChildrenList.get(1);
    }
    private void setSwitchFaceButton(){
        Button switchFaceButton = (Button)findViewById(R.id.switch_face_button);
        switchFaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickFaceMsg("Pick Your Face",CoinFlipMain.this);
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
                fadeIn.setDuration(1000);
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