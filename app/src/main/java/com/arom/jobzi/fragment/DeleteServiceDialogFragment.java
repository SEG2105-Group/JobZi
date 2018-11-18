package com.arom.jobzi.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Service;

import java.io.Serializable;

public class DeleteServiceDialogFragment extends DialogFragment {

    public static final String LISTENER_BUNDLE_ARG = "listener";
	public static final String SERVICE_BUNDLE_ARG = "service";
	
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		
		Bundle bundle = getArguments();

		final DeleteServiceListener listener = (DeleteServiceListener) bundle.getSerializable(LISTENER_BUNDLE_ARG);
		final Service service = (Service) bundle.getSerializable(SERVICE_BUNDLE_ARG);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setMessage(getString(R.string.dialog_delete_service, service.getName())).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				listener.onDelete(service);
			}
		}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dismiss();
			}
		});
		
		return builder.create();
		
	}

	public interface DeleteServiceListener extends Serializable {

	    void onDelete(Service service);

    }
	
}
