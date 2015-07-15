package com.example.zy.stry;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import android.os.Message;
import android.os.Handler;
import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.ThreadUsersMessage;
import com.example.zy.stry.util.UserGlobla;
import com.example.zy.stry.entity.UserEntity;
import android.view.View.OnClickListener;


public class LogActivity extends Activity{
       public static final int MAIN_ACTIVITY=1000;
       private Button btn_s=null;
       private TextView main_tv1=null;
       private LayoutInflater inflater=null;
       private RelativeLayout main_body_lin=null;
       private ListView page_list=null;
       private View v = null;
       private MyAdapter ma=null;
       private Handler han =null;
       My_DB db=null;
       private SQLiteDatabase job =null;
       private List<UserEntity> lt2=null;
       private MyAdapter ma2=null;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_choose);
        Intent intent=getIntent();
        Bundle result=intent.getExtras();
        han = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    // case 1:
                    //   ma.notifyDataSetChanged();
                    //         break;
                    //   case 2:
                    //   new OutDialog(LogActivity.this).getDialog("shibai",true).show();
                    //    break;
                    case 3:
                       // long param = db.addDate(UserGlobla.lts,job);
                       // if(param==UserGlobla.lts.size()){
                            page_list=(ListView)v.findViewById(R.id.page_list);
                            ma=new MyAdapter(UserGlobla.lts,LogActivity.this);
                            page_list.setAdapter(ma);
                            main_body_lin.addView(v);

                       // }
                        break;
                    default:break;
                }

            }
        };
        db=new My_DB(LogActivity.this,My_DB.MY_DB_MANE,null,My_DB.MY_DB_VERSION);
        job=db.getReadableDatabase();
        List<UserEntity> lt= new ArrayList<UserEntity>();  //=db.getUserAll(job);
        if(!lt.isEmpty()){
            UserGlobla.lts=lt;
        }
        else{
            ThreadUsersMessage str =new ThreadUsersMessage(han,result.getString("name"),result.getString("password"));
            new Thread(str).start();
        }
        inflater =getLayoutInflater();
        btn_s=(Button) findViewById(R.id.btn_s);
        main_tv1=(TextView)findViewById(R.id.main_tv1);
        main_body_lin=(RelativeLayout) findViewById(R.id.main_body_line);
        v= inflater.inflate(R.layout.main_page_1,null);
        page_list=(ListView)v.findViewById(R.id.page_list);
        ma=new MyAdapter(UserGlobla.lts,LogActivity.this);
        page_list.setAdapter(ma);
       // main_body_lin.addView(v);













       // main_body_lin.addView(v);
        Button btn=(Button)findViewById(R.id.btn_s);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                main_body_lin.removeAllViews();
                Intent intent_1=new Intent(LogActivity.this, Buy_Activity.class);
                startActivity(intent_1);

            }
        });


    }
    //  @Override
    // protected void onActiyityResult(int requestCode,int resultCode,Intent data){
    //    if
    // }


}
