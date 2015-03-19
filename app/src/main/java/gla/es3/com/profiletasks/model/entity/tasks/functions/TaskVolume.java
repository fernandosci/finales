package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.media.AudioManager;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;
import gla.es3.com.profiletasks.model.parameter.types.RangeIntType;


public class TaskVolume implements Task {

    @Override
    public String getID() {
        return "TASK_VOLUME";
    }

    @Override
    public String getDisplayName() {
        return "Volume";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(RangeIntType.class, new RangeIntType(0, 15, 7), "Ringtone", "Ringtone")
                .addParameter(RangeIntType.class, new RangeIntType(0, 15, 7), "Notification", "Notification")
                .addParameter(RangeIntType.class, new RangeIntType(0, 15, 7), "Alarm", "Alarm")
                .addParameter(RangeIntType.class, new RangeIntType(0, 15, 7), "Media", "Media")
                .addParameter(RangeIntType.class, new RangeIntType(0, 15, 7), "System", "System");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        return getDisplayName();
    }

    @Override
    public void run(TaskServiceHandler tHandler, ParameterContainer list) {
        if (list.getId().equals(getID())) {

            int n;
            AudioManager mgr = null;


            mgr = (AudioManager) tHandler.getContext().getSystemService(tHandler.getContext().AUDIO_SERVICE);

            n = 0;
            if (list.getList().get(n).hasValue()) {
                RangeIntType range = (RangeIntType) list.getList().get(n).getValue();
                mgr.setStreamVolume(AudioManager.STREAM_RING, range.getValue(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            }
            n = 1;
            if (list.getList().get(n).hasValue()) {
                RangeIntType range = (RangeIntType) list.getList().get(n).getValue();
                mgr.setStreamVolume(AudioManager.STREAM_NOTIFICATION, range.getValue(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            }
            n = 2;
            if (list.getList().get(n).hasValue()) {
                RangeIntType range = (RangeIntType) list.getList().get(n).getValue();
                mgr.setStreamVolume(AudioManager.STREAM_ALARM, range.getValue(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            }
            n = 3;
            if (list.getList().get(n).hasValue()) {
                RangeIntType range = (RangeIntType) list.getList().get(n).getValue();
                mgr.setStreamVolume(AudioManager.STREAM_MUSIC, range.getValue(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            }
            n = 4;
            if (list.getList().get(n).hasValue()) {
                RangeIntType range = (RangeIntType) list.getList().get(n).getValue();
                mgr.setStreamVolume(AudioManager.STREAM_SYSTEM, range.getValue(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            }
        }
    }

}

