package com.schedulingsimulator.schedulingsimulator;

import android.app.DialogFragment;
import android.app.Fragment;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements PeriodicTaskFragment.OnPeriodicTaskChangeListener, AperiodicTaskFragment.OnAperiodicTaskChangeListener {

    DialogFragment periodicFragment, aperiodicFragment;

    ArrayList<PeriodicTask> periodicTasks;
    ArrayList<AperiodicTask> aperiodicTasks;

    ArrayAdapter<PeriodicTask> periodicAdapter;
    ArrayAdapter<AperiodicTask> aperiodicAdapter;

    public static final String PERIODIC_ARRAY_KEY = "periodicArray";
    public static final String APERIODIC_ARRAY_KEY = "aperiodicArray";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(PERIODIC_ARRAY_KEY)) periodicTasks = new ArrayList<>();
        else periodicTasks = savedInstanceState.getParcelableArrayList(PERIODIC_ARRAY_KEY);
        if (savedInstanceState == null || !savedInstanceState.containsKey(APERIODIC_ARRAY_KEY)) aperiodicTasks = new ArrayList<>();
        else aperiodicTasks = savedInstanceState.getParcelableArrayList(APERIODIC_ARRAY_KEY);

        if (periodicTasks != null && aperiodicTasks != null)
        {
            Toast.makeText(this, "OnCreate Periodic length: " + periodicTasks.size() + " Aperiodic length: " + aperiodicTasks.size(), Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_main);
        //periodic task list
        ListView periodicList = (ListView) findViewById(R.id.periodic_tasks_list);
        periodicAdapter = new ArrayAdapter<PeriodicTask>(this, android.R.layout.simple_list_item_1, periodicTasks);
        periodicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                     PeriodicTask selected = (PeriodicTask) parent.getItemAtPosition(position);
                                                     //Toast.makeText(getApplicationContext(), "Periodic item clicked: " + selected.toString(), Toast.LENGTH_SHORT).show();
                                                     periodicFragment = new PeriodicTaskFragment();
                                                     Bundle args = new Bundle();
                                                     args.putInt(PeriodicTaskFragment.ARG_PARAM1, selected.getPeriod());
                                                     args.putInt(PeriodicTaskFragment.ARG_PARAM2, selected.getComputationTime());
                                                     //task is not new
                                                     args.putBoolean(PeriodicTaskFragment.ARG_PARAM3, false);
                                                     args.putInt(PeriodicTaskFragment.ARG_PARAM4, position);
                                                     periodicFragment.setArguments(args);
                                                     periodicFragment.show(getFragmentManager(), "newPeriodicTaskDialog");
                                                 }
                                             }
        );
        periodicList.setAdapter(periodicAdapter);

        //aperiodic task list
        ListView aperiodicList = (ListView) findViewById(R.id.aperiodic_tasks_list);
        aperiodicAdapter = new ArrayAdapter<AperiodicTask>(this, android.R.layout.simple_list_item_1, aperiodicTasks);
        aperiodicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     AperiodicTask selected = (AperiodicTask) parent.getItemAtPosition(position);
                     //Toast.makeText(getApplicationContext(), "Aperiodic item clicked: " + selected.toString(), Toast.LENGTH_SHORT).show();
                     aperiodicFragment = new AperiodicTaskFragment();
                     Bundle args = new Bundle();
                     args.putInt(AperiodicTaskFragment.ARG_PARAM1, selected.getReadyTime());
                     args.putInt(AperiodicTaskFragment.ARG_PARAM2, selected.getComputationTime());
                     args.putInt(AperiodicTaskFragment.ARG_PARAM3, selected.getDeadline());
                     //is not new
                     args.putBoolean(AperiodicTaskFragment.ARG_PARAM4, false);
                     args.putInt(AperiodicTaskFragment.ARG_PARAM5, position);
                     aperiodicFragment.setArguments(args);
                     aperiodicFragment.show(getFragmentManager(), "newAperiodicTaskDialog");
                 }
             }
        );
        aperiodicList.setAdapter(aperiodicAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        Toast.makeText(this, "OnResume Periodic length: " + periodicTasks.size() + " Aperiodic length: " + aperiodicTasks.size(), Toast.LENGTH_SHORT).show();
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

    public void onPeriodicTaskChange(int computationTime, int period, boolean isNew, int listPosition)
    {
        if (isNew) periodicTasks.add(new PeriodicTask(computationTime, period));
        else {
            Toast.makeText(this, "Position: " + listPosition, Toast.LENGTH_SHORT).show();
            periodicTasks.remove(listPosition);
            periodicTasks.add(listPosition, new PeriodicTask(computationTime, period));
            periodicAdapter.notifyDataSetChanged();
        }
    }

    public void onAperiodicTaskChange(int readyTime, int computationTime, int deadline, boolean isNew, int listPosition)
    {
        if (isNew) aperiodicTasks.add(new AperiodicTask(readyTime, computationTime, deadline));
        else {
            Toast.makeText(this, "Position: " + listPosition, Toast.LENGTH_SHORT).show();
            aperiodicTasks.remove(listPosition);
            aperiodicTasks.add(listPosition, new AperiodicTask(readyTime, computationTime, deadline));
            aperiodicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState)
    {
        savedState.putParcelableArrayList(PERIODIC_ARRAY_KEY, periodicTasks);
        savedState.putParcelableArrayList(APERIODIC_ARRAY_KEY, aperiodicTasks);
        super.onSaveInstanceState(savedState);
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
        aperiodicFragment = new AperiodicTaskFragment();
        aperiodicFragment.show(getFragmentManager(), "newAperiodicTaskDialog");
    }

    public void editPeriodicTask(View view)
    {
        Toast.makeText(this, "Edit periodic task", Toast.LENGTH_SHORT).show();
    }

    public void editAperiodicTask(View view)
    {
        Toast.makeText(this, "Edit aperiodic task", Toast.LENGTH_SHORT).show();
    }

}
