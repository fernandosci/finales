package gla.es3.com.profiletasks.model.entity.tasks;

import java.io.Serializable;

import gla.es3.com.profiletasks.model.parameter.ParameterContainer;


public class TaskDescriptor implements Serializable {

    private String id;
    private ParameterContainer container;

    public TaskDescriptor(String taskID, TaskServiceHandler tHandler, ParameterContainer container) {
        this.id = taskID;
        this.container = container;
    }

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


    public synchronized ParameterContainer getParameters() {
        return new ParameterContainer(container);
    }


    public String getCustomName(TaskServiceHandler tHandler) {
        return tHandler.getTaskProvider().getTask(id).getCustomName(getParameters());
    }


    public void run(TaskServiceHandler tHandler) {
        tHandler.getTaskProvider().getTask(id).run(tHandler, getParameters());
    }

    public synchronized void setParameterContainer(ParameterContainer container) {
        this.container = container;
    }

}
