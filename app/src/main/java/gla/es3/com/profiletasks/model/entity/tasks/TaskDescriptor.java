package gla.es3.com.profiletasks.model.entity.tasks;

import java.io.Serializable;

import gla.es3.com.profiletasks.model.parameter.ParameterContainer;

/**
 * Created by ito on 14/03/2015.
 */
public class TaskDescriptor implements Serializable {

    String id;
    ParameterContainer container;

    public TaskDescriptor(String taskID, TaskServiceHandler tHandler) {
        this.id = taskID;
        this.container = tHandler.getTaskProvider().getTask(id).getParameters();
    }


    public String getID() {
        return id;
    }


    public String getDisplayName(TaskServiceHandler tHandler) {
        return tHandler.getTaskProvider().getTask(id).getDisplayName();
    }


    public ParameterContainer getParameters() {
        return container;
    }


    public String getCustomName(TaskServiceHandler tHandler) {
        return tHandler.getTaskProvider().getTask(id).getCustomName(container);
    }


    public void run(TaskServiceHandler tHandler) {
        tHandler.getTaskProvider().getTask(id).run(tHandler, container);
    }
}
