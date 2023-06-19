package by.bsuir.petrovskiy.goodsfinder.ui.search;

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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import by.bsuir.petrovskiy.goodsfinder.FinderAdapter;
import by.bsuir.petrovskiy.goodsfinder.FindersItem;
import by.bsuir.petrovskiy.goodsfinder.JSONHelper;
import by.bsuir.petrovskiy.goodsfinder.R;
import by.bsuir.petrovskiy.goodsfinder.ui.home.HomeViewModel;

public class SearchFragment extends Fragment {
    private long homeId;
    private ArrayList<FindersItem> finders;
    ArrayList<FindersItem> filtered;
    private HomeViewModel homeViewModel;
    ListView listView_finders;
    View view1;
    private FinderAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        finders = homeViewModel.getFinders();
        filtered = homeViewModel.getFinders2();
        Log.d("ViewModel_Filt", filtered.toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view1 = inflater.inflate(R.layout.fragment_home, container, false);


        if (view1 != null) {
            listView_finders = (ListView) view1.findViewById(R.id.listView_finders);
        }
        if (filtered.size() == 0) {
            Log.d("TextUtils_filt", String.valueOf(filtered.size() == 0));
            filtered.addAll(finders);
        }
        adapter = new FinderAdapter(getActivity(), filtered);
        listView_finders.setAdapter(adapter);
        listView_finders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FindersItem item = (FindersItem) adapterView.getAdapter().getItem(i);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                homeViewModel.selectItem(finders.get(i));
                navController.navigate(R.id.nav_details);
            }
        });

        registerForContextMenu(listView_finders);
        return view1;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void clearList ()
    {
        filtered.clear();
        filtered.addAll(finders);
        adapter.notifyDataSetChanged();
        homeViewModel.setFinders2(finders);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        switch (item.getItemId()) {
            case R.id.up_home:
                clearList();
                navController.navigate(R.id.nav_home);
                return true;
            case R.id.clear_text:
                clearList();
                return true;
            case R.id.by_name:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();

                View view = inflater.inflate(R.layout.dialog_search_name, null);

                builder.setView(view)
                        .setPositiveButton("Искать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText nameView = (EditText) view.findViewById(R.id.name_search);

                                String name = nameView.getText().toString();
                                try {
                                    for(int i = 0; i < filtered.size(); i++) {
                                        Log.d("Search_Size", String.valueOf(filtered.size()));
                                        Log.d("Search_name", String.valueOf(i));
                                        FindersItem element = filtered.get(i);
                                        if(!element.getName().toLowerCase().contains(name.toLowerCase())){
                                            filtered.subList(i, i + 1).clear();
                                            i--;
                                            Log.d("Search_filter", filtered.toString());
                                        }
                                    }
                                    //adapter.setItems(filtered);
                                    adapter.notifyDataSetChanged();
                                    homeViewModel.setFinders2(filtered);
                                }
                                catch (Exception ex) {
                                    Log.e("Search_Ex", ex.getMessage(), ex);
                                }

                                Log.d("Search_adapter", adapter.toString());
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                Log.d("Search_End1", adapter.toString());
                builder.show();
                Log.d("Search_End2", adapter.toString());
                return true;
            case R.id.by_date:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater2 = requireActivity().getLayoutInflater();

                View view2 = inflater2.inflate(R.layout.dialog_search_date, null);

                builder2.setView(view2)
                        .setPositiveButton("Искать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                DatePicker datePicker = view2.findViewById(R.id.datePicker);

                                int year = datePicker.getYear();
                                int month = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();

                                String date = (day +"-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1))  + "-" + year);
                                for(int i = 0; i < filtered.size(); i++) {
                                    FindersItem element = filtered.get(i);
                                    if(!element.getDate_create().equals(date)){
                                        filtered.subList(i, i + 1).clear();
                                        i--;
                                    }
                                }
                                //adapter.setItems(filtered);
                                adapter.notifyDataSetChanged();
                                homeViewModel.setFinders2(filtered);
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder2.show();
                return true;
            case R.id.sort:
                Collections.sort(filtered, new Comparator<FindersItem>() {
                    @Override
                    public int compare(final FindersItem object1, final FindersItem object2) {
                        return object1.getName().compareTo(object2.getName());
                    }
                });
                //adapter.setItems(filtered);
                adapter.notifyDataSetChanged();
                homeViewModel.setFinders2(filtered);
                Log.d("Search_adapter_sort", adapter.toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
