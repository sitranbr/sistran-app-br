package net.sistransito.mobile.timeandime;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeAndIme {
	private Calendar calendar;
	private SimpleDateFormat sDatef;
	private SimpleDateFormat sTimef;
	private TelephonyManager mngr;

	public TimeAndIme(Context context) {

		mngr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		calendar = Calendar.getInstance();
		sDatef = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

		sTimef = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

	}

	public Date addHalfDay(){

		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 12);
		Date d = c.getTime();
		return d;

	}

	public static String subtraiHoras(String horaIni, String horaFinal){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date dataIni = null;
		Date dataFim = null;
		try {
			dataIni = sdf.parse(horaIni);
			dataFim = sdf.parse(horaFinal);
		} catch (Exception e) {

		}
		Date resultado = new Date(dataFim.getTime() - dataIni.getTime());
		//sdf.format(resultado);

		return sdf.format(resultado);


	}

	public String getTime() {
		return sTimef.format(calendar.getTime());
	}

	public String getDate() {
		return sDatef.format(calendar.getTime());
	}

	public String getUniqueId() {
		return "123456";//mngr.getDeviceId() + String.valueOf(System.currentTimeMillis());
	}

	public String getIME() {
		return "1312312";//'mngr.getDeviceId();
	}

}
