package com.schedulingsimulator.schedulingsimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class SchedulesActivity extends AppCompatActivity {

    public static final String SCHEDULE_LENGTH_KEY = "scheduleLength";
    public static final String SERVER_COMPUTATION_TIME_KEY = "serverComputationTime";
    public static final String SERVER_PERIOD_KEY = "serverPeriod";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_schedules);
        TableRow tr = (TableRow) findViewById(R.id.tableRow);
        TestingScheduler testingScheduler = new TestingScheduler();
        ArrayList<Task> schedule = testingScheduler.GetSchedule();
        for (int i = 0; i < schedule.size(); i++)
        {
            Task currentTask = schedule.get(i);
            TextView columnTextView = new TextView(this);
            columnTextView.setPadding(0, 0, 10, 0);
            columnTextView.setText(currentTask.getId());
            columnTextView.setBackgroundColor(currentTask.getColor());
            tr.addView(columnTextView);
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
