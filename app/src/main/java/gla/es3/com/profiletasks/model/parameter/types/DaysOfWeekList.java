package gla.es3.com.profiletasks.model.parameter.types;

import java.text.DateFormatSymbols;
import java.util.HashSet;
import java.util.Set;


public class DaysOfWeekList implements ListSelection {

    private Set<Integer> selectedIndexList;
    private String[] list;

    public DaysOfWeekList() {
        selectedIndexList = new HashSet<>();

        list = new DateFormatSymbols().getShortWeekdays();
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
        return true;
    }
}
