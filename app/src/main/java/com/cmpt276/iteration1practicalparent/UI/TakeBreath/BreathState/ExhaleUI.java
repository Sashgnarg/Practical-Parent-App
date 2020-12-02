package com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain;

import static com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain.NBreath;

public class ExhaleUI extends StateControlCommend {
    private TextView setBreathText, showBreathText;
    private SeekBar selectNBreath;
    private Button beginBreath;
    private StateControlCommend currentState;


    @Override
    public void Run(Context context, View view) {

        super.Run(context, view);
        initialLayout(context,view);
        Toast.makeText(context, "breath in exhale ui",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void setNextState(Context context, View view,StateControlCommend state){
        //set button to the next event
        super.setNextState(context,view, state);
        beginBreath.setOnClickListener((nextView)->state.Run(context, view));//move to next event button
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setStateChangeTimer(){
        beginBreath.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CountDownTimer timer_3s = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                    }
                };;
                CountDownTimer timer_10s = new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                    }
                };

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN://pressed
                        break;
                    case MotionEvent.ACTION_CANCEL: //released
                        // RELEASED
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void initialLayout(Context context, View view) {
        super.initialLayout(context, view);

        selectNBreath   = (SeekBar) view.findViewById(R.id.select_n_breath_seek_bar);//reset the UI elements of Breath
        setBreathText   = (TextView)view.findViewById(R.id.set_breath_text);
        beginBreath = (Button) view.findViewById(R.id.begin_breath_button);
        showBreathText  = (TextView)view.findViewById(R.id.show_breath_text);

        setExhaleUI(context,view);

        setNextState(context,view,new InhaleUI()); //set button to the next event
        NBreath -=1;

    }

    private void setExhaleUI(Context context, View view){
        //set up the UI
        selectNBreath.setVisibility(View.INVISIBLE);
        setText(view);
    }
    private void setText(View view){
        //set up text element

        beginBreath.setText(R.string.button_breath_out);
        showBreathText.setText(R.string.text_breath_out);
        showBreathText.setTextSize(20);
        setBreathText.setText(String.format(view.getResources().getString(R.string.remain_breath),NBreath));
    }

}
