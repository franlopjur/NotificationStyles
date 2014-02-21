package com.franlopez.notificationstyles.activities;

import com.franlopez.notificationstyles.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ActionsActivity extends Activity {

	private LinearLayout mLayoutContent;
	private String mNumberAction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actions);
		
		if(getIntent() != null)
			mNumberAction = getIntent().getAction();
		
		getViews();
		init();
	}

	private void init() {
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		TextView textAction = new TextView(ActionsActivity.this);
		textAction.setText(mNumberAction);
		textAction.setLayoutParams(lp);
		textAction.setGravity(Gravity.CENTER);
		mLayoutContent.addView(textAction);
	}

	private void getViews() {
		
		mLayoutContent = (LinearLayout) findViewById(R.id.layout__actions_content);
	}
}
