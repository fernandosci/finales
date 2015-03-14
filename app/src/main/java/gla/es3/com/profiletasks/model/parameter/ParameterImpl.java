package gla.es3.com.profiletasks.model.parameter;

/**
 * Created by ito on 14/03/2015.
 */
public class ParameterImpl implements Parameter {

    private Class<?> valueClass;
    private boolean used;
    private Object value;
    private Object defaultValue;
    private String text;

    public ParameterImpl(Class<?> valueClass, boolean used, Object value, Object defaultValue, String text) {
        this.valueClass = valueClass;
        this.used = used;
        this.value = value;
        this.defaultValue = defaultValue;
        this.text = text;
    }

    public ParameterImpl(ParameterImpl other) {
        this.valueClass = other.valueClass;
        this.value = other.value;
        this.defaultValue = other.defaultValue;
        this.text = other.text;
        this.used = other.used;
    }

    @Override
    public Class<?> getValueClass() {
        return valueClass;
    }

    public void setValueClass(Class<?> valueClass) {
        this.valueClass = valueClass;
    }

    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Parameter setValue(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Parameter copy() {
        return new ParameterImpl(this);
    }

    @Override
    public boolean hasValue() {
        return value != null;
    }

    @Override
    public boolean hasDefaultValue() {
        return defaultValue != null;
    }
}
