package com.example.zy.stry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.Config;

import org.json.JSONObject;

/**
 * Created by wendy on 15-10-12.
 */
public class OrderActivity extends AppCompatActivity {
    private ListView listView;
    private EditText addressText;
    private EditText commentText;
    private Button confirmBnt;
    private String username;
    private String books;
    private String address;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        listView =  (ListView) findViewById(R.id.listView1);
        addressText = (EditText) findViewById(R.id.text_adr);
        commentText = (EditText) findViewById(R.id.comment);
        confirmBnt = (Button) findViewById(R.id.button);


        Intent intent = getIntent();
        books =  intent.getStringExtra("books");
        username = intent.getStringExtra("username");

        confirmBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                address = addressText.getText().toString();
                comment = commentText.getText().toString();

                BookOperarion bookOpt = new BookOperarion();
                BookOperarion.buyBooks task = bookOpt.new buyBooks(username, books, address, comment);

                try {

                    MainActivity.executorService.submit(task);
                    JSONObject json = task.json;

                    if (json.getString(Config.KEY_SUCCESS) != null) {
                        finish();
                    } else {

                    }
                } catch (Exception e) {
                    System.out.println("Exception : " + e.getMessage());
                }


            }
        });
    }
}