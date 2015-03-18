package gla.es3.com.profiletasks.service.impl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import gla.es3.com.profiletasks.model.ModelContainer;
import gla.es3.com.profiletasks.model.ModelContainerImpl;
import gla.es3.com.profiletasks.service.MainServiceClientHandler;
import gla.es3.com.profiletasks.service.MainServiceModelHandler;
import gla.es3.com.profiletasks.service.impl.mainalarm.MainAlarmController;
import gla.es3.com.profiletasks.service.impl.mainalarm.MainAlarmHandler;

public class MainService extends Service {

    private static final String TAG = "MainService";

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    protected ModelContainer model;
    protected MainAlarmHandler mainAlarmHandler;
    protected MainServiceClientHandler mainServiceClientHandler;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;


    public MainService() {
    }

    @Override
    public void onCreate() {
        startHandlers();

        registerReceivers();

        initializeModel();

        Log.v(TAG, "ServiceOnCreate");

    }

    private void initializeModel() {
        this.model = new ModelContainerImpl(new ModelHandler());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "ProfileTasks Service Starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
//        Message msg = mServiceHandler.obtainMessage();
//        msg.arg1 = startId;
//        mServiceHandler.sendMessage(msg);

//        Toast.makeText(getApplicationContext(), "ServiceonStartCommand", Toast.LENGTH_SHORT).show();

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private void startHandlers() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
//        HandlerThread thread = new HandlerThread("ServiceStartArguments",
//                Process.THREAD_PRIORITY_BACKGROUND);
//        thread.start();
//
//        // Get the HandlerThread's Looper and use it for our Handler
//        mServiceLooper = thread.getLooper();
//        mServiceHandler = new ServiceHandler(mServiceLooper);

        this.mainServiceClientHandler = new MainServiceClientHandlerImpl(this);
        this.mainAlarmHandler = new MainAlarmHandler(getApplicationContext(), model);
    }


    private void registerReceivers() {
        //main alarm
        IntentFilter serviceIntent = new IntentFilter(MainAlarmController.CUSTOM_ACTION_MAINALARM);
        getApplicationContext().registerReceiver(mainAlarmHandler.getBroadcastReceiver(), serviceIntent);
        MainAlarmController.startMainAlarm(getApplicationContext(), false);
        //
    }

    private void unregisterReceivers() {
        //TODO
    }


    @Override
    public void onDestroy() {
        Log.v(TAG, "Service on Destroyd");
    }


    public class LocalBinder extends Binder {
        public MainServiceClientHandler getService() {
            Log.v(TAG, "Service.LocalBinder.getService");

            return mainServiceClientHandler;
        }
    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            long endTime = System.currentTimeMillis() + 5 * 1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            //stopSelf(msg.arg1);

            //Toast.makeText(MainService.this.getApplicationContext(), "handleMessage Ended", Toast.LENGTH_SHORT).show();
        }
    }


    private final class ModelHandler implements MainServiceModelHandler {
        @Override
        public Context getContext() {
            return getApplicationContext();
        }
    }

}
