package florian.michel.channelmessaging.channel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import florian.michel.channelmessaging.R;

/**
 * Created by Flo on 01/03/2016.
 */
public class ChannelAdapter extends BaseAdapter {

    private MessageResponseItem[] items;
    private Context context;

    public ChannelAdapter(MessageResponseItem[] items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return items[position].getUserID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.messagerowlayout, parent, false);
        TextView tvUserMessage = (TextView) rowView.findViewById(R.id.tvUserMesage);
        TextView tvDate = (TextView) rowView.findViewById(R.id.tvDate);

        tvUserMessage.setText(items[position].getUserID()+" : "+items[position].getMessage());
        tvDate.setText("Date : "+ items[position].getDate());
        return rowView;
    }
}
