package by.bsuir.petrovskiy.goodsfinder;

public class FindersItem {

    private String name;
    private String description;
    private String location_original;
    private String date;
    private String finder;
    private String location_current;
    private String photo;
    private String date_create;

    public FindersItem(String name, String description, String location_original, String date, String finder, String location_current, String photo, String date_create) {
        this.name = name;
        this.description = description;
        this.location_original = location_original;
        this.date = date;
        this.finder = finder;
        this.location_current = location_current;
        this.photo = photo;
        this.date_create = date_create;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation_original() {
        return location_original;
    }

    public void setLocation_original(String location_original) {
        this.location_original = location_original;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFinder() {
        return finder;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String getLocation_current() {
        return location_current;
    }

    public void setLocation_current(String location_current) {
        this.location_current = location_current;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
