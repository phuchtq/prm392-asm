package com.milkstoremobile_fronend.views.activitys.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;

import com.milkstoremobile_fronend.R;
import com.milkstoremobile_fronend.views.activitys.MainActivity;

public class PaymentActivity extends ComponentActivity {
    private Button btBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        bindView();
        btBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void bindView(){
        btBackHome = findViewById(R.id.btnBackHome);
    }
}
