package by.bsuir.petrovskiy.goodsfinder.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import by.bsuir.petrovskiy.goodsfinder.ListItem;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<ListItem> selectdItem = new MutableLiveData<>();;

    public void selectItem(ListItem item) {
        selectdItem.setValue(item);
    }

    public LiveData<ListItem> getSelectedItem() {
        return selectdItem;
    }
}