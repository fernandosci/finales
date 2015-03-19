package gla.es3.com.profiletasks.model.entity.triggers.functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.PowerManager;

import java.util.List;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.BaseTrigger;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerCallBackInfo;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerListener;
import gla.es3.com.profiletasks.model.parameter.Parameter;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;


public class TriggerCharging extends BaseTrigger {

    private LocalTriggerCharging triggerHandler;

    public TriggerCharging(TriggerListener listener, EntityServiceHandler eHandler) {
        super(listener, eHandler);

        triggerHandler = new LocalTriggerCharging();
        IntentFilter serviceIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        tHandler.getContext().registerReceiver(triggerHandler, serviceIntentFilter);
    }

    @Override
    public String getID() {
        return "TRIGGER_STATE_CHARGING";
    }

    @Override
    public String getDisplayName() {
        return "Charging";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(Boolean.class, new Boolean(true), "Charging Started/Stopped", "Charging Started/Stopped");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        if (list.getId().equals(getID())) {
            return getDisplayName() + "(" + (String) list.getList().get(0).getValue() + ")";
        } else
            return "";
    }

    @Override
    public void check(ParameterContainer list, String profileId) {

        //listener.notificationOfEvent(profileId);
    }


    private final class LocalTriggerCharging extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Set<String> profiles = getProfiles();

            for (String profile : profiles) {

                TriggerCallBackInfo callBackFromProfileID = getCallBackFromProfileID(profile);
                List<Parameter> list = callBackFromProfileID.getList().getList();
                Parameter parameter = list.get(0);

                if (parameter.hasValue()) {
                    Boolean state = (Boolean) parameter.getValue();

                    int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

                    if ((plugged == 0 && state) || (plugged != 0 && !state)) {
                        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                        wl.acquire();
                        listener.notificationOfEvent(profile);
                        wl.release();
                    }
                }
            }
        }
    }
}
