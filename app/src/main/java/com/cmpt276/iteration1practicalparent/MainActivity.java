package com.cmpt276.iteration1practicalparent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.cmpt276.iteration1practicalparent.coinFlip.CoinFlipMain;
import com.cmpt276.iteration1practicalparent.universalFunction.ButtonFunctions;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonFunctions buttonF = new ButtonFunctions();

        Button coinFlipButton = (Button) findViewById(R.id.test_button_to_coinFlip);
        Button testPopUpButton = (Button) findViewById(R.id.test_button2);
        buttonF.setPopUp(testPopUpButton,MainActivity.this,"lol");

        buttonF.setButtonActivity(() -> buttonF.setChangeActivity(coinFlipButton,MainActivity.this, CoinFlipMain.class, false));
        //buttonFunctions.setButtonActivity( () -> buttonFunctions.setPopUp(testPopUpButton,MainActivity.this,"hello"));
    }

}
