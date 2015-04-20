package com.lingyfh.editimg;

import java.util.List;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import com.lingyfh.ui.R;
import com.lingyfh.util.LogUtil;


public class NavBar extends RelativeLayout implements OnClickListener {

	private final String tag = NavBar.class.getSimpleName();

	public interface OnTabChangeListener {
		public void onTabSelected(int index);
		public void onTabDoubleTap();
	}

	private HorizontalScrollView mNavScrollView;
	private Scroller mScroller;
	private LinearLayout mNavLayout;
	private OnTabChangeListener mListener;
	private int mTabIndex = 0;
	private GestureDetectorCompat mGestureDetector;
	private int mTabNameBG = -1;
	private int mWidth = 0;

	public NavBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setTabNameBackgroundColor(int resid) {
		mTabNameBG = resid;
	}

	private void initGestureDetector() {
		mGestureDetector = new GestureDetectorCompat(this.getContext(),
				new GestureDetector.OnGestureListener() {
					@Override
					public boolean onDown(MotionEvent e) {
						LogUtil.i(tag, "onDown");
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e) {
						LogUtil.i(tag, "onShowPress");
					}

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						LogUtil.i(tag, "onSingleTapUp");
						return false;
					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						LogUtil.i(tag, "onScroll");
						return false;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						LogUtil.i(tag, "onLongPress");
					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						LogUtil.i(tag, "onFling");
						return false;
					}
				});
		mGestureDetector
				.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						LogUtil.i(tag, "onSingleTapConfirmed");
						return false;
					}

					@Override
					public boolean onDoubleTap(MotionEvent e) {
						LogUtil.i(tag, "onDoubleTap");
						if (mListener != null) {
							mListener.onTabDoubleTap();
						}
						return false;
					}

					@Override
					public boolean onDoubleTapEvent(MotionEvent e) {
						LogUtil.i(tag, "onDoubleTapEvent");
						return false;
					}
				});
	}

	public void setNavBarWidth(int width) {
		mWidth = width;
	}

	public void initView(List<? extends TabFactory> factories) {
		initGestureDetector();
		mNavLayout = (LinearLayout) findViewById(R.id.nav_layout);
		mNavScrollView = (HorizontalScrollView) findViewById(R.id.nav_scrollview);
		mScroller = new Scroller(mNavScrollView.getContext());
		DisplayMetrics dm = DeviceUtil.getDisplayMetrics(getContext());
		if (mWidth == 0) {
			mWidth = dm.widthPixels;
		}
		int width = (mWidth - mNavLayout.getPaddingLeft() - mNavLayout
				.getPaddingRight()) / factories.size();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		insertTabView(factories, params);
	}

	public void initView(int navBgResID, List<? extends TabFactory> factories) {
		initView(factories);
		if (navBgResID > 0) {
			mNavLayout.setBackgroundColor(getContext().getResources().getColor(
					navBgResID));
		}

	}

	public void initSecondNavView(List<? extends TabFactory> factories) {
		initView(R.color.bg_content, factories);
	}

	public void initView(float widthRate, List<? extends TabFactory> factories) {
		initGestureDetector();
		mNavLayout = (LinearLayout) findViewById(R.id.nav_layout);
		mNavScrollView = (HorizontalScrollView) findViewById(R.id.nav_scrollview);
		mScroller = new Scroller(mNavScrollView.getContext());
		DisplayMetrics dm = DeviceUtil.getDisplayMetrics(getContext());
		if (mWidth == 0) {
			mWidth = dm.widthPixels;
		}
		int width = (int) ((mWidth - mNavLayout.getPaddingLeft() - mNavLayout
				.getPaddingRight()) / widthRate);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;

		insertTabView(factories, params);
	}

	private void insertTabView(List<? extends TabFactory> factories,
			LinearLayout.LayoutParams params) {
		int tabIndex = 0;
		for (TabFactory factory : factories) {
			View tabView = factory.createTab(getContext());
			tabView.setTag(tabIndex++);
			tabView.setClickable(true);
			tabView.setOnClickListener(this);
			tabView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					mGestureDetector.onTouchEvent(event);
					return false;
				}
			});
			mNavLayout.addView(tabView, params);
		}
	}

	public void initView(float widthRate, int navBgResID,
			List<? extends TabFactory> factories) {
		initView(widthRate, factories);
		if (navBgResID > 0) {
			mNavLayout.setBackgroundColor(getContext().getResources().getColor(
					navBgResID));
		}

	}

	public void initSecondNavView(float widthRate,
			List<? extends TabFactory> factories) {
		initView(widthRate, R.color.bg_content, factories);
	}

	public void setOnTabChangeListener(OnTabChangeListener listener) {
		mListener = listener;
	}

	public void setCurrentTab(int index) {
		View newTab = mNavLayout.getChildAt(index);
		if (newTab != null && mTabIndex != index) {
			View oldTab = mNavLayout.getChildAt(mTabIndex);
			if (oldTab != null && oldTab.isSelected()) {
				oldTab.setSelected(false);
				oldTab.invalidate();
			}
			if (!newTab.isSelected()) {
				newTab.setSelected(true);
				newTab.invalidate();
			}
			mTabIndex = index;
			if (mListener != null) {
				mListener.onTabSelected(index);
			}

			int leftWidth = newTab.getWidth() * index;
			int rightWidth = newTab.getWidth() * (index + 1);
			int scrollX = mNavScrollView.getScrollX();
			if (rightWidth - scrollX > DeviceUtil.getDisplayW(newTab
					.getContext())) {
				mNavScrollView.smoothScrollBy(
						scrollX + rightWidth
								- DeviceUtil.getDisplayW(newTab.getContext()),
						0);
			} else if (leftWidth - scrollX < 0) {
				mNavScrollView.smoothScrollBy(leftWidth - scrollX, 0);
			}
		}
	}

	@Override
	public void onClick(View v) {
		int index = (Integer) v.getTag();
		setCurrentTab(index);
	}

}
