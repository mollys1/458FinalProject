package com.schedulingsimulator.schedulingsimulator;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    public static final boolean TEST = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(PERIODIC_ARRAY_KEY)) periodicTasks = new ArrayList<>();
        else periodicTasks = savedInstanceState.getParcelableArrayList(PERIODIC_ARRAY_KEY);
        if (savedInstanceState == null || !savedInstanceState.containsKey(APERIODIC_ARRAY_KEY)) aperiodicTasks = new ArrayList<>();
        else aperiodicTasks = savedInstanceState.getParcelableArrayList(APERIODIC_ARRAY_KEY);

        if (periodicTasks != null && aperiodicTasks != null)
        {
            if (TEST) Toast.makeText(this, "OnCreate Periodic length: " + periodicTasks.size() + " Aperiodic length: " + aperiodicTasks.size(), Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_main);
        //periodic task list
        final ListView periodicList = (ListView) findViewById(R.id.periodic_tasks_list);
        periodicAdapter = new ArrayAdapter<PeriodicTask>(this, android.R.layout.simple_list_item_1, periodicTasks);
        periodicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    PeriodicTask selected = (PeriodicTask) parent.getItemAtPosition(position);
                                                    //if (TEST) Toast.makeText(getApplicationContext(), "Periodic item clicked: " + selected.toString(), if (TEST) Toast.LENGTH_SHORT).show();
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
        //long click to delete
        periodicList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //create an alert dialog to confirm delete
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.confirm_delete_message)
                        .setTitle(R.string.confirm_delete_title)
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete the task, remove it from the list, update list view
                                periodicTasks.remove(position);
                                periodicAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //don't delete, close the dialog
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
        periodicList.setAdapter(periodicAdapter);

        //aperiodic task list
        ListView aperiodicList = (ListView) findViewById(R.id.aperiodic_tasks_list);
        aperiodicAdapter = new ArrayAdapter<AperiodicTask>(this, android.R.layout.simple_list_item_1, aperiodicTasks);
        aperiodicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                     AperiodicTask selected = (AperiodicTask) parent.getItemAtPosition(position);
                                                     //if (TEST) Toast.makeText(getApplicationContext(), "Aperiodic item clicked: " + selected.toString(), if (TEST) Toast.LENGTH_SHORT).show();
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
        aperiodicList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                     @Override
                                                     public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                                         //create an alert dialog to confirm delete
                                                         AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                         builder.setMessage(R.string.confirm_delete_message)
                                                                 .setTitle(R.string.confirm_delete_title)
                                                                 .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                                                     @Override
                                                                     public void onClick(DialogInterface dialog, int which) {
                                                                         //delete the task, remove it from the list, update list view
                                                                         aperiodicTasks.remove(position);
                                                                         aperiodicAdapter.notifyDataSetChanged();
                                                                         dialog.dismiss();
                                                                     }
                                                                 })
                                                                 .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                                                     @Override
                                                                     public void onClick(DialogInterface dialog, int which) {
                                                                         //don't delete, close the dialog=][
                                                                         dialog.dismiss();
                                                                     }
                                                                 })
                                                                 .show();
                                                         return true;
                                                     }
                                                 });
                aperiodicList.setAdapter(aperiodicAdapter);
        if (TEST)
        {
            periodicTasks.add(new PeriodicTask("P1", 1, 4));
            periodicTasks.add(new PeriodicTask("P2", 2, 6));
            aperiodicTasks.add(new AperiodicTask("A1", 2, 5, 25));
            //aperiodicTasks.add(new AperiodicTask("A2", 10, 2, 11));
            aperiodicTasks.add(new AperiodicTask("A2", 13, 2, 25));
        }


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
        if (TEST) Toast.makeText(this, "OnResume Periodic length: " + periodicTasks.size() + " Aperiodic length: " + aperiodicTasks.size(), Toast.LENGTH_SHORT).show();
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
        if (isNew) periodicTasks.add(new PeriodicTask("P" + listPosition, computationTime, period));
        else {
            if (TEST) Toast.makeText(this, "Position: " + listPosition, Toast.LENGTH_SHORT).show();
            periodicTasks.remove(listPosition);
            periodicTasks.add(listPosition, new PeriodicTask("P" + listPosition, computationTime, period));
            periodicAdapter.notifyDataSetChanged();
        }
    }

    public void onAperiodicTaskChange(int readyTime, int computationTime, int deadline, boolean isNew, int listPosition)
    {
        if (isNew) aperiodicTasks.add(new AperiodicTask("A" + listPosition, readyTime, computationTime, deadline));
        else {
            if (TEST) Toast.makeText(this, "Position: " + listPosition, Toast.LENGTH_SHORT).show();
            aperiodicTasks.remove(listPosition);
            aperiodicTasks.add(listPosition, new AperiodicTask("A" + listPosition, readyTime, computationTime, deadline));
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
        if (TEST) Toast.makeText(this, "Add periodic task", Toast.LENGTH_SHORT).show();
        periodicFragment = new PeriodicTaskFragment();
        periodicFragment.show(getFragmentManager(), "newPeriodicTaskDialog");
    }

    public void addAperiodicTask(View view)
    {
        if (TEST) Toast.makeText(this, "Add aperiodic task", Toast.LENGTH_SHORT).show();
        aperiodicFragment = new AperiodicTaskFragment();
        aperiodicFragment.show(getFragmentManager(), "newAperiodicTaskDialog");
    }

    public void createSchedule(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.schedule_params_message)
                .setTitle(R.string.schedule_params_title)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText computationTimeEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.serverComputationTime);
                        EditText periodEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.serverPeriod);
                        dialog.dismiss();
                        //int scheduleLength = Integer.parseInt(schedLengthEditText.getText().toString());
                        int computationTime = Integer.parseInt(computationTimeEditText.getText().toString());
                        int period = Integer.parseInt(periodEditText.getText().toString());
                        DeferredServerScheduler deferredServer = new DeferredServerScheduler(computationTime, period, periodicTasks, aperiodicTasks);
                        PollingServerScheduler pollingServer = new PollingServerScheduler(computationTime, period, periodicTasks, aperiodicTasks);
                        //check RMS schedulability
                        boolean schedulable = deferredServer.SchedulabilityTest();
                        if (TEST) Toast.makeText(MainActivity.this, "Schedulable: " + schedulable, Toast.LENGTH_SHORT).show();
                        //if exact analysis fails, show error that task set was not schedulable
                        if (!schedulable)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(R.string.not_schedulable_message)
                                    .setTitle(R.string.not_schedulable_title)
                                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                        //if schedulable go to schedules activity
                        else
                        {
                            //start schedules activity
                            Intent intent = new Intent(MainActivity.this, SchedulesActivity.class);
                            intent.putExtra(SchedulesActivity.SERVER_COMPUTATION_TIME_KEY, computationTime);
                            intent.putExtra(SchedulesActivity.SERVER_PERIOD_KEY, period);
                            intent.putExtra(SchedulesActivity.APERIODIC_TASKS_KEY, aperiodicTasks);
                            intent.putExtra(SchedulesActivity.PERIODIC_TASKS_KEY, periodicTasks);
                            startActivity(intent);
                        }
                    }
                })
                .setView(R.layout.dialog_schedule_params)
                .show();
    }
}
