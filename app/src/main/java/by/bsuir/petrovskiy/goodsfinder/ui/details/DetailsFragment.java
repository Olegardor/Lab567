package by.bsuir.petrovskiy.goodsfinder.ui.details;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import by.bsuir.petrovskiy.goodsfinder.FindersItem;
import by.bsuir.petrovskiy.goodsfinder.R;
import by.bsuir.petrovskiy.goodsfinder.ui.home.HomeViewModel;

public class DetailsFragment extends Fragment {
    private HomeViewModel homeViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView textView_name = view.findViewById(R.id.textView_name_2);
        TextView textView_description = view.findViewById(R.id.textView_description_2);
        TextView textView_location_original_2 = view.findViewById(R.id.textView_location_original_2);
        TextView textView_date_2 = view.findViewById(R.id.textView_date_2);
        TextView textView_finder_2 = view.findViewById(R.id.textView_finder_2);
        TextView textView_location_current_2 = view.findViewById(R.id.textView_location_current_2);
        ImageView imageView_photo = view.findViewById(R.id.imageView_photo);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        LiveData<FindersItem> item = homeViewModel.getSelectedItem();

        textView_name.setText(item.getValue().getName());
        Log.d("TextDet_check1", "");
        textView_description.setText(item.getValue().getDescription());
        textView_location_original_2.setText(item.getValue().getLocation_original());
        textView_date_2.setText(item.getValue().getDate());
        textView_finder_2.setText(item.getValue().getFinder());
        textView_location_current_2.setText(item.getValue().getLocation_current());
        String check = item.getValue().getPhoto();
        File file = new File(check);
        file.exists();
        Log.d("TextDet_check0",file.toString());
        Log.d("TextDet_check1",check);
        try {
            if (!TextUtils.isEmpty(check)) {
                Glide.with(getActivity())
                        .load(check)
                        .into(imageView_photo);
            }
        }
        catch (Exception e) {
            Log.e("TextDet_e", e.getMessage(), e);
        }

        Log.d("TextDet_check2","");
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_alone_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        switch (item.getItemId()) {
            case R.id.up_home:
                navController.navigate(R.id.nav_home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
