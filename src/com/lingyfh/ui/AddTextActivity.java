package com.lingyfh.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lingyfh.editimg.NavBar;
import com.lingyfh.editimg.TabView;
import com.lingyfh.editimg.TextTabBean;
import com.lingyfh.ui.ugc.PushBtnTouchListener;
import com.lingyfh.ui.ugc.ViewOnTouchListener;
import com.lingyfh.util.ImgUtil;
import com.lingyfh.util.LogUtil;
import com.lingyfh.view.DragRotateLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingyfh on 15/4/16.
 */
public class AddTextActivity extends Activity {

	private final static String tag = AddTextActivity.class.getSimpleName();

	private DragRotateLayout mDragRotateLayout;

	private View mContentView;
	private View mRotateView;
	private View mSaveView;
	private ImageView mPreviewImageView;
	private NavBar mNavBar;

	private int mDefaultWidth = 600;
	private float mScale;

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_text_activity);


		mContentView = findViewById(R.id.content_View);
		mRotateView = findViewById(R.id.rotate_view);


		final PushBtnTouchListener listener = new PushBtnTouchListener(mContentView);
		mRotateView.setOnTouchListener(listener);
		mContentView.setOnTouchListener(new ViewOnTouchListener(mRotateView));

		mSaveView = findViewById(R.id.save_btn);
		mSaveView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "onclick",
						Toast.LENGTH_SHORT).show();
			}
		});
		initView();
	}


	public void initView() {
		mNavBar = (NavBar)findViewById(R.id.text_bg_nav);

		List<TabView> mTabs = new ArrayList<TabView>();
		mTabs.add(new TabView(R.drawable.text_bg1));
		mTabs.add(new TabView(R.drawable.text_bg2));
		mTabs.add(new TabView(R.drawable.text_bg3));
		mTabs.add(new TabView(R.drawable.text_bg4));
		mTabs.add(new TabView(R.drawable.text_bg5));
		mTabs.add(new TabView(R.drawable.text_bg6));

		Toast.makeText(this, "nav bar = " + mNavBar, Toast.LENGTH_SHORT).show();

		mNavBar.initView(5.5f, mTabs);
		mNavBar.setOnTabChangeListener(new NavBar.OnTabChangeListener() {
			@Override
			public void onTabSelected(int index) {
				Toast.makeText(AddTextActivity.this, "index = " + index, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onTabDoubleTap() {

			}
		});

		mNavBar.setCurrentTab(0);
	}



}
