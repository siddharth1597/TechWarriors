package sidsim.techwarriors;

public class BasicDetails_entries {
        String name;
        String phone;
        String location_add;
        String location_lat;
        String location_long;
        public BasicDetails_entries(){
            //For Reading
        }
        public BasicDetails_entries(String name, String phone, String location_add, String location_lat, String location_long) {
            this.name = name;
            this.phone = phone;
            this.location_add = location_add;
            this.location_lat = location_lat;
            this.location_long = location_long;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getLocation_lat() {
            return location_lat;
        }

        public String getLocation_long() {
        return location_long;
    }

}
