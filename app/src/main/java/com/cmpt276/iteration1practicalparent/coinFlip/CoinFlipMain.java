package com.cmpt276.iteration1practicalparent.coinFlip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.universalFunction.ButtonFunctions;
import com.cmpt276.iteration1practicalparent.universalFunction.UtilityFunction;

import java.util.Random;

public class CoinFlipMain extends AppCompatActivity {
    Random random;
    public int coinFace = 0;
    ButtonFunctions buttonF;
    UtilityFunction utility;
    private TextView coinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip_main);

        buttonF = new ButtonFunctions();
        utility = new UtilityFunction();

        coinText = (TextView)findViewById(R.id.coin_text);
        Button flipButton = (Button)findViewById(R.id.flip_button);
        buttonF.setButtonActivity(()->buttonF.setFlipButton(flipButton,coinText));

    }




}