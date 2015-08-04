package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


/**
 * Created by wendy on 15-8-4.
 */
public class ProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String username = intent.getParcelableExtra("username");
        Toast.makeText(ProfileActivity.this, username, Toast.LENGTH_SHORT).show();



    }
}
