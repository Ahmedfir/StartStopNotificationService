/**
 * 
 */
package com.example.simplenotificationapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.simplenotificationapp.activities.NotifiedActivity;
import com.example.simplenotificationapp.core.ApplicationProperties;
import com.example.simplenotificationapplication.R;

/**
 * <p>
 * Shows a notification every lunch.
 * </p>
 * 
 * <p>
 * Started and destroyed by the ServiceLuncherActivity, by intent.
 * </p>
 * 
 * <p>
 * Sends an intent every state changing : stopping or starting.
 * </p>
 * 
 * 
 * @author Ahmed
 */
public class NotificationCreatorService extends Service {

	/**
	 * the intent tag for the extra boolean defining the Service state
	 */
	public final static String IS_SERVICE_RUNNING_TAG = "SERVICE_STATE_TAG";

	/**
	 * the intent sent to the activity when the state is changing
	 */
	private Intent broadcastIntentStateChange;

	/**
	 * sends an intent to the activity with the new state
	 * 
	 * @param isServiceRunning
	 *            defines the state of the service
	 */
	private void stateChange(boolean isServiceRunning) {
		broadcastIntentStateChange.putExtra(IS_SERVICE_RUNNING_TAG,
				isServiceRunning);
		this.sendBroadcast(broadcastIntentStateChange);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// create the broadcast intent of the Service state change only once
		broadcastIntentStateChange = new Intent();
		broadcastIntentStateChange
				.setAction(ApplicationProperties.SERVICE_STATE_CHANGE);

		stateChange(true);

		// create the notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher);

		// associate the NotifiedActivity with the notification
		Intent targetIntent = new Intent(this, NotifiedActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);
		NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// show the notification
		nManager.notify(ApplicationProperties.NOTIFICATION_ID, builder.build());

		return Service.START_NOT_STICKY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		stateChange(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
