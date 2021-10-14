package net.sistransito.mobile.main;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class BalanceAlarmReceiver extends WakefulBroadcastReceiver {

	private AlarmManager alarmMgr;

	private PendingIntent alarmIntent;

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent service = new Intent(context, BalanceSchedulingService.class);

		startWakefulService(context, service);
	}

	public void setAlarm(Context context) {
		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, BalanceAlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);

		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				alarmIntent);

		ComponentName receiver = new ComponentName(context,
				BalanceBootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	public void cancelAlarm(Context context) {

		if (alarmMgr != null) {
			alarmMgr.cancel(alarmIntent);
		}

		ComponentName receiver = new ComponentName(context,
				BalanceBootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);

	}

}
