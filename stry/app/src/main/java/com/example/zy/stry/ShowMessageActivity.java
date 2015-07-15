package com.example.zy.stry;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.zy.stry.R;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Created by zy on 15/7/10.
 */
public class ShowMessageActivity  extends Activity {
    public static final int SHOW_MESSAGE_CODE = 2000;
    private TextView bookname = null;
    private String bookName = null;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message_buy);
        bookname = (TextView) findViewById(R.id.book_name_);
        Intent it = new Intent(ShowMessageActivity.this, Buy_Activity.class);
        setResult(SHOW_MESSAGE_CODE);
        it = getIntent();
        if (it != null) {
            bookName = it.getStringExtra("bookName");
            position = it.getIntExtra("position", -1);
            bookname.setText(bookName);
        }
    }
}
