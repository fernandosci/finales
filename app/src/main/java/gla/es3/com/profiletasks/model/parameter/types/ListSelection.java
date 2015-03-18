package gla.es3.com.profiletasks.model.parameter.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;


public interface ListSelection extends Serializable {

    public String[] getDisplayNames();

    public Set<Integer> getSelectedIndexes();

    public void setSelectedIndexes(Set<Integer> selectedIndexes);

    public String getDisplayName(int i);

    public void addSelectedIndex(Set<Integer> selectedIndexes);

    public void addSelectedIndex(Integer selectedIndex);

    public void setNoSelection();

    public boolean isMultipleSelectionAllowed();

    public ArrayList<InfoRowdata> getInfoRowData();

    public void setSelected(ArrayList<InfoRowdata> infoRowdatas);
}
