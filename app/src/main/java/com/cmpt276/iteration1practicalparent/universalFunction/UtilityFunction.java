package com.cmpt276.iteration1practicalparent.universalFunction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.ConfigureChildren.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.cmpt276.iteration1practicalparent.universalFunction.Global.LIST_OF_CHILDREN;

public class UtilityFunction{
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
    public ArrayList<ConfigureChildrenItem> loadData(ArrayList<ConfigureChildrenItem> mChildrenList, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_OF_CHILDREN, null);
        Type type = new TypeToken<ArrayList<ConfigureChildrenItem>>(){}.getType();
        mChildrenList = gson.fromJson(json, type);

        if(mChildrenList == null){
            mChildrenList = new ArrayList<>();
        }
        return mChildrenList;
    }

}
