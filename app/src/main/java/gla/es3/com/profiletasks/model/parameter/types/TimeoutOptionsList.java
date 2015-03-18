package gla.es3.com.profiletasks.model.parameter.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class TimeoutOptionsList implements ListSelection {

    private Set<Integer> selectedIndexList;
    private String[] list;

    public TimeoutOptionsList() {
        selectedIndexList = new HashSet<>();

        list = new String[8];
        list[0] = "15 seconds";
        list[1] = "30 seconds";
        list[2] = "1 minute";
        list[3] = "2 minutes";
        list[4] = "5 minutes";
        list[5] = "10 minutes";
        list[6] = "30 minutes";
        list[7] = "Never";

    }

    @Override
    public String[] getDisplayNames() {
        return list;
    }

    @Override
    public Set<Integer> getSelectedIndexes() {
        return selectedIndexList;
    }

    @Override
    public void setSelectedIndexes(Set<Integer> selectedIndexes) {
        this.selectedIndexList = selectedIndexes;
    }

    @Override
    public String getDisplayName(int i) {
        return list[i];
    }

    @Override
    public void addSelectedIndex(Set<Integer> selectedIndexes) {
        selectedIndexes.addAll(selectedIndexes);
    }

    @Override
    public void addSelectedIndex(Integer selectedIndex) {
        this.selectedIndexList.add(selectedIndex);
    }

    @Override
    public void setNoSelection() {
        this.selectedIndexList.clear();

    }

    @Override
    public boolean isMultipleSelectionAllowed() {
        return false;
    }


    public ArrayList<InfoRowdata> getInfoRowData() {

        ArrayList<InfoRowdata> infoRowdatas = new ArrayList<>();

        for (int c = 0; c < list.length; c++) {
            if (selectedIndexList.contains(c))
                infoRowdatas.add(new InfoRowdata(true, c, isMultipleSelectionAllowed()));
            else
                infoRowdatas.add(new InfoRowdata(false, c, isMultipleSelectionAllowed()));
        }

        return infoRowdatas;
    }

    @Override
    public void setSelected(ArrayList<InfoRowdata> infoRowdatas) {
        for (InfoRowdata infoRowdata : infoRowdatas) {
            if (infoRowdata.isclicked)
                selectedIndexList.add(infoRowdata.index);
            else
                selectedIndexList.remove(infoRowdata.index);
        }
    }

}
