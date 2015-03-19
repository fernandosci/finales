package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.provider.Settings;

import java.util.Set;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;
import gla.es3.com.profiletasks.model.parameter.types.ListSelection;
import gla.es3.com.profiletasks.model.parameter.types.TimeoutOptionsList;


public class TaskScreenTimeout implements Task {

    @Override
    public String getID() {
        return "TASK_DISPLAY_SCREENTIMEOUT";
    }

    @Override
    public String getDisplayName() {
        return "Screen timeout";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(ListSelection.class, new TimeoutOptionsList(), "Screen time out", "Screen time out");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        return getDisplayName();
    }

    @Override
    public void run(TaskServiceHandler tHandler, ParameterContainer list) {
        if (list.getId().equals(getID())) {

            if (list.getList().get(0).hasValue()) {

                ListSelection value = (ListSelection) list.getList().get(0).getValue();
                setTimeout(tHandler, value);
            }


        }
    }

    void setTimeout(TaskServiceHandler tHandler, ListSelection value) {
        int time;
        Set<Integer> selectedIndexes = value.getSelectedIndexes();
        if (selectedIndexes.size() > 0) {
            switch (selectedIndexes.toArray(new Integer[0])[0]) {
                case 0:
                    time = 15000;
                    break;
                case 1:
                    time = 30000;
                    break;
                case 2:
                    time = 60000;
                    break;
                case 3:
                    time = 120000;
                    break;
                case 4:
                    time = 300000;
                    break;
                case 5:
                    time = 600000;
                    break;
                case 6:
                    time = 1800000;
                    break;
                default:
                    time = -1;
            }
            android.provider.Settings.System.putInt(tHandler.getContext().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT, time);
        }
    }
}



