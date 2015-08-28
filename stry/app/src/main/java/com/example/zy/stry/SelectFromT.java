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
import java.util.List;
import java.util.ArrayList;
import android.os.Message;
import android.os.Handler;

import com.example.zy.stry.util.CourseGlobla;
import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.ThreadUsersMessage;
import com.example.zy.stry.util.UserGlobla;
import com.example.zy.stry.entity.UserEntity;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class SelectFromT extends Activity{
       public static final int MAIN_ACTIVITY=1000;
       private Button btn_s=null;
       private TextView main_tv1=null;
       private LayoutInflater inflater=null;
       private RelativeLayout main_body_lin=null;
       private ListView page_list=null;
       private View v = null;
       private MyAdapter ma=null;
       My_DB db=null;
    List<UserEntity> lt=null;
       private SQLiteDatabase job =null;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_choose);
        //Intent intent=getIntent();
        //Bundle result=intent.getExtras();


        db=new My_DB(SelectFromT.this,My_DB.MY_DB_MANE,null,My_DB.MY_DB_VERSION);
        job=db.getReadableDatabase();
       // job.execSQL("update user set  isSelected = 1 where book  = '经济学原理' ");
        job.execSQL("insert into user(book,isSelected) values(?,?)", new Object[]{"测试数据",1});
        lt= db.getUserAll(job);
        //db.close();
        //long param = db.addDate(lt, job);
        //lt= db.getUserAll(job);
        // get need user book
        // is selling true false unknown
        // find unknown
        /*
        if(lt.isEmpty()){
            //UserGlobla.lts=lt;
            Intent intent_=new Intent(SelectFromT.this, MainActivity.class);
            startActivity(intent_);
            finish();
        }
        else{
            //ThreadUsersMessage str =new ThreadUsersMessage(han,result.getString("name"),result.getString("password"),"Override");
            //new Thread(str).start();
        }

*/





        inflater =getLayoutInflater();
        btn_s=(Button) findViewById(R.id.btn_s);
        main_tv1=(TextView)findViewById(R.id.main_tv1);
        main_body_lin=(RelativeLayout) findViewById(R.id.main_body_line);
        v= inflater.inflate(R.layout.main_page_1,null);
        page_list=(ListView)v.findViewById(R.id.page_list);
        page_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ma=new MyAdapter(UserGlobla.lts,SelectFromT.this,page_list);
        page_list.setAdapter(ma);
        main_body_lin.addView(v);
        page_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ma.notifyDataSetChanged();
               // main_body_lin.addView(v);

                //updateSeletedCount();
            }
        });

        // main_body_lin.addView(v);
        Button btn=(Button)findViewById(R.id.btn_s);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < ma.getCount(); i++) {
                    String na = lt.get(i).getBook();
                    if (page_list.isItemChecked(i))
                        for (UserEntity u : lt) {
                            //u.isSelected(1);

                            try {
                                job.execSQL("update user set  isSelected=1 where book=?", new String[]{na});


                            }
                            catch (Exception e   ){
                                Toast.makeText(SelectFromT.this, "err", Toast.LENGTH_LONG).show();
                                e.toString();

                            }
                            //page_list.setItemChecked(i, true);
                        }else{
                        job.execSQL("update user set  isSelected=-1 where book=?", new String[]{na});
                    }
                }
               // saveAll();
                //main_body_lin.removeAllViews();
                db.close();
                Intent intent_1 = new Intent(SelectFromT.this, MainActivity.class);
                startActivity(intent_1);

                finish();

            }
        });




    }
    public void saveAll() {
        for (int i = 0; i < ma.getCount(); i++) {
            if (page_list.isItemChecked(i))
                for (UserEntity u : lt) {
                    u.isSelected(1);
                    String na = lt.get(i).getBook();
                    job.execSQL("update user set  isSelected=1 where book =" + na);
                    //page_list.setItemChecked(i, true);
                }
        }
    }
        //  @Override
    // protected void onActiyityResult(int requestCode,int resultCode,Intent data){
    //    if
    // }


}
