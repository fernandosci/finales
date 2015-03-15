package gla.es3.com.profiletasks.model.entity.triggers;

import java.util.HashMap;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.functions.TriggerWifiName;

/**
 * Created by ito on 14/03/2015.
 */
public class TriggerProvider {

    private HashMap<String, Trigger> triggerMap = null;
    private TriggerListener listener;
    private EntityServiceHandler eHandler;

    public TriggerProvider(TriggerListener listener, EntityServiceHandler eHandler) {
        triggerMap = new HashMap<>();
        this.listener = listener;
        this.eHandler = eHandler;
        initializeTriggers();
    }

    private void initializeTriggers() {
        registerTrigger(new TriggerWifiName(listener, eHandler));
    }

    public void registerTrigger(Trigger t) {
        triggerMap.put(t.getID(), t);
    }

    public Set<String> getTriggerList() {
        return triggerMap.keySet();
    }

    public Trigger getTrigger(String id) {
        return triggerMap.get(id);
    }

}
