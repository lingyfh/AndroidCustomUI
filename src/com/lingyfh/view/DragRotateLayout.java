package com.lingyfh.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.lingyfh.ui.R;
import com.lingyfh.ui.ugc.PushBtnTouchListener;

/**
 * Created by lingyfh on 15/4/16.
 */
public class DragRotateLayout extends FrameLayout {

	private ViewDragHelper mDragHelper;

	private View mDragView;

	private boolean mDragHorizontal;
	private boolean mDragVertical;
	private boolean mDragEdge;
	private boolean mDragCapture;

	public DragRotateLayout(Context context) {
		super(context);
		mDragHelper = ViewDragHelper.create(this, 1.0f,
				new DragHelperCallback());
	}

	public DragRotateLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDragHelper = ViewDragHelper.create(this, 1.0f,
				new DragHelperCallback());
	}

	public DragRotateLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mDragHelper = ViewDragHelper.create(this, 1.0f,
				new DragHelperCallback());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mDragView = findViewById(R.id.drag_view);
	}

	public void setDragHorizontal(boolean dragHorizontial) {
		mDragHorizontal = dragHorizontial;
	}

	public void setDragVertical(boolean dragVertical) {
		mDragVertical = dragVertical;
	}

	public void setDragEdge(boolean dragEdge) {
		mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
		mDragEdge = dragEdge;
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
				return child == mDragView;
			}
			return true;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			Log.i(this.toString(), "onViewPositionChanged left = " + left
					+ " top = " + top + " dx = " + dx + " dy = " + dy);
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
			Log.i(this.toString(), "onEdgeTouched edgeFlags = " + edgeFlags
					+ " pointerId = " + pointerId);

			super.onEdgeTouched(edgeFlags, pointerId);
		}

		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
			if (mDragEdge) {
				mDragHelper.captureChildView(mDragView, pointerId);
			}
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (mDragHorizontal || mDragCapture || mDragEdge) {
				int leftBound = getPaddingLeft();
				int rightBound = getWidth() - mDragView.getWidth();

				int newLeft = Math.min(Math.max(left, leftBound), rightBound);
				return newLeft;
			}
			return super.clampViewPositionHorizontal(child, left, dx);
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {

			if (mDragVertical) {
				int topBound = getPaddingTop();
				int bottomBound = getHeight() - mDragView.getHeight();
				int newTop = Math.min(Math.max(top, topBound), bottomBound);
				return newTop;
			}
			return super.clampViewPositionVertical(child, top, dy);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_CANCEL
				|| action == MotionEvent.ACTION_UP) {
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
