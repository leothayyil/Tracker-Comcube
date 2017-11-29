package com.example.user.tracker;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity

        implements View.OnClickListener

{

    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;

    private Button btnGetLocation = null;
    private TextView editLocation = null;
    private ProgressBar pb = null;

    private static final String TAG = "Debug";

    DatabaseHelper myDb;
    EditText shopName;
    Button btnIn, btnVisited,btnOut;
    int id = 1;


    String inTime = "";
    String inLocation;
    String outTime = "";
    String outLocation = "";
    StringBuffer shpDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        myDb = new DatabaseHelper(this);
        btnIn = (Button) findViewById(R.id.btnIn);
        btnOut = (Button) findViewById(R.id.btnOut);
        shopName = (EditText) findViewById(R.id.shopName);
        btnVisited = (Button) findViewById(R.id.btnView);
        inTime = DateFormat.getDateTimeInstance().format(new Date());
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        String cityName=null;
        Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
        List<Address>  addresses;
        try {
            addresses = gcd.getFromLocation(latitude,longitude, 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName=addresses.get(0).getLocality();

            inLocation=cityName+"("+location.getLatitude()+","+location.getLongitude()+")";

        } catch (IOException e) {
            e.printStackTrace();
        }

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        editLocation = (TextView) findViewById(R.id.editTextLocation);

        btnGetLocation = (Button) findViewById(R.id.btnIn);
        btnGetLocation = (Button) findViewById(R.id.btnOut);

        locationListener=new MyLocationListener();


        locationMangaer = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        upDateData();
        AddData();
        ViewAll();

    }

    private void ViewAll() {
        btnVisited.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID :"+ res.getString(0)+"\n");
                            buffer.append("SHOP :"+ res.getString(1)+"\n");
                            buffer.append("IN_TIME :"+ res.getString(2)+"\n");
                            buffer.append("IN_LOCATION :"+ res.getString(3)+"\n");
                            buffer.append("OUT_TIME :"+ res.getString(3)+"\n");
                            buffer.append("OUT_LOCATION :"+ res.getString(3)+"\n\n");

                            shpDB = buffer.append("SHOP :" + res.getString(1) + "\n");
                        }

                        Toast.makeText(MainActivity.this, buffer.toString()+inLocation, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void AddData() {

        locationListener=new MyLocationListener();
        btnIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (shopName.getText().toString()!=null){

                            boolean isInserted = myDb.insertData(shopName.getText().toString(),inTime,
                                    inLocation,outTime,outLocation );
                            if(isInserted == true){

                                Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                                shopName.setVisibility(View.GONE);
                            }

                            else
                                Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(MainActivity.this, "Please the fields", Toast.LENGTH_SHORT).show();
                        
                    }
    }
        );
    }

    private void upDateData() {
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isUpdate=myDb.updateData(null,inTime,inLocation);

                if (isUpdate==true)
                    Toast.makeText(MainActivity.this, "You are Out", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Still in "+ shpDB, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        boolean flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");

            editLocation.setText("Please wait while  getting location !!");

            pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationMangaer.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);

            } else {
                alertbox("Gps Status!!", "Your GPS is: OFF");
            }

        }
        private Boolean displayGpsStatus() {
            ContentResolver contentResolver = getBaseContext()
                    .getContentResolver();
            boolean gpsStatus = Settings.Secure
                    .isLocationProviderEnabled(contentResolver,
                            LocationManager.GPS_PROVIDER);
            if (gpsStatus) {
                return true;

            } else {
                return false;
            }
        }

        protected void alertbox(String title, String mymessage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your Device's GPS is Disable")
                    .setCancelable(false)
                    .setTitle("** Gps Status **")
                    .setPositiveButton("Gps On",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent myIntent = new Intent(
                                            Settings.ACTION_SECURITY_SETTINGS);
                                    startActivity(myIntent);
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        private class MyLocationListener implements LocationListener {
            @Override
            public void onLocationChanged(Location loc) {

                editLocation.setText("");
                pb.setVisibility(View.INVISIBLE);


                String longitude = "Longitude: " +loc.getLongitude();
                Log.v(TAG, longitude);
                String latitude = "Latitude: " +loc.getLatitude();
                Log.v(TAG, latitude);


                String cityName=null;
                Geocoder gcd = new Geocoder(getBaseContext(),
                        Locale.getDefault());
                List<Address>  addresses;
                try {
                    addresses = gcd.getFromLocation(loc.getLatitude(), loc
                            .getLongitude(), 1);
                    if (addresses.size() > 0)
                        System.out.println(addresses.get(0).getLocality());
                    cityName=addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                inLocation = longitude+"\n"+latitude +
                        "\n\nMy Currrent City is: "+cityName;
                editLocation.setText(inLocation);


                Log.i(TAG,inLocation);


            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onStatusChanged(String provider,
                                        int status, Bundle extras) {

            }
        }

    }
