package com.schedulingsimulator.schedulingsimulator;

import android.app.DialogFragment;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements PeriodicTaskFragment.OnPeriodicTaskChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void onPeriodicTaskChange(Uri uri)
    {

    }


    public void addPeriodicTask(View view)
    {
        Toast.makeText(this, "Add periodic task", Toast.LENGTH_SHORT).show();
        DialogFragment periodicFragment = new PeriodicTaskFragment();
        periodicFragment.show(getFragmentManager(), "newPeriodicTaskDialog");
    }

    public void addAperiodicTask(View view)
    {
        Toast.makeText(this, "Add aperiodic task", Toast.LENGTH_SHORT).show();
    }
}
