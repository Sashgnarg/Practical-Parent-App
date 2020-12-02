package com.cmpt276.iteration1practicalparent.UI.ConfigureChildren;

import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;

import java.util.ArrayList;

/*
sets up buttons on each item for the recycler view to be clicked
 builds adapter
 */

public class AdapterForConfigureChildren extends RecyclerView.Adapter<AdapterForConfigureChildren.ViewHolderForConfigureChildren> {
    private ArrayList<ConfigureChildrenItem> mConfigureChildrenList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onChildPhotoClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ViewHolderForConfigureChildren extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView addition_infoTextView;
        public ImageView mDeleteImage;
        public ImageView mEditImage;
        public ImageView mChildPhoto;

        public ViewHolderForConfigureChildren(View itemView, final OnItemClickListener listener){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            addition_infoTextView = itemView.findViewById(R.id.addition_info);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mEditImage = itemView.findViewById(R.id.image_edit);
            mChildPhoto = itemView.findViewById(R.id.childPhoto);

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

            mEditImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }

                }
            });

            mChildPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onChildPhotoClick(position);
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
        Uri uri = Uri.parse(currentItem.getImageResource());
        holder.mChildPhoto.setImageURI(uri);
        holder.nameTextView.setText(currentItem.getmText1());
        holder.addition_infoTextView.setText(currentItem.getmText2());

    }

    @Override
    public int getItemCount() {
        return mConfigureChildrenList.size();
    }
}
