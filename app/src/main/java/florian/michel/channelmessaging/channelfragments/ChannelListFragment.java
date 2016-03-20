package florian.michel.channelmessaging.channelfragments;


import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import florian.michel.channelmessaging.R;
import florian.michel.channelmessaging.listchannels.ChannelsAdapter;
import florian.michel.channelmessaging.listchannels.ChannelsResponseItems;
import florian.michel.channelmessaging.listchannels.ChannelsResponseList;
import florian.michel.channelmessaging.listchannels.ListChannelsActivity;
import florian.michel.channelmessaging.login.LoginActivity;
import florian.michel.channelmessaging.network.OnWSUpdateListener;
import florian.michel.channelmessaging.network.WSRequestAsyncTask;

/**
 * Created by Flo on 07/03/2016.
 */
public class ChannelListFragment extends Fragment implements OnWSUpdateListener {


    private HashMap<String, String> tokens = new HashMap<>();
    private ListView lvFragment;
    private ChannelsResponseItems[] listChannels = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fraglv,container);
        lvFragment = (ListView)v.findViewById(R.id.listView);

        lvFragment.setOnItemClickListener((ListChannelsActivity)getActivity());

        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_FILE, 0);

        String token = settings.getString("ACCESS_TOKEN", "value");

        this.tokens.put("accesstoken", token);

        WSRequestAsyncTask request = new WSRequestAsyncTask(getActivity(), "http://www.raphaelbischof.fr/messaging/?function=getchannels", tokens);
        request.addWSRequestListener(this);
        request.execute();
        return v;
    }
    @Override
    public void onWSResponseUpdate(String response) {
        Gson gson = new Gson();

        ChannelsResponseList channels = gson.fromJson(response, ChannelsResponseList.class);

        listChannels = channels.getItems();

        lvFragment.setAdapter(new ChannelsAdapter(listChannels, getActivity()));
    }
}
