package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import android.os.Message;
import android.os.Handler;

import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.ThreadBooksMessage;
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.entity.BookEntity;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class SelectFromT extends Activity {

    public static final int MAIN_ACTIVITY=1000;
    private Button btn_s=null;
    private TextView main_tv1=null;
    private LayoutInflater inflater=null;
    private RelativeLayout main_body_lin=null;
    private ListView page_list=null;
    private View v = null;
    private MyAdapter ma=null;
    DatabaseHandler db=null;
    List<BookEntity> lt=null;
    private SQLiteDatabase job =null;
    private String books_data = null;
    private BookOperarion bookOperator;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_select);
        //Intent intent=getIntent();
        //Bundle result=intent.getExtras();



         db = new DatabaseHandler(getApplicationContext());

        //db=new My_DB(SelectFromT.this,My_DB.MY_DB_MANE,null,My_DB.MY_DB_VERSION);
        job=db.getReadableDatabase();
       // job.execSQL("update user set  isSelected = 1 where book  = '经济学原理' ");
        //job.execSQL("insert into user(book,isSelected) values(?,?)", new Object[]{"测试数据",1});
        lt= db.getUserAll(job);
        //db.close();
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

        // main_body_lin.addView(v);
        Button btn=(Button)findViewById(R.id.btn_s);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                job=db.getReadableDatabase();
                int n = ma.getCount();
                for (int i = 0; i < n; i++) {
                    String na = lt.get(i).getBook();
                    books_data += lt.toString();
                    if(i != n - 1) {
                        books_data += '\n';
                    }
                    if (page_list.isItemChecked(i))
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
                        }else{
                        job.execSQL("update courses set  isSelected=-1 where book=?", new String[]{na});
                    }
                    HashMap<String, String> user = db.getUsersData();

                    db.addSell(user.get("username"), lt.get(i).bookname, lt.get(i).courseid,lt.get(i).coursename, -1, null ,false,false,null,null,false,-1);

                }
                bookOperator.addSellBooks(username, books_data);
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
