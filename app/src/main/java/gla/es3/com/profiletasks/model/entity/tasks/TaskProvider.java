package gla.es3.com.profiletasks.model.entity.tasks;

import java.util.HashMap;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskBrightness;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskToast;

/**
 * Created by ito on 14/03/2015.
 */
public class TaskProvider {

    private HashMap<String, Task> taskMap = null;


    public TaskProvider() {
        taskMap = new HashMap<>();
        initializeTasks();
    }

    private void initializeTasks() {
        registerTask(new TaskBrightness());
        registerTask(new TaskToast());
    }

    public void registerTask(Task t) {
        taskMap.put(t.getID(), t);
    }

    public Set<String> getTaskList() {
        return taskMap.keySet();
    }


    public Task getTask(String id) {
        return taskMap.get(id);
    }

}
