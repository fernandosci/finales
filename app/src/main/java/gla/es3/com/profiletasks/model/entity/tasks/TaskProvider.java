package gla.es3.com.profiletasks.model.entity.tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskBrightness;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskToast;


public class TaskProvider {

    private LinkedHashMap<String, Task> taskMap = null;


    public TaskProvider() {
        taskMap = new LinkedHashMap<>();
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

    public List<String> getTaskListByOrder() {
        return new ArrayList<>(taskMap.keySet());
    }

    public Task getTask(String id) {
        return taskMap.get(id);
    }

}
