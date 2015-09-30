package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.UserbookGlobla;

import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONObject;


public class SetUserInfActivity extends Activity {




    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_set_inf);


    }


}
