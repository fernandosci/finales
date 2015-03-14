package gla.es3.com.profiletasks.model.entity.profile;

import java.io.Serializable;
import java.util.List;

import gla.es3.com.profiletasks.model.entity.Entity;
import gla.es3.com.profiletasks.model.entity.tasks.TaskDescriptor;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerDescriptor;

/**
 * Created by ito on 14/03/2015.
 */
public class ProfileDescriptor implements Entity, Serializable {

    private boolean enabled;
    private String name;
    private List<TriggerDescriptor> triggerDescriptors;
    private List<TaskDescriptor> taskDescriptors;

    public ProfileDescriptor() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<TriggerDescriptor> getTriggerDescriptors() {
        return triggerDescriptors;
    }

    public List<TaskDescriptor> getTaskDescriptors() {
        return taskDescriptors;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }
}
