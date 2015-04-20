package com.lingyfh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lingyfh.ui.R;
import com.lingyfh.ui.ugc.PushBtnTouchListener;
import com.lingyfh.ui.ugc.ViewOnTouchListener;

/**
 * Created by lingyfh on 15/4/16.
 */
public class DragResizeRotateView extends FrameLayout {
	private final ViewDragHelper mDragHelper;

	private View mDragView;

	private boolean mDragHorizontal;
	private boolean mDragVertical;
	private boolean mDragEdge;
	private boolean mDragCapture;

	private View mRotateView;

	public DragResizeRotateView(Context context) {
		this(context, null);
	}

	public DragResizeRotateView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragResizeRotateView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.parseAttr(context, attrs);
		mDragHelper = ViewDragHelper.create(this, 1.0f,
				new DragHelperCallback());
	}

	private void parseAttr(Context context, AttributeSet attrs) {
		if (null == attrs) return;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SingleFingerView);
		if (a != null) {
			int n = a.getIndexCount();
			for (int i = 0; i < n; i++) {
				int attr = a.getIndex(i);
				if (attr == R.styleable.SingleFingerView_centerInParent) {
					this.mCenterInParent = a.getBoolean(attr, true);
				} else if (attr == R.styleable.SingleFingerView_image) {
					this.mImageDrawable = a.getDrawable(attr);
				} else if (attr == R.styleable.SingleFingerView_image_height) {
					this.mImageHeight = a.getDimension(attr, 200 * _1dp);
				} else if (attr == R.styleable.SingleFingerView_image_width) {
					this.mImageWidth = a.getDimension(attr, 200 * _1dp);
				} else if (attr == R.styleable.SingleFingerView_push_image) {
					this.mPushImageDrawable = a.getDrawable(attr);
				} else if (attr == R.styleable.SingleFingerView_push_image_width) {
					this.mPushImageWidth = a.getDimension(attr, 50 * _1dp);
				} else if (attr == R.styleable.SingleFingerView_push_image_height) {
					this.mPushImageHeight = a.getDimension(attr, 50 * _1dp);
				} else if (attr == R.styleable.SingleFingerView_left) {
					this.mLeft = (int) a.getDimension(attr, 0 * _1dp);
				} else if (attr == R.styleable.SingleFingerView_top) {
					this.mTop = (int) a.getDimension(attr, 0 * _1dp);
				}
			}
		}
	}



	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mDragView = findViewById(R.id.drag_view);
		mRotateView = findViewById(R.id.rotate_view);
		mDragView.setOnTouchListener(new ViewOnTouchListener(mRotateView));
		mRotateView.setOnTouchListener(new PushBtnTouchListener(mDragView));
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setParamsForView(widthMeasureSpec, heightMeasureSpec);
		Log.i(this.toString(), "onMeasure");
	}

	private boolean hasSetParamsForView = false;

	private void setParamsForView(int widthMeasureSpec, int heightMeasureSpec) {
		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		if (null != layoutParams && !hasSetParamsForView) {
			System.out.println("AAAAAAAAAAAAAAAAAAA setParamsForView");
			hasSetParamsForView = true;
			int width;
			if ((getLayoutParams().width == LinearLayout.LayoutParams.MATCH_PARENT)) {
				width = MeasureSpec.getSize(widthMeasureSpec);
			} else {
				width = getLayoutParams().width;
			}
			int height;
			if ((getLayoutParams().height == LinearLayout.LayoutParams.MATCH_PARENT)) {
				height = MeasureSpec.getSize(heightMeasureSpec);
			} else {
				height = getLayoutParams().height;
			}
			setViewToAttr(width, height);
		}
	}

	private float _1dp;
	private boolean mCenterInParent = true;
	private Drawable mImageDrawable, mPushImageDrawable;
	private float mImageHeight, mImageWidth, mPushImageHeight, mPushImageWidth;
	private int mLeft = 0, mTop = 0;

	private void setViewToAttr(int pWidth, int pHeight) {
		if (null != mImageDrawable) {
			this.mDragView.setBackgroundDrawable(mImageDrawable);
		}
		if (null != mPushImageDrawable) {
			this.mRotateView.setBackgroundDrawable(mPushImageDrawable);
		}
		FrameLayout.LayoutParams viewLP = (FrameLayout.LayoutParams) this.mDragView.getLayoutParams();
		if (mImageWidth != 0 && mImageHeight != 0) {
			viewLP.width = (int) mImageWidth;
			viewLP.height = (int) mImageHeight;
			int left = 0, top = 0;
			if (mCenterInParent) {
				left = pWidth / 2 - viewLP.width / 2;
				top = pHeight / 2 - viewLP.height / 2;
			} else {
				if (mLeft > 0) left = mLeft;
				if (mTop > 0) top = mTop;
			}
			viewLP.leftMargin = left;
			viewLP.topMargin = top;
			this.mDragView.setLayoutParams(viewLP);
		}



		FrameLayout.LayoutParams pushViewLP = (FrameLayout.LayoutParams) mRotateView.getLayoutParams();
		if (mPushImageHeight != 0 && mPushImageWidth != 0) {
			pushViewLP.width = (int) mPushImageWidth;
			pushViewLP.height = (int) mPushImageHeight;
			pushViewLP.leftMargin = (int) (viewLP.leftMargin + mImageWidth - mPushImageWidth / 2);
			pushViewLP.topMargin = (int) (viewLP.topMargin + mImageHeight - mPushImageHeight / 2);
			mRotateView.setLayoutParams(pushViewLP);
		}
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
			Log.i(this.toString(), "onViewPositionChanged left = " + left + " top = " + top + " dx = " + dx + " dy = " + dy);
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
