package com.adonis_development_design12.mdrrmo_501;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Front_Face extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Spinner spinnerProvince;
    private Spinner spinnerMunicipality;
    private Spinner spinnerHospital;
    private Spinner spinnerLongitude;
    private Spinner spinnerLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_face);

        databaseReference = FirebaseDatabase.getInstance().getReference(); // Initialize the database reference

        spinnerProvince = findViewById(R.id.spinner_province);
        spinnerMunicipality = findViewById(R.id.spinner_municipality);
        spinnerHospital = findViewById(R.id.spinner_hospital);
        spinnerLongitude = findViewById(R.id.spinner_longitude);
        spinnerLatitude = findViewById(R.id.spinner_latitude);

        // Fetch province data from Firebase and populate the spinner
        ArrayList<String> provinceList = new ArrayList<>();
        DatabaseReference provinceRef = databaseReference.child("province");
        provinceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                provinceList.clear();
                for (DataSnapshot provinceSnapshot : dataSnapshot.getChildren()) {
                    String province = provinceSnapshot.getKey();
                    provinceList.add(province);
                }
                ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(Front_Face.this,
                        android.R.layout.simple_spinner_item, provinceList);
                provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProvince.setAdapter(provinceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if any
            }
        });

        // Fetch municipality data from Firebase and populate the spinner based on the selected province
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = parent.getItemAtPosition(position).toString();
                DatabaseReference municipalityRef = databaseReference.child("province").child(selectedProvince).child("municipality");
                municipalityRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> municipalityList = new ArrayList<>();
                        for (DataSnapshot municipalitySnapshot : dataSnapshot.getChildren()) {
                            String municipality = municipalitySnapshot.getKey();
                            municipalityList.add(municipality);
                        }
                        ArrayAdapter<String> municipalityAdapter = new ArrayAdapter<>(Front_Face.this,
                                android.R.layout.simple_spinner_item, municipalityList);
                        municipalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMunicipality.setAdapter(municipalityAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if any
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Fetch hospital data from Firebase and populate the spinner based on the selected municipality
        spinnerMunicipality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMunicipality = parent.getItemAtPosition(position).toString();
                DatabaseReference hospitalRef = databaseReference.child("municipality").child(selectedMunicipality).child("hospital");
                hospitalRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> hospitalList = new ArrayList<>();
                        for (DataSnapshot hospitalSnapshot : dataSnapshot.getChildren()) {
                            String hospital = hospitalSnapshot.getKey();
                            hospitalList.add(hospital);
                        }
                        ArrayAdapter<String> hospitalAdapter = new ArrayAdapter<>(Front_Face.this,
                                android.R.layout.simple_spinner_item, hospitalList);
                        hospitalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerHospital.setAdapter(hospitalAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if any
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Change activity when tapping on textView5
        TextView textView5 = findViewById(R.id.textView5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Front_Face.this, Registering_Addresses.class);
                startActivity(intent);
            }
        });
    }
}

