package sidsim.techwarriors;

public class StatusUpdateDetails {
    int status,totalBeds,vacantBeds,ventilators , vacantVentilaor,key;
    String name;
    String phone;
    String location_add;
    String location_lat;
    String location_long;
    String email;
    public StatusUpdateDetails() {
        //Reading
    }

    public StatusUpdateDetails(int status, int totalBeds, int vacantBeds, int ventilators,int vacantVentilaor,int key,String name,String phone,String add , String lat,String lang,String email) {
        this.status = status;
        this.totalBeds = totalBeds;
        this.vacantBeds = vacantBeds;
        this.ventilators = ventilators;
        this.name = name;
        this.phone = phone;
        this.location_add = add;
        this.location_lat = lat;
        this.location_long = lang;
        this.email = email;
        this.key= key;
        this.vacantVentilaor = vacantVentilaor;
    }

    public int getStatus() { return status; }

    public int getTotalBeds() { return totalBeds; }

    public int getVacantBeds() { return vacantBeds; }

    public String getEmail() {return email; }

    public int getVentilators() { return ventilators; }
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation_add() { return location_add; }

    public String getLocation_lat() {
        return location_lat;
    }

    public String getLocation_long() {
        return location_long;
    }

    public int getVacantVentilaor() { return vacantVentilaor; }

    public int getKey() { return key; }
}