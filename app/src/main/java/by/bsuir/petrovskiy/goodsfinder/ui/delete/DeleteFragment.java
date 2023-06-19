package by.bsuir.petrovskiy.goodsfinder.ui.delete;

import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.bsuir.petrovskiy.goodsfinder.FindersItem;
import by.bsuir.petrovskiy.goodsfinder.JSONHelper;
import by.bsuir.petrovskiy.goodsfinder.R;
import by.bsuir.petrovskiy.goodsfinder.ui.home.HomeViewModel;

public class DeleteFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private ArrayList<FindersItem> finders;
    FindersItem finder;
    Integer index;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        TextView textView_name = view.findViewById(R.id.textView_name_2);
        TextView textView_description = view.findViewById(R.id.textView_description_2);
        TextView textView_location_original_2 = view.findViewById(R.id.textView_location_original_2);
        TextView textView_date_2 = view.findViewById(R.id.textView_date_2);
        TextView textView_finder_2 = view.findViewById(R.id.textView_finder_2);
        TextView textView_location_current_2 = view.findViewById(R.id.textView_location_current_2);
        ImageView imageView_photo = view.findViewById(R.id.imageView_photo);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        finders = homeViewModel.getFinders();
        index = homeViewModel.getSelectedIndex();
        finder = finders.get(index);

        textView_name.setText(finder.getName());
        Log.d("TextDet_check1", "");
        textView_description.setText(finder.getDescription());
        textView_location_original_2.setText(finder.getLocation_original());
        textView_date_2.setText(finder.getDate());
        textView_finder_2.setText(finder.getFinder());
        textView_location_current_2.setText(finder.getLocation_current());
        String check = finder.getPhoto();
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
        inflater.inflate(R.menu.delete_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        switch (item.getItemId()) {
            case R.id.up_home:
                navController.navigate(R.id.nav_home);
                return true;
            case R.id.del_dis:
                deleteAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_yes_no, null);

        builder.setView(view)
                .setPositiveButton("Применить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deleteFinder();
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                        navController.navigate(R.id.nav_home);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    public void deleteFinder() {
        Log.d("DelFrom", finders.toString());
        finders.subList(index, index + 1).clear();

        //finders.remove(index);
        Log.d("DelTo", finders.toString());
        boolean result = JSONHelper.exportToJSON(getActivity(), finders);
        if(result){
            //Toast.makeText(getActivity(), "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }
}
