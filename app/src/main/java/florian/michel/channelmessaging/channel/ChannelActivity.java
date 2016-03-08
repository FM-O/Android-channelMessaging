package florian.michel.channelmessaging.channel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.gps.GPSActivity;
import florian.michel.channelmessaging.login.LoginActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

public class ChannelActivity extends GPSActivity {

//    private HashMap<String,String> requestedParams = new HashMap<>();
//    private ListView lvMessages;
//    private Handler mMessageHandler;
//    private EditText messageSender;
//    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        lvMessages = (ListView) findViewById(R.id.listViewMessages);
//        messageSender = (EditText) findViewById(R.id.editSendMessage);
//        sendButton = (Button) findViewById(R.id.btnSendMessage);
//        sendButton.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Intent listChan = getIntent();

        Integer channelID = listChan.getIntExtra("channelID", 0);
        this.requestedParams.put("channelid", String.valueOf(channelID));
        Log.d("requestedParams:", String.valueOf(channelID));

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_FILE, 0);
        String token = settings.getString("ACCESS_TOKEN", "value");
        this.requestedParams.put("accesstoken", token);

        WSRequestAsyncTask request = new WSRequestAsyncTask(this, "http://www.raphaelbischof.fr/messaging/?function=getmessages",this.requestedParams);
        request.addWSRequestListener(this);
        request.execute();

        mMessageHandler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                WSRequestAsyncTask request = new WSRequestAsyncTask(ChannelActivity.this, "http://www.raphaelbischof.fr/messaging/?function=getmessages",ChannelActivity.this.requestedParams);
                request.addWSRequestListener(ChannelActivity.this);
                request.execute();
                mMessageHandler.postDelayed(this, 10000);
            }
        };

        mMessageHandler.post(r);*/
    }

   /* }

    @Override
    public void onWSResponseUpdate(String response) {
        Gson gson = new Gson();

        MessageResponseList messages = gson.fromJson(response, MessageResponseList.class);

        lvMessages.setAdapter(new ChannelAdapter(messages.getItems(), this));

        Log.d("reponse: ", String.valueOf(messages.getItems()[0].getImageUrl()));
    }

    @Override
    public void onClick(View v) {

        HashMap<String,String> params;

        params = this.requestedParams;

        params.put("message", messageSender.getText().toString());

        WSRequestAsyncTask request = new WSRequestAsyncTask(this.getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=sendmessage", params);
        request.execute();
    }

    */
}


