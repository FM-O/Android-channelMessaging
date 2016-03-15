package florian.michel.channelmessaging.channelfragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.channel.ChannelActivity;
import florian.michel.channelmessaging.channel.ChannelAdapter;
import florian.michel.channelmessaging.channel.MessageResponseList;
import florian.michel.channelmessaging.gps.GPSActivity;
import florian.michel.channelmessaging.gps.MapsActivity;
import florian.michel.channelmessaging.login.LoginActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

/**
 * Created by Flo on 08/03/2016.
 */
public class MessageFragment extends Fragment implements OnWSUpdateListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private HashMap<String,String> requestedParams = new HashMap<>();
    private ListView lvMessages;
    private Handler mMessageHandler;
    private EditText messageSender;
    private Button sendButton;
    private Integer channelID;

    private MessageResponseList messages;

    private Location currentLocation = new Location("currLocate");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmsg, container);
        lvMessages = (ListView) v.findViewById(R.id.listViewMessages);
        messageSender = (EditText) v.findViewById(R.id.editSendMessage);
        sendButton = (Button) v.findViewById(R.id.btnSendMessage);
        sendButton.setOnClickListener(this);

        lvMessages.setOnItemClickListener(this);

        Intent listChan = getActivity().getIntent();

        this.channelID = listChan.getIntExtra("channelID", 0);

        this.requestedParams.put("channelid", String.valueOf(this.channelID));
        Log.d("requestedParams:", String.valueOf(this.channelID));

        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_FILE, 0);
        String token = settings.getString("ACCESS_TOKEN", "value");
        this.requestedParams.put("accesstoken", token);

        WSRequestAsyncTask request = new WSRequestAsyncTask(getActivity(), "http://www.raphaelbischof.fr/messaging/?function=getmessages",this.requestedParams);
        request.addWSRequestListener(this);
        request.execute();

        mMessageHandler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                if (MessageFragment.this.getActivity() != null) {
                    WSRequestAsyncTask request = new WSRequestAsyncTask(MessageFragment.this.getActivity(), "http://www.raphaelbischof.fr/messaging/?function=getmessages", MessageFragment.this.requestedParams);
                    request.addWSRequestListener(MessageFragment.this);
                    request.execute();
                    mMessageHandler.postDelayed(this, 2000);
                }
            }
        };

        mMessageHandler.post(r);

        return v;
    }

    @Override
    public void onWSResponseUpdate(String response) {
        Gson gson = new Gson();

        this.messages = gson.fromJson(response, MessageResponseList.class);

        lvMessages.setAdapter(new ChannelAdapter(messages.getItems(), getActivity()));
    }

    @Override
    public void onClick(View v) {

        HashMap<String,String> params;

        params = this.requestedParams;

        params.put("message", messageSender.getText().toString());
        params.put("latitude", String.valueOf(this.currentLocation.getLatitude()));
        params.put("longitude", String.valueOf(this.currentLocation.getLongitude()));

        WSRequestAsyncTask request = new WSRequestAsyncTask(getActivity().getApplicationContext(), "http://www.raphaelbischof.fr/messaging/?function=sendmessage", params);
        request.execute();
    }

    public void updateChannel(Integer channelID) {
        this.channelID = channelID;
        this.requestedParams.remove("channelid");
        this.requestedParams.put("channelid", String.valueOf(this.channelID));
    }

    @Override
    public void onItemClick(AdapterView<?>parent, View view, final int position, long id) {
        String[] arr = {"Ajouter un ami", "Voir sur la carte"};
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.make_a_choice)
                .setItems(arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            Intent MapsAct = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
                            MapsAct.putExtra("latitude", messages.getItems()[position].getLatitude());
                            MapsAct.putExtra("longitude", messages.getItems()[position].getLongitude());

                            startActivity(MapsAct);
                        } else {
                            Log.d("entry", String.valueOf(which));
                        }
                    }
                }).show();
    }

    public void setLocation(Double latitude, Double longitude) {
        currentLocation.setLatitude(latitude);
        currentLocation.setLongitude(longitude);
    }
}
