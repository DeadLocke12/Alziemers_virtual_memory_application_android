package com.example.a.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a.myapplication.activities.MainActivity;

public class ReminderMenu extends AppCompatActivity {
    Button btnNotesMenu, btnReminderFuntion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_menu);

        btnReminderFuntion =findViewById(R.id.btnReminderFunction);
        btnNotesMenu =findViewById(R.id.btnNotesFunction);

        btnNotesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(ReminderMenu.this, MainActivity.class);
                startActivity(game);
            }
        });

        btnReminderFuntion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(ReminderMenu.this, ReminderPage.class);
                startActivity(game);
            }
        });





    }
}