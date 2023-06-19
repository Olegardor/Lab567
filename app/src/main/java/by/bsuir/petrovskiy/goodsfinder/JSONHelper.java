package by.bsuir.petrovskiy.goodsfinder;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    private static final String FILE_NAME = "data.json";

    public static boolean exportToJSON(Context context, ArrayList<FindersItem> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setFinders(dataList);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<FindersItem> importFromJSON(Context context) {

        try (FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)) {

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getFinders();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static class DataItems {
        private ArrayList<FindersItem> finders;

        ArrayList<FindersItem> getFinders() {
            return finders;
        }
        void setFinders(ArrayList finders) {
            this.finders = finders;
        }
    }
}
