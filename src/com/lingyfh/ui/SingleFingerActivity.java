package com.lingyfh.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import com.lingyfh.ui.ugc.SingleFingerView;
import com.lingyfh.view.DragLayout;

public class SingleFingerActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_touch_main);
    }
}
