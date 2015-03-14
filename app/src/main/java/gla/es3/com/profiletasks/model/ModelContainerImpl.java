package gla.es3.com.profiletasks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;

/**
 * Created by ito on 14/03/2015.
 */
public class ModelContainerImpl implements ModelContainer, Serializable {

    private final List<ProfileDescriptor> profiles;

    public ModelContainerImpl() {
        this.profiles = new ArrayList<>();
    }

    public void updateReferences() {
        //TODO
    }


    public void executeProfile(String id, TaskServiceHandler tHandler) {

        synchronized (profiles) {
            for (ProfileDescriptor pf : profiles) {

                if (pf.getID().equals(id) && pf.isEnabled()) {
                    pf.execute(tHandler);
                }

            }
        }
    }


    @Override
    public List<ProfileDescriptor> getList() {
        ArrayList<ProfileDescriptor> profileDescriptors;
        synchronized (profiles) {
            profileDescriptors = new ArrayList<>(profiles);
        }

        return profileDescriptors;
    }

    @Override
    public void addProfile(ProfileDescriptor pf) {
        synchronized (profiles) {
            profiles.add(pf);
        }
    }

    @Override
    public void removeProfile(ProfileDescriptor pf) {
        synchronized (profiles) {
            profiles.remove(pf);
        }
    }

    @Override
    public ProfileDescriptor getProfile(String id) {
        ProfileDescriptor pf = null;
        synchronized (profiles) {
            for (ProfileDescriptor pdesc : profiles) {
                if (pdesc.equals(id)) {
                    pf = pdesc;
                    break;
                }
            }
        }
        return pf;
    }
}
