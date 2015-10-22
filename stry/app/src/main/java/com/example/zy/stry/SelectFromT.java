package com.example.zy.stry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Handler;
import android.os.Message;
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
import com.example.zy.stry.lib.Function;
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.UserbookGlobla;

import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONObject;


public class SelectFromT extends AppCompatActivity {

    private Button btn_s=null;
    private TextView main_tv1=null;
    private LayoutInflater inflater=null;
    private RelativeLayout main_body_lin=null;
    private ListView page_list=null;
    private View v = null;
    private MyAdapter ma=null;
    DatabaseHandler db=null;

    private BookOperarion bookOperator;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor prefEditor;


    private SQLiteDatabase job =null;

    public static final String PREFS_NAME = "MyPrefs";
    private String books_data = "";
    private String username;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_select);
        //Intent intent=getIntent();
        //Bundle result=intent.getExtras();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("点击选择要买的书");
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



        db = new DatabaseHandler(getApplicationContext());
        //long param = db.addDate(lt, job);
        List<BookEntity> lt= db.getCoursesAll();
        // get need user book
        // is selling true false unknown
        // find unknown



        inflater = getLayoutInflater();
        btn_s=(Button) findViewById(R.id.btn_s);
        main_tv1=(TextView)findViewById(R.id.main_tv1);
        main_body_lin=(RelativeLayout) findViewById(R.id.main_body_line);
        v= inflater.inflate(R.layout.main_page_1,null);
        page_list=(ListView)v.findViewById(R.id.page_list);
        page_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ma=new MyAdapter(BookGlobla.lts,SelectFromT.this,page_list);
        page_list.setAdapter(ma);
        main_body_lin.addView(v);




        page_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ma.notifyDataSetChanged();
                // main_body_lin.addView(v);

                //updateSeletedCount();
            }
        });
        bookOperator = new BookOperarion();

        shared_preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        username = shared_preferences.getString("username", null);

        prefEditor = shared_preferences.edit();
        if (UserbookGlobla.user.name!=null)
            prefEditor.putString("name", UserbookGlobla.user.name);
        if (UserbookGlobla.user.faculty!=null)
            prefEditor.putString("faculty", UserbookGlobla.user.faculty);
        if (UserbookGlobla.user.major!=null)
            prefEditor.putString("major", UserbookGlobla.user.major);
        if (UserbookGlobla.user.grade!=null)
            prefEditor.putString("grade", UserbookGlobla.user.grade);
        if (UserbookGlobla.user.student_ID!=null)
            prefEditor.putString("student_ID", UserbookGlobla.user.student_ID);
        if (UserbookGlobla.user.password!=null)
            prefEditor.putString("password_T", UserbookGlobla.user.password);
        prefEditor.apply();



        // main_body_lin.addView(v);
        Button btn=(Button)findViewById(R.id.btn_s);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int lastid = db.getLastSellID();
                int n = ma.getCount();
                for (int i = 0; i < n; i++) {
                    String na = BookGlobla.lts.get(i).getBook();
                    String courseid = Integer.toString(BookGlobla.lts.get(i).courseid);
                    if (page_list.isItemChecked(i)) {
                        books_data += courseid + ',' + na;
                        if (i != n - 1) {
                            books_data += '\n';
                        }
                        for (BookEntity u : BookGlobla.lts) {
                            //u.isSelected(1);

                            // try {
                            job.execSQL("update courses set  isSelected=1 where book=?", new String[]{na});


                            //}
                            //catch (Exception e   ){
                            // Toast.makeText(SelectFromT.this, "err", Toast.LENGTH_LONG).show();
                            //  e.toString();

                            //}
                            //page_list.setItemChecked(i, true);
                        }
                        db.addSell(++lastid, username, na, BookGlobla.lts.get(i).courseid, na, -1, null ,0,0,null,null,0,-1, "","","","","");
                    } else{
                        job.execSQL("update courses set  isSelected=-1 where book=?", new String[]{na});
                    }
                }
                db.close();

                BookOperarion book = new BookOperarion();
                BookOperarion.addSellBooks task = book.new addSellBooks(username, books_data.substring(0, books_data.length() - 1),
                        new Function<JSONObject, Void>() {
                            @Override
                            public Void apply(JSONObject json) {
                                try{
                                if (json == null) {
                                    Message msg = new Message();
                                    msg.what = 0;
                                    handler.sendMessage(msg);
                                } else {
                                    if (json.getString("success") != null) {
                                        db.close();
                                        BookGlobla.lts=null;
                                        Intent intent_1 = new Intent(SelectFromT.this, MainActivity.class);
                                        startActivity(intent_1);
                                        finish();
                                        Message msg = new Message();
                                        msg.what = 1;
                                        handler.sendMessage(msg);
                                    } else {
                                        Message msg = new Message();
                                        msg.what = -1;
                                        handler.sendMessage(msg);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Exception : " + e.getMessage());
                            }
                                return null;
                            }
                        });


                    MainActivity.executorService.submit(task);




               // saveAll();
                //main_body_lin.removeAllViews();

            }
        });




    }
        //  @Override
    // protected void onActiyityResult(int requestCode,int resultCode,Intent data){
    //    if
    // }
        private Handler handler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();


                        break;
                    case -1:
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        break;


                }

            }
        };



}
