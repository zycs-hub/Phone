package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.zy.stry.R;
import android.view.View.OnClickListener;





//import zychoose.DUTCrawler;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt=(Button)findViewById(R.id.login);
        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name=(EditText)findViewById(R.id.name);
                EditText password=(EditText)findViewById(R.id.password);
                Bundle data=new Bundle();
                data.putString("name",name.getText().toString());
                data.putString("password",password.getText().toString());
                Intent  intent=new Intent(MainActivity.this, LogActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
