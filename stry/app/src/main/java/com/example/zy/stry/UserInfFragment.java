package com.example.zy.stry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;


/**
 * Created by zy on 15/9/30.
 */
public class UserInfFragment extends Fragment {
    private View rootView;
    SharedPreferences shared_preferences;
    public static final String PREFS_NAME = "MyPrefs";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_userinfor, container, false);
        }
        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        String name ,faculty,major,student_ID;
        name = shared_preferences.getString("name", null);
        faculty = shared_preferences.getString("faculty", null);
        major = shared_preferences.getString("major", null);
        student_ID = shared_preferences.getString("student_ID", null);
        TextView textView = (TextView)rootView.findViewById(R.id.userinfor);
        textView.setText("姓名："+name+"\n"+"学号："+student_ID+"\n"+"学部/学院："+faculty+"\n"+ "专业："+major);
        return rootView;
    }

}
