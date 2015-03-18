package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.provider.Settings;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;


public class TaskAutoRotate implements Task {

    @Override
    public String getID() {
        return "TASK_DISPLAY_AUTOROTATE";
    }

    @Override
    public String getDisplayName() {
        return "Display Auto rotate";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(Boolean.class, new Boolean(true), "Auto Rotate", "Auto Rotate");
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
                setAutoRotate(tHandler, value);
            }


        }
    }

    void setAutoRotate(TaskServiceHandler tHandler, boolean value) {

        Settings.System.putInt(tHandler.getContext().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, value ? 1 : 0);

    }

}
