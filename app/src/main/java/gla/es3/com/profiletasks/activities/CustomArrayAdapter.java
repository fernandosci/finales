package gla.es3.com.profiletasks.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gla.es3.com.profiletasks.model.ModelContainer;


public class CustomArrayAdapter extends ArrayAdapter<CustomArrayItem> {

    private LayoutInflater inflater;

    private ModelContainer model;
    private int resource;
    private int textBoxID;
    private List<CustomArrayItem> objects;

    public CustomArrayAdapter(Context context, int resource, List<CustomArrayItem> objects, int textBoxID, ModelContainer model) {
        super(context, resource, objects);
        this.objects = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.textBoxID = textBoxID;
        this.resource = resource;

        this.model = model;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }
        TextView text = (TextView) convertView.findViewById(textBoxID);

        try {
            text.setText(super.getItem(position).toString());
        } catch (Exception e) {

        }

        return convertView;
    }


    public void addItem(CustomArrayItem item) {
        objects.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        objects.remove(position);
        notifyDataSetChanged();
    }


//    public int getSelectedIndex() {
//        return selectedIndex;
//    }
//
//    public void setSelectedIndex(int selectedIndex) {
//        this.selectedIndex = selectedIndex;
//    }

}
