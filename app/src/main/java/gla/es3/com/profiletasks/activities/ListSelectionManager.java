package gla.es3.com.profiletasks.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import gla.es3.com.profiletasks.R;
import gla.es3.com.profiletasks.model.parameter.types.InfoRowdata;

public class ListSelectionManager {

    Context context;

    private ListView llChb;

    private String[] data;

    private ArrayList<InfoRowdata> infodata;

    public ListSelectionManager(Context context, ListView llChb, String[] data, ArrayList<InfoRowdata> infodata) {
        this.context = context;
        this.llChb = llChb;
        this.data = data;

        this.infodata = infodata;
        llChb.invalidate();
        llChb.setAdapter(new MyAdapter());
    }

    public ArrayList<InfoRowdata> getInfodata() {
        return infodata;
    }

    public void setInfodata(ArrayList<InfoRowdata> infodata) {
        this.infodata = infodata;
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = null;
            row = View.inflate(context, R.layout.view_parameter_listselection_item, null);
            TextView tvContent = (TextView) row.findViewById(R.id.tvContent);
            //tvContent.setText(data[position]);
            tvContent.setText(data[position]);
            //System.out.println("The Text is here like.. == "+tvContent.getText().toString());

            final CheckBox cb = (CheckBox) row
                    .findViewById(R.id.chbContent);
            cb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (infodata.get(position).isclicked) {
                        infodata.get(position).isclicked = false;
                    } else {
                        infodata.get(position).isclicked = true;
                    }

                    for (int i = 0; i < infodata.size(); i++) {
                        if (infodata.get(i).isclicked) {
                            System.out.println("Selectes Are == " + data[i]);
                        }
                    }
                }
            });

            if (infodata.get(position).isclicked) {

                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            return row;
        }
    }
}
