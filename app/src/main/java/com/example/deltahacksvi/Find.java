package com.example.deltahacksvi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Find extends AppCompatActivity {

    private double current_latitude;
    private double current_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("safadf","find reached");

        setContentView(R.layout.activity_find);

        Button clickMeFindButton =  (Button) findViewById(R.id.clickMeFindButton);
        clickMeFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    // check self permission, if true, run line
                    calculateLatLong();
                    read();

                }

            }
        });
//
//        Button showMapButton =  (Button) findViewById(R.id.showMapButton);
//        showMapButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(this,Map.class);
////                startActivity(intent);
//            }
//        });



    }

    private boolean checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Find.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(Find.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return false;

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        } else {
            // Permission has already been granted
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    calculateLatLong();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void calculateLatLong() {
        Log.i("asfafa","hi2");
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        current_longitude = location.getLongitude();
//        current_latitude = location.getLatitude();


        //Cathedral
//        current_latitude = 43.26097061;
//        current_longitude = -79.92282154;


        //Mountain
        current_latitude = 43.22;
        current_longitude = -79.89;


        Log.i("asfasdf",Double.toString(current_latitude));
        Log.i("assdfdsffasdf",Double.toString(current_longitude));

    }

    //Distance
    private static double distance(double lat1, double long1, double lat2, double long2) {
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        long1 = Math.toRadians(long1);
        long2 = Math.toRadians(long2);
        double delta_lat = lat2 - lat1;
        double delta_long = long2 - long1;
        final int RADIUS = 6371;
        double a = Math.pow(Math.sin(delta_lat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(delta_long) / 2, 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = RADIUS * c;

        return d;
    }

    private void read() {
        final String dir = System.getProperty("user.dir");
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("locations",
                        "raw", getPackageName()));
        Scanner scanner = new Scanner(ins);
        String headers = scanner.nextLine();
        int count = 0;
        int rowNum = 0;
        double minDistance = 1000000000; //TODO:change
        List<List<String>> data2d = new ArrayList<List<String>>();
        while (scanner.hasNextLine() && count != 14) {
            List<String> tempList = new ArrayList<String>(Arrays.asList(scanner.nextLine().split(",")));
            data2d.add(tempList);
            Log.i("sef","array="+Arrays.toString(tempList.toArray()));
            double latitude = Double.parseDouble(tempList.get(5));
            double longitude = Double.parseDouble(tempList.get(6));
//            call distance function, if min update mindistance and rownum
            double distance = distance(current_latitude,current_longitude,latitude,longitude);
            if (distance < minDistance) {
                minDistance = distance;
                rowNum = count;
            }
            count++;
        }
        scanner.close();
        Log.i("saf","rowNUm"+Integer.toString(rowNum));
        Log.i("asfd",Arrays.toString(data2d.get(rowNum).toArray()));

        List<String> target = data2d.get(rowNum);
        String name = target.get(0);
        String address = target.get(1);
        String postalCode = target.get(2);
        String phoneNum = target.get(3);
        String email = target.get(4);

        //Update text view
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView addressTextView = (TextView) findViewById(R.id.addressTextView);
        TextView postalCodeTextView = (TextView) findViewById(R.id.postalCodeTextView);
        TextView phoneNumTextView = (TextView) findViewById(R.id.phoneNumTextView);
        TextView emailTextView = (TextView) findViewById(R.id.emailTextView);

        nameTextView.setText(name);
        addressTextView.setText(address);
        postalCodeTextView.setText(postalCode);
        phoneNumTextView.setText(phoneNum);
        emailTextView.setText(email);


    }





}