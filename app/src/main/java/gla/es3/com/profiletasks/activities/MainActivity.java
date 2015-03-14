package gla.es3.com.profiletasks.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import gla.es3.com.profiletasks.R;
import gla.es3.com.profiletasks.service.MainServiceClientHandler;
import gla.es3.com.profiletasks.service.impl.MainService;


public class MainActivity extends ActionBarActivity {

    private ServiceConnection serviceConnection;
    private MainServiceClientHandler serviceHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //permanently starts the service in its own thread
        // use this to start and trigger a service
        Intent i = new Intent(getApplicationContext(), MainService.class);
        // potentially add data to the intent
        //i.putExtra("KEY1", "Value to be used by the service");
        getApplicationContext().startService(i);


        //IPC
        serviceConnection = new ServiceMonitor();
        Intent serviceIntent = new Intent(this, MainService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);


        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onDebugClick(View view) {

        serviceHandler.serviceInteract();

//        serviceHandler.serviceInteract();
//
//        //permanently starts the service in its own thread
//        // use this to start and trigger a service
//        Intent i= new Intent(getApplicationContext(), MainService.class);
//        // potentially add data to the intent
//        i.putExtra("KEY1", "Value to be used by the service");
//        getApplicationContext().startService(i);

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


    class ServiceMonitor implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MainService.LocalBinder serviceBinder = (MainService.LocalBinder) service;
            serviceHandler = serviceBinder.getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this.getApplicationContext(), "onServiceDisconnected", Toast.LENGTH_SHORT).show();
            serviceHandler = null;
        }
    }
}
