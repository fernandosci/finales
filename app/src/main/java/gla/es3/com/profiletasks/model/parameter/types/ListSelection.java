package gla.es3.com.profiletasks.model.parameter.types;

import java.util.List;

/**
 * Created by ito on 15/03/2015.
 */
public interface ListSelection {

    public String[] getDisplayNames();

    public List<Integer> getSelectedIndex();

    public void setSelectedIndexes(List<Integer> selectedIndexes);

    public void setNoSelection();

    public boolean isMultipleSelectionAllowed();
}
