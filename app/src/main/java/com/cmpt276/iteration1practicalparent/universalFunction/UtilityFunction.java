package com.cmpt276.iteration1practicalparent.universalFunction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.MainActivity;
import com.cmpt276.iteration1practicalparent.R;

import java.util.Random;

public class UtilityFunction {
    Random random = new Random();

    //UtilityFunction
    // 1. popUpMsg pop a msg base on popup_dialog.xml
    // 2. random 2 face and update content(content will set up later)

    public void popUpMsg(String msg, Context context){
        // popup screen base on popup_dialog.xml
        // change popup_dialog.xml for more effect
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.popup_dialog,null);

        TextView text = (TextView)view.findViewById(R.id.message);
        text.setText(msg);

        builder.setCancelable(true);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog pop = builder.create();
        pop.show();
    }
    public int randomTwoFace(){
        //random
        return (random.nextInt(1000)%2);
    }
}
