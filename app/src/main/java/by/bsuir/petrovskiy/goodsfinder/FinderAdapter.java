package by.bsuir.petrovskiy.goodsfinder;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import by.bsuir.petrovskiy.goodsfinder.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FinderAdapter extends BaseAdapter{

    ArrayList<FindersItem> items;
    Context context;
    ArrayList<FindersItem> finders;
    private static LayoutInflater inflater = null;

    public FinderAdapter(Context context, ArrayList<FindersItem> finders) {
        this.context = context;
        this.finders = finders;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItems(ArrayList<FindersItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return finders.size();
    }

    @Override
    public Object getItem(int i) {
        return finders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        FindersItem res = finders.get(i);
        if (v == null)
            v = inflater.inflate(R.layout.row, null);
        TextView text = v.findViewById(R.id.text);
        TextView date = v.findViewById(R.id.date);
        TextView date_create = v.findViewById(R.id.date_create);
        ImageView imageView = v.findViewById(R.id.imageView);
        text.setText(res.getName());
        date.setText(res.getDate());
        date_create.setText(res.getDate_create());
        Log.d("AdapterP1",res.getPhoto());
        try {
            File file = new File(res.getPhoto());
            file.exists();
            if (!TextUtils.isEmpty(res.getPhoto())) {
                Log.d("AdapterP2","true");
                Glide.with(context)
                        .load(res.getPhoto())
                        .into(imageView);

            }
        }
        catch (Exception e) {
            Log.e("AdapterE", e.getMessage(), e);
        }

        return v;
    }

}
