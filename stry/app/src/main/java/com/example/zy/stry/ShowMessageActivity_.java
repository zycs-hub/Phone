package com.example.zy.stry;

import android.app.Activity;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zy on 15/7/10.
 */
public class ShowMessageActivity_  extends Activity {
    public static final int SHOW_MESSAGE_CODE_ = 3000;
    private TextView bookname = null;
    private String bookName = null;
    private int position = -1;
    private LinearLayout show_message_lay=null;
    private LinearLayout show_more_lay=null;

    private boolean usershowflag=true;
    private Button sms=null;
    private String phone="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message_sell);
        bookname = (TextView) findViewById(R.id.book_name);
        show_message_lay=(LinearLayout)findViewById(R.id.book_show_lay);
        show_more_lay=(LinearLayout)findViewById(R.id.more_message_lay);

        sms=(Button)findViewById(R.id.sms);
        phone="18525425667";

        Intent it1 = new Intent(ShowMessageActivity_.this, Buy_Activity.class);
        setResult(SHOW_MESSAGE_CODE_);
        it1 = getIntent();
        if (it1 != null) {
            bookName = it1.getStringExtra("bookName");
            position = it1.getIntExtra("position", -1);
            bookname.setText(bookName);
        }
        show_message_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usershowflag){
                    show_more_lay.setVisibility(View.VISIBLE);
                    usershowflag=false;
                }
                else {
                    show_more_lay.setVisibility(View.GONE);
                    usershowflag=true;
                }

            }
        });

        sms.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri smsToUri=Uri.parse("smsto:"+phone);
                Intent it=new Intent(Intent.ACTION_SENDTO, smsToUri);
                it.putExtra("sss","sss");
                startActivity(it);
            }
        });

    }
}
