package com.lingyfh.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.lingyfh.ui.ugc.PushBtnTouchListener;

/**
 * Created by lingyfh on 15/4/16.
 */
public class RotateActivity extends Activity {

	private View mContentView;
	private View mRotateView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotate_main);

		mContentView = findViewById(R.id.content_View);
		mRotateView = findViewById(R.id.rotate_view);

		mRotateView.setOnTouchListener(new PushBtnTouchListener(mContentView));
	}
}
