package com.dreamguard.stereo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.dreamguard.stereo.ui.fragment.MyFragment;
import com.dreamguard.stereo.ui.fragment.NewsFragment;
import com.dreamguard.stereo.ui.fragment.SelectionFragment;

/**
 * Created by hailin on 17-7-7.
 */

public class HomeActivity extends AppCompatActivity {


    private NewsFragment newsFragment;

    private SelectionFragment selectionFragment;

    private MyFragment myFragment;

    private Fragment currentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(newsFragment,"news");
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(selectionFragment,"selection");
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(myFragment,"my");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        newsFragment = new NewsFragment();
        selectionFragment = new SelectionFragment();
        myFragment = new MyFragment();

        switchFragment(newsFragment,"news");
    }

    private void switchFragment(Fragment fragment, String title) {

        if (currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setTitle(title);
        }
    }

}
