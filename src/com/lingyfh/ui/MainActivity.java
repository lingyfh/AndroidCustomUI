package com.lingyfh.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import com.lingyfh.ui.ugc.PushBtnTouchListener;
import com.lingyfh.view.DragLayout;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag);

		DragLayout dragLayout = (DragLayout) findViewById(R.id.draglayout);
		dragLayout.setDragHorizontal(true);
		dragLayout.setDragVertical(true);
		dragLayout.setEDGE(ViewDragHelper.EDGE_RIGHT);
		dragLayout.setDragEdge(true);


		findViewById(R.id.openNewActivityRotate).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.getContext().startActivity(new Intent(v.getContext(), RotateActivity.class));
			}
		});

		findViewById(R.id.openNewActivityDrag).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.getContext().startActivity(new Intent(v.getContext(), DragResizeRotateActivity.class));
			}
		});

		findViewById(R.id.openNewActivitySingler).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.getContext().startActivity(new Intent(v.getContext(), SingleFingerActivity.class));
			}
		});

		findViewById(R.id.openNewActivityRotateMove).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.getContext().startActivity(new Intent(v.getContext(), RotateMoveActivity.class));
			}
		});

		findViewById(R.id.openAddTextAt).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.getContext().startActivity(new Intent(v.getContext(), AddTextActivity.class));
			}
		});


		View contentView = findViewById(R.id.content_View);
		//findViewById(R.id.rotate_view_2).setOnTouchListener(new PushBtnTouchListener(contentView));
	}
}
