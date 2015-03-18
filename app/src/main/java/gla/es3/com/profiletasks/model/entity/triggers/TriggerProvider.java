package gla.es3.com.profiletasks.model.entity.triggers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.functions.TriggerCharging;
import gla.es3.com.profiletasks.model.entity.triggers.functions.TriggerDateTime;
import gla.es3.com.profiletasks.model.entity.triggers.functions.TriggerWifiName;


public class TriggerProvider {

    private LinkedHashMap<String, Trigger> triggerMap = null;
    private TriggerListener listener;
    private EntityServiceHandler eHandler;

    public TriggerProvider(TriggerListener listener, EntityServiceHandler eHandler) {
        triggerMap = new LinkedHashMap<>();
        this.listener = listener;
        this.eHandler = eHandler;
        initializeTriggers();
    }

    private void initializeTriggers() {
        registerTrigger(new TriggerWifiName(listener, eHandler));
        registerTrigger(new TriggerDateTime(listener, eHandler));
        registerTrigger(new TriggerCharging(listener, eHandler));
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

    public List<String> getTriggerListByOrder() {
        return new ArrayList<>(triggerMap.keySet());
    }

}
