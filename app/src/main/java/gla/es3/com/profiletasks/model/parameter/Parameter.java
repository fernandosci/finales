package gla.es3.com.profiletasks.model.parameter;

import java.io.Serializable;


public interface Parameter extends Serializable {

    public Parameter copy();

    public String getText();

    public Object getDefaultValue();

    public Object getValue();

    public Parameter setValue(Object value);

    public Class<?> getValueClass();

    public boolean hasValue();

    public boolean hasDefaultValue();

    public boolean isUsed();

    public void setUsed(boolean used);

    public String getTitle();

    public void setTitle(String title);
}
