package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.adapter.MyAdapter;
import com.example.zy.stry.lib.User;
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.UserbookGlobla;

import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONObject;


public class SetUserInfActivity extends AppCompatActivity {
    RelativeLayout logout;
    SharedPreferences shared_preferences;
    public static final String PREFS_NAME = "MyPrefs";





    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_set_inf);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("个人");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        logout = (RelativeLayout) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
//                try {
                User user = new User();
                user.logoutUser(SetUserInfActivity.this);
                UserbookGlobla.lts.clear();
                Fragment3.collapsingToolbar.setTitle("登录");
                Fragment3.head.setVisibility(View.GONE);
                Intent intent_1 = new Intent(SetUserInfActivity.this, MainActivity.class);
                startActivity(intent_1);
                finish();
//                }
//
//                catch (Exception e){
//                    e.printStackTrace();
//                }

            }
        });

        TextView tname = (TextView) findViewById(R.id.i_tname);
        TextView tstudentid = (TextView) findViewById(R.id.i_tstudentid);
        TextView tfaculty = (TextView) findViewById(R.id.i_tfaculty);
        TextView tmajor = (TextView) findViewById(R.id.i_tmajor);
        TextView tgrade = (TextView) findViewById(R.id.i_tgrade);
        TextView taddress = (TextView) findViewById(R.id.i_taddress);
        String name ,faculty,major,student_ID,grade;
        shared_preferences = this.getSharedPreferences(PREFS_NAME, this.MODE_PRIVATE);
        name = shared_preferences.getString("name", null);
        faculty = shared_preferences.getString("faculty", null);
        major = shared_preferences.getString("major", null);
        student_ID = shared_preferences.getString("student_ID", null);
        grade = shared_preferences.getString("grade", null);
        tname.setText("姓名："+name);
        tstudentid.setText("学号："+student_ID);
        tfaculty.setText("学部/学院："+faculty);
        tmajor.setText("专业："+major);
        tgrade.setText("年级："+ grade);
        taddress.setText("地址：");




    }


}
