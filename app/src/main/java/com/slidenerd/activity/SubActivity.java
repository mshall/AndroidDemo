package com.slidenerd.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.slidenerd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubActivity extends AppCompatActivity {

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.activity_sub)
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_sub_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.app_bar_sub_activity_refresh) {
            Snackbar.make(relativeLayout, "You want to refresh? ", Snackbar.LENGTH_LONG).setAction("Yes? ", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar.make(relativeLayout, "Good, Refresh is done!", Snackbar.LENGTH_SHORT).show();
                }
            }).show();
        } else if (item.getItemId() == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }


        return super.onOptionsItemSelected(item);
    }
}
