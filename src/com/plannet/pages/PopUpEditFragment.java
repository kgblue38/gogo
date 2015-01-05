package com.plannet.pages;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.plannet.clientdb.CategoryDAO;
import com.plannet.model.Category;

public class PopUpEditFragment extends DialogFragment {

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("카테고리 수정");

		final ArrayList<Category> categoryList = new CategoryDAO(getActivity().getApplicationContext()).select();
		ArrayList<String> selectItems = new ArrayList<String>();
		for (Category eachItem : categoryList) {
			selectItems.add(eachItem.getName());
		}
		Object[] selectObject = selectItems.toArray();
		String selectString[] = new String[selectObject.length];
		for (int i = 0; i < selectString.length; i++)
			selectString[i] = selectObject[i].toString();

		builder.setItems(selectString, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Bundle bundle = new Bundle();
				bundle.putString("name", categoryList.get(id).getName());
				bundle.putInt("cid", categoryList.get(id).getCid());
				bundle.putInt("tabPosition", id);
				PopUpEditRealFragment popup = new PopUpEditRealFragment();
				popup.setArguments(bundle);
				popup.show(getFragmentManager(), "EditCategoryCool");
			}
		});
		
		return builder.create();
	}
}