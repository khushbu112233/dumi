package com.ample.dumi.Activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ample.dumi.Fragments.LoanFragment;
import com.ample.dumi.Fragments.NameCardFragment;
import com.ample.dumi.LocationUtil.PermissionUtils;
import com.ample.dumi.R;
import com.ample.dumi.Utils.util;
import com.ample.dumi.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.ample.dumi.Utils.Utility.decrypt;
import static com.ample.dumi.Utils.util.MIME_TEXT;
import static com.ample.dumi.Utils.util.secretKey;

public class MainActivity extends AppCompatActivity implements PermissionUtils.PermissionResultCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    private Fragment fragment = null;
    public static ActivityMainBinding activityMainBinding;
    boolean doubleBackToExitPressedOnce = false;
    private NfcAdapter mNfcAdapter;
    public static final byte[] MIME_TEXT1 = "application/com.amplearch.circleone".getBytes();
    boolean done = false;
    ArrayList<String> arrayNFC  = new ArrayList<>();
    Boolean netCheck= false;
    private String nfcProfileId = "";
    public static Location mLastLocation;
    public static boolean isPermissionGranted;
    GoogleSignInOptions gso;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    public static GoogleApiClient mGoogleApiClient;
    public double latitude;
    public double longitude;
    String lat = "", lng = "";
    String CardCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        fragment = new LoanFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_wrapper, fragment)
                .addToBackStack(null).commit();
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        netCheck = util.isNetworkAvailable(getApplicationContext());
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        activityMainBinding.includeTabbar.lnrTabLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.VISIBLE);
                activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.INVISIBLE);

                fragment = new LoanFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_wrapper, fragment)
                            .addToBackStack(null)
                            .commit();

                setActionBarTitle("Loan");
            }
        });


        activityMainBinding.includeTabbar.lnrTabNameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.INVISIBLE);
                activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.VISIBLE);

                fragment = new NameCardFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_wrapper, fragment)
                        .addToBackStack(null)
                        .commit();
                setActionBarTitle("Name Cards");
            }
        });
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this,resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    public static void setActionBarTitle(String title) {
        activityMainBinding.includeActionbar.txtActionBarTitle.setText(title);

    }

    public Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container_wrapper);
        //    Log.e("currentFragment",""+currentFragment);
        return currentFragment;

    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
        isPermissionGranted=true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

    @Override
    public void onBackPressed() {
        if (getCurrentFragment() instanceof LoanFragment){
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();

            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

        else if (getCurrentFragment() instanceof NameCardFragment){

            activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.VISIBLE);
            activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.INVISIBLE);

            fragment = new LoanFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container_wrapper, fragment)
                    .addToBackStack(null)
                    .commit();

            setActionBarTitle("Loan");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    public static void getLocation() {

        if (isPermissionGranted) {
            try
            {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
        }

    }

    protected synchronized void buildGoogleApiClient() {
       /* mGoogleApiClient = new GoogleApiClient.Builder(CardsActivity.this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .build();*/


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    @Override
    public void onNewIntent(final Intent paramIntent) {
        super.onNewIntent(paramIntent);

        String action = paramIntent.getAction();
        Tag tag = paramIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        String s = "";

        // parse through all NDEF messages and their records and pick text type only
        Parcelable[] data = paramIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (data != null) {
            try {
                arrayNFC = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    NdefRecord[] recs = ((NdefMessage)data[i]).getRecords();
                    for (int j = 0; j < recs.length; j++) {
                        if (recs[j].getTnf() == NdefRecord.TNF_MIME_MEDIA && Arrays.equals(recs[j].getType(), MIME_TEXT)) {

                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                            int langCodeLen = payload[0] & 0077;

                            s += ("\n" +
                                    new String(payload, langCodeLen + 1,
                                            payload.length - langCodeLen - 1, textEncoding) );
                            String s1 = new String(payload, langCodeLen + 1,
                                    payload.length - langCodeLen - 1, textEncoding);
                            String decryptstr = decrypt(s1, secretKey);
                            arrayNFC.add(decryptstr);
                        }
                        else if (recs[j].getTnf() == NdefRecord.TNF_MIME_MEDIA && Arrays.equals(recs[j].getType(), MIME_TEXT1)) {

                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                            int langCodeLen = payload[0] & 0077;

                            s += ("\n" +
                                    new String(payload, langCodeLen + 1,
                                            payload.length - langCodeLen - 1, textEncoding) );
                            String s1 = new String(payload, langCodeLen + 1,
                                    payload.length - langCodeLen - 1, textEncoding);
                            String decryptstr = decrypt(s1, secretKey);
                            arrayNFC.add(decryptstr);
                        }
                        else {
                            try {


                                NdefMessage[] msgs = null;
                                msgs = new NdefMessage[data.length];
                                for (int i1 = 0; i1 < data.length; i1++) {
                                    msgs[i1] = (NdefMessage) data[i1];
                                }

                                byte[] payload = msgs[0].getRecords()[0].getPayload();

                                String message = new String(payload);

                                // mEtMessage.setText(new String(payload));

                                message = message.substring(1, message.length());

                                String decryptstr = decrypt(message, secretKey);
                                arrayNFC.add(decryptstr);

                            } catch (GeneralSecurityException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }

        }
        String ProfileId = "", card_code = "";

        if (netCheck == false){
            netCheck = util.isNetworkAvailable(getApplicationContext());
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.net_check), Toast.LENGTH_LONG).show();
        }
        else {


            //Toast.makeText(getApplicationContext(), arrayNFC.toString(), Toast.LENGTH_LONG).show();
            if (arrayNFC.size() == 1) {
                nfcProfileId = arrayNFC.get(0).toString();


                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                } else {
                    lat = "";
                    lng = "";
                    getLocation();
                    //  Toast.makeText(getApplicationContext(), "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_LONG).show();
                }
                //  Toast.makeText(getApplicationContext(), String.valueOf(latitude + " " + longitude), Toast.LENGTH_LONG).show();

                try {
                  //  new HttpAsyncTask().execute(util.BASE_URL + "FriendConnection_Operation");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                //  txtNoGroup.setText("Your Card is already verified..");
                //  ivAddCard.setVisibility(View.GONE);
            } else if (arrayNFC.size() == 2) {
                nfcProfileId = arrayNFC.get(0).toString();
                CardCode = arrayNFC.get(1).toString();


                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                } else {
                    lat = "";
                    lng = "";
                    getLocation();
                    // Toast.makeText(getApplicationContext(), "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_LONG).show();
                }
                //  Toast.makeText(getApplicationContext(), String.valueOf(latitude + " " + longitude), Toast.LENGTH_LONG).show();

                try {
                    //new HttpAsyncTask().execute(util.BASE_URL + "FriendConnection_Operation");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(getApplicationContext(), ProfileId + " " + CardCode, Toast.LENGTH_LONG).show();
                // new HttpAsyncActivateNFC().execute(Utility.BASE_URL+"NFCSecurity/ActivateNFC");
            } else {
                nfcProfileId = "";
                Toast.makeText(getApplicationContext(), "Please use only CircleOne NFC-Card for unlock", Toast.LENGTH_LONG).show();
                //txtNoGroup.setText("Your Card is already verified..");
                //ivAddCard.setVisibility(View.GONE);
            }

            activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.INVISIBLE);
            activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.VISIBLE);

            fragment = new NameCardFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container_wrapper, fragment)
                    .addToBackStack(null)
                    .commit();
            setActionBarTitle("Name Cards");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!done) {
            NdefMessage[] msgs = null;

            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
                Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

                if (rawMsgs != null) {
                    try {
                        arrayNFC = new ArrayList<>();
                        for (int i = 0; i < rawMsgs.length; i++) {
                            NdefRecord[] recs = ((NdefMessage) rawMsgs[i]).getRecords();
                            for (int j = 0; j < recs.length; j++) {
                                if (recs[j].getTnf() == NdefRecord.TNF_MIME_MEDIA &&
                                        Arrays.equals(recs[j].getType(), MIME_TEXT)) {

                                    byte[] payload = recs[j].getPayload();
                                    String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                                    int langCodeLen = payload[0] & 0077;

                                    /*s += ("\n" +
                                            new String(payload, langCodeLen + 1,
                                                    payload.length - langCodeLen - 1, textEncoding) );
*/
                                    String s1 = new String(payload, langCodeLen + 1,
                                            payload.length - langCodeLen - 1, textEncoding);
                                    String decryptstr = decrypt(s1, secretKey);
                                    arrayNFC.add(decryptstr);
                                } else if (recs[j].getTnf() == NdefRecord.TNF_MIME_MEDIA && Arrays.equals(recs[j].getType(), MIME_TEXT1)) {

                                    byte[] payload = recs[j].getPayload();
                                    String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                                    int langCodeLen = payload[0] & 0077;

                                   /* s += ("\n" +
                                            new String(payload, langCodeLen + 1,
                                                    payload.length - langCodeLen - 1, textEncoding) );*/
                                    String s1 = new String(payload, langCodeLen + 1,
                                            payload.length - langCodeLen - 1, textEncoding);
                                    String decryptstr = decrypt(s1, secretKey);
                                    arrayNFC.add(decryptstr);
                                } else {
                                    try {
                                        msgs = new NdefMessage[rawMsgs.length];
                                        for (int i1 = 0; i1 < rawMsgs.length; i1++) {
                                            msgs[i1] = (NdefMessage) rawMsgs[i1];
                                        }

                                        byte[] payload = msgs[0].getRecords()[0].getPayload();

                                        String message = new String(payload);
                                        /* 把tag的資訊放到textview裡面 */
                                        // mEtMessage.setText(new String(payload));

                                        message = message.substring(1, message.length());

                                        String decryptstr = decrypt(message, secretKey);
                                        arrayNFC.add(decryptstr);

                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("TagDispatch", e.toString());
                    }

                }
                String ProfileId = "", card_code = "";

                if (netCheck == false) {
                    netCheck = util.isNetworkAvailable(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.net_check), Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), arrayNFC.toString(), Toast.LENGTH_LONG).show();
                    if (arrayNFC.size() == 1) {
                        nfcProfileId = arrayNFC.get(0).toString();
                        //  txtNoGroup.setText("Your Card is already verified..");
                        //  ivAddCard.setVisibility(View.GONE);
                        if (mLastLocation != null) {
                            latitude = mLastLocation.getLatitude();
                            longitude = mLastLocation.getLongitude();
                        } else {
                            lat = "";
                            lng = "";
                            getLocation();
                            //  Toast.makeText(getApplicationContext(), "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_LONG).show();
                        }

                        try {
                          //  new HttpAsyncTask().execute(util.BASE_URL + "FriendConnection_Operation");
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else if (arrayNFC.size() == 2) {
                        nfcProfileId = arrayNFC.get(0).toString();
                        CardCode = arrayNFC.get(1).toString();

                        if (mLastLocation != null) {
                            latitude = mLastLocation.getLatitude();
                            longitude = mLastLocation.getLongitude();
                        } else {
                            lat = "";
                            lng = "";
                            getLocation();
                            // Toast.makeText(getApplicationContext(), "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_LONG).show();
                        }


                        try {
                           // new HttpAsyncTask().execute(util.BASE_URL + "FriendConnection_Operation");
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                        // Toast.makeText(getApplicationContext(), ProfileId + " " + CardCode, Toast.LENGTH_LONG).show();
                        // new HttpAsyncActivateNFC().execute(Utility.BASE_URL+"NFCSecurity/ActivateNFC");
                    } else {
                        nfcProfileId = "";
                        Toast.makeText(getApplicationContext(), "Please use only CircleOne NFC-Card for unlock", Toast.LENGTH_LONG).show();
                        //txtNoGroup.setText("Your Card is already verified..");
                        //ivAddCard.setVisibility(View.GONE);
                    }
                }

                activityMainBinding.includeTabbar.imgLoanSelected.setVisibility(View.INVISIBLE);
                activityMainBinding.includeTabbar.imgNameSelected.setVisibility(View.VISIBLE);

                fragment = new NameCardFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container_wrapper, fragment)
                        .addToBackStack(null)
                        .commit();
                setActionBarTitle("Name Cards");

                done = true;
            }

            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
            IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected, tagDetected, ndefDetected};

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            if (mNfcAdapter != null)
                mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);


        }


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
