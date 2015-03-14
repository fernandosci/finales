package gla.es3.com.profiletasks.model.entity.tasks;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by ito on 14/03/2015.
 */
public class TaskProvider {

    private static HashMap<String, Task> taskMap = null;
    private static boolean initialized = false;

    public static void registerTask(Task t) {
        if (!initialized)
            initializeTasks();

        taskMap.put(t.getID(), t);
    }


    public static void initializeTasks() {
        if (!initialized)
            taskMap = new HashMap<>();
        initialized = true;
    }

    public static Set<String> getTaskList() {
        return taskMap.keySet();
    }


    public static Task getTask(String id) {
        return taskMap.get(id);
    }

}
