package florian.michel.channelmessaging.listchannels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import florian.michel.channelmessaging.R;

/**
 * Created by Flo on 01/03/2016.
 */
public class ChannelsAdapter extends BaseAdapter {

    private ChannelsResponseItems[] items;
    private Context context;

    public ChannelsAdapter(ChannelsResponseItems[] items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public ChannelsResponseItems getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return items[position].getChannelID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView tvSubTitle = (TextView) rowView.findViewById(R.id.tvSubTitle);

        tvTitle.setText(items[position].getName());
        tvSubTitle.setText("Nombre d'utilisateurs connectés : "+ items[position].getConnectedusers().toString());
        return rowView;
    }
}
