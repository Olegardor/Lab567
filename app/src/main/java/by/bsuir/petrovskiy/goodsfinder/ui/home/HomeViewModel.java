package by.bsuir.petrovskiy.goodsfinder.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.petrovskiy.goodsfinder.FindersItem;

public class HomeViewModel extends ViewModel {

    private int selectedIndex;

    private ArrayList<FindersItem> finders = new ArrayList<>();
    private ArrayList<FindersItem> finders2 = new ArrayList<>();

    private final MutableLiveData<FindersItem> selectItem = new MutableLiveData<>();

    public void selectItem(FindersItem item) {
        selectItem.setValue(item);
    }

    public LiveData<FindersItem> getSelectedItem() {
        return selectItem;
    }

    public void setFinders(ArrayList<FindersItem> finders) {
        this.finders = finders;
    }

    public ArrayList<FindersItem> getFinders() {
        return finders;
    }

    public void setFinders2(ArrayList<FindersItem> finders2) {
        this.finders2 = finders2;
    }

    public ArrayList<FindersItem> getFinders2() {
        return finders2;
    }

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}