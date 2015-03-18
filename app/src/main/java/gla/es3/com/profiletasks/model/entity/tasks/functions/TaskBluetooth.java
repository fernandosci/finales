package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.bluetooth.BluetoothAdapter;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;


public class TaskBluetooth implements Task {

    @Override
    public String getID() {
        return "TASK_DEVICE_WIFI";
    }

    @Override
    public String getDisplayName() {
        return "Turn Bluetooth on/off";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(Boolean.class, new Boolean(true), "Bluetooth on/off", "Bluetooth on/off");
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

                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                boolean isEnabled = bluetoothAdapter.isEnabled();
                if (value && !isEnabled) {
                    bluetoothAdapter.enable();
                } else if (!value && isEnabled) {
                    bluetoothAdapter.disable();
                }
            }
        }
    }

}
