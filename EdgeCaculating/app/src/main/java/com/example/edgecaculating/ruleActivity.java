package com.example.edgecaculating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ruleActivity extends AppCompatActivity {
    private Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        closeBtn = findViewById(R.id.btnClose);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ruleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
