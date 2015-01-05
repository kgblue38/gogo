package com.plannet.others;

import com.plannet.pages.PagerAdapter;


public class GlobalVariables {
	private static int currentPageCid;
	private static PagerAdapter pagerAdapter;

	private GlobalVariables() {
	}

	public static int getCurrentPageCid() {
		return currentPageCid;
	}

	public static void setCurrentPageCid(int cid) {
		currentPageCid = cid;
	}

	public static PagerAdapter getPagerAdapter() {
		return pagerAdapter;
	}

	public static void setPagerAdapter(PagerAdapter pagerAdapter) {
		GlobalVariables.pagerAdapter = pagerAdapter;
	}

}