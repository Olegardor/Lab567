package by.bsuir.petrovskiy.goodsfinder.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import by.bsuir.petrovskiy.goodsfinder.R;
import by.bsuir.petrovskiy.goodsfinder.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private long homeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        if (view != null) {
            ListView listView = (ListView) view.findViewById(R.id.listView_finders);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setHome(long id) {
        this.homeId = id;
    }

}