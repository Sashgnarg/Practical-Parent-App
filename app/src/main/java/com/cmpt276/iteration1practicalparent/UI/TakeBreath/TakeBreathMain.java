package com.cmpt276.iteration1practicalparent.UI.TakeBreath;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState.BreathUI;
import com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState.ExhaleUI;
import com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState.InhaleUI;
import com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState.StateControlCommend;

import java.util.ArrayList;
import java.util.Objects;

public class TakeBreathMain extends AppCompatActivity {

    public static int NBreath,programState;
    //NBreath       -> how many breath
    //program state -> the navigate away between UIs
    //hit back in Exhale and Inhale will return to
    //hit back in BreathUI will return to menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath_main);
        NBreath = 3;
        programState = 1;//default -> return to menu
        setTitle(R.string.breath);

        initialLayout();
        //when back pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                switch (programState){
                    case 1:
                        finish();
                        break;
                    case 2:
                        start(new BreathUI()); //back to BreathUI
                        programState = 1;
                        break;
                }

            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
        //top left arrow pressed
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    private void initialLayout(){
        start(new BreathUI());
    }
    private void start(StateControlCommend state){
        state.Run(this, findViewById(android.R.id.content).getRootView()); //set to breathUI
    }
    @Override
    public boolean onSupportNavigateUp() {
        //press back event
        switch (programState){
            case 1:
                finish();
                break;
            case 2:
                start(new BreathUI());
                programState = 1;
                break;
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //reset back to start when navigate away
        super.onWindowFocusChanged(hasFocus);
        start(new BreathUI());
        programState = 1;
    }
}