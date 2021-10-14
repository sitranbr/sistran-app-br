package net.sistransito.mobile.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This BroadcastReceiver automatically (re)starts the alarmReceiver when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in
 * the application's manifest file. When the user sets the alarmReceiver, the receiver
 * is enabled. When the user cancels the alarmReceiver, the receiver is disabled, so
 * that rebooting the device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class BalanceBootReceiver extends BroadcastReceiver {

	BalanceAlarmReceiver alarmReceiver = new BalanceAlarmReceiver();

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			alarmReceiver.setAlarm(context);
		}

	}
}
// END_INCLUDE(autostart)
