package com.example.team6metaldetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button for using the phone's sensor
        Button phoneSensorButton = findViewById(R.id.phoneSensorButton);
        phoneSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the MetalDetectorActivity when clicked
                Intent intent = new Intent(MainActivity.this, MetalDetectorActivity.class);
                startActivity(intent);
            }
        });

        // TODO: Button for using additional hardware (currently does nothing)
        Button hardwareButton = findViewById(R.id.hardwareButton);
        hardwareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}
