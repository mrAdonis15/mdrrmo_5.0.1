package com.adonis_development_design12.mdrrmo_501;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        // Initialize the TextView
        textView = findViewById(R.id.textView);

        // Set a click listener for the TextView
        textView.setOnClickListener(view -> {
            // Start the new activity
            Intent intent = new Intent(Loading.this, Front_Face.class);
            startActivity(intent);
            finish();
        });
    }
}
