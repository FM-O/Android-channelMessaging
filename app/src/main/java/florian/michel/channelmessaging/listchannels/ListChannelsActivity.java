package florian.michel.channelmessaging.listchannels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.channel.ChannelActivity;
import florian.michel.channelmessaging.channelfragments.MessageFragment;
import florian.michel.channelmessaging.gps.GPSActivity;

public class ListChannelsActivity extends GPSActivity implements AdapterView.OnItemClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_channels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onItemClick(AdapterView<?>parent, View view, int position, long id) {

        Integer channelID = (int)id;

        Log.d("channel id:", String.valueOf(channelID));

//        ChannelListFragment chfrag = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragListChannel);
        MessageFragment msgfrag = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragChannelMessage);

        if (msgfrag == null || !msgfrag.isInLayout()) {
            Intent ChanAct = new Intent(getApplicationContext(), ChannelActivity.class);
            ChanAct.putExtra("channelID", channelID);
            ChanAct.putExtra("Latitude", mCurrentLocation.getLatitude());
            ChanAct.putExtra("Longitude", mCurrentLocation.getLongitude());

            startActivity(ChanAct);
        } else {
            msgfrag.updateChannel(channelID, mCurrentLocation);
        }
    }

}
