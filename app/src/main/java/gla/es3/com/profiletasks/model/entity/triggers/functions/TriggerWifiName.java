package gla.es3.com.profiletasks.model.entity.triggers.functions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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


public class TriggerWifiName extends BaseTrigger {

    private static boolean firsttime = true;

    private LocalTriggerWifi localTriggerWifi;

    public TriggerWifiName(TriggerListener listener, EntityServiceHandler eHandler) {
        super(listener, eHandler);

        localTriggerWifi = new LocalTriggerWifi();
        IntentFilter serviceIntentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        tHandler.getContext().registerReceiver(localTriggerWifi, serviceIntentFilter);
    }

    @Override
    public String getID() {
        return "TRIGGER_STATE_WIFINAME";
    }

    @Override
    public String getDisplayName() {
        return " Wifi Name";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(String.class, "", "Wifi Name", "Wifi Name");
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

    @Override
    public void updatedParameters(ParameterContainer list, String profileId) {
        super.updatedParameters(list, profileId);

    }

    private final class LocalTriggerWifi extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

                if (firsttime) {
                    firsttime = false;
                    return;
                }

                if (info.isConnected()) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String ssid = wifiInfo.getSSID();

                    Set<String> profiles = getProfiles();

                    for (String profile : profiles) {

                        TriggerCallBackInfo callBackFromProfileID = getCallBackFromProfileID(profile);
                        List<Parameter> list = callBackFromProfileID.getList().getList();
                        Parameter parameter = list.get(0);

                        if (parameter.hasValue()) {
                            String name = "\"" + (String) parameter.getValue() + "\"";

                            if (name.equals(ssid)) {

                                if (!parameter.isUsed()) {
                                    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                                    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                                    wl.acquire();
                                    parameter.setUsed(true);
                                    listener.notificationOfEvent(profile);
                                    wl.release();
                                }
                            } else
                                parameter.setUsed(false);
                        }
                    }
                } else {
                    Set<String> profiles = getProfiles();

                    for (String profile : profiles) {
                        TriggerCallBackInfo callBackFromProfileID = getCallBackFromProfileID(profile);
                        List<Parameter> list = callBackFromProfileID.getList().getList();
                        Parameter parameter = list.get(0);
                        if (parameter.hasValue()) {
                            parameter.setUsed(false);
                        }
                    }
                }
            }
        }
    }
}
