package com.example.team6metaldetector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    // Default server URL - update this if your Mac's IP changes
    private static final String UNITY_SERVER_URL = "http://192.168.1.4:8080/trigger-metal-detection";
    private OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize HTTP client
        httpClient = new OkHttpClient();

        // Button for using the phone's sensor
        Button phoneSensorButton = findViewById(R.id.phoneSensorButton);
        phoneSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger Unity digital twin call
                triggerUnityDigitalTwin();
                
                // Launch the MetalDetectorActivity when clicked
                Intent intent = new Intent(MainActivity.this, MetalDetectorActivity.class);
                startActivity(intent);
            }
        });

        // Button for testing Unity connection
        Button hardwareButton = findViewById(R.id.hardwareButton);
        hardwareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Test Unity connection without launching metal detector
                testUnityConnection();
            }
        });
    }
    
    /**
     * Triggers the Unity digital twin by sending an HTTP POST request
     */
    private void triggerUnityDigitalTwin() {
        Log.d(TAG, "Triggering Unity digital twin...");
        Log.d(TAG, "Server URL: " + UNITY_SERVER_URL);
        
        // Create simplified JSON payload (Unity server expects any JSON body)
        String jsonPayload = "{\"message\":\"Trigger metal detection\"}";
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));
        
        // Create HTTP request with proper headers
        Request request = new Request.Builder()
                .url(UNITY_SERVER_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        
        // Execute the request asynchronously
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to connect to Unity digital twin", e);
                runOnUiThread(() -> {
                    String errorMsg = "Connection failed: " + e.getMessage();
                    Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Check if Unity is running and both devices are on same network");
                });
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body() != null ? response.body().string() : "No response body";
                Log.d(TAG, "Response code: " + response.code());
                Log.d(TAG, "Response body: " + responseBody);
                
                if (response.isSuccessful()) {
                    Log.d(TAG, "Successfully triggered Unity digital twin");
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Unity digital twin activated! ✅", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Log.e(TAG, "Unity digital twin returned error: " + response.code() + " - " + responseBody);
                    runOnUiThread(() -> {
                        String errorMsg = "Unity error " + response.code() + ": " + responseBody;
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    });
                }
                response.close();
            }
        });
    }
    
    /**
     * Test Unity connection without triggering metal detection
     */
    private void testUnityConnection() {
        Log.d(TAG, "Testing Unity connection...");
        Toast.makeText(this, "Testing Unity connection...", Toast.LENGTH_SHORT).show();
        
        // Create a simple test payload
        String jsonPayload = "{\"test\":\"connection\"}";
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));
        
        // Create HTTP request
        Request request = new Request.Builder()
                .url(UNITY_SERVER_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        
        // Execute the request asynchronously
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Unity connection test failed", e);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "❌ Unity connection failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body() != null ? response.body().string() : "No response body";
                Log.d(TAG, "Connection test response: " + response.code() + " - " + responseBody);
                
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "✅ Unity connection successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "❌ Unity error: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                });
                response.close();
            }
        });
    }
}
