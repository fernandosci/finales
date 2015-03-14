package gla.es3.com.profiletasks.model.entity.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gla.es3.com.profiletasks.model.entity.Entity;
import gla.es3.com.profiletasks.model.entity.tasks.TaskDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerDescriptor;

/**
 * Created by ito on 14/03/2015.
 */
public class ProfileDescriptor implements Entity, Serializable {

    private final List<TriggerDescriptor> triggerDescriptors;
    private final List<TaskDescriptor> taskDescriptors;
    private UUID id;
    private boolean enabled;
    private String name;

    public ProfileDescriptor(String name) {
        id = UUID.randomUUID();
        this.name = name;
        this.enabled = true;
        this.triggerDescriptors = new ArrayList<>();
        this.taskDescriptors = new ArrayList<>();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addTriggerDescriptor(TriggerDescriptor id) {
        synchronized (triggerDescriptors) {
            triggerDescriptors.add(id);
        }
    }

    public void removeTriggerDescriptor(TriggerDescriptor id) {
        synchronized (triggerDescriptors) {
            triggerDescriptors.remove(id);
        }
    }

    public List<TriggerDescriptor> getTriggerDescriptorsList() {
        ArrayList<TriggerDescriptor> triggerDescriptors1;
        synchronized (triggerDescriptors) {
            triggerDescriptors1 = new ArrayList<>(triggerDescriptors);
        }
        return triggerDescriptors1;
    }

    public void addTaskDescriptor(TaskDescriptor id) {
        synchronized (taskDescriptors) {
            taskDescriptors.add(id);
        }
    }

    public void removeTaskDescriptor(TriggerDescriptor id) {
        synchronized (taskDescriptors) {
            taskDescriptors.remove(id);
        }
    }

    public List<TaskDescriptor> getTTaskDescriptorsList() {
        ArrayList<TaskDescriptor> taskDescriptors1;
        synchronized (taskDescriptors) {
            taskDescriptors1 = new ArrayList<>(taskDescriptors);
        }
        return taskDescriptors1;
    }

    @Override
    public String getID() {
        return id.toString();
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    public void execute(TaskServiceHandler tHandler) {

        synchronized (taskDescriptors) {
            for (TaskDescriptor td : taskDescriptors) {
                td.run(tHandler);
            }
        }
    }
}
