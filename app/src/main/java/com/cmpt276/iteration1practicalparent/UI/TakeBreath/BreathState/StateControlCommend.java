package com.cmpt276.iteration1practicalparent.UI.TakeBreath.BreathState;

import android.content.Context;
import android.view.View;

public abstract class StateControlCommend {
    public void Run(Context context, View view){} //move to the next event
    public void setNextState(Context context,View view,StateControlCommend state){} //Set State
    public void initialLayout(Context context,View view){} //set up ui element
    //public void breath(Context context,View view){}
    //public void inhale(Context context,View view){}
    //public void exhale(Context context,View view){}
}

