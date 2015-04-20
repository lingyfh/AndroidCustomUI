package com.lingyfh.ui;

import android.app.Activity;
import android.os.Bundle;
import com.lingyfh.view.TouchImageView;

public class TouchImageViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TouchImageView img = new TouchImageView(this);
        setContentView(img);
    }
}