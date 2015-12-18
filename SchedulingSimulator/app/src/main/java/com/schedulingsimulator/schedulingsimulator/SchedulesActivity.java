package com.schedulingsimulator.schedulingsimulator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class SchedulesActivity extends AppCompatActivity {

    public static final String PERIODIC_TASKS_KEY = "periodicTasks";
    public static final String APERIODIC_TASKS_KEY = "aperiodicTasks";
    public static final String SERVER_PERIOD_KEY = "serverPeriod";
    public static final String SERVER_COMPUTATION_TIME_KEY = "serverComputationTime";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ArrayList<PeriodicTask> periodicTasks = intent.getParcelableArrayListExtra(PERIODIC_TASKS_KEY);
        ArrayList<AperiodicTask> aperiodicTasks = intent.getParcelableArrayListExtra(APERIODIC_TASKS_KEY);
        int serverComputationTime = intent.getIntExtra(SERVER_COMPUTATION_TIME_KEY, -1);
        int serverPeriod = intent.getIntExtra(SERVER_PERIOD_KEY, -1);
        setContentView(R.layout.activity_schedules);
        TableRow pollingRow = (TableRow) findViewById(R.id.pollingScheduleRow);
        TableRow deferredRow = (TableRow) findViewById(R.id.deferredScheduleRow);
        TableRow timeRow = (TableRow) findViewById(R.id.timeRow);
        DeferredServerScheduler deferredServerScheduler = new DeferredServerScheduler(serverComputationTime, serverPeriod, periodicTasks, aperiodicTasks);
        ArrayList<Task> deferredSchedule = deferredServerScheduler.GetSchedule();

        PollingServerScheduler pollingServerScheduler = new PollingServerScheduler(serverComputationTime, serverPeriod, periodicTasks, aperiodicTasks);
        ArrayList<Task> pollingSchedule = pollingServerScheduler.GetSchedule();
        int padding = 10;
        for (int i = 0; i < deferredSchedule.size(); i++)
        {
            //deferredScheduleRow
            Task currentDeferredTask = deferredSchedule.get(i);
            TextView deferredColumnTextView = new TextView(this);
            deferredColumnTextView.setPadding(0, 0, padding, 0);
            if (currentDeferredTask != null )
            {
                deferredColumnTextView.setText(currentDeferredTask.getId());
                deferredColumnTextView.setBackgroundColor(currentDeferredTask.getColor());
            }
            else
            {
                deferredColumnTextView.setText("");
                deferredColumnTextView.setBackgroundColor(Color.WHITE);
            }
            deferredRow.addView(deferredColumnTextView);
            //pollingScheduleRow
            Task currentPollingTask = pollingSchedule.get(i);
            TextView pollingColumnTextView = new TextView(this);
            pollingColumnTextView.setPadding(0, 0, padding, 0);
            if (currentPollingTask != null )
            {
                pollingColumnTextView.setText(currentPollingTask.getId());
                pollingColumnTextView.setBackgroundColor(currentPollingTask.getColor());
            }
            else
            {
                pollingColumnTextView.setText("");
                pollingColumnTextView.setBackgroundColor(Color.WHITE);
            }
            pollingRow.addView(pollingColumnTextView);

            //add counter to timerow
            TextView indexTextView = new TextView(this);
            indexTextView.setText(i + "");
            indexTextView.setPadding(0, 0, padding, 0);
            timeRow.addView(indexTextView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedules, menu);
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
}
