package gla.es3.com.profiletasks.activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import gla.es3.com.profiletasks.model.parameter.types.Hours;


public class TimePickerManager implements View.OnClickListener {


    private Button btnTimePicker;
    private EditText txtTime;
    private Context context;


    private Hours hours;

    public TimePickerManager(Context context, Button btnTimePicker, EditText txtTime, Hours hours) {
        this.btnTimePicker = btnTimePicker;
        this.txtTime = txtTime;
        this.hours = hours;
        this.context = context;

        Calendar calendar = hours.getCalendar();
        txtTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));

        btnTimePicker.setOnClickListener(this);
    }

    public Hours getHours() {
        return hours;
    }

    @Override
    public void onClick(View v) {


        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        Calendar calendar = hours.getCalendar();
                        calendar.set(Calendar.HOUR, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        txtTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
                        hours.setCalendar(calendar);
                    }
                }, hours.getCalendar().get(Calendar.HOUR_OF_DAY), hours.getCalendar().get(Calendar.MINUTE), false);
        tpd.show();
    }


}