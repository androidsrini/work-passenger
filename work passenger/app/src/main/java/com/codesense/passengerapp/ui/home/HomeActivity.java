package com.codesense.passengerapp.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.ui.drawer.DrawerActivity;
import com.codesense.passengerapp.ui.helper.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class HomeActivity extends DrawerActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = HomeActivity.class.getName();
    private static final float DISABLE_ALPHA = 0.8f;
    private static final float ENABLE_ALPHA = 1f;
    ImageView toolbarClose, mapPinImageView;
    LinearLayout ll_btn;
    Button btnRideLater,btnRideNow;
    RecyclerView recyclerView;
    private FrameLayout pickupCardView, dropCardView;
    private ImageView pickupDownArrowImageView, deliveryDownArrowImageView, dottedWayLineImageView, pickupFavImageView;
    private ConstraintLayout homeConstraintLayout;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest.Builder locationSettingsRequest;
    private PendingResult<LocationSettingsResult> pendingResult;
    private static final int DEFAULT_INTERVAL_TIME = 10 * 1000;
    private static final int INTERVAL_TIME = 1000;
    public static final int REQUEST_LOCATION = 001;
    private boolean mResolvingError = false;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private Location lastLocation;
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    LocationManager locationManager;
    public boolean isGPSEnabled;
    GoogleMap map;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean isPinAnimationCompleted;
    //private ConstraintSet constraintSet;

    public static void start(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.activity_home, null, false);
        frameLayout.addView(contentView);
        initialize();
    }

    private void initialize() {
        homeConstraintLayout = findViewById(R.id.homeConstraintLayout);
        toolbarClose = findViewById(R.id.toolbarClose);
        ll_btn = findViewById(R.id.ll_btn);
        btnRideLater = findViewById(R.id.btnRideLater);
        btnRideNow = findViewById(R.id.btnRideNow);
        recyclerView = findViewById(R.id.recyclerView);
        mapPinImageView = findViewById(R.id.mapPinImageView);
        pickupCardView = findViewById(R.id.pickupCardView);
        dropCardView = findViewById(R.id.dropCardView);
        pickupDownArrowImageView = findViewById(R.id.pickupDownArrowImageView);
        deliveryDownArrowImageView = findViewById(R.id.deliveryDownArrowImageView);
        dottedWayLineImageView = findViewById(R.id.dottedWayLineImageView);
        pickupFavImageView = findViewById(R.id.pickupFavImageView);
        //ConstraintSet object create
        /*constraintSet = new ConstraintSet();
        constraintSet.clone(homeConstraintLayout);*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //assert mapFragment != null;
        mapFragment.getMapAsync(this);

        recyclerView.setAdapter(new HomeCarTypeAdapter(this, screenWidth, screenHeight));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        locationManager = (LocationManager) this.getSystemService(
                Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(HomeActivity.this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
            setLocationSetting();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            checkPermission();
        }
        pickupCardView.setOnClickListener(this::handlePickupAndDeliveryClick);
        dropCardView.setOnClickListener(this::handlePickupAndDeliveryClick);
        handlePickupAndDeliveryClick(pickupCardView);
    }

    /**
     * This method to update topToBottom param value for dottedWayLineImageView view.
     * @param resourceId
     */
    private void updateDottedWayLineTopParamUI(int resourceId) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) dottedWayLineImageView.getLayoutParams();
        layoutParams.topToBottom = resourceId;
        dottedWayLineImageView.setLayoutParams(layoutParams);
    }

    private void updateDropViewTopParamUI(int resourceId) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) dropCardView.getLayoutParams();
        layoutParams.topToBottom = resourceId;
        dropCardView.setLayoutParams(layoutParams);
    }

    private void handlePickupAndDeliveryClick(View v) {
        switch (v.getId()) {
            case R.id.pickupCardView:
                dropCardView.setAlpha(DISABLE_ALPHA);
                pickupCardView.setAlpha(ENABLE_ALPHA);
                deliveryDownArrowImageView.setVisibility(View.GONE);
                pickupDownArrowImageView.setVisibility(View.VISIBLE);
                pickupFavImageView.setVisibility(View.VISIBLE);
                updateDropViewTopParamUI(R.id.pickupDownArrowImageView);
                updateDottedWayLineTopParamUI(R.id.pickupDownArrowImageView);
            break;
            case R.id.dropCardView:
                pickupCardView.setAlpha(DISABLE_ALPHA);
                dropCardView.setAlpha(ENABLE_ALPHA);
                pickupDownArrowImageView.setVisibility(View.GONE);
                pickupFavImageView.setVisibility(View.GONE);
                deliveryDownArrowImageView.setVisibility(View.VISIBLE);
                updateDropViewTopParamUI(R.id.pickupCardView);
                updateDottedWayLineTopParamUI(R.id.deliveryDownArrowImageView);
            break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notificationMenu:
                Utils.GetInstance().showToastMsg("Notificaiton clicked");
                Log.d(TAG, " Notification menu clicked");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    MULTIPLE_PERMISSIONS);
        }
    }

    public void setLocationSetting() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(DEFAULT_INTERVAL_TIME);
        locationRequest.setFastestInterval(2000);
        locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        showLocationPermissionDialog();
    }

    public void showLocationPermissionDialog() {
        pendingResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, locationSettingsRequest.build());
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        checkLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(HomeActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    public void checkLocation() {
        locationManager = (LocationManager) this.getSystemService(
                Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
            try {
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (lastLocation != null) {
                    updateMapUI();
                } else {
                    startLocationUpdates();
                }

            } catch (Exception e) {
                Log.e("exceptionLocation", "" + e);
            }


    }
    private void updateMapUI(){
        //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_pointer);

        //MarkerOptions markerOptions = new MarkerOptions();

        LatLng latLng = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        // Setting the position for the marker
        //markerOptions.position(latLng);
        //markerOptions.icon(-1);
        //map.addMarker(markerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(17).build();
        //Zoom in and animate the camera.
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.setOnCameraMoveListener(this::showAnimationMarker);
    }

    private void showAnimationMarker() {
        if (!isPinAnimationCompleted) {
            TranslateAnimation transAnim = new TranslateAnimation(0, 0, -screenHeight / 10, 0);
            transAnim.setStartOffset(500);
            transAnim.setDuration(3000);
            transAnim.setRepeatCount(0);
            transAnim.setRepeatMode(Animation.REVERSE);
            transAnim.setInterpolator(new BounceInterpolator());
            transAnim.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {


                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isPinAnimationCompleted = true;
                    final int left = mapPinImageView.getLeft();
                    final int top = mapPinImageView.getTop();
                    final int right = mapPinImageView.getRight();
                    final int bottom = mapPinImageView.getBottom();
                    mapPinImageView.layout(left, top, right, bottom);

                }
            });
            mapPinImageView.startAnimation(transAnim);
        }
    }


    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        Log.i(TAG, "Name: " + name);
//                        Log.i(TAG, "Phone Number: " + phoneNo);

                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLatestLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
//            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected())
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                locationRequest, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (lastLocation == null || location.getAccuracy() < lastLocation.getAccuracy()) {
                            lastLocation = location;
                        }
                        updateLatestLocation();
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                    }
                });
    }

    private void updateLatestLocation() {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation == null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkLocation();
                }
            }, 2000);
        } else {
            checkLocation();
        }
    }

    @Override
    public void onPause() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.unregisterConnectionCallbacks(this);
            mGoogleApiClient.unregisterConnectionFailedListener(this);
        }
        super.onPause();

    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mGoogleApiClient.disconnect();

        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] arg1,
                                           int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, arg1, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // screenDisplay();
                    checkLocation();

                } else {

                    //  screenDisplay();
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            double latop = location.getLatitude();
            double longe = location.getLongitude();
            lastLocation = location;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                if (!hasLocationPermissionEnabled()) {
                    //It will occurred when app go background and come foreground.
                    showAppRuntimePermissionDialog();
                } else {
                    updateLatestLocation();
                }
                break;
            case Activity.RESULT_CANCELED:
                break;
            default:
                break;
        }
    }
    private void showAppRuntimePermissionDialog() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            int result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    MULTIPLE_PERMISSIONS);
        }
    }
    private boolean hasLocationPermissionEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager != null && !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        for (String p : permissions) {
            int result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
