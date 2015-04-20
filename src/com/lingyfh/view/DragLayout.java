package com.lingyfh.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.lingyfh.ui.R;

/**
 * Created by lingyfh on 15/4/16.
 */
public class DragLayout extends LinearLayout {
	private final ViewDragHelper mDragHelper;
	private View mDragView;

	private View mDragView1;
	private View mDragView2;

	private boolean mDragHorizontal;
	private boolean mDragVertical;
	private boolean mDragEdge;
	private boolean mDragCapture;

	private View mRotateView;

	public DragLayout(Context context) {
		this(context, null);
	}

	public DragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mDragView1 = findViewById(R.id.drag1);
		mDragView2 = findViewById(R.id.drag2);
		mRotateView = findViewById(R.id.rotate_view);
		mRotateView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i(this.toString(), "onTouch");
				return true;
			}
		});
	}


	public void setDragHorizontal(boolean dragHorizontial) {
		mDragHorizontal = dragHorizontial;
		mDragView2.setVisibility(GONE);
	}

	public void setDragVertical(boolean dragVertical) {
		mDragVertical = dragVertical;
		mDragView2.setVisibility(GONE);
	}

	public void setDragEdge(boolean dragEdge) {
		mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
		mDragEdge = dragEdge;
		mDragView2.setVisibility(GONE);
	}

	public void setEDGE(int edge) {
		mDragHelper.setEdgeTrackingEnabled(edge);
	}

	public void setDragCapture(boolean dragCapture) {
		mDragCapture = dragCapture;
	}


	private class DragHelperCallback extends ViewDragHelper.Callback {

		@Override
		public boolean tryCaptureView(View child, int i) {
			if (mDragCapture) {
				return child == mDragView1;
			}
			return true;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			invalidate();
		}

		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			super.onViewCaptured(capturedChild, activePointerId);
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
		}

		@Override
		public void onEdgeTouched(int edgeFlags, int pointerId) {
			Log.i(this.toString(), "onEdgeTouched edgeFlags = " + edgeFlags + " pointerId = " + pointerId);

			super.onEdgeTouched(edgeFlags, pointerId);
		}

		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
			if (mDragEdge) {
				mDragHelper.captureChildView(mDragView1, pointerId);
			}
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (mDragHorizontal || mDragCapture || mDragEdge) {
				int leftBound = getPaddingLeft();
				int rightBound = getWidth() - mDragView1.getWidth();

				int newLeft = Math.min(Math.max(left, leftBound), rightBound);
				return newLeft;
			}
			return super.clampViewPositionHorizontal(child, left, dx);
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {

			if (mDragVertical) {
				int topBound = getPaddingTop();
				int bottomBound = getHeight() - mDragView1.getHeight();
				int newTop = Math.min(Math.max(top, topBound), bottomBound);
				return newTop;
			}
			return super.clampViewPositionVertical(child, top, dy);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mDragHelper.cancel();
			return false;
		}
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDragHelper.processTouchEvent(event);
		return true;
	}
}
