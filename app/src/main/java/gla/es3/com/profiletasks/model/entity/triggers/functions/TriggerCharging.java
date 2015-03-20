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

    public static boolean _statusIsPlugged = false;

    private LocalTriggerCharging triggerHandler;

    public TriggerCharging(TriggerListener listener, EntityServiceHandler eHandler) {
        super(listener, eHandler);

        Intent intent = tHandler.getContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        _statusIsPlugged = plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;

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

            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {

                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);


                if ((plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB) && _statusIsPlugged) {
                    return;
                } else if ((plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB) && !_statusIsPlugged) {
                    _statusIsPlugged = true;
                } else if (plugged == 0 && !_statusIsPlugged) {
                    return;
                } else if (plugged == 0 && _statusIsPlugged) {
                    _statusIsPlugged = false;
                }


                for (String profile : profiles) {
                    TriggerCallBackInfo callBackFromProfileID = getCallBackFromProfileID(profile);
                    List<Parameter> list = callBackFromProfileID.getList().getList();
                    Parameter parameter = list.get(0);

                    if (parameter.hasValue()) {
                        Boolean state = (Boolean) parameter.getValue();

                        if ((plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB) && state) {
                            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                            wl.acquire();
                            parameter.setUsed(parameter.isUsed());
                            listener.notificationOfEvent(profile);
                            wl.release();
                        } else if (plugged == 0 && !state) {
                            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                            wl.acquire();
                            parameter.setUsed(!parameter.isUsed());
                            listener.notificationOfEvent(profile);
                            wl.release();
                        } else {
                            // intent didnt include extra info
                        }
                    }
                }
            }
        }
    }
}
