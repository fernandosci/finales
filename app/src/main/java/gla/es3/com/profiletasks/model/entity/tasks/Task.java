package gla.es3.com.profiletasks.model.entity.tasks;


import gla.es3.com.profiletasks.model.entity.Entity;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;

/**
 * Created by ito on 14/03/2015.
 */
public interface Task extends Entity {

    public ParameterContainer getParameters();

    public String getCustomName(ParameterContainer list);

    public void run(TaskServiceHandler tHandler, ParameterContainer list);

}
