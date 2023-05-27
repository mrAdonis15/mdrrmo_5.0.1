package com.adonis_development_design12.mdrrmo_501;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registering_Addresses extends AppCompatActivity {

    private Spinner spinnerProvince;
    private Spinner spinnerMunicipality;
    private EditText editTextHospitalName;
    private EditText editTextLongitude;
    private EditText editTextLatitude;
    private Button addButton;
    private Button updateButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registering_addresses);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        spinnerProvince = findViewById(R.id.spinner_province);
        spinnerMunicipality = findViewById(R.id.spinner_municipality);
        editTextHospitalName = findViewById(R.id.editTextHospitalName);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        editTextLatitude = findViewById(R.id.editTextLatitude);

        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);

        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource(this, R.array.provinces, android.R.layout.simple_spinner_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(provinceAdapter);

        ArrayAdapter<CharSequence> municipalityAdapter = ArrayAdapter.createFromResource(this, R.array.municipality, android.R.layout.simple_spinner_item);
        municipalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMunicipality.setAdapter(municipalityAdapter);

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle province selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String province = spinnerProvince.getSelectedItem().toString();
                String municipality = spinnerMunicipality.getSelectedItem().toString();
                String hospitalName = editTextHospitalName.getText().toString().trim();
                String longitude = editTextLongitude.getText().toString().trim();
                String latitude = editTextLatitude.getText().toString().trim();

                if (hospitalName.isEmpty() || longitude.isEmpty() || latitude.isEmpty()) {
                    showToast("Please fill all the fields");
                } else {
                    saveDataToFirebase(province, municipality, hospitalName, longitude, latitude);
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update operation
            }
        });
    }

    private void saveDataToFirebase(String province, String municipality, String hospitalName, String longitude, String latitude) {
        DatabaseReference provinceRef = databaseReference.child("province").child(province);
        DatabaseReference municipalityRef = provinceRef.child("municipality").child(municipality);
        DatabaseReference hospitalRef = municipalityRef.child("hospital").child(hospitalName);

        hospitalRef.child("longitude").setValue(longitude);
        hospitalRef.child("latitude").setValue(latitude);

        showToast("Data saved successfully");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
