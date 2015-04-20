package com.lingyfh.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import com.lingyfh.view.DragResizeRotateView;

/**
 * Created by lingyfh on 15/4/16.
 */
public class DragResizeRotateActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drag_resize_rotate);

		DragResizeRotateView view = (DragResizeRotateView)findViewById(R.id.draglayout);
		view.setDragHorizontal(true);
		view.setDragVertical(true);
		view.setEDGE(ViewDragHelper.EDGE_RIGHT);
		view.setDragEdge(true);
	}
}
