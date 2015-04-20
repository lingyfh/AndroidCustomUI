package com.lingyfh.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lingyfh.editimg.DeviceUtil;
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

	private PopupWindow mPopupWindow;

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
		initSeekBar();
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

	public void showPopWindow(View view) {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			return;
		}
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//Inflate the view from a predefined XML layout
		View layout = inflater.inflate(R.layout.seekbar_thum_layout,  null);
		// create a 300px width and 470px height PopupWindow
		mPopupWindow = new PopupWindow(layout, 200, 200);
		// display the popup in the center

		int[] location = new int[2];
		view.getLocationOnScreen(location);

		mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-mPopupWindow.getHeight());
	}

	private void initSeekBar() {



		SeekBar seekbar = (SeekBar) findViewById(R.id.text_seekbar);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
										  boolean fromUser) {
				if (mPopupWindow != null) {
					int[] location = new int[2];
					seekBar.getLocationOnScreen(location);
					int displayW = DeviceUtil.getDisplayW(AddTextActivity.this);
					int seekBarW = displayW - DeviceUtil.dip2px(AddTextActivity.this, 60);
					float moveScale = (float)progress/100.0f;
					float moveX = seekBarW*moveScale;
					mPopupWindow.update(location[0]+ (int)moveX, location[1]-mPopupWindow.getHeight(), -1, -1, true);
				}

				LogUtil.i(tag, "onProgressChanged progress = " + progress
						+ " fromUser = " + fromUser + " seekBar = " + seekBar);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				LogUtil.i(tag, "onStartTrackingTouch");
				showPopWindow(seekBar);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				LogUtil.i(tag, "onStopTrackingTouch");
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
	}



}
