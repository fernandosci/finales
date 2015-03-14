package gla.es3.com.profiletasks.model.entity.triggers;

import gla.es3.com.profiletasks.model.ModelContainer;
import gla.es3.com.profiletasks.model.entity.Entity;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;

/**
 * Created by ito on 14/03/2015.
 */
public interface Trigger extends Entity {

    public ParameterContainer getParameters();

    public String getCustomName(ParameterContainer list);

    public void check(TriggerServiceHandler tHandler, ParameterContainer list);

    public void register(ModelContainer modelcontainer, TriggerServiceHandler tHandler, ParameterContainer list);
}

