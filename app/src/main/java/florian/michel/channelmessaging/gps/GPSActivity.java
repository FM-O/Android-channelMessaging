package florian.michel.channelmessaging.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Flo on 08/03/2016.
 */
public class GPSActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);

        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mGoogleApiClient = builder.build();

        mGoogleApiClient.connect();


    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Connection successful", String.valueOf(bundle));

        requestLocation();

        Log.d("ObjectLocation", String.valueOf(mlocation));

//        Log.d("Latitude", String.valueOf(mlocation.getLatitude()));
//        Log.d("Longitude", String.valueOf(mlocation.getLongitude()));
    }

    private void requestLocation() {

        if (mGoogleApiClient.isConnected()) {
            this.mlocation.set(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
            this.mlocation.setLatitude(13);
            this.mlocation.setLongitude(100);
           // this.mlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Connection suspended", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Connection failed", String.valueOf(connectionResult));
    }
}
