package gla.es3.com.profiletasks.model.parameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ito on 14/03/2015.
 */
public class ParameterContainer implements Serializable {

    private String id;
    private List<Parameter> list;

    public ParameterContainer(String id) {
        this.id = id;
        list = new ArrayList<>();
    }

    public ParameterContainer(ParameterContainer other) {
        this.id = other.id;
        this.list = other.list;
    }


    public String getId() {
        return id;
    }

    public List<Parameter> getList() {
        return list;
    }
}
