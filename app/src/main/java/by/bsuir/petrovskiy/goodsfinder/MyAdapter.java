package by.bsuir.petrovskiy.goodsfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<ListItem> {
    public MyAdapter(Context context, List<ListItem> items) {
        super(context, android.R.layout.simple_list_item_2, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_2, null);
        }
        ListItem item = getItem(position);
        if (item != null) {
            TextView title = (TextView) view.findViewById(android.R.id.text1);
            TextView description = (TextView) view.findViewById(android.R.id.text2);
            if (title != null) {
                title.setText(item.getTitle());
            }
            if (description != null) {
                description.setText(item.getDescription());
            }
        }
        return view;
    }
}
