package com.dreamguard.stereo.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.dreamguard.stereo.R;
import com.dreamguard.stereo.ui.fragment.MyFragment;
import com.dreamguard.stereo.ui.fragment.NewsFragment;
import com.dreamguard.stereo.ui.fragment.SelectionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hailin on 17-7-7.
 */

public class HomeActivity extends AppCompatActivity {


    private NewsFragment newsFragment;

    private SelectionFragment selectionFragment;

    private MyFragment myFragment;

    private Fragment currentFragment;

    @BindView(R.id.title)
    TextView mTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_hot:
                    switchFragment(newsFragment,R.string.hot);
                    return true;
                case R.id.navigation_selection:
                    switchFragment(selectionFragment,R.string.selection);
                    return true;
                case R.id.navigation_me:
                    switchFragment(myFragment,R.string.me);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        newsFragment = new NewsFragment();
        selectionFragment = new SelectionFragment();
        myFragment = new MyFragment();

        switchFragment(newsFragment,R.string.hot);
    }

    private void switchFragment(Fragment fragment, int title) {

        if (currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            mTitle.setText(title);
        }
    }

}
