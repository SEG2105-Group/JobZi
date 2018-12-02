package com.arom.jobzi.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.arom.jobzi.R;

import java.io.Serializable;

public class DeleteAvailabilityDialogFragment extends DialogFragment {
    
    public static final String LISTENER_BUNDLE_ARG = "listener";
    public static final String DAY_BUNDLE_ARG = "day";
    public static final String INDEX_BUNDLE_ARG = "index";
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        
        Bundle bundle = getArguments();
        
        final DeleteAvailabilityListener listener = (DeleteAvailabilityListener) bundle.getSerializable(LISTENER_BUNDLE_ARG);
        final String day = bundle.getString(DAY_BUNDLE_ARG);
        final int index = bundle.getInt(INDEX_BUNDLE_ARG);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setTitle(getString(R.string.dialog_delete_availability)).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDelete(day, index);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        
        return builder.create();
        
    }
    
    public interface DeleteAvailabilityListener extends Serializable {
        
        void onDelete(String day, int index);
        
    }
    
}
