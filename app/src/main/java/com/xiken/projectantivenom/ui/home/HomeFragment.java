package com.xiken.projectantivenom.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;



import com.google.firebase.database.DatabaseReference;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.xiken.projectantivenom.PhotoScreenActivity;
import com.xiken.projectantivenom.R;



import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    public static final int SMS_REQUEST = 102;
    public static final int CAMERA_REQUEST_PERMISSION = 101;


    private HomeViewModel homeViewModel;
    CardView button;
    Uri imageUri;
    CardView button1;
    Bitmap bitmap = null;
    public static boolean hasCamera = false;
    CardView plant;
    public static int intentNUmber;
    DatabaseReference databaseReference;
    ImageView cardViewImageView;
    String abhinav = "9304838572";
    String gouri = "8249766641";
    CarouselView carouselView;
    double latitude = -1;
    double longitude = -1;
    List<Address> locationName = null;
    String pincode = null;
    String addressLine = null;
    LocationListener locationListener;
    int[] imageSources = {R.raw.one_two, R.raw.two_two, R.raw.three_two, R.raw.four_two, R.raw.five_two};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.activity_main, container, false);

        //main method
        button = root.findViewById(R.id.snake_is_near_me);
        button1 = root.findViewById(R.id.forest_department);
        carouselView = root.findViewById(R.id.carouselView);
        carouselView.setPageCount(imageSources.length);
        Log.d(TAG, "onCreateView: home fragment created");
        carouselView.setImageListener(imageListener);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    processImage();

                }

            }
        });
        final int[] arrayOfImage = {R.raw.one, R.raw.two, R.raw.three, R.raw.four, R.raw.five};
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                hasCamera = false;
                intentNUmber = 2;
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, 0);
                return true;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) root.getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                    } else {
                        initLocationLister();
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 50000, locationListener);


                        Geocoder geocoder = new Geocoder(root.getContext());
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location == null){
                            latitude = 21.49555;
                            longitude = 83.90623;
                        }


                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                            try {
                                locationName = geocoder.getFromLocation(latitude, longitude, 2);
                                for (int i = 0; i < locationName.size(); i++) {
                                    Log.d(TAG, "onClick: " + locationName.get(i).getLatitude());
                                }
                                pincode = locationName.get(0).getPostalCode();
                                addressLine = locationName.get(0).getLocality();
                                Log.d(TAG, "onClick: pincode" + pincode);
                                Log.d(TAG, "onClick: addressLine" + addressLine);
                                Log.d(TAG, "onClick: " + locationName.get(0).toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    }


                }else {
                    initLocationLister();
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 50000, locationListener);


                    Geocoder geocoder = new Geocoder(root.getContext());
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location == null){
                        latitude = 21.49555;
                        longitude = 83.90623;
                    }

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    try {
                        locationName = geocoder.getFromLocation(latitude, longitude, 2);
                        for (int i = 0; i < locationName.size(); i++) {
                            Log.d(TAG, "onClick: " + locationName.get(i).getLatitude());
                        }
                        pincode = locationName.get(0).getPostalCode();
                        addressLine = locationName.get(0).getLocality();
                        Log.d(TAG, "onClick: pincode" + pincode);
                        Log.d(TAG, "onClick: addressLine" + addressLine);
                        Log.d(TAG, "onClick: " + locationName.get(0).toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) root.getContext(), new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST);
                    return;
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("8249766641", "", "Help me  \nlatitude =" + latitude + "\n" + "longitude" + longitude + "\n" + "pincode = " + pincode + "\naddressLine = " + addressLine + "  " + "view in map " + "http://maps.google.com/maps?daddr=" + latitude + "," + longitude, null, null);
//                    smsManager.sendTextMessage(abhinav, "", "Help me  \nlatitude =" + latitude + "\n" + "longitude" + longitude + "\n" + "pincode = " + pincode + "\naddressLine = " + addressLine + "  " + "view in map " + "http://maps.google.com/maps?daddr=" + latitude + "," + longitude, null, null);
//                    smsManager.sendTextMessage("6370983499", "", "Help me  \nlatitude =" + latitude + "\n" + "longitude" + longitude + "\n" + "pincode = " + pincode + "\naddressLine = " + addressLine + "  " + "view in map " + "http://maps.google.com/maps?daddr=" + latitude + "," + longitude, null, null);

                }


                Log.d(TAG, "onClick: " + latitude);
                Log.d(TAG, "onClick: " + longitude);



                Intent intent = new Intent(getContext(), Emergency.class);
                intent.putExtra("latitude", String.valueOf(latitude));
                intent.putExtra("longitude", String.valueOf(longitude));
                intent.putExtra("pincode", pincode);
                intent.putExtra("addressLine", addressLine);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


            }

            private void initLocationLister() {
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };
            }
        });


        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (intentNUmber == 1) {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
                Intent intent = new Intent(getContext(), PhotoScreenActivity.class);

                intent.putExtras(data.getExtras());
                startActivity(intent);
            }
        } else if (intentNUmber == 2) {

            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
                Intent intent = new Intent(getContext(), PhotoScreenActivity.class);
                intent.putExtra("Uri", data.getData().toString());

                startActivity(intent);
                imageUri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            }
        } else if (intentNUmber == 3) {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
                Intent intent = new Intent(getContext(), PlantDetction.class);
                intent.putExtras(data.getExtras());
                startActivity(intent);
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == SMS_REQUEST) {
            if (data != null) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(abhinav, null, "Help me", null, null);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("volley", "onRequestPermissionsResult:called ");
        if (requestCode == CAMERA_REQUEST_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                processImage();
                Log.d("volley", "onRequestPermissionsResult: success");


            }


        }
        if (requestCode == SMS_REQUEST) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(abhinav, null, "Help me", null, null);
                }
            }
        }
        if (requestCode == 200 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }

    }

    private void processImage() {
        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_warning_alert)
                .setTitle("Alert")
                .setMessage("Images without snakes develop random results.\nThis feature only works when the image contains a snake.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        hasCamera = true;
                        intentNUmber = 1;


                        startActivityForResult(intent, 0);

                    }
                }).show();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(imageSources[position]);
        }
    };
}