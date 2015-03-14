package gla.es3.com.profiletasks.model.entity.tasks;

import java.io.Serializable;

import gla.es3.com.profiletasks.model.parameter.ParameterContainer;

/**
 * Created by ito on 14/03/2015.
 */
public class TaskDescriptor implements Serializable {

    String id;
    ParameterContainer container;

    public TaskDescriptor(String taskID) {
        this.id = taskID;
        this.container = TaskProvider.getTask(id).getParameters();
    }


    public String getID() {
        return TaskProvider.getTask(id).getID();
    }


    public String getDisplayName() {
        return TaskProvider.getTask(id).getDisplayName();
    }


    public ParameterContainer getParameters() {
        return container;
    }


    public String getCustomName() {
        return TaskProvider.getTask(id).getCustomName(container);
    }


    public void run(TaskServiceHandler tHandler) {
        TaskProvider.getTask(id).run(tHandler, container);
    }
}
