package gla.es3.com.profiletasks.model.entity.triggers;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;


public class TriggerCallBackInfo {

    private EntityServiceHandler eHandler;
    private ParameterContainer list;
    private String profileId;


    public TriggerCallBackInfo(EntityServiceHandler eHandler, ParameterContainer list, String profileId) {
        this.eHandler = eHandler;
        this.list = list;
        this.profileId = profileId;
    }

    public EntityServiceHandler geteHandler() {
        return eHandler;
    }

    public void seteHandler(EntityServiceHandler eHandler) {
        this.eHandler = eHandler;
    }

    public ParameterContainer getList() {
        return list;
    }

    public void setList(ParameterContainer list) {
        this.list = list;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
