package com.example.zy.stry;

import com.example.zy.stry.lib.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wendy on 15-8-4.
 */
public class RegisterActivity extends Activity {
    Button register_button;
    private EditText inputName;
    private EditText inputPass;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_NAME = "username";
    private static String KEY_PASS = "password";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";

    ProgressDialog dialog = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Importing all assets like buttons, text fields
        inputName = (EditText) findViewById(R.id.username);
        inputPass = (EditText) findViewById(R.id.password);
        register_button = (Button) findViewById(R.id.register);

        // Register Button Click event
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final String username = inputName.getText().toString();
                final String password = inputPass.getText().toString();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                }

                dialog = ProgressDialog.show(RegisterActivity.this, "",
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
                            JSONObject json = user.registerUser(username, password);

//                            System.out.println(json.getString(config.LOGIN_INFO_ERROR));
                            if (json.getString(KEY_SUCCESS) != null) {
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                JSONObject json_user = json.getJSONObject("user");

                                // Clear all previous data in database
                                user.logoutUser(getApplicationContext());
                                db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_PASS));
                                // Launch Dashboard Screen
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, Config.LOGIN_INFO_ERROR, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            dialog.dismiss();
                            System.out.println("Exception : " + e.getMessage());
                        }
                    }
                }).start();
            }
        });

    }
}
