package sidsim.techwarriors.Status_fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sidsim.techwarriors.R;
import sidsim.techwarriors.StatusUpdateDetails;
import sidsim.techwarriors.Status_Update;

public class NormalView extends Fragment {
Button edit, update;
Switch available;
EditText name, address, total_vent, vac_vent, total_bed, vac_bed, phone_no;


    String hospital_name="";
    String hospital_address="",phoneno,latitude,longitude;
    int hospital_availability =0,count = 0;
    int hospital_totalbeds =0, hospital_occupiedbeds=0, hospital_vacantbeds=0; //beds
    int hospital_total_vent =0, hospital_occupied_vent=0, hospital_vacant_vent=0; //ventilators
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    List<StatusUpdateDetails> hospitalDetails,hospitals,updateDetails;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.normal_view, container, false);

        edit = v.findViewById(R.id.edit);
        update = v.findViewById(R.id.update);
        name = v.findViewById(R.id.hosp_name);
        address = v.findViewById(R.id.address_hosp);
        total_bed = v.findViewById(R.id.total_bed);
        vac_bed = v.findViewById(R.id.vac_bed);
        total_vent = v.findViewById(R.id.total_vent);
        vac_vent = v.findViewById(R.id.vanc_vent);
        available = v.findViewById(R.id.availability);
        phone_no = v.findViewById(R.id.phone_no_hosp);
        dialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();
        hospitalDetails = new ArrayList<>();
        hospitals = new ArrayList<>();
        updateDetails = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("tblhospitals");
        //get the details of hospital

        getData();
        getHospital();
        Toast.makeText(getContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                available.setEnabled(true);
                name.setEnabled(true);
                address.setEnabled(true);
                total_bed.setEnabled(true);
                total_vent.setEnabled(true);
                vac_bed.setEnabled(true);
                vac_vent.setEnabled(true);
                phone_no.setEnabled(true);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            int avl = 0;
            @Override
            public void onClick(View view) {
                dialog.setMessage("Updating");
                dialog.show();
                boolean avail = available.isChecked();
                if(avail)
                    avl = 1;
                String hosp_name = name.getText().toString().trim();
                String hosp_add = address.getText().toString().trim();
                String total_beds = total_bed.getText().toString().trim();
                String total_ven = total_vent.getText().toString().trim();
                String vacant_ven = vac_vent.getText().toString().trim();
                String vacant_bed = vac_bed.getText().toString().trim();
                String phone = phone_no.getText().toString().trim();


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot mySnap : dataSnapshot.getChildren()){
                            DatabaseReference db = databaseReference.child(mySnap.getKey());
                            Log.d("KeyUpper",mySnap.getKey());
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot mySnap:dataSnapshot.getChildren()){
                                      Log.d("KeyLower",mySnap.getKey());
                                      DatabaseReference sb2 =db.child(mySnap.getKey());
                                      sb2.addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                              updateDetails.clear();
                                              int i = 0;
                                              for(DataSnapshot mySnap:dataSnapshot.getChildren()){
                                                  StatusUpdateDetails sd = mySnap.getValue(StatusUpdateDetails.class);
                                                  updateDetails.add(sd);
                                                  if(String.valueOf(updateDetails.get(i).getEmail()).equals(auth.getCurrentUser().getEmail())){
                                                      StatusUpdateDetails ad = new StatusUpdateDetails(avl, Integer.parseInt(total_beds), Integer.parseInt(vacant_bed), Integer.parseInt(total_ven),
                                                              Integer.parseInt(vacant_ven),updateDetails.get(i).getKey() ,
                                                              hosp_name, phone, hosp_add, latitude, longitude,auth.getCurrentUser().getEmail());
                                                      sb2.child(String.valueOf(updateDetails.get(i).getKey())).setValue(ad).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<Void> task) {
                                                              if (task.isSuccessful()) {
                                                                  Toast.makeText(getContext(), "Data Updated Successful", Toast.LENGTH_SHORT).show();
                                                                  dialog.dismiss();
                                                                  available.setEnabled(false);
                                                                  name.setEnabled(false);
                                                                  address.setEnabled(false);
                                                                  total_bed.setEnabled(false);
                                                                  total_vent.setEnabled(false);
                                                                  vac_bed.setEnabled(false);
                                                                  vac_vent.setEnabled(false);
                                                                  phone_no.setEnabled(false);
                                                              }
                                                          }
                                                      }).addOnFailureListener(new OnFailureListener() {
                                                          @Override
                                                          public void onFailure(@NonNull Exception e) {
                                                              Toast.makeText(getContext(), "Data Saving Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                              dialog.dismiss();
                                                              name.setText("");
                                                              address.setText("");
                                                              total_bed.setText("");
                                                              total_vent.setText("");
                                                              vac_bed.setText("");
                                                              vac_vent.setText("");
                                                              phone_no.setText("");
                                                          }
                                                      });
                                                      break;
                                                  }
                                                  i++;
                                              }

                                          }

                                          @Override
                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                          }
                                      });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return v;
    }

    private void getData() {
        try {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    hospitalDetails.clear();
                    int i = 0;
                    Log.d("Check 1 --","Hello");
                   for (DataSnapshot mySnap : dataSnapshot.getChildren()) {
                        DatabaseReference db = databaseReference.child(mySnap.getKey());
                        Log.d("Check 2 --","Hello");
                        db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("Check 3 --","Hello");
                                for (DataSnapshot mySnap : dataSnapshot.getChildren()) {
                                    db.child(mySnap.getKey()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            int i = 0;
                                            hospitalDetails.clear();

                                            for (DataSnapshot mySnap : dataSnapshot.getChildren()) {
                                                StatusUpdateDetails sd = mySnap.getValue(StatusUpdateDetails.class);
                                                hospitalDetails.add(sd);
                                                Log.d("Check 4 --","Hello");
                                                Log.d("Check 5 --",hospitalDetails.get(i).getEmail());

                                                if (String.valueOf(hospitalDetails.get(i).getEmail()).equals(auth.getCurrentUser().getEmail())) {
                                                    hospital_availability = hospitalDetails.get(i).getStatus();
                                                    hospital_totalbeds = hospitalDetails.get(i).getTotalBeds();
                                                    hospital_address = hospitalDetails.get(i).getLocation_add();
                                                    hospital_name = hospitalDetails.get(i).getName();
                                                    phoneno = hospitalDetails.get(i).getPhone();
                                                    latitude = hospitalDetails.get(i).getLocation_lat();
                                                    longitude = hospitalDetails.get(i).getLocation_long();
                                                    hospital_vacantbeds = hospitalDetails.get(i).getVacantBeds();
                                                    hospital_total_vent = hospitalDetails.get(i).getVentilators();
                                                    hospital_vacant_vent = hospitalDetails.get(i).getVacantVentilaor();
                                                    break;
                                                }
                                                i++;
                                            }
                                           setData();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }

    }
    private void setData(){
        try {
            if (hospital_availability == 0)
                available.setChecked(false);
            else
                available.setChecked(true);
            name.setText(hospital_name);
            address.setText(hospital_address);
            total_bed.setText(String.valueOf(hospital_totalbeds));
            total_vent.setText(String.valueOf(hospital_total_vent));
            vac_vent.setText(String.valueOf(hospital_vacant_vent));
            vac_bed.setText(String.valueOf(hospital_vacantbeds));
            phone_no.setText(phoneno);
        }
        catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }

   private void getHospital() {
        hospitals = new ArrayList<>();
        DatabaseReference db = databaseReference.child("Uttar Pradesh").child("Moradabad");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitals.clear();
                for (DataSnapshot mySnap : dataSnapshot.getChildren()) {
                    StatusUpdateDetails sd = mySnap.getValue(StatusUpdateDetails.class);
                    hospitals.add(sd);

                }
                Log.d("size",String.valueOf(hospitals.size()));
                for(int i = 0 ;i < hospitals.size();i++){
                    Log.d("name"+i,hospitals.get(i).getName());
                    Log.d( "Availability"+i,String.valueOf(hospitals.get(i).getStatus()));
                    Log.d("Beds"+i,String.valueOf(hospitals.get(i).getTotalBeds()));
                    Log.d("Address"+i, hospitals.get(i).getLocation_add());
                    Log.d("Name"+i,hospitals.get(i).getName());
                     Log.d("Phone"+i,String.valueOf( hospitals.get(i).getPhone()));
                    Log.d( "Vacant Beds"+i,String.valueOf(hospitals.get(i).getVacantBeds()));
                     Log.d("Ventilators"+i,String.valueOf(hospitals.get(i).getVentilators()));
                     Log.d("Vacant Ventilators"+i,String.valueOf(hospitals.get(i).getVacantVentilaor()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

