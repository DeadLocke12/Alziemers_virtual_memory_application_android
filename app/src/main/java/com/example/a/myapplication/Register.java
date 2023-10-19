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

public class Register extends AppCompatActivity {
    private Button btnRegister;
    private EditText name, email, username, password, password2;
    private String txtName, txtEmail, txtUsername, txtPassword, txtPassword2;
    private String RegisterUrl="https://dissertationproject.com/test/insert_user_details.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.registerBtn);
        name = (EditText) findViewById(R.id.etName);
        email = (EditText) findViewById(R.id.etEmail);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        password2 = (EditText) findViewById(R.id.et_confirmPassword);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                String Password2= password2.getText().toString();

                String Name = name.getText().toString();
                String Email = email.getText().toString();

                if((Password).equals(Password2)){
                    getJSON(RegisterUrl, Username, Password, Name, Email);
                } else{
                    Toast.makeText(Register.this, "the passwords do not match, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        //this method is actually fetching the json string
        private void getJSON(final String urlWebService,String Username,String Password, String Name,String Email) {
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
                        phpfeedback(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {

                    String user_name = Username;
                    String password = Password;
                    String name =Name;
                    String email = Email;


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
                                +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                                +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                                +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
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

        private void phpfeedback(String json) throws JSONException {
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
