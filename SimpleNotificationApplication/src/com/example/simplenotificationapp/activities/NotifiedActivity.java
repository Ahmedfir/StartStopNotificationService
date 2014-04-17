package com.example.simplenotificationapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.simplenotificationapplication.R;

/**
 * The activity lunched by the notification
 * 
 * @author Ahmed
 */
public class NotifiedActivity extends Activity {

	/**
	 * the button to navigate to the luncher Activity
	 */
	private Button testAgainButton;

	/**
	 * the listener to navigate to the luncher Activity
	 */
	private OnClickListener testAgainListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(NotifiedActivity.this,
					ServiceLuncherActivity.class);
			startActivity(intent);

		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notified);

		testAgainButton = (Button) findViewById(R.id.SNA_notified_activity_button);

		testAgainButton.setOnClickListener(testAgainListener);
	}
}
