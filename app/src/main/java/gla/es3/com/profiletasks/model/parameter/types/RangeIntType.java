package gla.es3.com.profiletasks.model.parameter.types;

import java.io.Serializable;


public class RangeIntType implements Serializable {
    private int min;
    private int max;
    private int value;

    public RangeIntType(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public RangeIntType(int min, int max, int value) {
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
