/**
 * 
 */
package com.example.simplenotificationapp.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * @author Ahmed
 * 
 */
public final class AndroidUtil {

	/**
	 * Internal constructor; not to be called as this class provides static
	 * utilities only.
	 */
	private AndroidUtil() {
		throw new UnsupportedOperationException("No instances permitted");
	}

	/**
	 * @param context
	 * @param serviceToCheck
	 * @return is the service running
	 */
	public static <T> boolean isServiceRunning(Context context,
			Class<T> serviceToCheck) {

		boolean isRunning = false;

		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {

			if (serviceToCheck.getName().equals(service.service.getClassName())) {
				// the service is running
				isRunning = true;
			}

		}

		return isRunning;
	}
}
