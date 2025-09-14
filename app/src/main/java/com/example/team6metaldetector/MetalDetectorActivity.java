package com.example.team6metaldetector;
import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MetalDetectorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor magnetometer;
    private TextView metalStatusTextView;

    //Adding media playing sound -JMEYERS41
    MediaPlayer mediaPlayer;
    //GeomagneticField locationMagField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Added by -Jmeyers41
        mediaPlayer = MediaPlayer.create(this, R.raw.pingphone);

        //Need to get the current magnetic field of location. - jmeyers41
       // locationMagField = new GeomagneticField(33.521370f,-84.354073f,281.94f,System.currentTimeMillis());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metal_detector);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Log.d("Metal Detector", sensorManager.toString());

        metalStatusTextView = findViewById(R.id.metalStatusTextView);

        Button stopButton = findViewById(R.id.stopButton);
        Button startButton = findViewById(R.id.startButton);
        stopButton.setOnClickListener(view -> stopMetalDetection());
        startButton.setOnClickListener(view -> startMetalDetection());
        stopButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // nav back to the previous screen
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startMetalDetection() {
        metalStatusTextView.setText("Searching...");
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

        findViewById(R.id.stopButton).setVisibility(View.VISIBLE);
        findViewById(R.id.startButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.radarImageView1).setVisibility(View.INVISIBLE);
        findViewById(R.id.radarImageView2).setVisibility(View.VISIBLE);
    }
    private void stopMetalDetection() {
        metalStatusTextView.setText("...");
        onPause();

        findViewById(R.id.startButton).setVisibility(View.VISIBLE);
        findViewById(R.id.stopButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.radarImageView2).setVisibility(View.INVISIBLE);
        findViewById(R.id.radarImageView1).setVisibility(View.VISIBLE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float magneticFieldStrength = event.values[0]; // Get the magnetic field strength
        float threshold = 80.00000f;
        double magneticFieldStrengthCalc;
       // double magValue = 0f; //Added by Jmeyers41
        //float magStrengthX = event.values[0];
        //float magStrengthY = event.values[1];
        //float magStrengthZ = event.values[2];
        //int powerOf = 2;

        Log.d("Metal Detector Act X", String.valueOf(event.values[0]));
        Log.d("Metal Detector Act Y", String.valueOf(event.values[1]));
        Log.d("Metal Detector Act Z", String.valueOf(event.values[2]));

        //Calculates the total magnetic intensity value of the magnetic field.
       /* magValue = Math.sqrt(Math.pow(magStrengthX,powerOf) + Math.pow(magStrengthY,powerOf)
                + Math.pow(magStrengthZ,powerOf));

        double finalMagValue = magValue;
        Log.d("All 3 Mag values", String.valueOf(finalMagValue));
        Log.d("Geo sensor data", String.valueOf(locationMagField.getFieldStrength()));
        new Handler().postDelayed(() -> {
            if (finalMagValue > 1.4 * locationMagField.getFieldStrength() || finalMagValue < 0.6 * locationMagField.getFieldStrength()) {
                metalStatusTextView.setText("Metal detected!");
                mediaPlayer.start();
            } else {
                metalStatusTextView.setText("No metal detected.");
                mediaPlayer.pause();
            }
        }, 1500);*/


//Start of new code -Jmeyers41 10/6  *************************************************************

        // Lower Threshold (e.g., 10-20 µT):
        //Pros: Higher sensitivity, more likely to detect small or distant metal objects.
        //Cons: Increased risk of false positives, especially in environments with varying magnetic fields.
        //Mid-Range Threshold (e.g., 30-40 µT):

        //Balanced sensitivity and specificity, suitable for many scenarios.
        //Good starting point for experimentation.

        //Higher Threshold (e.g., 50 µT and above):
        //Pros: Reduced false positives, ideal for environments with significant magnetic interference.
        //Cons: Reduced sensitivity, may miss small or distant metal objects.

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float magFX = event.values[0];
            float magFy = event.values[1];
            float magFz = event.values[2];
            magneticFieldStrengthCalc = Math.sqrt((magFX * magFX) + (magFy * magFy) + (magFz + magFz));

            Log.d("1MetalDetectornewcode", String.valueOf(magneticFieldStrengthCalc));

        } else {
            magneticFieldStrengthCalc = 0.00;
        }


        new Handler().postDelayed(() -> {
                Log.d("MagneticFieldCal", String.valueOf(magneticFieldStrengthCalc));

            if ( magneticFieldStrengthCalc > threshold) {
                metalStatusTextView.setText("Metal detected!");
                mediaPlayer.start();
            } else {
                metalStatusTextView.setText("No metal detected.");
                mediaPlayer.pause();
            }
        }, 1500); // 2000 milliseconds (2 seconds)
//End of new code ***************************************************************************

        // Lower Threshold (e.g., 10-20 µT):
        //Pros: Higher sensitivity, more likely to detect small or distant metal objects.
        //Cons: Increased risk of false positives, especially in environments with varying magnetic fields.
        //Mid-Range Threshold (e.g., 30-40 µT):

        //Balanced sensitivity and specificity, suitable for many scenarios.
        //Good starting point for experimentation.

        //Higher Threshold (e.g., 50 µT and above):
        //Pros: Reduced false positives, ideal for environments with significant magnetic interference.
        //Cons: Reduced sensitivity, may miss small or distant metal objects.
/*        new Handler().postDelayed(() -> {
            if (magneticFieldStrength > threshold) {
                metalStatusTextView.setText("Metal detected!");
                mediaPlayer.start();
            } else {
                metalStatusTextView.setText("No metal detected.");
                mediaPlayer.pause();
            }
        }, 1500); // 2000 milliseconds (2 seconds)*/

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // override abstract method onAccuracyChanged(Sensor,int) in SensorEventListener
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
