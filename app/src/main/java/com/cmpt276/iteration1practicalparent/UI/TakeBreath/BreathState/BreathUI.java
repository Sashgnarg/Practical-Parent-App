package com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain;

import java.util.Locale;

public class BreathUI extends StateControlCommend {

    private TextView setBreathText, showBreathText; //reset the UI elements of Breath
    private SeekBar selectNBreath;
    private Button beginBreath;

    private StateControlCommend currentState; //state

    @Override
    public void Run(Context context, View view) {
        super.Run(context, view);
        initialLayout(context,view); //do event on Breath
        Toast.makeText(context, "breath in breath ui",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void setNextState(Context context, View view, StateControlCommend state){
        //set button to the next event
        super.setNextState(context,view, state);
        beginBreath.setOnClickListener((nextView)->state.Run(context, view));//move to next event button
    }
    @Override
    public void initialLayout(Context context,View view){
        super.initialLayout(context,view);
        selectNBreath   = (SeekBar) view.findViewById(R.id.select_n_breath_seek_bar);//reset the UI elements of Breath
        setBreathText   = (TextView)view.findViewById(R.id.set_breath_text);
        beginBreath = (Button) view.findViewById(R.id.begin_breath_button);
        showBreathText  = (TextView)view.findViewById(R.id.show_breath_text);

        setBreathSeekBar(context,view);
        setText(view);

        setNextState(context,view,new InhaleUI()); //set button to the next event

    }
    public void setText(View view){
        showBreathText.setText(String.format(view.getResources().getString(R.string.n_breaths),TakeBreathMain.NBreath));
    }
    public void setBreathSeekBar(Context context,View view){
        selectNBreath.setVisibility(View.VISIBLE);
        setBreathText.setVisibility(View.VISIBLE);
        selectNBreath.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0){
                    progress = 1; //min (set min function on seekBar only available for api 26 lol)
                }
                TakeBreathMain.NBreath = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setText(view);
                Toast.makeText(context, ""+String.format(Locale.CANADA,"Selected %d breath(s)",TakeBreathMain.NBreath),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
