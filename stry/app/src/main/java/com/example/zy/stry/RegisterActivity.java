package com.example.zy.stry;

import com.example.zy.stry.lib.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by wendy on 15-8-4.
 */
public class RegisterActivity extends Activity {
    Button register_button;
    private EditText inputName;
    private EditText inputPass;

    public static final String PREFS_NAME = "MyPrefs";
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



                final User user = new User();
                User.registerUser task = user.new registerUser(username, password,new Function<JSONObject, Void>() {
                    @Override
                    public Void apply(JSONObject json) {

                        Message msg = new Message();
                        try {
                            if (json == null) {
                                msg.what = 0;
                                handler.sendMessage(msg);
                            } else {

                                if (json.getString(Config.KEY_SUCCESS) != null) {
                                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                    JSONObject json_user = json.getJSONObject("user");

                                    // Clear all previous data in database
                                    user.logoutUser(getApplicationContext());
                                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_PASS));
                                    MainActivity.settings = getApplicationContext().getSharedPreferences(PREFS_NAME, getApplicationContext().MODE_PRIVATE);
                                    MainActivity.prefEditor = MainActivity.settings.edit();
                                    MainActivity.prefEditor.putString("username", username);
                                    MainActivity.prefEditor.apply();
                                    msg.what = 1;
                                    handler.sendMessage(msg);
                                } else {
                                    msg.what = -1;
                                    handler.sendMessage(msg);
                                }
                            }

                        } catch (Exception e) {
                            System.out.println("Exception : " + e.getMessage());
                        }
                        return null;
                    }
                });

                MainActivity.executorService.submit(task);

            }
        });

    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), Config.LOAD_ERROR, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "注册成功，请登入", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -1:
                    Toast.makeText(RegisterActivity.this, Config.LOGIN_INFO_ERROR, Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };
}
