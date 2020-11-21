package com.cmpt276.iteration1practicalparent.UI;

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

import com.cmpt276.iteration1practicalparent.Model.ConfigureChildrenItem;
import com.cmpt276.iteration1practicalparent.R;
import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;

public class DialogueForTask extends AppCompatDialogFragment {
    private EditText editTextTaskName;
    private EditText editTextTaskDescription;
    private DialogueForTaskListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_edit_task_dialog, null);

        builder.setView(view)
                .setTitle("Edit Task Name and Task Description")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String taskName = editTextTaskName.getText().toString();
                        String taskDescription = editTextTaskDescription.getText().toString();
                        listener.applyTaskChanges(taskName, taskDescription);
                    }
                });
        editTextTaskName = view.findViewById(R.id.editTextTaskName);
        editTextTaskDescription = view.findViewById(R.id.editTextTaskDescription);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogueForTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement dialoguefortasklistener");
        }
    }

    public interface DialogueForTaskListener {
        void applyTaskChanges(String taskName, String taskDescription);
    }
}
