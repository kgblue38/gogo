package com.plannet.pages;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;

import com.plannet.activity.MyPlanActivity;
import com.plannet.activity.R;
import com.plannet.clientdb.CategoryDAO;
import com.plannet.model.Category;
import com.plannet.others.GlobalVariables;
import com.plannet.others.Utilities;

public class PopUpDeleteFragment extends DialogFragment {

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("카테고리 삭제");
		final ArrayList<Category> categoryList = new CategoryDAO(getActivity().getApplicationContext()).select();
		ArrayList<String> selectItems = new ArrayList<String>();
		for (Category eachItem : categoryList) {
			selectItems.add(eachItem.getName());
		}
		final Object[] a = selectItems.toArray();
		String String_Array[] = new String[a.length];
		for (int i=0;i<String_Array.length;i++) 
			String_Array[i]= a[i].toString();
		
		builder.setItems(String_Array, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Activity currentActivity = PopUpDeleteFragment.this.getActivity();
				new CategoryDAO(currentActivity).delete(categoryList.get(id).getCid());
				ActionBar a = currentActivity.getActionBar();
				a.removeTab(a.getTabAt(id));
				
				//PagerAdapter pagerAdapter = GlobalVariables.getPagerAdapter();
				//ViewGroup vg = (ViewGroup) currentActivity.findViewById(R.id.pager_container);
				//pagerAdapter.destroyItem(vg, id, pagerAdapter.getModelList().get(id));
				
				Utilities.moveToAnotherActivity(currentActivity, MyPlanActivity.class);
			}
			
		}
		);	
		return builder.create();
	}

}