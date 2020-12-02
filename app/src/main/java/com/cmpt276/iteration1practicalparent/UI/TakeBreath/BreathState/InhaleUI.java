package com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276.iteration1practicalparent.R;

import static com.cmpt276.iteration1practicalparent.UI.TakeBreath.TakeBreathMain.NBreath;

public class InhaleUI extends StateControlCommend {
    private TextView setBreathText, showBreathText;
    private SeekBar selectNBreath;
    private Button beginBreath;
    private StateControlCommend currentState;


    @Override
    public void Run(Context context, View view) {
        super.Run(context, view);
        Toast.makeText(context, "breath in inhale ui",
                Toast.LENGTH_SHORT).show();
        initialLayout(context,view);
    }
    @Override

    public void setNextState(Context context, View view,StateControlCommend state){
        //set button to the next event
        super.setNextState(context,view, state);
        beginBreath.setOnClickListener((nextView)->state.Run(context, view));//move to next event button
    }

    @Override
    public void initialLayout(Context context, View view) {
        super.initialLayout(context, view);

        selectNBreath   = (SeekBar) view.findViewById(R.id.select_n_breath_seek_bar);//reset the UI elements of Breath
        setBreathText   = (TextView)view.findViewById(R.id.set_breath_text);
        beginBreath = (Button) view.findViewById(R.id.begin_breath_button);
        showBreathText  = (TextView)view.findViewById(R.id.show_breath_text);

        //currentState = stateBreath;
        //setStateButton(stateInhale);
        setInhaleUI(context,view);
        if (NBreath == 0){
            setFinishText();
            setNextState(context,view,new BreathUI()); //set button to the next event
        }
        else{
            setNextState(context,view,new ExhaleUI()); //set button to the next event
        }


    }
    private void setInhaleUI(Context context, View view){
        selectNBreath.setVisibility(View.INVISIBLE);
        setText(view);
    }
    private void setText(View view){
        beginBreath.setText(R.string.button_breath_in);
        showBreathText.setText(R.string.text_breath_in);
        showBreathText.setTextSize(20);
        setBreathText.setText(String.format(view.getResources().getString(R.string.remain_breath),NBreath));
    }
    private void setFinishText(){
        beginBreath.setText(R.string.good_job_button);
        showBreathText.setText(R.string.finish_text);
        showBreathText.setTextSize(20);
    }
}
