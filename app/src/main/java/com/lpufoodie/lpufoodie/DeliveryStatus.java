package com.lpufoodie.lpufoodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DeliveryStatus extends AppCompatActivity {

    TextView status;
    ContentLoadingProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status);
        status = findViewById(R.id.status);
        progressBar = findViewById(R.id.progressBar);

    }
}
