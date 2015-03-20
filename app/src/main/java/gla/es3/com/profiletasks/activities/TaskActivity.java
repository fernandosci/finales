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
import gla.es3.com.profiletasks.model.entity.tasks.Task;
import gla.es3.com.profiletasks.model.entity.tasks.TaskDescriptor;
import gla.es3.com.profiletasks.model.entity.tasks.TaskProvider;
import gla.es3.com.profiletasks.model.parameter.ParameterContainer;
import gla.es3.com.profiletasks.service.MainServiceClientHandler;
import gla.es3.com.profiletasks.service.impl.MainService;

public class TaskActivity extends ActionBarActivity {

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

        btnAdd.setText("New Task");
    }

    private void configureAfterServiceLoads() {
        model = serviceHandler.getModel();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model != null)
                    onNewTask(v);
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

        List<TaskDescriptor> tTaskDescriptorsList = profile.getTTaskDescriptorsList();

        List<CustomArrayItem> viewList = new ArrayList<>();
        for (TaskDescriptor pd : tTaskDescriptorsList) {
            viewList.add(new CustomArrayItem(pd, model));
        }

        itemsAdapter = new CustomArrayAdapter(this, R.layout.view_entity, viewList, R.id.entityTextView, model);
        viewMain.setAdapter(itemsAdapter);
        OnItemClick onItemClick = new OnItemClick();
        viewMain.setOnItemClickListener(onItemClick);
        viewMain.setOnItemLongClickListener(onItemClick);
    }


    public void onNewTask(View view) {
        newTask();
    }

    private void onRemoveTask(int position) {
        TaskDescriptor taskDescriptor = profile.getTTaskDescriptorsList().get(position);
        profile.removeTaskDescriptor(taskDescriptor);
        itemsAdapter.removeItem(position);
    }

    public void onDebugClick(View view) {

        int which = 0;
        final TaskProvider taskProvider = model.getTaskServiceHandler().getTaskProvider();
        final List<String> taskList = taskProvider.getTaskListByOrder();
        final String[] strings = new String[taskList.size()];
        Task task = taskProvider.getTask(taskList.get(which));

        Bundle b = new Bundle();
        b.putSerializable("parameters", task.getParameters());
        b.putInt("which", which);
        Intent i = new Intent(TaskActivity.this, ParameterActivity.class);
        i.putExtras(b);
        TaskActivity.this.startActivityForResult(i, 1);

    }

    private void newTask() {
        TaskDescriptor descriptor = null;

        final TaskProvider taskProvider = model.getTaskServiceHandler().getTaskProvider();
        final List<String> taskList = taskProvider.getTaskListByOrder();
        final String[] strings = new String[taskList.size()];

        for (int c = 0; c < taskList.size(); c++) {
            strings[c] = taskProvider.getTask(taskList.get(c)).getDisplayName();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Task");
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Task task = taskProvider.getTask(taskList.get(which));

                Bundle b = new Bundle();
                b.putSerializable("parameters", task.getParameters());
                b.putInt("which", which);
                Intent i = new Intent(TaskActivity.this, ParameterActivity.class);
                i.putExtras(b);
                TaskActivity.this.startActivityForResult(i, 1);
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
            final List<String> taskList = model.getTaskServiceHandler().getTaskProvider().getTaskListByOrder();

            Task task = model.getTaskServiceHandler().getTaskProvider().getTask(taskList.get(which));
            TaskDescriptor desc = new TaskDescriptor(task.getID(), model.getTaskServiceHandler(), parameters);
            profile.addTaskDescriptor(desc);
            itemsAdapter.addItem(new CustomArrayItem(desc, model));
        } else if (requestCode == 2) {

            TaskDescriptor taskDescriptor = profile.getTTaskDescriptorsList().get(which);

            taskDescriptor.setParameterContainer(parameters);
        }

        model.save();
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

            TaskDescriptor taskDescriptor = profile.getTTaskDescriptorsList().get(position);

            Bundle b = new Bundle();
            b.putSerializable("parameters", taskDescriptor.getParameters());
            b.putInt("which", position);
            Intent i = new Intent(TaskActivity.this, ParameterActivity.class);
            i.putExtras(b);
            TaskActivity.this.startActivityForResult(i, 2);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            TaskDescriptor taskDescriptor = profile.getTTaskDescriptorsList().get(position);

            AlertDialog.Builder alert = new AlertDialog.Builder(TaskActivity.this);
            alert.setTitle("Remove Task");
            alert.setMessage("Are you sure you want to remove " + taskDescriptor.getDisplayName(model.getTaskServiceHandler()) + "?");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    onRemoveTask(position);
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
