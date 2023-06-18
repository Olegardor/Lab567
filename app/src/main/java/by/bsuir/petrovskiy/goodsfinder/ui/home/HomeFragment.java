package by.bsuir.petrovskiy.goodsfinder.ui.home;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.petrovskiy.goodsfinder.ListItem;
import by.bsuir.petrovskiy.goodsfinder.MyAdapter;
import by.bsuir.petrovskiy.goodsfinder.R;
import by.bsuir.petrovskiy.goodsfinder.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private long homeId;
    private List<ListItem> items = new ArrayList<>();
    private HomeViewModel homeViewModel;
    ListView listView_finders;
    View view1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view1 = inflater.inflate(R.layout.fragment_home, container, false);
        if (view1 != null) {
            listView_finders = (ListView) view1.findViewById(R.id.listView_finders);
        }
        items.add(new ListItem("Молоко", "1 литр"));
        items.add(new ListItem("Хлеб", "белый, 400 грамм"));
        items.add(new ListItem("Яблоки", "красные, 1 кг"));
        MyAdapter adapter = new MyAdapter(getContext(), items);
        listView_finders.setAdapter(adapter);

        registerForContextMenu(listView_finders);
        listView_finders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListItem item = (ListItem) adapterView.getAdapter().getItem(i);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_edit);
            }
        });

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getSelectedItem().observe(getActivity(), item -> {});



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
            case R.id.add_dis:
                navController.navigate(R.id.nav_add);
                return true;
            case R.id.search_dis:
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
                homeViewModel.selectItem(items.get(info.position));
                navController.navigate(R.id.nav_edit);
                return true;
            case R.id.contextDetails:
                homeViewModel.selectItem(items.get(info.position));
                navController.navigate(R.id.nav_details);
                return true;
            case R.id.contextDelete:
                homeViewModel.selectItem(items.get(info.position));
                navController.navigate(R.id.nav_delete);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}