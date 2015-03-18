package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.net.wifi.WifiManager;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;


public class TaskWifi implements Task {

    @Override
    public String getID() {
        return "TASK_DEVICE_WIFI";
    }

    @Override
    public String getDisplayName() {
        return "Turn Wifi on/off";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(Boolean.class, new Boolean(true), "Wifi on/off", "Wifi on/off");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        return getDisplayName();
    }

    @Override
    public void run(TaskServiceHandler tHandler, ParameterContainer list) {
        if (list.getId() == getID()) {

            if (list.getList().get(0).hasValue()) {

                Boolean value = (Boolean) list.getList().get(0).getValue();

                WifiManager wifiManager = (WifiManager) tHandler.getContext().getSystemService(tHandler.getContext().WIFI_SERVICE);
                wifiManager.setWifiEnabled(value);
            }
        }
    }

}
