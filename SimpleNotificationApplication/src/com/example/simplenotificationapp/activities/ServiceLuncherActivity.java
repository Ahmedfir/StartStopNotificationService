package com.example.simplenotificationapp.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.simplenotificationapp.core.ApplicationProperties;
import com.example.simplenotificationapp.services.NotificationCreatorService;
import com.example.simplenotificationapp.util.AndroidUtil;
import com.example.simplenotificationapp.util.DataUtil;
import com.example.simplenotificationapplication.R;

/**
 * The first activity lunched
 * 
 * @author Ahmed
 */
public class ServiceLuncherActivity extends Activity {

	/**
	 * the intent to start or stop the service
	 */
	private Intent serviceLuncherIntent;

	/**
	 * the button to start or stop the service
	 */
	private Button startStopServiceButton;

	/**
	 * the click listener to start the service
	 */
	private OnClickListener startServiceListener = new OnClickListener() {
		public void onClick(View v) {
			startService(serviceLuncherIntent);
		}
	};

	/**
	 * the click listener to stop the service
	 */
	private OnClickListener stopServiceListener = new OnClickListener() {
		public void onClick(View v) {
			stopService(serviceLuncherIntent);
		}
	};

	/**
	 * the service state receiver
	 */
	private BroadcastReceiver serviceStateChangeReceiver;

	/**
	 * The intent filter of the service state receiver
	 */
	private final IntentFilter serviceStateChangeIntentFilter = new IntentFilter(
			ApplicationProperties.SERVICE_STATE_CHANGE);

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_luncher);

		serviceLuncherIntent = new Intent(ServiceLuncherActivity.this,
				NotificationCreatorService.class);

		startStopServiceButton = (Button) findViewById(R.id.SNA_start_stop_service_button);

		startStopServiceButton.setOnClickListener(startServiceListener);

		// create the broadcast receiver to catch the service state changing
		serviceStateChangeReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				if (DataUtil.isNotEmpty(intent.getExtras())) {
					boolean isRunning = intent.getExtras().getBoolean(
							NotificationCreatorService.IS_SERVICE_RUNNING_TAG);
					updateButton(isRunning);
				} else {
					updateButton(AndroidUtil.isServiceRunning(context,
							NotificationCreatorService.class));
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// register the receiver
		registerReceiver(serviceStateChangeReceiver,
				serviceStateChangeIntentFilter);
		// check if the service is running or not when the application is
		// resumed
		updateButton(AndroidUtil.isServiceRunning(this,
				NotificationCreatorService.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	protected void onPause() {
		super.onPause();
		// unregister the receiver
		unregisterReceiver(serviceStateChangeReceiver);
	}

	/**
	 * update the button text and listener depending on the state of the service
	 * 
	 * @param isServiceLunched
	 */
	private void updateButton(Boolean isServiceLunched) {
		if (isServiceLunched) {
			// the service is running
			startStopServiceButton
					.setText(R.string.SNA_service_luncher_stop_button);
			startStopServiceButton.setOnClickListener(stopServiceListener);
		} else {
			// the service is not running
			startStopServiceButton
					.setText(R.string.SNA_service_luncher_start_button);
			startStopServiceButton.setOnClickListener(startServiceListener);
		}
	}

}
