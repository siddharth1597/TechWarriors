package sidsim.techwarriors.Status_fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import sidsim.techwarriors.R;

public class Mapview extends Fragment implements LocationListener, OnMapReadyCallback{

    private GoogleMap mMap;
    double latitude=28.83470802102238, longitude=78.78073786385357; //28.83470802102238,78.78073786385357
    String hospital_name="City Hospital Moradabad";
    String hospital_address="";
    int hospital_availability=1;  // 0-red, 1-green
    int hospital_totalbeds=0, hospital_occupiedbeds=0, hospital_vacantbeds=0; //beds
    int hospital_total_vent=0, hospital_occupied_vent=0, hospital_vacant_vent=0; //ventilators
    SupportMapFragment mapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mapview, container, false);

        //get the data from firebase

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in location and move the camera
        LatLng current = new LatLng(latitude, longitude);

        if(hospital_availability!=0) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(current).title(hospital_name)
                    .snippet("Availabile: "+"YES" + "\n" + "Total beds: "+ hospital_totalbeds+"\n"+
                            "Vacant beds: "+ hospital_vacantbeds + "\n"+"Total ventilators: "+hospital_total_vent+
                            "\n"+ "Vacant ventilators: "+hospital_vacant_vent).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            marker.showInfoWindow();
        }
        else {
            Marker marker = mMap.addMarker(new MarkerOptions().position(current).title(hospital_name)
                    .snippet("Available: "+"NO" + "\n" + "Total beds: "+ hospital_totalbeds+"\n"+
                            "Vacant beds: "+ hospital_vacantbeds + "\n"+"Total ventilators: "+hospital_total_vent+
                            "\n"+ "Vacant ventilators: "+hospital_vacant_vent).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            marker.showInfoWindow();
        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        mMap.getMaxZoomLevel();

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
