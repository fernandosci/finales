package gla.es3.com.profiletasks.service.Impl.client;

import android.content.Context;
import android.widget.Toast;

import gla.es3.com.profiletasks.service.MainServiceHandler;
import gla.es3.com.profiletasks.service.ModelContainer;
import gla.es3.com.profiletasks.service.Impl.mainalarm.MainAlarmController;

/**
 * Created by ito on 14/03/2015.
 */
public class MainServiceClientHandlerImpl implements MainServiceHandler{

    Context context;

    ModelContainer model;

    public MainServiceClientHandlerImpl(Context context, ModelContainer model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public void serviceInteract() {

        Toast.makeText(context, "serviceInteract", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startAlarm() {
        MainAlarmController.startMainAlarm(context, false);
    }
}
