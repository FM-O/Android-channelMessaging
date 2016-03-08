package florian.michel.channelmessaging.listchannels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.channel.ChannelActivity;
import florian.michel.channelmessaging.channelfragments.ChannelListFragment;
import florian.michel.channelmessaging.channelfragments.MessageFragment;
import florian.michel.channelmessaging.login.LoginActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

public class ListChannelsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


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

        ChannelListFragment chfrag = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragListChannel);
        MessageFragment msgfrag = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragChannelMessage);

        if (msgfrag == null || !msgfrag.isInLayout()) {
            Intent ChanAct = new Intent(getApplicationContext(), ChannelActivity.class);
            ChanAct.putExtra("channelID", channelID);

            startActivity(ChanAct);
        } else {
//            Intent fragAct = new Intent(getApplicationContext(), MessageFragment.class);
//            fragAct.putExtra("channelID", channelID);
//
//            startActivity(fragAct);
            msgfrag.updateChannel(channelID);
        }

        /*Intent ChanAct = new Intent(getApplicationContext(), ChannelActivity.class);
        ChanAct.putExtra("channelID", channelID);

        startActivity(ChanAct);*/
    }

}
