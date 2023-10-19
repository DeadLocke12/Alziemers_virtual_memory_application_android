package com.example.a.myapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {
    TextView btnRegister,btnForgotPassword;
    EditText username, password;
    Button btnLogin;
    String txtUsername, txtPassword;

    String Emp_id="", Emp_pass="";

    TextView tv_test;
    private String LoginUrl="https://dissertationproject.com/test/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegister = (TextView) findViewById(R.id.registerBtn);
        btnForgotPassword = (TextView) findViewById(R.id.btnForgotPassword);

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.login);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(getApplicationContext(),UpdatePassword.class);
                startActivity(forgot);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), Register.class);
                startActivity(register);
            }
        });

      btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Emp_id= username.getText().toString();
                Emp_pass = password.getText().toString();

                loginChecker(LoginUrl,Emp_id,Emp_pass);

            }
        });

    }



    private void loginChecker(final String urlWebService, String Username, String Password) {

        class LoginChecker extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    phpFeedback(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                String user_name = Username;
                String password = Password;

                try {
                    URL url = new URL(urlWebService);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while ((json = bufferedReader.readLine()) != null) {

                        sb.append(json + "\n");
                    }
                    String result =sb.toString().trim();
                    return result;
                } catch (Exception e) {
                    return null;
                }

            }
        }

        LoginChecker loginChecker = new LoginChecker();
        loginChecker.execute();
    }

    private void phpFeedback(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int success = jsonObject.getInt("success");
        String abc =success+"";
        if(success==1){
            Intent intent = new Intent(Login.this, AppMenu.class);
            startActivity(intent);
        }if(success==0){
            Toast.makeText(this, "Wrong Username or Password, please try agian", Toast.LENGTH_SHORT).show();
        }
    }


}
