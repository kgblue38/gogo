package com.plannet.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.plannet.clientdb.CategoryDAO;
import com.plannet.listener.DrawerListItemOnClickListener;
import com.plannet.model.Category;
import com.plannet.others.GlobalVariables;
import com.plannet.others.Utilities;
import com.plannet.pages.PopUpCreateFragment;
import com.plannet.pages.PopUpDeleteFragment;
import com.plannet.pages.ModelFragment;
import com.plannet.pages.PagerAdapter;
import com.plannet.pages.PopUpEditFragment;

public class MyPlanActivity extends FragmentActivity {
	private String[] navItems;
	private ListView drawerNavList;
	private DrawerLayout drawerLayout;

	private ActionBar tabActionBar;
	private ActionBarDrawerToggle drawerToggle;

	private LinearLayout pagerContainer;
	private PagerAdapter pagerAdapter;
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_plan);

		// ////////////////////////
		// 여기서부터 드로어
		// ////////////////////////

		// 초기화
		navItems = getResources().getStringArray(R.array.drawer_nav_list_items);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerNavList = (ListView) findViewById(R.id.drawer_nav_list);
		// 드로어 네비게이션 리스트 내용 설정
		drawerNavList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
		// 드로어 아이템 클릭 리스너 설정
		drawerNavList.setOnItemClickListener(new DrawerListItemOnClickListener(drawerLayout));
		// 액션바 초기화
		tabActionBar = getActionBar();
		// 액션바와 드로어를 연결 시켜주는 토글
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
				R.string.open_drawer_message, R.string.open_drawer_message);
		drawerLayout.setDrawerListener(drawerToggle); // 드로어와 액션바 토글을 연결
		tabActionBar.setDisplayHomeAsUpEnabled(true);
		
		// ////////////////////////
		// 여기서부터 페이저
		// ////////////////////////

		// 초기화
		pagerContainer = (LinearLayout) findViewById(R.id.pager_container);
		pager = new ViewPager(this);
		pager.setId(1); // view의 아이디 꼭 필요함
		pager.setOffscreenPageLimit(1); // 양옆으로 파괴 안하고 유지할 fragment 개수
		pagerContainer.addView(pager);// LinearLayout의 자식으로 ViewPager를 넣어줌

		pagerAdapter = new PagerAdapter(this, pager); // pagerAdapter 클래스에 있는 설명 참고
		tabActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		GlobalVariables.setPagerAdapter(pagerAdapter);
		
		ArrayList<Category> categoryList =  new CategoryDAO(this).select();
		
		for(Category c : categoryList){
			pagerAdapter.addTab(tabActionBar.newTab().setText(c.getName()), ModelFragment.class, c.getCid()); // 맨 마지막 것이 cid
		}

		// ////////////////////////
		// 여기서부터 버튼 리스너 추가
		// ////////////////////////

		Utilities.addPortalToButton(findViewById(R.id.add_plan_button), this, AddPlanActivity.class);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// 주의! : 이 부분 매번 페이지 옮겨갈 때마다 뷰 생성되면서 실행됨
		GlobalVariables.setCurrentPageCid((Integer) tabActionBar.getTabAt(pager.getCurrentItem()).getTag());
		// 페이지가 바뀔때마다 currentPageCid 정보를 갱신해준다
	}

	// ////////////////////////
	// 여기서부터 액션바와 드로어 상태 동기화 작업
	// ////////////////////////

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch(id){
		case R.id.createCategory:
			PopUpCreateFragment createPopup = new PopUpCreateFragment();
			createPopup.show(getSupportFragmentManager(), "CategoryPopup");
			break;

		case R.id.deleteCategory:
			PopUpDeleteFragment deletePopup = new PopUpDeleteFragment();
			deletePopup.show(getSupportFragmentManager(), "CategoryPopup");
			break;
			
		case R.id.editCategory:
			PopUpEditFragment editPopup = new PopUpEditFragment();
			Bundle b = new Bundle();
			b.putInt("aaa", 2);
			editPopup.setArguments(b);
			editPopup.show(getSupportFragmentManager(), "CategoryPopup");
			break;
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}
	
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//		builder.setTitle(listItemTitle);
//		String[] selectItems = { "수정", "삭제"};
//		
//		builder.setItems(selectItems, new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//				switch (id) {
//				case 0:
//					listener.onEdit(listItemId);
//					break;
//				case 1:
//					listener.onDelete(listItemId);
//					break;
//				}
//			}
//		});
//
//		return builder.create();
//	}
}