package gla.es3.com.profiletasks.model.parameter;

import java.util.List;

/**
 * Created by ito on 14/03/2015.
 */
public class ParameterFactory {

    ParameterContainer container;
    List<Parameter> list;

    public ParameterFactory(String id) {
        container = new ParameterContainer(id);
        list = container.getList();
    }

    public ParameterFactory addParameter(Class<?> valueClass, Object value, Object defaultValue, String text) {
        list.add(new ParameterImpl(valueClass, false, value, defaultValue, text));
        return this;
    }

    public ParameterFactory addParameter(Class<?> valueClass, Object value, String text) {
        list.add(new ParameterImpl(valueClass, false, value, value, text));
        return this;
    }

    public ParameterFactory setLastValueClass(Class<?> valueClass) {

        if (list.size() > 0)
            ((ParameterImpl) this.list.get(list.size() - 1)).setValueClass(valueClass);

        return this;
    }

    public ParameterFactory setLastValue(Object value) {

        if (list.size() > 0)
            ((ParameterImpl) this.list.get(list.size() - 1)).setValue(value);

        return this;
    }

    public ParameterFactory setLastDefaultValue(Object defaultValue) {

        if (list.size() > 0)
            ((ParameterImpl) this.list.get(list.size() - 1)).setDefaultValue(defaultValue);

        return this;
    }

    public ParameterFactory setLastText(String text) {

        if (list.size() > 0)
            ((ParameterImpl) this.list.get(list.size() - 1)).setText(text);

        return this;
    }


    public ParameterContainer getContainer() {
        return container;
    }


}
