package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.os.Vibrator;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;
import gla.es3.com.profiletasks.model.parameter.types.RangeIntType;


public class TaskVibrate implements Task {

    @Override
    public String getID() {
        return "TASK_VIBRATE";
    }

    @Override
    public String getDisplayName() {
        return "Vibrate";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(Boolean.class, new Boolean(true), "Vibrate", "Vibrate").
                addParameter(RangeIntType.class, new RangeIntType(0, 1000, 500), "Vibrate duration(ms)", "Vibrate duration(ms)");
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

                if (value && list.getList().get(1).hasValue()) {

                    RangeIntType range = (RangeIntType) list.getList().get(1).getValue();

                    Vibrator vib = (Vibrator) tHandler.getContext().getSystemService(tHandler.getContext().VIBRATOR_SERVICE);
                    vib.vibrate(range.getValue());
                }
            }
        }
    }

}
