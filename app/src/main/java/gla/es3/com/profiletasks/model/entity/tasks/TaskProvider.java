package gla.es3.com.profiletasks.model.entity.tasks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskAutoRotate;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskBluetooth;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskBrightness;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskScreenTimeout;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskToast;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskVibrate;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskVolume;
import gla.es3.com.profiletasks.model.entity.tasks.functions.TaskWifi;


public class TaskProvider {

    private LinkedHashMap<String, Task> taskMap = null;


    public TaskProvider() {
        taskMap = new LinkedHashMap<>();
        initializeTasks();
    }

    private void initializeTasks() {

        registerTask(new TaskAutoRotate());
        registerTask(new TaskBluetooth());
        registerTask(new TaskBrightness());
        registerTask(new TaskScreenTimeout());
        registerTask(new TaskToast());
        registerTask(new TaskVibrate());
        registerTask(new TaskVolume());
        registerTask(new TaskWifi());
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
