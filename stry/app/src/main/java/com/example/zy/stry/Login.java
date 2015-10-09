package com.example.zy.stry;

import com.example.zy.stry.lib.*;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by wendy on 15-8-4.
 */
public class Login extends Fragment implements View.OnClickListener {
    private View rootView;
    Button login_button;
    Button register_button;
    private EditText inputName;
    private EditText inputPass;
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    public static final String PREFS_NAME = "MyPrefs";
    private static String KEY_SUCCESS = "success";
    String username;
    String password;



    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_login, container, false);
        }
        InputStream inputStream = null;



        inputName = (EditText) rootView.findViewById(R.id.username);
        inputPass = (EditText) rootView.findViewById(R.id.password);
        //      regist login_button clicker event;
        login_button = (Button) rootView.findViewById(R.id.login);
        register_button = (Button) rootView.findViewById(R.id.register);

        login_button.setOnClickListener(this);
        register_button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                username = inputName.getText().toString();
                password = inputPass.getText().toString();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Username or password must be filled", Toast.LENGTH_LONG).show();
                    return;
                }

                //add your data
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));

                Integer result = 0;
                try {
                    User.loginUser user = new User().new loginUser(username, password);

                    MainActivity.executorService.submit(user);
                    JSONObject json = user.json;

//                    result = new LoginTask().execute().get();
                    if (json.getString(KEY_SUCCESS) != null) {
                    // user successfully logged in
                    // Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        JSONObject json_user = json.getJSONObject("user");

                        // Clear all previous data in database
                        new User().logoutUser(getActivity());
                        db.addUser(username, password);
                        settings = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
                        prefEditor = settings.edit();
                        prefEditor.putString("username", username);
                        prefEditor.apply();
                        final FragmentTransaction ft = MainActivity.fmg.beginTransaction();
                        Fragment3.collapsingToolbar.setTitle("登录教务处");
                        ft.replace(R.id.fragment_3, new AccountFragment());
//                    ft.detach(frg);
//                    if (frg == null) {
//                        ft.add(R.id.fragment_3, new ProfileFragment());
//                    } else {
//                        ft.attach(frg);
//                    }
                        ft.commitAllowingStateLoss();
                    } else {
                        Toast.makeText(getActivity(), Config.LOGIN_INFO_ERROR, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                }
                break;
            case R.id.register:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//		Log.d(TAG, "TestFragment-----onDestroy");
    }

}

