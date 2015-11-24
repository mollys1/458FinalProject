package com.schedulingsimulator.schedulingsimulator;

import android.app.DialogFragment;
import android.app.Fragment;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements PeriodicTaskFragment.OnPeriodicTaskChangeListener {

    DialogFragment periodicFragment, aperiodicFragment;

    ArrayList<PeriodicTask> periodicTasks = new ArrayList<PeriodicTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView periodicList = (ListView) findViewById(R.id.periodic_tasks_list);
        final ArrayAdapter<PeriodicTask> adapter = new ArrayAdapter<PeriodicTask>(this, android.R.layout.simple_list_item_1, periodicTasks);
        periodicList.setAdapter(adapter);
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

    public void onPeriodicTaskChange(int computationTime, int period, boolean isNew)
    {
        Toast.makeText(this, "Comp time: " + computationTime + " period: " + period + " isNew: " + isNew, Toast.LENGTH_SHORT).show();
        if (isNew) periodicTasks.add(new PeriodicTask(computationTime, period));
    }


    public void addPeriodicTask(View view)
    {
        Toast.makeText(this, "Add periodic task", Toast.LENGTH_SHORT).show();
        periodicFragment = new PeriodicTaskFragment();
        periodicFragment.show(getFragmentManager(), "newPeriodicTaskDialog");
    }

    public void addAperiodicTask(View view)
    {
        Toast.makeText(this, "Add aperiodic task", Toast.LENGTH_SHORT).show();
    }

}
