package com.lingyfh.editimg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.lingyfh.ui.R;

/**
 * Created by lingyfh on 15/4/20.
 */
public class TabView implements TabFactory {

	public int mIconResid;

	public TabView(int iconResid) {
		this.mIconResid = iconResid;
	}

	@Override
	public View createTab(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View tabView = inflater.inflate(R.layout.text_bg_tab, null);
		// tabView.setBackgroundResource(R.drawable.selector_tab_home);
		ImageView tabIcon = (ImageView) tabView.findViewById(R.id.tab_icon);
		tabIcon.setImageResource(mIconResid);
		return tabView;
	}
}
