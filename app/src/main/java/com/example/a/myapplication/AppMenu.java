package com.example.a.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class AppMenu extends AppCompatActivity {

    Button btnCognitiveGame, btnReminderMenu, btnChatbot, btnLogout, btnCallMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_menu);
        btnCognitiveGame=findViewById(R.id.btnCognitiveGames);
        btnReminderMenu =findViewById(R.id.btnNotes);
        btnChatbot=findViewById(R.id.btnChatbot);
        btnLogout=findViewById(R.id.btnLogout2);
        btnCallMenu =findViewById(R.id.btnCall);

        btnCallMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(AppMenu.this, CallMenu.class);
                startActivity(game);
            }
        });

        btnCognitiveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(AppMenu.this, GameMenu.class);
                startActivity(game);
            }
        });

        btnChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(AppMenu.this, ChatbotActivity.class);
                startActivity(chat);
            }
        });

        btnReminderMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(AppMenu.this, ReminderMenu.class);
                startActivity(chat);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(AppMenu.this, Login.class);
                startActivity(logout);
            }
        });


    }
}