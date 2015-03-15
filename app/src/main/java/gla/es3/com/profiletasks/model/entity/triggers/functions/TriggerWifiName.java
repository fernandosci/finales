package gla.es3.com.profiletasks.model.entity.triggers.functions;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.BaseTrigger;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerListener;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;

/**
 * Created by ito on 14/03/2015.
 */
public class TriggerWifiName extends BaseTrigger {

    public TriggerWifiName(TriggerListener listener, EntityServiceHandler eHandler) {
        super(listener, eHandler);
    }

    @Override
    public String getID() {
        return "TRIGGER_STATE_WIFINAME";
    }

    @Override
    public String getDisplayName() {
        return " Wifi Name";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(String.class, "", "Wifi Name");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        if (list.getId().equals(getID())) {
            return getDisplayName() + "(" + (String) list.getList().get(0).getValue() + ")";
        } else
            return "";

    }

    @Override
    public void check(ParameterContainer list, String profileId) {

        //listener.notificationOfEvent(profileId);
    }
}
