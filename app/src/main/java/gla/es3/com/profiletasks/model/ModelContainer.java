package gla.es3.com.profiletasks.model;

import java.util.List;

import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;

/**
 * Created by ito on 14/03/2015.
 */
public interface ModelContainer {

    public List<ProfileDescriptor> getList();

    public void addProfile(ProfileDescriptor pf);

    public void removeProfile(ProfileDescriptor pf);

    public ProfileDescriptor getProfile(String id);

}
