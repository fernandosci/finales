package gla.es3.com.profiletasks.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gla.es3.com.profiletasks.R;
import gla.es3.com.profiletasks.model.parameter.Parameter;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.model.parameter.types.Hours;
import gla.es3.com.profiletasks.model.parameter.types.ListSelection;
import gla.es3.com.profiletasks.model.parameter.types.RangeIntType;

public class ParameterActivity extends ActionBarActivity {

    private ParameterContainer parameters;
    private List<Object> parameterViewsList;
    private int which;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_parameters);
        Bundle b = getIntent().getExtras();
        parameters = (ParameterContainer) b.get("parameters");
        which = b.getInt("which");

        parameterViewsList = new ArrayList<>();
        configure();
    }

    private void configure() {

        ViewGroup paramMainViewGroup = (ViewGroup) findViewById(R.id.viewgroup_parametersMain);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (Parameter parameter : parameters.getList()) {

            View paramView = vi.inflate(R.layout.view_parameter, null);
            ViewGroup paramViewGroup = (ViewGroup) paramView.findViewById(R.id.viewgroup_parameter);
            TextView eText = (TextView) paramView.findViewById(R.id.parameter_title);
            eText.setText(parameter.getTitle());

            if (parameter.getValueClass() == String.class) {

                View stringView = vi.inflate(R.layout.view_parameter_string, null);
                EditText eTextValue = (EditText) stringView.findViewById(R.id.parameter_string);
                eTextValue.setText((String) parameter.getValue());
                parameterViewsList.add(eTextValue);
                paramViewGroup.addView(stringView);

            } else if (parameter.getValueClass() == Boolean.class) {

                CheckBox cbox = new CheckBox(getApplicationContext());
                cbox.setTextColor(Color.BLACK);
                cbox.setText(parameter.getText());
                cbox.setGravity(Gravity.RIGHT);
                cbox.setChecked(((Boolean) parameter.getValue()));
                parameterViewsList.add(cbox);
                paramViewGroup.addView(cbox);

            } else if (parameter.getValueClass() == RangeIntType.class) {
                RangeIntType rangeValue = (RangeIntType) parameter.getValue();
                View listSelectionView = vi.inflate(R.layout.view_parameter_rangeselection, null);
                SeekBar eSeekBar = (SeekBar) listSelectionView.findViewById(R.id.parameter_range_value);
                eSeekBar.setMax(rangeValue.getMax() - rangeValue.getMin());
                eSeekBar.setProgress(rangeValue.getValue() - rangeValue.getMin());
                parameterViewsList.add(eSeekBar);

                TextView tMinValue = (TextView) listSelectionView.findViewById(R.id.parameter_range_min);
                tMinValue.setText(String.valueOf(rangeValue.getMin()));
                TextView tMaxValue = (TextView) listSelectionView.findViewById(R.id.parameter_range_max);
                tMaxValue.setText(String.valueOf(rangeValue.getMax()));

                paramViewGroup.addView(listSelectionView);

            } else if (parameter.getValueClass() == Hours.class) {
                Hours hoursValue = (Hours) parameter.getValue();
                View hourView = vi.inflate(R.layout.view_parameter_time, null);
                Button btnView = (Button) hourView.findViewById(R.id.parameter_hour_btnselect);
                EditText editText = (EditText) hourView.findViewById(R.id.parameter_hour_edittext);
                TimePickerManager mngr = new TimePickerManager(ParameterActivity.this, btnView, editText, hoursValue);
                parameterViewsList.add(mngr);

                paramViewGroup.addView(hourView);

            } else if (parameter.getValueClass() == ListSelection.class) {
                ListSelection listSelection = (ListSelection) parameter.getValue();
                View viewSelection = vi.inflate(R.layout.view_parameter_listselection, null);
                ListView listView = (ListView) viewSelection.findViewById(R.id.parameter_listselection);
                ListSelectionManager mngr = new ListSelectionManager(getApplicationContext(), listView, listSelection.getDisplayNames(), listSelection.getInfoRowData());
                parameterViewsList.add(mngr);

                paramViewGroup.addView(viewSelection);

            } else if (parameter.getValueClass() == String.class) {

            }


            paramMainViewGroup.addView(paramView);
        }
        paramMainViewGroup.invalidate();
        //add parameters widget

//        btnAdd = (Button)findViewById(R.id.btnAdd);
//        viewMain = (ListView)findViewById(R.id.viewModel);
//        btnDebug = (Button)findViewById(R.id.btnDebug);
//
//        btnAdd.setText("New Task");
    }

    @Override
    public void onBackPressed() {

        List<Parameter> list = parameters.getList();
        for (int c = 0; c < list.size(); c++) {
            Parameter parameter = list.get(c);

            if (parameter.getValueClass() == String.class) {
                View view = (View) parameterViewsList.get(c);
                parameter.setValue(((EditText) view).getText().toString());
                parameter.setUsed(false);

            } else if (parameter.getValueClass() == Boolean.class) {
                View view = (View) parameterViewsList.get(c);
                parameter.setValue(((CheckBox) view).isChecked());

                parameter.setUsed(!((CheckBox) view).isChecked());

            } else if (parameter.getValueClass() == RangeIntType.class) {
                View view = (View) parameterViewsList.get(c);
                RangeIntType rangeValue = (RangeIntType) parameter.getValue();
                rangeValue.setValue(((SeekBar) view).getProgress() + rangeValue.getMin());
                parameter.setValue(rangeValue);
            } else if (parameter.getValueClass() == Hours.class) {

                TimePickerManager mngr = (TimePickerManager) parameterViewsList.get(c);
                parameter.setValue(mngr.getHours());

            } else if (parameter.getValueClass() == ListSelection.class) {
                ListSelectionManager mngr = (ListSelectionManager) parameterViewsList.get(c);
                ListSelection listSelection = (ListSelection) parameter.getValue();

                listSelection.setSelected(mngr.getInfodata());

                parameter.setValue(listSelection);
            }
        }


        Bundle b = new Bundle();
        b.putSerializable("parameters", parameters);
        b.putInt("which", which);


        Intent data = new Intent();
        data.putExtras(b);

        if (getParent() == null) {
            setResult(Activity.RESULT_OK, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}