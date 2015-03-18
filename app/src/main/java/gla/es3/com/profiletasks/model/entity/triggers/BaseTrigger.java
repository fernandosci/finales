package gla.es3.com.profiletasks.model.entity.triggers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;


public abstract class BaseTrigger implements Trigger {

    private final Map<String, TriggerCallBackInfo> callbacks = new HashMap<>();

    protected TriggerListener listener;
    protected EntityServiceHandler tHandler;

    protected BaseTrigger(TriggerListener listener, EntityServiceHandler tHandler) {
        this.listener = listener;
        this.tHandler = tHandler;
    }

    @Override
    public void register(ParameterContainer list, String profileId) {
        synchronized (callbacks) {
            callbacks.put(profileId, new TriggerCallBackInfo(tHandler, list, profileId));
        }
    }

    protected Set<String> getProfiles() {
        Set<String> strings;
        synchronized (callbacks) {

            strings = callbacks.keySet();
        }
        return strings;
    }

    protected TriggerCallBackInfo getCallBackFromProfileID(String id) {
        TriggerCallBackInfo cl = null;
        synchronized (callbacks) {

            cl = callbacks.get(id);
        }
        return cl;
    }

    @Override
    public void unregister(String profileId) {
        synchronized (callbacks) {
            callbacks.remove(profileId);
        }
    }

}
