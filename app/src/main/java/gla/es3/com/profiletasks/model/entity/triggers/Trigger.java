package gla.es3.com.profiletasks.model.entity.triggers;

import gla.es3.com.profiletasks.model.entity.Entity;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;


public interface Trigger extends Entity {

    public ParameterContainer getParameters();

    public String getCustomName(ParameterContainer list);

    public void check(ParameterContainer list, String profileId);

    public void updatedParameters(ParameterContainer list, String profileId);

    public void register(ParameterContainer list, String profileId);

    public void unregister(String profileId);
}

