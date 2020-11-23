package com.cmpt276.iteration1practicalparent.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;
import com.cmpt276.iteration1practicalparent.UI.Timer.TimerActivity;
import com.cmpt276.iteration1practicalparent.UI.CoinFlip.CoinFlipMain;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.ButtonFunctions;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonFunctions buttonF = new ButtonFunctions();

        Button coinFlipButton = (Button) findViewById(R.id.test_button_to_coinFlip);
//        Button testPopUpButton = (Button) findViewById(R.id.test_button2);
//        buttonF.setPopUp(testPopUpButton,MainActivity.this,"lol");

        buttonF.setButtonActivity(()->buttonF.setChangeActivity(coinFlipButton,MainActivity.this, CoinFlipMain.class, false));
        //buttonFunctions.setButtonActivity( () -> buttonFunctions.setPopUp(testPopUpButton,MainActivity.this,"hello"));


        setupConfigureChildrenButton();

        setupTimerButton();
    }


    public void setupConfigureChildrenButton(){
        Button configureChildren = (Button) findViewById(R.id.configure_children);

        configureChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfigureChildren.class);
                startActivity(intent);
            }
        });
    }


    public void setupTimerButton() {
        Button timerBtn = (Button) findViewById(R.id.btnTimer);
        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });
    }

}
