package gla.es3.com.profiletasks.model.parameter.types;

import java.io.Serializable;

/**
 * Created by ito on 17/03/2015.
 */
public class InfoRowdata implements Serializable {
    public boolean isclicked = false;
    public int index;
    public boolean single;


    public InfoRowdata(boolean isclicked, int index, boolean single) {
        this.index = index;
        this.isclicked = isclicked;
        this.single = single;
    }
}
