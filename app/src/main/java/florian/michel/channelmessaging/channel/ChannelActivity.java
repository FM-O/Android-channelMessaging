package florian.michel.channelmessaging.channel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.login.LoginActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

public class ChannelActivity extends AppCompatActivity implements OnWSUpdateListener {

    private HashMap<String,String> requestedParams = new HashMap<>();
    private ListView lvMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvMessages = (ListView) findViewById(R.id.listViewMessages);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent listChan = getIntent();

        Integer channelID = listChan.getIntExtra("channelID",0);
        this.requestedParams.put("channelid", String.valueOf(channelID));
        Log.d("requestedParams:", String.valueOf(channelID));

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_FILE, 0);
        String token = settings.getString("ACCESS_TOKEN", "value");
        this.requestedParams.put("accesstoken", token);

        WSRequestAsyncTask request = new WSRequestAsyncTask(this, "http://www.raphaelbischof.fr/messaging/?function=getmessages",this.requestedParams);
        request.addWSRequestListener(this);
        request.execute();
    }

    @Override
    public void onWSResponseUpdate(String response) {
        Gson gson = new Gson();

        MessageResponseList messages = gson.fromJson(response, MessageResponseList.class);

        lvMessages.setAdapter(new ChannelAdapter(messages.getItems(), this));

        Log.d("reponse: ", String.valueOf(messages.getItems()[0].getImageUrl()));
    }
}
