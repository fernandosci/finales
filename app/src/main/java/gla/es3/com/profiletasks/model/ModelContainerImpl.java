package gla.es3.com.profiletasks.model;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskProvider;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerProvider;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerServiceHandler;
import gla.es3.com.profiletasks.service.MainServiceModelHandler;

/**
 * Created by ito on 14/03/2015.
 */
public class ModelContainerImpl implements ModelContainer, Serializable {

    private static final String FILENAME = "model.bin";

    private final List<ProfileDescriptor> profiles;

    protected TaskProvider taskProvider;

    protected TriggerProvider triggerProvider;

    MainServiceModelHandler serviceModelHandler;

    EntitiesServiceHandler entitiesServiceHandler;


    public ModelContainerImpl(MainServiceModelHandler serviceModelHandler) {
        this.serviceModelHandler = serviceModelHandler;

        this.profiles = load();

        initialize();
        updateReferences();
    }

    private List<ProfileDescriptor> load() {
        FileInputStream fis = null;
        List<ProfileDescriptor> list;
        try {
            fis = serviceModelHandler.getContext().openFileInput(FILENAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            list = (List<ProfileDescriptor>) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            list = new ArrayList<>();
        }
        return list;
    }

    private void initialize() {
        entitiesServiceHandler = new EntitiesServiceHandler();
        taskProvider = new TaskProvider();
        triggerProvider = new TriggerProvider(entitiesServiceHandler, entitiesServiceHandler);
    }


    private void updateReferences() {
        synchronized (profiles) {
            for (ProfileDescriptor pf : profiles) {
                pf.setEnabled(pf.isEnabled(), entitiesServiceHandler);
            }
        }
    }

    public void save() {
        FileOutputStream fos = null;
        try {
            fos = serviceModelHandler.getContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(profiles);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkProfiles() {
        List<ProfileDescriptor> profileCopy;

        synchronized (profiles) {
            profileCopy = new ArrayList<>(profiles);
        }

        for (ProfileDescriptor pdesc : profileCopy) {
            pdesc.check(entitiesServiceHandler);
        }
    }


    public void executeProfile(String id) {
        synchronized (profiles) {
            for (ProfileDescriptor pf : profiles) {
                if (pf.getID().equals(id)) {
                    pf.execute(entitiesServiceHandler);
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

    @Override
    public TriggerServiceHandler getTriggerServiceHandler() {
        return entitiesServiceHandler;
    }

    @Override
    public TaskServiceHandler getTaskServiceHandler() {
        return entitiesServiceHandler;
    }

    public void notificationOfEvent(String profileID) {
        ProfileDescriptor pf = null;

        synchronized (profiles) {
            for (ProfileDescriptor pdesc : profiles) {
                if (pdesc.equals(profileID)) {
                    pf = pdesc;
                    break;
                }
            }
        }

        pf.execute(entitiesServiceHandler);
    }

    private class EntitiesServiceHandler implements TriggerServiceHandler, TaskServiceHandler {

        @Override
        public Context getContext() {
            return serviceModelHandler.getContext();
        }

        @Override
        public void notificationOfEvent(String profileID) {
            ModelContainerImpl.this.notificationOfEvent(profileID);
        }

        @Override
        public TaskProvider getTaskProvider() {
            return taskProvider;
        }

        @Override
        public TriggerProvider getTriggerProvider() {
            return triggerProvider;
        }
    }

}
