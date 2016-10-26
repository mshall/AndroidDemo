package com.slidenerd.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slidenerd.R;
import com.slidenerd.util.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    public static final String USER_AWARE_OF_DRAWER = "user_aware_of_drawer";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private boolean isUserAwareOfDrawer, isFromInstanceState;


    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isUserAwareOfDrawer = Boolean.valueOf(SharedPreferencesUtil.getFromSharedPreferences(getActivity(), USER_AWARE_OF_DRAWER, "false"));
        isFromInstanceState = (savedInstanceState != null);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        return view;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        this.drawerLayout = drawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isUserAwareOfDrawer) {
                    isUserAwareOfDrawer = true; //Make the user knwo that he opened it at least one time
                    SharedPreferencesUtil.saveBooleanToSharedPreferences(getActivity(), USER_AWARE_OF_DRAWER, isUserAwareOfDrawer + "");
                }
                getActivity().invalidateOptionsMenu();//Makes the Actionbar redrawn
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();//Makes the Actionbar redrawn
            }
        };
        if (!isUserAwareOfDrawer && !isFromInstanceState) {
            drawerLayout.openDrawer(containerView);
        }
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();//Let the activity know the last state of the drawer
            }
        });

    }
}
