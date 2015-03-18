package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.widget.Toast;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;


public class TaskToast implements Task {

    @Override
    public String getID() {
        return "TASK_MESSAGE_TOAST";
    }

    @Override
    public String getDisplayName() {
        return "Toast";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(String.class, "Hello World!", "Toast", "Toast Message");

        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        return getDisplayName();
    }

    @Override
    public void run(TaskServiceHandler tHandler, ParameterContainer list) {
        if (list.getId().equals(getID())) {

            if (list.getList().get(0).hasValue()) {

                String value = (String) list.getList().get(0).getValue();
                Toast.makeText(tHandler.getContext(), value, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
