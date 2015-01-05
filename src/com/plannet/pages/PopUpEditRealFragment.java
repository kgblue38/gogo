package com.plannet.pages;

import com.plannet.clientdb.CategoryDAO;

import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

public class PopUpEditRealFragment extends DialogFragment {
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final String name = getArguments().getString("name");
		final int cid = getArguments().getInt("cid");
		
		final EditText input = new EditText(getActivity());
		input.setText(name);
		builder.setView(input);
		
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				new CategoryDAO(getActivity()).update(cid, value);
				Tab tab = getActivity().getActionBar().getTabAt(getArguments().getInt("tabPosition"));
				tab.setText(value);
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				return;
			}
		});
		
		return builder.create();
	}
}