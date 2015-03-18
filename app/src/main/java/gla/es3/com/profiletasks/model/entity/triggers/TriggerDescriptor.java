package gla.es3.com.profiletasks.model.entity.triggers;

import java.io.Serializable;

import gla.es3.com.profiletasks.model.parameter.ParameterContainer;


public class TriggerDescriptor implements Serializable {

    private String profileID;
    private String id;
    private ParameterContainer container;

    public TriggerDescriptor(String profileID, String triggerID, TriggerServiceHandler tHandler) {
        this.profileID = profileID;
        this.id = triggerID;
        this.container = tHandler.getTriggerProvider().getTrigger(id).getParameters();
    }

    public TriggerDescriptor(String profileID, String triggerID, TriggerServiceHandler tHandler, ParameterContainer container) {
        this.profileID = profileID;
        this.id = triggerID;
        this.container = container;
    }

    public String getID(TriggerServiceHandler tHandler) {
        return id;
    }

    public String getDisplayName(TriggerServiceHandler tHandler) {
        return tHandler.getTriggerProvider().getTrigger(id).getDisplayName();
    }

    public synchronized ParameterContainer getParameters() {
        return container;
    }

    public String getCustomName(TriggerServiceHandler tHandler) {
        return tHandler.getTriggerProvider().getTrigger(id).getCustomName(getParameters());
    }

    public void check(TriggerServiceHandler tHandler) {
        tHandler.getTriggerProvider().getTrigger(id).check(getParameters(), profileID);
    }

    public void parameterUpdated(TriggerServiceHandler tHandler) {

    }

    public void register(TriggerServiceHandler tHandler) {
        tHandler.getTriggerProvider().getTrigger(id).register(getParameters(), profileID);
    }

    public void unregister(TriggerServiceHandler tHandler) {
        tHandler.getTriggerProvider().getTrigger(id).unregister(profileID);
    }

    public synchronized void setParameterContainer(ParameterContainer container) {
        this.container = container;
    }
}
