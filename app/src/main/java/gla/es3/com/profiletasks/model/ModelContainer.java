package gla.es3.com.profiletasks.model;

import java.util.List;

import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerServiceHandler;


public interface ModelContainer {

    public List<ProfileDescriptor> getList();

    public void addProfile(ProfileDescriptor pf);

    public void removeProfile(ProfileDescriptor pf);

    public ProfileDescriptor getProfile(String id);

    public TriggerServiceHandler getTriggerServiceHandler();

    public TaskServiceHandler getTaskServiceHandler();

    public void save();
}
