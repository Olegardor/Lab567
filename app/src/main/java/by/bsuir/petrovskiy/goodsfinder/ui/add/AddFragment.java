package by.bsuir.petrovskiy.goodsfinder.ui.add;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import by.bsuir.petrovskiy.goodsfinder.FinderAdapter;
import by.bsuir.petrovskiy.goodsfinder.FindersItem;
import by.bsuir.petrovskiy.goodsfinder.JSONHelper;
import by.bsuir.petrovskiy.goodsfinder.R;
import by.bsuir.petrovskiy.goodsfinder.ui.home.HomeViewModel;

public class AddFragment extends Fragment {
    private ArrayList<FindersItem> finders;
    private final int Pick_image = 1;
    private Uri imageUri;
    private HomeViewModel homeViewModel;

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
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        editText_name = (EditText) view.findViewById(R.id.editText_name);
        editText_description = (EditText) view.findViewById(R.id.editText_description);
        editText_location_original = (EditText) view.findViewById(R.id.editText_location_original);
        editText_date = (EditText) view.findViewById(R.id.editText_date);
        editText_finder = (EditText) view.findViewById(R.id.editText_finder);
        editText_location_current = (EditText) view.findViewById(R.id.editText_location_current);
        imageView_photo = (ImageView) view.findViewById(R.id.imageView_photo);
        imageUri = Uri.parse("");


        finders = new ArrayList<>();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        finders = homeViewModel.getFinders();

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
                addFinder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addFinder() {
        Log.d("TestAdd2", "true");
        FindersItem findersItem = new FindersItem();
        Log.d("TestAdd3", "true");
        findersItem.setName(editText_name.getText().toString());
        findersItem.setDescription(editText_description.getText().toString());
        findersItem.setLocation_original(editText_location_original.getText().toString());
        findersItem.setFinder(editText_finder.getText().toString());
        findersItem.setDate(editText_date.getText().toString());
        findersItem.setLocation_current(editText_location_current.getText().toString());
        findersItem.setPhoto(imageUri.toString());
        Log.d("TestAdd4", imageUri.toString());
        findersItem.setDate_create(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        Log.d("TestAdd5", "true");

        finders.add(findersItem);
        Log.d("TestAdd6", "true");

        boolean result = JSONHelper.exportToJSON(getActivity(), finders);
        if(result){
            Toast.makeText(getActivity(), "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
    }

    //Установка изображения
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    imageUri = imageReturnedIntent.getData();
                    Glide.with(getActivity())
                            .load(imageUri.toString())
                            .into(imageView_photo);
                }
        }
    }
}
