package by.bsuir.petrovskiy.goodsfinder.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import by.bsuir.petrovskiy.goodsfinder.ListItem;
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

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        LiveData<ListItem> item = homeViewModel.getSelectedItem();
        textView_name.setText(item.getValue().getTitle());
        textView_description.setText(item.getValue().getDescription());

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
