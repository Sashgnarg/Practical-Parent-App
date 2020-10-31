package com.cmpt276.iteration1practicalparent.ConfigureChildren;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.cmpt276.iteration1practicalparent.R;

public class DialogueForConfigureChildren extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextAdditionalInfo;
    private DialogueForConfigureChildrenListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_children_dialogue, null);

        builder.setView(view)
                .setTitle("Edit Name and Additional Info")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editTextName.getText().toString();
                        String addition_info = editTextAdditionalInfo.getText().toString();
                        listener.applyChanges(name, addition_info);
                    }
                });
        editTextName = view.findViewById(R.id.edit_name);
        editTextAdditionalInfo = view.findViewById(R.id.edit_additional_info);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogueForConfigureChildrenListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement dialogueforchildrenlistener");
        }
    }

    public interface DialogueForConfigureChildrenListener{
        void applyChanges(String name, String addition_info);
    }
}
