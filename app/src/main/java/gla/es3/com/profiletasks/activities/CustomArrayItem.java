package gla.es3.com.profiletasks.activities;

import gla.es3.com.profiletasks.model.ModelContainer;
import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskDescriptor;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerDescriptor;


public class CustomArrayItem {
    private Object object;
    private ModelContainer model;

    public CustomArrayItem(Object object, ModelContainer model) {
        this.object = object;
        this.model = model;
    }


    @Override
    public String toString() {
        if (object instanceof ProfileDescriptor) {
            return (((ProfileDescriptor) object).getDisplayName());
        } else if (object instanceof TaskDescriptor) {
            return (((TaskDescriptor) object).getDisplayName(model.getTaskServiceHandler()));
        } else if (object instanceof TriggerDescriptor) {
            return (((TriggerDescriptor) object).getDisplayName(model.getTriggerServiceHandler()));
        } else
            return object.toString();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ModelContainer getModel() {
        return model;
    }

    public void setModel(ModelContainer model) {
        this.model = model;
    }
}
