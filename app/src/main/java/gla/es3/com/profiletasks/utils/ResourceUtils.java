package gla.es3.com.profiletasks.utils;

import android.content.Context;
import android.util.TypedValue;


public class ResourceUtils {

    public static float getMSFromResouce(Context context, int resourceID) {
        //in xml @dimen/my_float_value

        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(resourceID, typedValue, true);
        return typedValue.getFloat() * 60 * 1000;
    }
}
