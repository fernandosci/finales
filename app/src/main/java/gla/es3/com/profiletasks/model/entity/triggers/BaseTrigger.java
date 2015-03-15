package gla.es3.com.profiletasks.model.entity.triggers;

import java.util.HashMap;
import java.util.Map;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;

/**
 * Created by ito on 14/03/2015.
 */
public abstract class BaseTrigger implements Trigger {

    protected Map<String, TriggerCallBackInfo> callbacks = new HashMap<>();

    protected TriggerListener listener;
    protected EntityServiceHandler tHandler;

    protected BaseTrigger(TriggerListener listener, EntityServiceHandler tHandler) {
        this.listener = listener;
        this.tHandler = tHandler;
    }

    @Override
    public void register(ParameterContainer list, String profileId) {
        callbacks.put(profileId, new TriggerCallBackInfo(tHandler, list, profileId));
    }

    @Override
    public void unregister(String profileId) {
        callbacks.remove(profileId);
    }

}
