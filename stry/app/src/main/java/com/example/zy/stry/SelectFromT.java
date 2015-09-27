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
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONObject;


public class SelectFromT extends Activity {

    private Button btn_s=null;
    private TextView main_tv1=null;
    private LayoutInflater inflater=null;
    private RelativeLayout main_body_lin=null;
    private ListView page_list=null;
    private View v = null;
    private MyAdapter ma=null;
    DatabaseHandler db=null;
    List<BookEntity> lt=null;

    private BookOperarion bookOperator;
    SharedPreferences shared_preferences;

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



        db = new DatabaseHandler(getApplicationContext());
        lt= db.getCoursesAll();
        //long param = db.addDate(lt, job);
        //lt= db.getUserAll(job);
        // get need user book
        // is selling true false unknown
        // find unknown

        if(lt.isEmpty()){
            //BookGlobla=lt;
            Intent intent_=new Intent(SelectFromT.this, MainActivity.class);
            startActivity(intent_);
            finish();
        }
        else{
            //ThreadBooksMessage str =new ThreadBooksMessage(han,result.getString("name"),result.getString("password"),"Override");
            //new Thread(str).start();
        }


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



        // main_body_lin.addView(v);
        Button btn=(Button)findViewById(R.id.btn_s);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                job=db.getReadableDatabase();
                int n = ma.getCount();
                for (int i = 0; i < n; i++) {
                    String na = lt.get(i).getBook();
                    String courseid = Integer.toString(lt.get(i).courseid);
                    if (page_list.isItemChecked(i)) {
                        books_data += courseid + ',' + na;
                        if (i != n - 1) {
                            books_data += '\n';
                        }
                        for (BookEntity u : lt) {
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
                        db.addSell(username, na, lt.get(i).courseid, na, -1, null ,false,false,null,null,false,-1);
                    } else{
                        job.execSQL("update courses set  isSelected=-1 where book=?", new String[]{na});
                    }
                }
                db.close();
                new Thread(new Runnable() {
                    public void run() {
                        try {

                            JSONObject json = bookOperator.addSellBooks(username, books_data.substring(0, books_data.length() - 1));
                            if (json.getString("success") != null) {
                                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            System.out.println("Exception : " + e.getMessage());
                        }
                    }
                }).start();

               // saveAll();
                //main_body_lin.removeAllViews();
                db.close();
                Intent intent_1 = new Intent(SelectFromT.this, MainActivity.class);
                startActivity(intent_1);
                finish();
            }
        });




    }
        //  @Override
    // protected void onActiyityResult(int requestCode,int resultCode,Intent data){
    //    if
    // }


}
