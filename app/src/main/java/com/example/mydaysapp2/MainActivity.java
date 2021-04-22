package com.example.mydaysapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mydaysapp2.data.model.Group;
import com.example.mydaysapp2.ui.group.GroupFragment;
import com.example.mydaysapp2.ui.group.callbacks.SendDataToOtherFragment;
import com.example.mydaysapp2.ui.mainpage.MainPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static BottomNavigationView bottomNavigationView;
    private NavController navController;
    private static View toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
         initBottomTabNav();
         toolbar = findViewById(R.id.toolbar);

    }



    private void initBottomTabNav() {
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    public static void showTabLayout() {
        if (bottomNavigationView.getVisibility()==View.GONE) {
            bottomNavigationView.setVisibility(VISIBLE);
            toolbar.setVisibility(VISIBLE);
        }
    }

    public static void hideTabLayout() {
        bottomNavigationView.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
    }

}