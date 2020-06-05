package sidsim.techwarriors.Status_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import sidsim.techwarriors.R;
import sidsim.techwarriors.StatusUpdateDetails;
import sidsim.techwarriors.Status_Update;

public class NormalView extends Fragment {
Button edit, update;
Switch available;
EditText name, address, total_vent, vac_vent, total_bed, vac_bed, phone_no;

    DatabaseReference databaseReference;
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
        address = v.findViewById(R.id.address);
        total_bed = v.findViewById(R.id.total_bed);
        vac_bed = v.findViewById(R.id.vac_bed);
        total_vent = v.findViewById(R.id.total_vent);
        vac_vent = v.findViewById(R.id.vanc_vent);
        available = v.findViewById(R.id.availability);
        phone_no = v.findViewById(R.id.phone_no);


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
            @Override
            public void onClick(View view) {

                boolean avail = available.isChecked();
                String hosp_name = name.getText().toString().trim();
                String hosp_add = address.getText().toString().trim();
                String total_beds = total_bed.getText().toString().trim();
                String total_ven = total_vent.getText().toString().trim();
                String vacant_ven = vac_vent.getText().toString().trim();
                String vacant_bed = vac_bed.getText().toString().trim();
                String phone = phone_no.getText().toString().trim();


              /*  StatusUpdateDetails ad = new StatusUpdateDetails(i, total, vacant, venti, vacantVentilator,id+count ,name, phone, address, lat, lang,auth.getCurrentUser().getEmail());
                databaseReference.child().setValue(ad).
                        addOnCompleteListener(getContext(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Data Saved Successful", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        })
                        .addOnFailureListener(getContext(), new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Data Saving Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });*/
                //update table

            }
        });

        return v;
    }
}
