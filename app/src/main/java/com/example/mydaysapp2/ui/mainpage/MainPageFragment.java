package com.example.mydaysapp2.ui.mainpage;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydaysapp2.MainActivity;
import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.Chat;
import com.example.mydaysapp2.data.model.Group;
import com.example.mydaysapp2.ui.group.ChatAdapter;
import com.example.mydaysapp2.ui.group.callbacks.CallbackGroup;
import com.example.mydaysapp2.ui.group.callbacks.CallbackMessages;
import com.example.mydaysapp2.ui.utils.TabLayoutDisabler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainPageFragment extends Fragment implements TabLayoutDisabler {

    private MainPageViewModel mViewModel;
    private View root;
    private FusedLocationProviderClient fusedLocationClient;

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        showTabLayout();
        root = inflater.inflate(R.layout.main_page_fragment, container, false);
        initWeather(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainPageViewModel.class);

        initTabs();
    }

    private void initTabs() {
        TabAdapter adapter = new TabAdapter(getChildFragmentManager(),getActivity() );
        ViewPager pager = root.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
    }






    public void initWeather(View root) {
        String city = "Gdańsk";
        DownloadWeatherTask downloadWeatherTask = new DownloadWeatherTask();
        downloadWeatherTask.setView(root);
        downloadWeatherTask.execute("http://api.openweathermap.org/data/2.5/weather?q=" + city
                + "&units=metric&appid=a80caa7a9b78c13adb3dfeb72c3ab5f8&lang=pl");

        TextView cityTV = root.findViewById(R.id.cityTV);
        cityTV.setText(city);

            //Todo ogarnąć pobieranie lokalizacji
        /*fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(getContext(), Locale.getDefault());

                            Log.d("location", String.valueOf(location.getLatitude()));
                            try {


                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses != null) {
                                    String address = addresses.get(0).getAddressLine(0);
                                    city = addresses.get(0).getLocality();
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });*/

    }


    @Override
    public void hideTabLayout() {

    }

    @Override
    public void showTabLayout() {
        MainActivity.showTabLayout();
    }
}