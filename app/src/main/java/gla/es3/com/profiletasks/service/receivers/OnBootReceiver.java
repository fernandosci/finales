package gla.es3.com.profiletasks.service.receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import gla.es3.com.profiletasks.service.impl.MainService;

/**
 * Created by ito on 14/03/2015.
 */
public class OnBootReceiver extends BroadcastReceiver {
//requires manifest receiver
//requires manifest boot permission

    public static void enableReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, OnBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public static void disableReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, OnBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            Intent i = new Intent(context, MainService.class);
            // potentially add data to the intent
            //i.putExtra("KEY1", "Value to be used by the service");
            context.startService(i);


            //MainAlarmController.startMainAlarm(context, true);

        }
    }


}
