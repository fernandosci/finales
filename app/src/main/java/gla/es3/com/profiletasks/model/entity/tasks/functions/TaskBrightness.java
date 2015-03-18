package gla.es3.com.profiletasks.model.entity.tasks.functions;

import android.provider.Settings;

import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskServiceHandler;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;
import gla.es3.com.profiletasks.model.parameter.types.RangeIntType;


public class TaskBrightness implements Task {

    @Override
    public String getID() {
        return "TASK_DISPLAY_BRIGHTNESS1";
    }

    @Override
    public String getDisplayName() {
        return "Brightness";
    }

    @Override
    public ParameterContainer getParameters() {
        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(Boolean.class, new Boolean(true), "Auto Brightness", "Auto Brightness").
                addParameter(RangeIntType.class, new RangeIntType(0, 255, 127), "Brightness Value", "Brightness Value");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        return getDisplayName();
    }

    @Override
    public void run(TaskServiceHandler tHandler, ParameterContainer list) {
        if (list.getId() == getID()) {

            if (list.getList().get(0).hasValue()) {

                Boolean value = (Boolean) list.getList().get(0).getValue();
                setAutoBrightness(tHandler, value);
            }

            if (list.getList().get(1).hasValue()) {

                RangeIntType range = (RangeIntType) list.getList().get(1).getValue();
                android.provider.Settings.System.putInt(tHandler.getContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS,
                        range.getValue());
            }
        }
    }

    void setAutoBrightness(TaskServiceHandler tHandler, boolean value) {
        if (value) {
            Settings.System.putInt(tHandler.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        } else {
            Settings.System.putInt(tHandler.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }

//        // After brightness change we need to "refresh" current app brightness
//        if (value) {
//            refreshBrightness(tHandler,-1);
//        } else {
//            refreshBrightness(tHandler,getBrightnessLevel());
//        }
    }

//    private void refreshBrightness(TaskServiceHandler tHandler, float brightness) {
//         WindowManager.LayoutParams lp = getWindow().getAttributes();
//        if (brightness < 0) {
//            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
//        } else {
//            lp.screenBrightness = brightness;
//        }
//        getWindow().setAttributes(lp);
//    }
//
//    int getBrightnessLevel() {
//        try {
//            int value = Settings.System.getInt(getContentResolver(), SCREEN_BRIGHTNESS);
//            // convert brightness level to range 0..1
//            value = value / 255;
//            return value;
//        } catch (SettingNotFoundException e) {
//            return 0;
//        }
//    }


}
