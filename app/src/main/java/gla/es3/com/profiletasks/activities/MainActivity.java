package gla.es3.com.profiletasks.activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gla.es3.com.profiletasks.R;
import gla.es3.com.profiletasks.model.ModelContainer;
import gla.es3.com.profiletasks.model.entity.profile.ProfileDescriptor;
import gla.es3.com.profiletasks.service.MainServiceClientHandler;
import gla.es3.com.profiletasks.service.impl.MainService;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private ServiceConnection serviceConnection;
    private MainServiceClientHandler serviceHandler = null;

    private Button btnAdd;
    private Button btnRemove;
    private Button btnDebug;
    private ListView viewMain;

    private ModelContainer model = null;
    private CustomArrayAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configure();
        initService();

    }

    private void initService() {
        Intent i = new Intent(getApplicationContext(), MainService.class);
        getApplicationContext().startService(i);

        //IPC
        serviceConnection = new ServiceMonitor();
        Intent serviceIntent = new Intent(this, MainService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void configure() {

        btnAdd = (Button) findViewById(R.id.btnAdd);
        viewMain = (ListView) findViewById(R.id.viewModel);
        btnDebug = (Button) findViewById(R.id.btnDebug);

        btnAdd.setText("New profile");
    }

    private void configureAfterServiceLoads() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null)
                    onNewProfile(v);
            }
        });

        btnDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null)
                    onDebugClick(v);
            }
        });

        model = serviceHandler.getModel();

        List<ProfileDescriptor> list = model.getList();
        List<CustomArrayItem> viewList = new ArrayList<>();
        for (ProfileDescriptor pd : list) {
            viewList.add(new CustomArrayItem(pd, model));
        }


        itemsAdapter = new CustomArrayAdapter(this, R.layout.view_entity, viewList, R.id.entityTextView, model);
        viewMain.setAdapter(itemsAdapter);
        OnItemClick onItemClick = new OnItemClick();
        viewMain.setOnItemClickListener(onItemClick);
        viewMain.setOnItemLongClickListener(onItemClick);


        //DEBUG

        newProfile("DEBUGPROFILE");
    }


    public void onNewProfile(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Profile name");
        alert.setMessage("Enter Profile name");
        final EditText input = new EditText(this);
        alert.setView(input);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                newProfile(value);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    private void onRemoveProfile(int position) {
        ProfileDescriptor profileDescriptor = model.getList().get(position);

        model.removeProfile(profileDescriptor);

    }

    public void onDebugClick(View view) {

        serviceHandler.serviceInteract();
    }

    private void newProfile(String name) {
        ProfileDescriptor profile = new ProfileDescriptor(name);

        model.addProfile(profile);
        itemsAdapter.addItem(new CustomArrayItem(profile, model));

//        TaskDescriptor taskd = new TaskDescriptor(("TASK_MESSAGE_TOAST"), model.getTaskServiceHandler());
//
//        ParameterContainer parameters = taskd.getParameters();
//        parameters.getList().get(0).setValue("Hello World!");
//
//        profile.addTaskDescriptor(taskd);
//
//
//        TriggerDescriptor trig = new TriggerDescriptor(profile.getID(),"TRIGGER_DATE_DATETIME", model.getTriggerServiceHandler());
//        parameters = trig.getParameters();
//        DaysOfWeekList dow = new DaysOfWeekList();
//        dow.addSelectedIndex(0);
//        dow.addSelectedIndex(1);
//        dow.addSelectedIndex(2);
//
//        Calendar instance = Calendar.getInstance();
//        instance.setTimeInMillis(System.currentTimeMillis());
//        instance.add(Calendar.MINUTE,1);
//
//        parameters.getList().get(0).setValue(dow);
//        parameters.getList().get(1).setValue(new Hours(instance.getTime()));
//
//        profile.addTriggerDescriptor(trig, model.getTriggerServiceHandler());


    }


    @Override
    protected void onDestroy() {
        if (serviceHandler != null) {
            unbindService(serviceConnection);
        }
//        Intent serviceIntent = new Intent(this, MainService.class);
//        stopService(serviceIntent);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    class OnItemClick implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
            CharSequence entities[] = new CharSequence[]{"TASKS", "TRIGGERS"};

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Select an option");
            builder.setItems(entities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TextView text = (TextView) view.findViewById(R.id.entityTextView);

                    CustomArrayItem item = (CustomArrayItem) parent.getAdapter().getItem(position);
                    ProfileDescriptor pd = (ProfileDescriptor) item.getObject();


                    Bundle b = new Bundle();
                    b.putString("profileID", pd.getID());

                    if (which == 0) {
                        Intent i = new Intent(MainActivity.this, TaskActivity.class);
                        i.putExtras(b);
                        MainActivity.this.startActivityForResult(i, 0);

                    } else if (which == 1) {
                        Intent i = new Intent(MainActivity.this, TriggerActivity.class);
                        i.putExtras(b);
                        MainActivity.this.startActivityForResult(i, 0);
                    }
                }
            });
            builder.show();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            ProfileDescriptor profileDescriptor = model.getList().get(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Remove Profile");
            alert.setMessage("Are you sure you want to remove " + profileDescriptor.getDisplayName() + "?");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    onRemoveProfile(position);
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
            Log.v(TAG, "onServiceDisconnected");
            serviceHandler = null;
        }
    }

}
