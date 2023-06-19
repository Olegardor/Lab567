package by.bsuir.petrovskiy.goodsfinder.ui.edit;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.bsuir.petrovskiy.goodsfinder.FindersItem;
import by.bsuir.petrovskiy.goodsfinder.JSONHelper;
import by.bsuir.petrovskiy.goodsfinder.R;
import by.bsuir.petrovskiy.goodsfinder.ui.home.HomeViewModel;

public class EditFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private ArrayList<FindersItem> finders;
    String check;
    FindersItem finder;
    Integer index;
    private final int Pick_image = 1;

    EditText editText_name;
    EditText editText_description;
    EditText editText_location_original;
    EditText editText_date;
    EditText editText_finder;
    EditText editText_location_current;
    ImageView imageView_photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        editText_name = (EditText) view.findViewById(R.id.editText_name);
        editText_description = (EditText) view.findViewById(R.id.editText_description);
        editText_location_original = (EditText) view.findViewById(R.id.editText_location_original);
        editText_date = (EditText) view.findViewById(R.id.editText_date);
        editText_finder = (EditText) view.findViewById(R.id.editText_finder);
        editText_location_current = (EditText) view.findViewById(R.id.editText_location_current);
        imageView_photo = (ImageView) view.findViewById(R.id.imageView_photo);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        finders = homeViewModel.getFinders();
        index = homeViewModel.getSelectedIndex();
        finder = finders.get(index);

        editText_name.setText(finder.getName());
        editText_description.setText(finder.getDescription());
        editText_location_original.setText(finder.getLocation_original());
        editText_date.setText(finder.getDate());
        editText_finder.setText(finder.getFinder());
        editText_location_current.setText(finder.getLocation_current());
        check = finder.getPhoto();
        if (check != "") {
            if (!TextUtils.isEmpty(check)) {
                Glide.with(getActivity())
                        .load(check)
                        .into(imageView_photo);
            }
        }

        //Выбор изображения при нажатии на imageAva
        imageView_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        switch (item.getItemId()) {
            case R.id.up_home:
                navController.navigate(R.id.nav_home);
                return true;
            case R.id.add_save:
                saveAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_yes_no, null);

        builder.setView(view)
                .setPositiveButton("Применить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        saveFinder();
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

    public void saveFinder() {
        finder.setName(editText_name.getText().toString());
        finder.setDescription(editText_description.getText().toString());
        finder.setLocation_original(editText_location_original.getText().toString());
        finder.setFinder(editText_finder.getText().toString());
        finder.setDate(editText_date.getText().toString());
        finder.setLocation_current(editText_location_current.getText().toString());
        finder.setPhoto(check);
        finder.setDate_create(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

        finders.set(index, finder);

        boolean result = JSONHelper.exportToJSON(getActivity(), finders);
        if(result){
            //Toast.makeText(getActivity(), "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }

    //Установка изображения в imageAva
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    check = imageReturnedIntent.getData().toString();
                    Glide.with(getActivity())
                            .load(check)
                            .into(imageView_photo);
                }
        }
    }
}
