package gla.es3.com.profiletasks.model.parameter.types;

import java.io.Serializable;
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
}
