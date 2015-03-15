package gla.es3.com.profiletasks.model.entity.triggers;

import java.io.Serializable;

import gla.es3.com.profiletasks.model.parameter.ParameterContainer;

/**
 * Created by ito on 14/03/2015.
 */
public class TriggerDescriptor implements Serializable {

    private String profileID;
    private String id;
    private ParameterContainer container;

    public TriggerDescriptor(String profileID, String triggerID, TriggerServiceHandler tHandler) {
        this.profileID = profileID;
        this.id = triggerID;
        this.container = tHandler.getTriggerProvider().getTrigger(id).getParameters();
    }

    public String getID(TriggerServiceHandler tHandler) {
        return id;
    }

    public String getDisplayName(TriggerServiceHandler tHandler) {
        return tHandler.getTriggerProvider().getTrigger(id).getDisplayName();
    }

    public ParameterContainer getParameters() {
        return container;
    }

    public String getCustomName(TriggerServiceHandler tHandler) {
        return tHandler.getTriggerProvider().getTrigger(id).getCustomName(container);
    }

    public void check(TriggerServiceHandler tHandler) {
        tHandler.getTriggerProvider().getTrigger(id).check(container, profileID);
    }

    public void register(TriggerServiceHandler tHandler) {
        tHandler.getTriggerProvider().getTrigger(id).register(container, profileID);
    }

    public void unregister(TriggerServiceHandler tHandler) {
        tHandler.getTriggerProvider().getTrigger(id).unregister(profileID);
    }
}
