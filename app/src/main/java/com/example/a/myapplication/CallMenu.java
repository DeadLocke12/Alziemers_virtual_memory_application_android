package com.example.a.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CallMenu extends AppCompatActivity {
    Button btnVoiceCall, btnVideoCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_menu);
        btnVoiceCall =findViewById(R.id.btnVoiceCall);
        btnVideoCall =findViewById(R.id.btnVideoCall);

        btnVoiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(CallMenu.this, CallActivity.class);
                startActivity(game);
            }
        });

        btnVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(CallMenu.this, ConferencingPage.class);
                startActivity(game);
            }
        });
    }
}