package com.cmpt276.iteration1practicalparent.UI.CoinFlip;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.Model.CoinHistoryClass;
import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.Model.UniversalFunction.UtilityFunction;
import com.cmpt276.iteration1practicalparent.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.historyAdapterHolder> {
    private UtilityFunction utility;
    private ArrayList<CoinHistoryClass> coinHistoryAll;
    private ArrayList<ConfigureChildrenItem> childList;

    public historyAdapter(Context context, ArrayList<CoinHistoryClass> coinHistoryAll){
        utility = new UtilityFunction();
        childList = utility.loadData(context);
        this.coinHistoryAll = coinHistoryAll;
    }
    public static class historyAdapterHolder extends RecyclerView.ViewHolder{
        public ImageView childImg;
        public TextView display;
        public historyAdapterHolder(@NonNull View itemView) {
            super(itemView);
            childImg = itemView.findViewById(R.id.childImgHistoryAll);
            display = itemView.findViewById(R.id.historyAllTxtView);
        }
    }

    @NonNull
    @Override
    public historyAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history_all,parent,false);
        return new historyAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull historyAdapterHolder holder, int position) {
        CoinHistoryClass history = coinHistoryAll.get(position);
        String face = "";
        if (history.getFace() == 0){
            face = "tail";
        }
        else if (history.getFace() == 1){
            face = "head";
        }
        holder.display.setText(history.getTime() + ":\n" +
                "   " + history.getPickersName() + " chose " + face + "\n" +
                "   The result was " + history.getWinner());
        ConfigureChildrenItem tempChild = IDChecker(history);
        if (tempChild != null) {
            Uri currentTaskChildPicUri = Uri.parse(tempChild.getImageResource());
            try{
                holder.childImg.setImageURI(currentTaskChildPicUri);
                Log.d("check: 1 ", currentTaskChildPicUri.toString());
            }catch (NullPointerException exception){
                Log.d("check: 2 ", currentTaskChildPicUri.toString());
            }
        }
    }
    @Override
    public int getItemCount() {
        return coinHistoryAll.size();
    }

    public ConfigureChildrenItem IDChecker(CoinHistoryClass history){
        for (ConfigureChildrenItem child : childList){
            if (child.getIdOfChild() == history.getChild().getIdOfChild()){
                return child;
            }
        }
        return null;
    }
}
