package com.cmpt276.iteration1practicalparent.ConfigureChildren;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmpt276.iteration1practicalparent.R;

import java.util.ArrayList;

public class AdapterForConfigureChildren extends RecyclerView.Adapter<AdapterForConfigureChildren.ViewHolderForConfigureChildren> {
    private ArrayList<ConfigureChildrenItem> mConfigureChildrenList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ViewHolderForConfigureChildren extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;

        public ViewHolderForConfigureChildren(View itemView, final OnItemClickListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.image1);
            mTextView1 = itemView.findViewById(R.id.text1);
            mTextView2 = itemView.findViewById(R.id.text2);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

    }

    public AdapterForConfigureChildren(ArrayList<ConfigureChildrenItem> childrenList){
        mConfigureChildrenList = childrenList;
    }

    @NonNull
    @Override
    public ViewHolderForConfigureChildren onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_for_configure_children, parent, false);
        ViewHolderForConfigureChildren evh = new ViewHolderForConfigureChildren(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForConfigureChildren holder, int position) {
        ConfigureChildrenItem currentItem = mConfigureChildrenList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());

    }

    @Override
    public int getItemCount() {
        return mConfigureChildrenList.size();
    }
}
