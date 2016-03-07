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
import florian.michel.channelmessaging.login.LoginActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

public class ListChannelsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnWSUpdateListener {

    private static final int MY_ACTIVITY_REQUEST_CODE = 1;
    private ListView lvChannels;
    private ChannelsResponseItems[] listChannels = {};

    private HashMap<String, String> tokens = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_channels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvChannels = (ListView) findViewById(R.id.listView);

        lvChannels.setOnItemClickListener(this);

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_FILE, 0);

        String token = settings.getString("ACCESS_TOKEN", "value");

        this.tokens.put("accesstoken", token);

        WSRequestAsyncTask request = new WSRequestAsyncTask(this.getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=getchannels", tokens);
        request.addWSRequestListener(this);
        request.execute();
    }

    @Override
    public void onItemClick(AdapterView<?>parent, View view, int position, long id) {

        Integer channelID = listChannels[position].getChannelID();

        Intent ChanAct = new Intent(getApplicationContext(), ChannelActivity.class);
        ChanAct.putExtra("channelID", channelID);

        startActivity(ChanAct);
    }

    @Override
    public void onWSResponseUpdate(String response) {
        Gson gson = new Gson();

        ChannelsResponseList channels = gson.fromJson(response, ChannelsResponseList.class);

        listChannels = channels.getItems();

        lvChannels.setAdapter(new ChannelsAdapter(channels.getItems(), this));

        Log.d("reponse: ", listChannels[0].getName());
    }
}
