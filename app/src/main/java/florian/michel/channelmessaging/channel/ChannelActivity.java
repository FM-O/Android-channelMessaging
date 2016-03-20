package florian.michel.channelmessaging.channel;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.channelfragments.MessageFragment;
import florian.michel.channelmessaging.gps.GPSActivity;

public class ChannelActivity extends GPSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        MessageFragment messageFragment = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragChannelMessage);

        if (mCurrentLocation != null) {
            messageFragment.setLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}


