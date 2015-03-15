package gla.es3.com.profiletasks.model.parameter.types;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ito on 15/03/2015.
 */
public class DaysOfWeekList implements ListSelection {

    private List<Integer> selectedIndexList;
    private String[] list;

    public DaysOfWeekList() {
        selectedIndexList = new ArrayList<>();

        list = new DateFormatSymbols().getShortWeekdays();
    }

    @Override
    public String[] getDisplayNames() {
        return new String[0];
    }

    @Override
    public List<Integer> getSelectedIndex() {
        return selectedIndexList;
    }

    @Override
    public void setSelectedIndexes(List<Integer> selectedIndexes) {
        this.selectedIndexList = selectedIndexes;

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
