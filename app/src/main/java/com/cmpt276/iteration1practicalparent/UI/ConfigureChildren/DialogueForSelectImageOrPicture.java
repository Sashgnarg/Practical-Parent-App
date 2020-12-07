package com.cmpt276.iteration1practicalparent.UI.ConfigureChildren;


import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.cmpt276.iteration1practicalparent.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/*

 */

public class DialogueForSelectImageOrPicture extends AppCompatDialogFragment {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int OPEN_GALLERY_CODE = 1002;
    private ImageButton takePicture;
    private ImageView mPhotoOfChild;
    private ImageButton viewGallery;
    private DialogueForSelectImageOrTakePictureListener listener;

    Uri image_uri;
    Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.take_photo_or_choose_image_dialogue, null);
        takePicture = view.findViewById(R.id.takephoto);
        viewGallery = view.findViewById(R.id.gallery);
        mPhotoOfChild = view.findViewById(R.id.photoOfChildInDialogue);
        mPhotoOfChild.setImageURI(setDefaultImageForChild());
        image_uri = setDefaultImageForChild();

        builder.setView(view)
                .setTitle("Select Image or take picture")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            listener.applyChangesForPhoto(image_uri.toString());
                    }
                });

        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED||ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                            PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        openCamera();
                    }
                }
                else{
                    openCamera();
                }
            }
        });



        return builder.create();
    }

    private Uri setDefaultImageForChild() {
        context = getContext();
        context.getApplicationContext();
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(R.drawable.ic_child)
                + '/' + context.getResources().getResourceTypeName(R.drawable.ic_child)
                + '/' + context.getResources().getResourceEntryName(R.drawable.ic_child) );
        return imageUri;
    }

    private void openGallery() {
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(
                    Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, OPEN_GALLERY_CODE);
        } else {
            intent = new Intent(
                    Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            startActivityForResult(intent, OPEN_GALLERY_CODE);
        }
    }


    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length>0&&grantResults[0]==
                        PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(getActivity(), "Permission denied for camera", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE&& data!=null){
            mPhotoOfChild.setImageURI(image_uri);
        }
        if(resultCode == RESULT_OK && requestCode == OPEN_GALLERY_CODE&& data!=null){
            image_uri = data.getData();
            mPhotoOfChild.setImageURI(image_uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogueForSelectImageOrTakePictureListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement DialogueForSelectImageOrTakePictureListener");
        }
    }

    public interface DialogueForSelectImageOrTakePictureListener{
        void applyChangesForPhoto(String image_uri);
    }
}
