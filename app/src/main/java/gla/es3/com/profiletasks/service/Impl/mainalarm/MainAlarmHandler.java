package gla.es3.com.profiletasks.service.impl.mainalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import gla.es3.com.profiletasks.model.ModelContainer;


public class MainAlarmHandler {


    private Context context;
    private ModelContainer model;
    private LocalMainAlarmHandler broadcastReceiver;


    public MainAlarmHandler(Context context, ModelContainer model) {
        this.context = context;
        this.model = model;
        this.broadcastReceiver = new LocalMainAlarmHandler();
    }

    public LocalMainAlarmHandler getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public final class LocalMainAlarmHandler extends BroadcastReceiver {

        private static final String TAG = "LocalMainAlarmHandler";

        public LocalMainAlarmHandler() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {


            if (intent.getAction().equals(MainAlarmController.CUSTOM_ACTION_MAINALARM)) {

                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                wl.acquire();


                Log.v(TAG, "Alarm !!");

                wl.release();

            }


        }
    }

}
