package gla.es3.com.profiletasks.activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gla.es3.com.profiletasks.R;
import gla.es3.com.profiletasks.model.ModelContainer;
import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;
import gla.es3.com.profiletasks.model.entity.triggers.Trigger;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerDescriptor;
import gla.es3.com.profiletasks.model.entity.triggers.TriggerProvider;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.service.MainServiceClientHandler;
import gla.es3.com.profiletasks.service.impl.MainService;

public class TriggerActivity extends ActionBarActivity {

    private ServiceConnection serviceConnection;
    private MainServiceClientHandler serviceHandler = null;

    private Button btnAdd;
    private Button btnRemove;
    private Button btnDebug;
    private ListView viewMain;

    private ModelContainer model = null;
    private CustomArrayAdapter itemsAdapter;
    private String profileID;
    private ProfileDescriptor profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configure();
        initService();


        Bundle b = getIntent().getExtras();
        profileID = (String) b.get("profileID");
    }

    private void initService() {
        //IPC
        serviceConnection = new ServiceMonitor();
        Intent serviceIntent = new Intent(this, MainService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }


    private void configure() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        viewMain = (ListView) findViewById(R.id.viewModel);
//        btnDebug = (Button) findViewById(R.id.btnDebug);

        btnAdd.setText("New Trigger");
    }

    private void configureAfterServiceLoads() {
        model = serviceHandler.getModel();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null)
                    onNewTrigger(v);
            }
        });

//        btnDebug.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (model != null)
//                    onDebugClick(v);
//            }
//        });

        profile = model.getProfile(profileID);

        List<TriggerDescriptor> triggerDescriptorsList = profile.getTriggerDescriptorsList();

        List<CustomArrayItem> viewList = new ArrayList<>();
        for (TriggerDescriptor td : triggerDescriptorsList) {
            viewList.add(new CustomArrayItem(td, model));
        }

        itemsAdapter = new CustomArrayAdapter(this, R.layout.view_entity, viewList, R.id.entityTextView, model);
        viewMain.setAdapter(itemsAdapter);
        OnItemClick onItemClick = new OnItemClick();
        viewMain.setOnItemClickListener(onItemClick);
        viewMain.setOnItemLongClickListener(onItemClick);
    }


    public void onNewTrigger(View view) {
        newTrigger();
    }

    private void onRemoveTrigger(int position) {
        TriggerDescriptor triggerDescriptor = profile.getTriggerDescriptorsList().get(position);
        profile.removeTriggerDescriptor(triggerDescriptor, model.getTriggerServiceHandler());
        itemsAdapter.removeItem(position);
    }

    public void onDebugClick(View view) {

        int which = 0;
        final TriggerProvider triggerProvider = model.getTriggerServiceHandler().getTriggerProvider();
        final List<String> list = triggerProvider.getTriggerListByOrder();
        final String[] strings = new String[list.size()];
        Trigger trigger = triggerProvider.getTrigger(list.get(which));

        Bundle b = new Bundle();
        b.putSerializable("parameters", trigger.getParameters());
        b.putInt("which", which);
        Intent i = new Intent(TriggerActivity.this, ParameterActivity.class);
        i.putExtras(b);
        TriggerActivity.this.startActivityForResult(i, 1);

    }

    private void newTrigger() {
        TriggerDescriptor descriptor = null;

        final TriggerProvider triggerProvider = model.getTriggerServiceHandler().getTriggerProvider();
        final List<String> triggerList = triggerProvider.getTriggerListByOrder();
        final String[] strings = new String[triggerList.size()];

        for (int c = 0; c < triggerList.size(); c++) {
            strings[c] = triggerProvider.getTrigger(triggerList.get(c)).getDisplayName();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Trigger");
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Trigger trigger = triggerProvider.getTrigger(triggerList.get(which));

                Bundle b = new Bundle();
                b.putSerializable("parameters", trigger.getParameters());
                b.putInt("which", which);
                Intent i = new Intent(TriggerActivity.this, ParameterActivity.class);
                i.putExtras(b);
                TriggerActivity.this.startActivityForResult(i, 1);
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle bParam = data.getExtras();
        ParameterContainer parameters = (ParameterContainer) bParam.get("parameters");
        int which = bParam.getInt("which");


        if (requestCode == 1) {
            final List<String> triggerList = model.getTriggerServiceHandler().getTriggerProvider().getTriggerListByOrder();

            Trigger trigger = model.getTriggerServiceHandler().getTriggerProvider().getTrigger(triggerList.get(which));
            TriggerDescriptor desc = new TriggerDescriptor(profileID, trigger.getID(), model.getTriggerServiceHandler(), parameters);
            profile.addTriggerDescriptor(desc, model.getTriggerServiceHandler());
            itemsAdapter.addItem(new CustomArrayItem(desc, model));
        } else if (requestCode == 2) {

            TriggerDescriptor triggerDescriptor = profile.getTriggerDescriptorsList().get(which);

            triggerDescriptor.setParameterContainer(parameters);

            profile.updated(triggerDescriptor, model.getTriggerServiceHandler());
        }
    }

    @Override
    protected void onDestroy() {
        if (serviceHandler != null) {
            unbindService(serviceConnection);
        }

        super.onDestroy();
    }

    class OnItemClick implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TriggerDescriptor triggerDescriptor = profile.getTriggerDescriptorsList().get(position);

            Bundle b = new Bundle();
            b.putSerializable("parameters", triggerDescriptor.getParameters());
            b.putInt("which", position);
            Intent i = new Intent(TriggerActivity.this, ParameterActivity.class);
            i.putExtras(b);
            TriggerActivity.this.startActivityForResult(i, 2);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            TriggerDescriptor triggerDescriptor = profile.getTriggerDescriptorsList().get(position);

            AlertDialog.Builder alert = new AlertDialog.Builder(TriggerActivity.this);
            alert.setTitle("Remove Trigger");
            alert.setMessage("Are you sure you want to remove " + triggerDescriptor.getDisplayName(model.getTriggerServiceHandler()) + "?");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    onRemoveTrigger(position);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();

            return true;
        }

    }

    class ServiceMonitor implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MainService.LocalBinder serviceBinder = (MainService.LocalBinder) service;
            serviceHandler = serviceBinder.getService();

            configureAfterServiceLoads();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceHandler = null;
        }
    }


}
