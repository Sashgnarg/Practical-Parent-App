package com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain;

import java.util.Locale;

import static com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain.NBreath;
import static com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain.programState;

public class ExhaleUI extends StateControlCommend {
    private TextView setBreathText, showBreathText;
    private SeekBar selectNBreath;
    private Button beginBreath,breathingButton;
    private ImageView breathingCircle;
    private Animation animation;
    private StateControlCommend currentState;
    private MediaPlayer exhaleSound;



    @Override
    public void Run(Context context, View view) {
        super.Run(context, view);
        programState = 2;
        initialLayout(context,view);
        Toast.makeText(context, "breath in exhale ui",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void setNextState(Context context, View view,StateControlCommend state){
        //set button to the next event
        super.setNextState(context,view, state);
        state.Run(context, view);//move to next event button
    }

    @Override
    public void initialLayout(Context context, View view) {
        super.initialLayout(context, view);
        selectNBreath   = (SeekBar) view.findViewById(R.id.select_n_breath_seek_bar);//reset the UI elements of Breath
        setBreathText   = (TextView)view.findViewById(R.id.set_breath_text);
        breathingButton = (Button) view.findViewById(R.id.breathing_button);
        beginBreath     =(Button)view.findViewById(R.id.begin_breath_button);
        showBreathText  = (TextView)view.findViewById(R.id.show_breath_text);
        breathingCircle = (ImageView) view.findViewById(R.id.breathing_circle);
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_zoom_out);
        exhaleSound = MediaPlayer.create(context, R.raw.exhale_sound);


        beginBreath.setEnabled(false);
        beginBreath.setVisibility(View.INVISIBLE);

        breathingCircle.setImageResource(R.drawable.pink_exhale_circle);

        breathingButton.setEnabled(false); //disable the button


        setExhaleUI(context,view);
        CountDownTimer timer_3s = new CountDownTimer(3100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (programState ==1){
                    cancel();
                }else{
                    showBreathText.setText(String.format(Locale.CANADA, view.getResources().getString(R.string.text_breath_out)
                            ,millisUntilFinished/1000));
                }
            }
            @Override
            public void onFinish() {
                showBreathText.setText(R.string.text_breath_out_ok);
                NBreath -=1;
                setNextState(context,view,new InhaleUI());
                exhaleSound.stop();
            }

        };
        CountDownTimer timer_10s = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //do something
                if (programState ==1){
                    cancel();
                }
            }
            @Override
            public void onFinish() {

            }
        };
        timer_3s.start();
        timer_10s.start();
        breathingCircle.startAnimation(animation);
        exhaleSound.seekTo(1000);
        exhaleSound.start();
        //beginBreath.setOnClickListener((view1)->setNextState(context,view,new InhaleUI())); //set button to the next event

    }

    private void setExhaleUI(Context context, View view){
        //set up the UI
        selectNBreath.setVisibility(View.INVISIBLE);
        setText(view);
    }
    private void setText(View view){
        //set up text element
        breathingButton.setText(R.string.button_breath_out);
        showBreathText.setText(R.string.text_breath_out);
        showBreathText.setTextSize(20);
        setBreathText.setText(String.format(view.getResources().getString(R.string.remain_breath),NBreath));
    }

}
