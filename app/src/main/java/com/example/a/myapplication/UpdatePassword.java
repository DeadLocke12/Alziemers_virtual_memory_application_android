package com.example.a.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdatePassword extends AppCompatActivity {
    private EditText username, password, password2;
    private String txtUsername, txtPassword, txtPassword2;
    private Button btnUpdate;
    String updatePasswordUrl= "https://dissertationproject.com/test/update_user_authentication_details.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        username = (EditText) findViewById(R.id.et_forgotPassword_username);
        password = (EditText) findViewById(R.id.et_forgotPassword_password);
        password2 = (EditText) findViewById(R.id.et_forgotPassword_confirmPassword);
        btnUpdate = (Button) findViewById(R.id.updatePasswordBtn);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUsername  =username.getText().toString();
                txtPassword = password.getText().toString();
                txtPassword2=password2.getText().toString();

                if (txtPassword.equals(txtPassword2)){
                    getJSON(updatePasswordUrl,txtUsername,txtPassword);
                }else{
                    Toast.makeText(UpdatePassword.this, "the passwords do not match, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //this method is actually fetching the json string
    private void getJSON(final String urlWebService, String Username, String Password) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                String user_name = Username;
                String password = Password;

                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
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

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }
                    String result =sb.toString().trim();
                    //finally returning the read string
                    return result;
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int success = jsonObject.getInt("success");

        String abc =success+"";
        if(success==1){
            Toast.makeText(this, "the data was updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "the data was not updated", Toast.LENGTH_SHORT).show();
        }
    }
}