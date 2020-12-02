package com.cmpt276.iteration1practicalparent.UI.TakeBreath;

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

public class TakeBreathMain extends AppCompatActivity {
    private StateControlCommend currentState;

    public static int NBreath = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath_main);
        initialLayout();
    }
    private void initialLayout(){
        currentState = new BreathUI(); //set to breath
        start(currentState);
    }
    private void start(StateControlCommend state){
        state.Run(this, findViewById(android.R.id.content).getRootView()); //set to breathUI
    }
}