package gla.es3.com.profiletasks.service.impl.mainalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import gla.es3.com.profiletasks.R;
import gla.es3.com.profiletasks.utils.ResourceUtils;


public class MainAlarmController {

    public static final String CUSTOM_ACTION_MAINALARM = "gla.es3.com.profiletasks.service.impl.mainalarm.MainAlarmHandler";
    private static final String TAG = "MainAlarmController";

    public static void startMainAlarm(Context context, boolean bootTime) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(CUSTOM_ACTION_MAINALARM);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, serviceIntent, PendingIntent.FLAG_NO_CREATE);
        if (alarmIntent == null)
            alarmIntent = PendingIntent.getBroadcast(context, 0, serviceIntent, 0);

        int timeAfterBoot;
        if (bootTime)
            timeAfterBoot = Math.round(ResourceUtils.getMSFromResouce(context, R.dimen.time_afeterboot));
        else
            timeAfterBoot = 1000;

        int timeBetweenCalls = Math.round(ResourceUtils.getMSFromResouce(context, R.dimen.time_service_betweencallst));

        if (alarmMgr != null && alarmIntent != null) {
            alarmMgr.cancel(alarmIntent);
        }

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                timeAfterBoot,
                timeBetweenCalls, alarmIntent);
    }

    public static void stopMainAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(CUSTOM_ACTION_MAINALARM);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, serviceIntent, PendingIntent.FLAG_NO_CREATE);
        if (alarmMgr != null && alarmIntent != null) {
            alarmMgr.cancel(alarmIntent);

            Log.v(TAG, "stopMainAlarm");
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }


}
