package com.plannet.pages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.plannet.clientdb.CategoryDAO;
import com.plannet.others.GlobalVariables;

public class PopUpCreateFragment extends DialogFragment {
	String Title[] = { "생성하려면 클릭하시오." };

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		final EditText input = new EditText(getActivity());
		builder.setView(input);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String name = input.getText().toString();
				int rowId = (int) new CategoryDAO(getActivity()).insert(1, name);
				PagerAdapter pagerAdapter = GlobalVariables.getPagerAdapter();
				pagerAdapter.addTab(getActivity().getActionBar().newTab().setText(name), ModelFragment.class, rowId);
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