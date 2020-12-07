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
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CoinFlipMain extends AppCompatActivity {

    ButtonFunctions buttonF;
    UtilityFunction utility;
    private TextView currentChildTextV, coinFlipResultText,nextChildTextV, currentFaceV;
    private ArrayList<ConfigureChildrenItem> mChildrenList, newChildrenQueueList;
    private ArrayList<Integer> childrenQueue;

    private String winner;
    private ConfigureChildrenItem currentChild, previousChild, nextChild;
    private int selection,coinFace;
    private ImageView childImage;
    ArrayList<CoinHistoryClass> coinHistory;

    private Button historyAllBtn;
    private Button historyCurrBtn;

    private Uri currentChildrenImgUri;

    private int programState = 0;
    // hard coded programState to manuel check for not put the current child to the end of the list.
    // I guess a "queue" means that I should not drop the child on the top.
    // instead, add child on the "top" of the queue and push everything down.



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

        historyAllBtn = findViewById(R.id.viewAllHistory);
        buttonF.setChangeActivity(historyAllBtn,CoinFlipMain.this,HistoryAllDisplay.class,false);
        historyCurrBtn = findViewById(R.id.viewCurrHistory);
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
        //textViews
        currentChildTextV  = findViewById(R.id.current_children);
        nextChildTextV     = findViewById(R.id.new_child_text);
        currentFaceV       = findViewById(R.id.coin_current_face);
        coinFlipResultText = findViewById(R.id.coin_flip_result_text);

        childImage = findViewById(R.id.child_image_view);
        childImage.setImageResource(R.drawable.ic_child); //default image

        //coinText = (TextView)findViewById(R.id.coin_text);

        Button flipButton  = (Button)findViewById(R.id.flip_button);

        setFlipButton(flipButton); //button to flip coin
        setSwitchChildButton();// button to switch child
        setSwitchFaceButton(); // button to pick new face

        //load queue of config children
        loadQueue();

        //load coin history
        coinHistory = utility.loadCoinHistory(this);
    }
    public void loadQueue(){
        mChildrenList = utility.loadData(this);
        childrenQueue = utility.loadQueue(this);
        newChildrenQueueList = new ArrayList<>();
        if (!childrenQueue.isEmpty()){
            // check childID in old queue exist in the children list

            for (int childID : childrenQueue){
                for (ConfigureChildrenItem child : mChildrenList){
                    if (child.getIdOfChild()==childID){
                        newChildrenQueueList.add(child);
                    }
                }
            }
        }
        //add the newly updated child (not yet added to the queue)
        for (ConfigureChildrenItem child : mChildrenList){
            if(!childrenQueue.contains(child.getIdOfChild())){
                newChildrenQueueList.add(child);
            }
        }
        saveQueue();
        if (!newChildrenQueueList.isEmpty()){ //if there is config children
            popUpChildren(this);
        }
    }
    public void saveQueue(){
        ArrayList<Integer> newChildrenQueue = new ArrayList<>();
        for (ConfigureChildrenItem child : newChildrenQueueList){
            newChildrenQueue.add(child.getIdOfChild());
        }
        utility.saveQueue(this,newChildrenQueue);
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
                nextChild = null;
                updateUI();
                nextChild = newChildrenQueueList.get(0);
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

        TextView text   = (TextView)view.findViewById(R.id.message);
        ImageView image = (ImageView) view.findViewById(R.id.message_image);
        //image.setImageURI(nextChildrenImgUri);
        image.setImageURI(currentChildrenImgUri);
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

        AdapterForConfigureChildren mAdapter = new AdapterForConfigureChildren(newChildrenQueueList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterForConfigureChildren.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (currentChild!=null){
                    previousChild = currentChild;
                }
                nextChild = newChildrenQueueList.get(position);
                currentChild = newChildrenQueueList.get(position);
                updateUI();
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
            if (nextChild!=null){
                nextChildTextV.setText("Next:  "+nextChild.getmText1());//next Child
            }
            currentChildrenImgUri = Uri.parse(currentChild.getImageResource());
            childImage.setImageURI(currentChildrenImgUri);
        }
        else{
            currentChildTextV.setText("");
            if (nextChild!=null){
                nextChildTextV.setText("Next:  "+nextChild.getmText1());//next Child
            }
            else{
                nextChildTextV.setText("Next:  ");
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
        coinHistory.add(new CoinHistoryClass(currentChild, currentChild.getmText1(),currentTime,coinFace,winner));
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
                if (!newChildrenQueueList.isEmpty()){
                    if (currentChild != null){ //if there is config children
                        saveHistory();
                        updateUI();
                        previousChild = currentChild;
                        if (newChildrenQueueList.size() > 1) {
                            currentChild = newChildrenQueueList.get(1);
                        }
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
               if (!newChildrenQueueList.isEmpty()){ //if there is config children
                   programState = 1;
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
        if (currentChild!=null){
            for (int i=0;i<newChildrenQueueList.size();i++){
                if(newChildrenQueueList.get(0)!=currentChild){
                    tempChildItem.add(newChildrenQueueList.get(0));//move one to the back
                    newChildrenQueueList.remove(0);
                }
                else{
                    break;
                }
            }
            newChildrenQueueList.addAll(tempChildItem);
            if (newChildrenQueueList.size() > 1) {
                nextChild = newChildrenQueueList.get(1);
            }
        }
        else{
            currentChild = newChildrenQueueList.get(0);
            nextChild = newChildrenQueueList.get(1);
        }
        saveQueue();//save
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