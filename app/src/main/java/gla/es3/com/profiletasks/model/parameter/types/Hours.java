package gla.es3.com.profiletasks.model.parameter.types;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class Hours implements Serializable {

    private Date date;

    public Hours() {
        date = new Date();
    }

    public Hours(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Calendar getCalendar() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    public void setCalendar(Calendar calendar) {
        this.date = calendar.getTime();
    }

}
