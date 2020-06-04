package sidsim.techwarriors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BasicDetails extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    EditText Name,phone_no;
    Button current_loc, next_button;
    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference databaseReference;
    String name;
    String phone;
    String location_address;
    String latitude;
    String longitude;
    int id=0;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);
        setIds();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        FirebaseApp.initializeApp(this);
    }

    private void setIds() {
        Name = findViewById(R.id.name);
        phone_no = findViewById(R.id.phone);
        next_button = findViewById(R.id.next);
        current_loc = findViewById(R.id.current_loc);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("hospital_info");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Close");
        builder.setMessage("Do you want to exit this application?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void next(View view) {
         name = Name.getText().toString().trim();
         phone = phone_no.getText().toString().trim();
         location_address = current_loc.getText().toString().trim();

        if(name.length() != 0){
            if(phone.length() == 10){
                if(location_address.length() != 0){
                    // move to next page
                    update_table();
                    Intent in = new Intent(getApplicationContext(), Status_Update.class);
                    startActivity(in);
                }
                else
                    Toast.makeText(this, "Add your location first", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Phone No. should be equal to 10 digits", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Fill all entries", Toast.LENGTH_SHORT).show();
    }

    private void update_table() {
        dialog.show();
        try{
            id += 1;
            BasicDetails_entries details = new BasicDetails_entries(name, phone, location_address, String.valueOf(latitude), String.valueOf(longitude));

            databaseReference.child(String.valueOf(id)).setValue(details).addOnCompleteListener(BasicDetails.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(BasicDetails.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(Items_added.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Failed to save", e.toString());
                }
            });

          }
        catch (Exception e)
        {

        }
    }

    public void getCurrentLocation(View view) {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                BasicDetails.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                BasicDetails.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Toast.makeText(this, "Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
