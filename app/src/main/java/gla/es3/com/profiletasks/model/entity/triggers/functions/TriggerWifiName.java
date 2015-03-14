package gla.es3.com.profiletasks.model.entity.triggers.functions;

import gla.es3.com.profiletasks.model.ModelContainer;
import gla.es3.com.profiletasks.model.entity.triggers.Trigger;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerProvider;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;

/**
 * Created by ito on 14/03/2015.
 */
public class TriggerWifiName implements Trigger {

    static {
        TriggerProvider.registerTrigger(new TriggerWifiName());
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
    public void check(TriggerServiceHandler tHandler, ParameterContainer list) {

    }

    @Override
    public void register(ModelContainer modelcontainer, TriggerServiceHandler tHandler, ParameterContainer list) {

    }

    @Override
    public String getID() {
        return null;
    }


}
