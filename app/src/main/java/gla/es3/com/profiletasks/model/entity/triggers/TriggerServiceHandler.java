package gla.es3.com.profiletasks.model.entity.triggers;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;


public interface TriggerServiceHandler extends EntityServiceHandler, TriggerListener {

    public TriggerProvider getTriggerProvider();

}
