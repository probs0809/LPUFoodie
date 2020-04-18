package com.lpufoodie.lpufoodie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.google.android.material.snackbar.Snackbar;

public class DeliveryStatus extends AppCompatActivity implements LpuFoodie{

    TextView status;
    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status);
        status = findViewById(R.id.status);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onBackPressed() {
        if(!LF_Booleans.get("orders")){
            startActivity(new Intent(DeliveryStatus.this,MainActivity.class));
            finish();
        }else{
            Snackbar.make(findViewById(R.id.mainActivity),"Your order is being delivered.",Snackbar.LENGTH_LONG).show();
        }
    }
}
