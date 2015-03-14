package gla.es3.com.profiletasks.model.entity.triggers;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by ito on 14/03/2015.
 */
public class TriggerProvider {

    private static HashMap<String, Trigger> triggerMap = null;
    private static boolean initialized = false;

    public static void registerTrigger(Trigger t) {
        if (!initialized)
            initializeTasks();

        triggerMap.put(t.getID(), t);
    }


    public static void initializeTasks() {
        if (!initialized)
            triggerMap = new HashMap<>();
        initialized = true;
    }

    public static Set<String> getTaskList() {
        return triggerMap.keySet();
    }


    public static Trigger getTask(String id) {
        return triggerMap.get(id);
    }

}
