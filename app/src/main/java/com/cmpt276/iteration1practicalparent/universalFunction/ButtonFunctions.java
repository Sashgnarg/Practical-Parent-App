package com.cmpt276.iteration1practicalparent.universalFunction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonFunctions {
    UtilityFunction utility = new UtilityFunction();// utility functions

    //setup button with commands

    // (not useful) now for example:
    // buttonFunctions.setButtonActivity( () -> buttonFunctions.setChangeActivity(coinFlipButton,MainActivity.this,coinFlipMain.class, false));
    // which is using setChangeActivity()
    // buttonFunctions.setButtonActivity( () -> buttonFunctions.setPopUp(testPopUpButton,MainActivity.this,"hello"));
    // which is using setPopUp()

    //button commends:
    //1. setChangeActivity -> switch between event
    //2. setPopUp          -> a simple popup

    public void setButtonActivity(ButtonCommend buttonCommend){
        buttonCommend.start();
    }

    public void setChangeActivity(Button button, Activity from, Class to, boolean isfinish){
        //switch event
        //a button from current activity to new activity
        //if isfinish is true -> also close current activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(from.getApplicationContext(), to);
                from.startActivity(intent);
                if (isfinish){
                    //finish current activity
                    from.finish();
                }
            }
        });
    }
    public void setPopUp(Button button, Context context, String msg){
        // a simple popup
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utility.popUpMsg(msg ,context);
            }
        });
    }
    public void setFlipButton(Button button,TextView coinText) {
        // setup Flip Button and then update textView everyClick
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coinFace;
                coinFace = utility.randomTwoFace();
                coinText.setText(""+coinFace);
            }
        });
    }



}
