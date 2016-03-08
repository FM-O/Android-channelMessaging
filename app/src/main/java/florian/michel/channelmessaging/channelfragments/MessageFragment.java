package florian.michel.channelmessaging.channelfragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.channel.ChannelActivity;
import florian.michel.channelmessaging.channel.ChannelAdapter;
import florian.michel.channelmessaging.channel.MessageResponseList;
import florian.michel.channelmessaging.login.LoginActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

/**
 * Created by Flo on 08/03/2016.
 */
public class MessageFragment extends Fragment implements OnWSUpdateListener, View.OnClickListener {

    private HashMap<String,String> requestedParams = new HashMap<>();
    private ListView lvMessages;
    private Handler mMessageHandler;
    private EditText messageSender;
    private Button sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmsg, container);
        lvMessages = (ListView) v.findViewById(R.id.listViewMessages);
        messageSender = (EditText) v.findViewById(R.id.editSendMessage);
        sendButton = (Button) v.findViewById(R.id.btnSendMessage);
        sendButton.setOnClickListener(this);

        Intent listChan = getActivity().getIntent();

        Integer channelID = listChan.getIntExtra("channelID", 0);
        this.requestedParams.put("channelid", String.valueOf(channelID));
        Log.d("requestedParams:", String.valueOf(channelID));

        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_FILE, 0);
        String token = settings.getString("ACCESS_TOKEN", "value");
        this.requestedParams.put("accesstoken", token);

        WSRequestAsyncTask request = new WSRequestAsyncTask(getActivity(), "http://www.raphaelbischof.fr/messaging/?function=getmessages",this.requestedParams);
        request.addWSRequestListener(this);
        request.execute();

        mMessageHandler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                WSRequestAsyncTask request = new WSRequestAsyncTask(MessageFragment.this.getActivity(), "http://www.raphaelbischof.fr/messaging/?function=getmessages", MessageFragment.this.requestedParams);
                request.addWSRequestListener(MessageFragment.this);
                request.execute();
                mMessageHandler.postDelayed(this, 10000);
            }
        };

        mMessageHandler.post(r);

        return v;
    }

    @Override
    public void onWSResponseUpdate(String response) {
        Gson gson = new Gson();

        MessageResponseList messages = gson.fromJson(response, MessageResponseList.class);

        lvMessages.setAdapter(new ChannelAdapter(messages.getItems(), getActivity()));

        Log.d("reponse: ", String.valueOf(messages.getItems()[0].getImageUrl()));
    }

    @Override
    public void onClick(View v) {

        HashMap<String,String> params;

        params = this.requestedParams;

        params.put("message", messageSender.getText().toString());

        WSRequestAsyncTask request = new WSRequestAsyncTask(getActivity().getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=sendmessage", params);
        request.execute();
    }
}
