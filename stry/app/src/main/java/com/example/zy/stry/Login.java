package com.example.zy.stry;

import com.example.zy.stry.lib.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-8-4.
 */
public class Login extends Activity {
    Button login_button;
    Button register_button;
    private EditText inputName;
    private EditText inputPass;

    private static String KEY_SUCCESS = "success";
    private static String KEY_NAME = "username";
    private static String KEY_PASS = "password";


    TextView tv;

    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputStream inputStream = null;


        inputName = (EditText) findViewById(R.id.username);
        inputPass = (EditText) findViewById(R.id.password);
        //      regist login_button clicker event;
        login_button = (Button) findViewById(R.id.login);
        register_button = (Button) findViewById(R.id.register);

        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String username = inputName.getText().toString();
                final String password = inputPass.getText().toString();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(Login.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                }

                //add your data
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));

                dialog = ProgressDialog.show(Login.this, "",
                        "Validating user...", true);
                new Thread(new Runnable() {
                    public void run() {
                        try {

                            runOnUiThread(new Runnable() {
                                public void run() {
//                                    tv.setText("Response from Python : " + response);
                                    dialog.dismiss();
                                }
                            });
                            User user = new User();
                            JSONObject json = user.loginUser(username, password);

//                            System.out.println(json.getString(config.LOGIN_INFO_ERROR));
                            if (json.getString(KEY_SUCCESS) != null) {
                                // user successfully logged in
                                // Store user details in SQLite Database
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                JSONObject json_user = json.getJSONObject("user");

                                // Clear all previous data in database
                                user.logoutUser(getApplicationContext());
                                db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_PASS));

                                // Launch Dashboard Screen
                                Intent profile = new Intent(getApplicationContext(), ProfileFragment.class);

//                                profile.putExtra(KEY_NAME, username);
                                startActivity(profile);
                                finish();
                            } else {
                                Toast.makeText(Login.this, Config.LOGIN_INFO_ERROR, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            dialog.dismiss();
                            System.out.println("Exception : " + e.getMessage());
                        }
                    }
                }).start();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}

