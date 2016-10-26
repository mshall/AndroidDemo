package com.slidenerd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.slidenerd.R;
import com.slidenerd.fragment.NavigationDrawerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.activity_main)
    RelativeLayout rlMain;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigationDrawerFragment);
        navigationDrawerFragment.setup(R.id.navigationDrawerFragment, drawerLayout, toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_menu_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.app_bar_menu_next:
                startActivity(new Intent(this, SubActivity.class));
                break;
            case R.id.app_bar_menu_settings:
                Snackbar.make(rlMain, "You clicked settings", Snackbar.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
