package gla.es3.com.profiletasks.service.impl;

import android.util.Log;

import java.util.Calendar;

import gla.es3.com.profiletasks.model.ModelContainer;
import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskDescriptor;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerDescriptor;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.types.DaysOfWeekList;
import gla.es3.com.profiletasks.model.parameter.types.Hours;
import gla.es3.com.profiletasks.service.MainServiceClientHandler;


public class MainServiceClientHandlerImpl implements MainServiceClientHandler {

    private static final String TAG = "MSClientHandlerImpl";
    MainService service;

    public MainServiceClientHandlerImpl(MainService service) {
        this.service = service;
    }

    @Override
    public void serviceInteract() {

        Log.v(TAG, "serviceInteract");


        ProfileDescriptor profile = new ProfileDescriptor("profileName");

        TaskDescriptor taskd = new TaskDescriptor(("TASK_MESSAGE_TOAST"), service.model.getTaskServiceHandler());

        ParameterContainer parameters = taskd.getParameters();
        parameters.getList().get(0).setValue("Hello World!");

        profile.addTaskDescriptor(taskd);


        TriggerDescriptor trig = new TriggerDescriptor(profile.getID(), "TRIGGER_DATE_DATETIME", service.model.getTriggerServiceHandler());
//                .addParameter(Hours.class, new Hours(Calendar.getInstance().getTime()), "Time");
        parameters = trig.getParameters();
        DaysOfWeekList dow = new DaysOfWeekList();
        dow.addSelectedIndex(0);
        dow.addSelectedIndex(1);
        dow.addSelectedIndex(2);

        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.add(Calendar.MINUTE, 1);

        parameters.getList().get(0).setValue(dow);
        parameters.getList().get(1).setValue(new Hours(instance.getTime()));

        profile.addTriggerDescriptor(trig, service.model.getTriggerServiceHandler());

        service.model.addProfile(profile);
    }

    @Override
    public ModelContainer getModel() {
        return service.model;
    }


}
