package net.sistransito.mobile.database.sync;

import android.net.ParseException;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class SyncAitService {

    public static boolean isIntervalOfHours(String timeOpen, String timeClose) {
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date().getTime());// Pega hora atual do Sistema

        Integer scheduleClose[] = getHourAndMinute(timeClose);
        Integer scheduleOpen[]  = getHourAndMinute(timeOpen);

        if( scheduleClose[0] < scheduleOpen[0] ) {
            Integer currentSchedule[] = getHourAndMinute(currentTime);

            if(currentSchedule[0] <= scheduleClose[0]) {
                if(currentSchedule[1] >= scheduleClose[1] && currentSchedule[0] >= scheduleClose[0] ) {
                    return true;
                } else {
                    return false;
                }
            }  else {
                timeClose = "23:59";
            }
        }

        try {
            if (isComparaHora(currentTime, timeClose) && !isComparaHora(currentTime, timeOpen)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException ex) {
            Log.d("Tag: ", "Horário Inválido...");
            return false;
        }

    }

    public static Integer[] getHourAndMinute(String hora) {
        int schedule, minutes;

        schedule = Integer.parseInt(hora.substring(0, 2));
        minutes = Integer.parseInt(hora.substring(3, 5));

        return new Integer[]{schedule, minutes};
    }

    private static boolean isComparaHora(String currentTime, String timeClose) throws ParseException {
        boolean closeCashier = false;
        try {
            if (!new SimpleDateFormat("HH:mm").parse(timeClose).before(new SimpleDateFormat("HH:mm").parse(currentTime))) {
                closeCashier = true;
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return closeCashier;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isTimeSync(String horaAbre , String horaFecha) {

        String currentTime = new SimpleDateFormat("HH:mm").format(new Date().getTime());// Pega hora atual do Sistema

        LocalTime start = LocalTime.parse( "11:00" ) ;
        LocalTime stop = LocalTime.parse( "12:00" ) ;

        if(LocalTime.parse(currentTime).isAfter(LocalTime.parse(horaFecha)));

        return true;

    }

}
