package gla.es3.com.profiletasks.model.entity.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gla.es3.com.profiletasks.model.entity.Entity;
import gla.es3.com.profiletasks.model.entity.tasks.TaskDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerDescriptor;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerServiceHandler;

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
        this.triggerDescriptors = new ArrayList<TriggerDescriptor>();
        this.taskDescriptors = new ArrayList<TaskDescriptor>();
    }

    private void registerAll(TriggerServiceHandler sHandler) {
        synchronized (triggerDescriptors) {
            for (TriggerDescriptor td : triggerDescriptors) {
                td.register(sHandler);
            }
        }
    }

    private void unregisterAll(TriggerServiceHandler sHandler) {
        synchronized (triggerDescriptors) {
            for (TriggerDescriptor td : triggerDescriptors) {
                td.unregister(sHandler);
            }
        }
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled, TriggerServiceHandler sHandler) {
        this.enabled = enabled;
        if (enabled) {
            registerAll(sHandler);
        } else {
            unregisterAll(sHandler);
        }
    }

    public void updated(TriggerDescriptor id, TriggerServiceHandler sHandler) {
        synchronized (triggerDescriptors) {
            if (enabled) {
                id.register(sHandler);
            }
        }
    }

    public void addTriggerDescriptor(TriggerDescriptor id, TriggerServiceHandler sHandler) {
        synchronized (triggerDescriptors) {
            triggerDescriptors.add(id);
            if (enabled) {
                id.register(sHandler);
            }
        }
    }

    public void removeTriggerDescriptor(TriggerDescriptor id, TriggerServiceHandler sHandler) {
        synchronized (triggerDescriptors) {
            triggerDescriptors.remove(id);
            id.unregister(sHandler);
        }
    }

    public List<TriggerDescriptor> getTriggerDescriptorsList() {
        ArrayList<TriggerDescriptor> triggerDescriptors1;
        synchronized (triggerDescriptors) {
            triggerDescriptors1 = new ArrayList<TriggerDescriptor>(triggerDescriptors);
        }
        return triggerDescriptors1;
    }

    public void addTaskDescriptor(TaskDescriptor id) {
        synchronized (taskDescriptors) {
            taskDescriptors.add(id);
        }
    }

    public void removeTaskDescriptor(TaskDescriptor id) {
        synchronized (taskDescriptors) {
            taskDescriptors.remove(id);
        }
    }

    public List<TaskDescriptor> getTTaskDescriptorsList() {
        ArrayList<TaskDescriptor> taskDescriptors1;
        synchronized (taskDescriptors) {
            taskDescriptors1 = new ArrayList<TaskDescriptor>(taskDescriptors);
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
        if (!isEnabled())
            return;

        synchronized (taskDescriptors) {
            for (TaskDescriptor td : taskDescriptors) {
                td.run(tHandler);
            }
        }
    }

    public void check(TriggerServiceHandler sHandler) {
        if (!isEnabled())
            return;

        List<TriggerDescriptor> copyTriggers;

        synchronized (triggerDescriptors) {
            copyTriggers = new ArrayList<TriggerDescriptor>(triggerDescriptors);
        }
        for (TriggerDescriptor td : copyTriggers) {
            td.check(sHandler);
        }
    }
}
