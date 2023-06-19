package by.bsuir.petrovskiy.goodsfinder.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.petrovskiy.goodsfinder.FinderAdapter;
import by.bsuir.petrovskiy.goodsfinder.FindersItem;
import by.bsuir.petrovskiy.goodsfinder.JSONHelper;
import by.bsuir.petrovskiy.goodsfinder.R;

public class HomeFragment extends Fragment {
    private long homeId;
    private ArrayList<FindersItem> finders;
    private HomeViewModel homeViewModel;
    ListView listView_finders;
    View view1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view1 = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getSelectedItem().observe(getActivity(), item -> {});

        if (view1 != null) {
            listView_finders = (ListView) view1.findViewById(R.id.listView_finders);
        }
        if (open()){
            listView_finders.setAdapter(new FinderAdapter(getActivity(), finders));
        }
        listView_finders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FindersItem item = (FindersItem) adapterView.getAdapter().getItem(i);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                homeViewModel.setFinders(finders);
                homeViewModel.setSelectedIndex(i);
                navController.navigate(R.id.nav_edit);
            }
        });

        registerForContextMenu(listView_finders);
        return view1;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();


    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        switch (item.getItemId()) {
            case R.id.up_home:
                navController.navigate(R.id.nav_home);
                return true;
            case R.id.add_fin:
                homeViewModel.setFinders(finders);
                navController.navigate(R.id.nav_add);
                return true;
            case R.id.search_fin:
                homeViewModel.setFinders(finders);
                navController.navigate(R.id.nav_search);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setHome(long id) {
        this.homeId = id;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);

        switch (item.getItemId()) {
            case R.id.contextEdit:
                homeViewModel.setFinders(finders);
                homeViewModel.setSelectedIndex(info.position);
                navController.navigate(R.id.nav_edit);
                return true;
            case R.id.contextDetails:
                homeViewModel.selectItem(finders.get(info.position));
                Log.d("TestDet1", String.valueOf(info.position));
                navController.navigate(R.id.nav_details);
                return true;
            case R.id.contextDelete:
                homeViewModel.setFinders(finders);
                homeViewModel.setSelectedIndex(info.position);
                navController.navigate(R.id.nav_delete);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public boolean open() {
        finders = JSONHelper.importFromJSON(getActivity());
        if(finders!=null) {
            //Toast.makeText(getActivity(), "Данные восстановлены", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            Toast.makeText(getActivity(), "Не удалось открыть данные", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}