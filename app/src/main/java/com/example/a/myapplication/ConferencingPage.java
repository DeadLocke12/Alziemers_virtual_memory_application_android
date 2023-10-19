package com.example.a.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class ConferencingPage extends AppCompatActivity {
    EditText ed_room;
    Button btn_join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferencing_page);
        btn_join=findViewById(R.id.enterConference);
        ed_room = findViewById(R.id.conferenceName);
        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        btn_join = findViewById(R.id.enterConference);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.conferenceName);
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    JitsiMeetConferenceOptions options
                            = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(text)
                            .build();
                    JitsiMeetActivity.launch(ConferencingPage.this, options);
                }
            }
        });
    }
}