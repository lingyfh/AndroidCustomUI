package com.lingyfh.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;
import com.lingyfh.ui.ugc.PushBtnTouchListener;
import com.lingyfh.ui.ugc.ViewOnTouchListener;
import com.lingyfh.util.ImgUtil;
import com.lingyfh.util.LogUtil;
import com.lingyfh.view.DragRotateLayout;

/**
 * Created by lingyfh on 15/4/16.
 */
public class RotateMoveActivity extends Activity {

	private final static String tag = RotateMoveActivity.class.getSimpleName();

	private DragRotateLayout mDragRotateLayout;

	private View mContentView;
	private View mRotateView;
	private View mSaveView;
	private ImageView mPreviewImageView;

	private int mDefaultWidth = 600;
	private float mScale;

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotate_move_main);

		/*mDragRotateLayout = (DragRotateLayout)findViewById(R.id.drag_rotate_layout);
		mDragRotateLayout.setDragHorizontal(true);
		mDragRotateLayout.setDragVertical(true);
		mDragRotateLayout.setEDGE(ViewDragHelper.EDGE_RIGHT);
		mDragRotateLayout.setDragEdge(true);*/

		mContentView = findViewById(R.id.content_View);

		mRotateView = findViewById(R.id.rotate_view);

		final PushBtnTouchListener listener = new PushBtnTouchListener(mContentView);

		mRotateView.setOnTouchListener(listener);
		mContentView.setOnTouchListener(new ViewOnTouchListener(mRotateView));

		mPreviewImageView = (ImageView)findViewById(R.id.preview_iv);


		mSaveView = findViewById(R.id.save_btn);
		mSaveView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mContentView.setDrawingCacheEnabled(true);
				int width = mContentView.getLayoutParams().width;
				int height = mContentView.getLayoutParams().height;
				LogUtil.i(tag, "width = " + width + " height = " + height
						+ " mDefaultWidth = " + mDefaultWidth);
				Bitmap bitmap = mContentView.getDrawingCache();
				if (bitmap != null) {
					LogUtil.i(tag, "Degree = " + listener.getRotateDegree()
							+ " float = " + (float) listener.getRotateDegree());
					mScale = (float) width / (float) mDefaultWidth;
					Bitmap rotateBitmap = ImgUtil.getRotatedBitamp(bitmap,
							(float) listener.getRotateDegree(), false);
					Bitmap scaleBitmap = ImgUtil.getScaledBitmap(rotateBitmap,
							mScale, mScale, true);
					mPreviewImageView.setImageBitmap(scaleBitmap);
					return;
				}
				Toast.makeText(v.getContext(), "bitmap = " + bitmap,
						Toast.LENGTH_SHORT).show();
			}
		});
	}




}
