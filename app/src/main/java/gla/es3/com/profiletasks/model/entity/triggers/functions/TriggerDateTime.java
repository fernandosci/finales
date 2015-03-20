package gla.es3.com.profiletasks.model.entity.triggers.functions;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import gla.es3.com.profiletasks.model.entity.EntityServiceHandler;
import gla.es3.com.profiletasks.model.entity.triggers.BaseTrigger;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerCallBackInfo;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerListener;
import gla.es3.com.profiletasks.model.parameter.Parameter;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.ParameterFactory;
import gla.es3.com.profiletasks.model.parameter.types.DaysOfWeekList;
import gla.es3.com.profiletasks.model.parameter.types.Hours;
import gla.es3.com.profiletasks.model.parameter.types.ListSelection;


public class TriggerDateTime extends BaseTrigger {

    private static final String CUSTOM_ACTION_DATETIMEALARM = "gla.es3.com.profiletasks.model.entity.triggers.function.TriggerDateTime";

    private AlarmManager alarmMgr;
    // private Intent serviceIntent;
    private LocalAlarmHandler alarmHandler;


    public TriggerDateTime(TriggerListener listener, EntityServiceHandler tHandler) {
        super(listener, tHandler);

        alarmHandler = new LocalAlarmHandler();
        IntentFilter serviceIntentFilter = new IntentFilter(CUSTOM_ACTION_DATETIMEALARM);
        tHandler.getContext().registerReceiver(alarmHandler, serviceIntentFilter);

        alarmMgr = (AlarmManager) tHandler.getContext().getSystemService(Context.ALARM_SERVICE);
    }

    private static int parseDayOfWeek(String day)
            throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E");
        Date date = dayFormat.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    @Override
    public String getID() {
        return "TRIGGER_DATE_DATETIME";
    }

    @Override
    public String getDisplayName() {
        return "TIme/Day of Week";
    }

    @Override
    public ParameterContainer getParameters() {

        ParameterFactory f = new ParameterFactory(getID());
        f.addParameter(ListSelection.class, new DaysOfWeekList(), "Day of Week", "Day of Week")
                .addParameter(Hours.class, new Hours(Calendar.getInstance().getTime()), "Time", "Time");
        return f.getContainer();
    }

    @Override
    public String getCustomName(ParameterContainer list) {
        if (list.getId().equals(getID())) {
            return getDisplayName();
        } else
            return "";
    }

    @Override
    public void check(ParameterContainer list, String profileId) {

        //listener.notificationOfEvent(profileId);
    }

    @Override
    public void unregister(String profileId) {
        super.unregister(profileId);
        setNextToCall();
    }

    @Override
    public void updatedParameters(ParameterContainer list, String profileId) {
        super.updatedParameters(list, profileId);

        setNextToCall();
    }

    @Override
    public void register(ParameterContainer list, String profileId) {
        super.register(list, profileId);
        setNextToCall();
    }

    private void setNextToCall() {
        String nextProfile = findNext();

        Intent serviceIntent = new Intent(CUSTOM_ACTION_DATETIMEALARM);
        serviceIntent.putExtra("profileId", nextProfile);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(tHandler.getContext(), 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmIntent == null)
            alarmIntent = PendingIntent.getBroadcast(tHandler.getContext(), 0, serviceIntent, 0);

        if (alarmMgr != null && alarmIntent != null) {
            alarmMgr.cancel(alarmIntent);
        }


        if (!nextProfile.isEmpty()) {

            //get hour parameter
            TriggerCallBackInfo callBackFromProfileID = getCallBackFromProfileID(nextProfile);
            List<Parameter> list = callBackFromProfileID.getList().getList();
            Parameter parameter = list.get(1);
            Hours hours = (Hours) parameter.getValue();

            //get hour value
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTime(hours.getDate());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, tmpCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            String tmp = calendar.getTime().toString();

            //Toast.makeText(tHandler.getContext(), "CONFIGURING ALARM FOR TRIGGER !!!!!!!!!!", Toast.LENGTH_SHORT).show(); // For example


            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    private String findNext() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        Set<String> profiles = getProfiles();
        List<String> targetProf = new ArrayList<>();

        //go for each profile registered
        for (String str : profiles) {
            //get relevant parameter (first one, index 0, dayofweek
            TriggerCallBackInfo callBackFromProfileID = getCallBackFromProfileID(str);

            List<Parameter> list = callBackFromProfileID.getList().getList();
            Parameter parameter = list.get(0);

            DaysOfWeekList dow = (DaysOfWeekList) parameter.getValue();

            //index of selected week days
            Set<Integer> indexesList = dow.getSelectedIndexes();

            //find one of today
            for (Integer iv : indexesList) {
                try {
                    if (!dow.getDisplayName(iv).isEmpty() && currentDayOfWeek == parseDayOfWeek(dow.getDisplayName(iv))) {
                        targetProf.add(str);
                        break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        //from here, have list of profiles
        String result = "";
        int differenceHour = 25;
        int differenceMinute = 61;
        int currentHour = calendar.get(Calendar.DAY_OF_WEEK);
        for (int c = 0; c < targetProf.size(); c++) {

            //get hour parameter
            TriggerCallBackInfo callBackFromProfileID = getCallBackFromProfileID(targetProf.get(c));
            List<Parameter> list = callBackFromProfileID.getList().getList();
            Parameter parameter = list.get(1);
            Hours hours = (Hours) parameter.getValue();

            //get hour value
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTime(hours.getDate());

            if (tmpCalendar.get(Calendar.HOUR_OF_DAY) >= calendar.get(Calendar.HOUR_OF_DAY) &&
                    tmpCalendar.get(Calendar.MINUTE) > calendar.get(calendar.MINUTE) &&
                    tmpCalendar.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY) <= differenceHour) {
                if (tmpCalendar.get(Calendar.MINUTE) > calendar.get(Calendar.MINUTE) &&
                        tmpCalendar.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE) < differenceMinute) {

                    differenceHour = tmpCalendar.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
                    differenceMinute = tmpCalendar.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                    result = targetProf.get(c);
                }
            }
        }

        return result;
    }

    private final class LocalAlarmHandler extends BroadcastReceiver {

        public LocalAlarmHandler() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {


            if (intent.getAction().equals(CUSTOM_ACTION_DATETIMEALARM)) {

                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                wl.acquire();
                //Toast.makeText(context, "HouRTIMEALARM !!!!!!!!!!", Toast.LENGTH_SHORT).show(); // For example

                String profileId = intent.getStringExtra("profileId");
                listener.notificationOfEvent(profileId);
                setNextToCall();

                wl.release();

            }
        }
    }
}
