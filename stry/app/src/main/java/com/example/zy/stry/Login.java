package com.example.zy.stry;

import com.example.zy.stry.lib.*;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
                    result = new LoginTask().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(result == 1) {
//                    Fragment frg = null;
//                    int id = MainActivity.viewPager.getCurrentItem();
//                    TabsAdapter adapter = ((TabsAdapter) MainActivity.viewPager.getAdapter());
//                    frg = adapter.getRegisteredFragment(id);
//                    frg = MainActivity.fmg.findFragmentById(R.id.fragment_profile);
                    final FragmentTransaction ft = MainActivity.fmg.beginTransaction();
                    ft.replace(R.id.fragment_profile, new Account());
//                    ft.detach(frg);
//                    if (frg == null) {
//                        ft.add(R.id.fragment_profile, new ProfileFragment());
//                    } else {
//                        ft.attach(frg);
//                    }
                    ft.commit();
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

    class LoginTask extends AsyncTask<Void, Void, Integer> {

        private Exception exception;
        private ProgressDialog Dialog;


        @Override
        protected Integer doInBackground(Void... params) {

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
            }

            User user = new User();
            try {
                JSONObject json = user.loginUser(username, password);

                if (json.getString(KEY_SUCCESS) != null) {
                    // user successfully logged in
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    JSONObject json_user = json.getJSONObject("user");

                    // Clear all previous data in database
                    user.logoutUser(getActivity());
                    db.addUser(username, password);
                    settings = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
                    prefEditor = settings.edit();
                    prefEditor.putString("username", username);
                    prefEditor.apply();
                    return 1;
                } else {
                    Dialog.show(getActivity(), "",
                            Config.LOGIN_INFO_ERROR, true);
                }
            } catch (Exception e) {
                this.exception = e;
            }
            return 0;
        }

        protected void onPostExecute() {
            Dialog.show(getActivity(), "",
                    "验证用户信息...", true);
        }
    }
}

