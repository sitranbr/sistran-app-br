package net.sistransito.mobile.main;

import android.app.IntentService;
import android.content.Intent;

import net.sistransito.mobile.database.DatabaseCreator;
import net.sqlcipher.database.SQLiteDatabase;

public class BalanceSchedulingService extends IntentService {

	public BalanceSchedulingService() {

		super("SchedulingService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {
			SQLiteDatabase.loadLibs(this);
		} catch (Exception e) {
		}

		(DatabaseCreator.getBalanceDatabaseAdapter(this)).setAitPerformed(0);
		(DatabaseCreator.getBalanceDatabaseAdapter(this)).setRrdPerformed(0);
		(DatabaseCreator.getBalanceDatabaseAdapter(this)).setTcaPerformed(0);
		(DatabaseCreator.getBalanceDatabaseAdapter(this)).setTavPerformed(0);

		BalanceAlarmReceiver.completeWakefulIntent(intent);

	}

}
