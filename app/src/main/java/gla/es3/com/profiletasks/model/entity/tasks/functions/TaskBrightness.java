package gla.es3.com.profiletasks.model.entity.tasks.functions;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskProvider;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;
import gla.es3.com.profiletasks.model.parameter.types.RangeIntType;

/**
 * Created by ito on 14/03/2015.
 */
public class TaskBrightness implements Task {

    static {
        TaskProvider.registerTask(new TaskBrightness());
    }

    @Override
    public String getID() {
        return "TASK_DISPLAY_BRIGHTNESS1";
    }

    @Override
    public String getDisplayName() {
        return "Brightness";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(Boolean.TYPE, new Boolean(true), "Auto Brightness").
                addParameter(RangeIntType.class, new RangeIntType(0, 255, 127), "Brightness Value");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        return null;
    }

    @Override
    public void run(TaskServiceHandler tHandler, ParameterContainer list) {

    }


}
