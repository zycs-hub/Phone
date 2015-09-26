package com.example.zy.stry;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.UserbookGlobla;

/**
 * Created by zy on 15/9/26.
 */
public class SelfDefineActivity  extends AppCompatActivity {
    TextView tprice ;
    TextView taddress ;
    TextView tdamage ;
    TextView tremarks ;
    TextView tname ;
    TextView tauthor ;
    TextView tpublish ;
    TextView toprice;
    TextView tpage ;
    public static BookEntity mbook =new BookEntity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);
        Toolbar mToolbar;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("点击选择匹配的书");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                mbook = new BookEntity();
            }
        });
        RelativeLayout add = (RelativeLayout) findViewById(R.id.addmore);
        RelativeLayout price = (RelativeLayout) findViewById(R.id.price);
        RelativeLayout damage = (RelativeLayout) findViewById(R.id.damage);
        RelativeLayout remark = (RelativeLayout) findViewById(R.id.remark);
        RelativeLayout address = (RelativeLayout) findViewById(R.id.address);
        RelativeLayout name = (RelativeLayout) findViewById(R.id.name);
        RelativeLayout author = (RelativeLayout) findViewById(R.id.author);
        RelativeLayout publish = (RelativeLayout) findViewById(R.id.publish);
        RelativeLayout oprice = (RelativeLayout) findViewById(R.id.oprice);
        RelativeLayout page = (RelativeLayout) findViewById(R.id.page);
        tprice = (TextView) findViewById(R.id.tprice);
        taddress = (TextView) findViewById(R.id.taddress);
        tdamage = (TextView) findViewById(R.id.tdamage);
        tremarks = (TextView) findViewById(R.id.tremarks);
        tname = (TextView) findViewById(R.id.tname);
        tauthor = (TextView) findViewById(R.id.tauthor);
        tpublish = (TextView) findViewById(R.id.tpublish);
        toprice = (TextView) findViewById(R.id.toprice);
        tpage = (TextView) findViewById(R.id.tpage);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //           ∧__∧
                //        　( ●ω●)
                //        　｜つ／(＿＿＿
                //        ／└-(＿＿＿_／


                Intent intent = new Intent(SelfDefineActivity.this, ChooseActivity.class);
                //intent.putExtra("book", book);
                //intent.putExtra("position", position);
                startActivity(intent);

            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入价格")
                        .content("输入价格")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tprice.setText("价 格：" + input);
                                    mbook.price=input.toString();
                                    //UserbookGlobla.lts.get(position).price=input.toString();

                                }
                            }
                        }).show();

            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入价格")
                        .content("输入价格")
                                //.inputType(InputType.TEXT )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    taddress.setText("地 址：" + input);

                                }
                            }
                        }).show();

            }
        });
        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入价格")
                        .content("输入价格")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tdamage.setText("破损程度：" + input);
                                    mbook.damage = input.toString();
                                    //UserbookGlobla.lts.get(position).damage = input.toString();
                                }
                            }
                        }).show();

            }
        });
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入价格")
                        .content("输入价格")
                                //.inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tremarks.setText("备 注：" + input);
                                    mbook.remarks = input.toString();
                                    //UserbookGlobla.lts.get(position).remarks = input.toString();
                                }
                            }
                        }).show();

            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入书名")
                        .content("输入书名")
                                //.inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tname.setText("书 名：" + input);
                                    mbook.bookname=input.toString();
                                    //UserbookGlobla.lts.get(position).remarks = input.toString();
                                }
                            }
                        }).show();

            }
        });
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入作者")
                        .content("输入作者")
                                //.inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tauthor.setText("作 者：" + input);
                                    mbook.author=input.toString();
                                    //UserbookGlobla.lts.get(position).remarks = input.toString();
                                }
                            }
                        }).show();

            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入出版社")
                        .content("输入出版社")
                                //.inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tpublish.setText("出版社：" + input);
                                    mbook.publisher=input.toString();
                                }
                            }
                        }).show();

            }
        });
        oprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入原价")
                        .content("输入原价")
                                //.inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    toprice.setText("原 价：" + input);
                                    mbook.origprice=input.toString();
                                    //UserbookGlobla.lts.get(position).remarks = input.toString();
                                }
                            }
                        }).show();

            }
        });
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(SelfDefineActivity.this)
                        .title("输入页数")
                        .content("输入页数")
                                //.inputType(InputType.TYPE_CLASS_NUMBER )
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    tpage.setText("页 数：" + input);
                                    mbook.pages=input.toString();
                                }
                            }
                        }).show();

            }
        });

        update();
        setUpFAB();

    }
    public void update() {
        try {
            if (mbook.price==null)
                tprice.setText("售 价：");
            else
                tprice.setText("售 价："+mbook.price);
            if (mbook.remarks==null)
                tremarks.setText("备 注：");
            else
                tremarks.setText("备 注："+mbook.remarks);
            if (mbook.damage==null)
                tdamage.setText("破损程度：");
            else
                tdamage.setText("破损程度："+mbook.damage);
            if (mbook.bookname==null)
                tname.setText("书 名：");
            else
                tname.setText("书 名："+mbook.bookname);
            if (mbook.author==null)
                tauthor.setText("作 者：");
            else
                tauthor.setText("作 者："+mbook.author);
            if (mbook.publisher==null)
                tpublish.setText("出版社：");
            else
                tpublish.setText("出版社："+mbook.publisher);
            if (mbook.pages!=null)
                tpage.setText("页 数："+mbook.pages);
            else
                tpage.setText("页 数：");
            if (mbook.origprice!=null)
                toprice.setText("原 价："+mbook.origprice);
            else
                toprice.setText("原 价：");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setUpFAB( ) {
        FloatingActionButton mFabButton = (FloatingActionButton) findViewById(R.id.fab);

        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mbook.bookname==null) {
                    Toast.makeText(SelfDefineActivity.this,"请完善信息",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(SelfDefineActivity.this,"已添加",Toast.LENGTH_LONG).show();
                UserbookGlobla.lts.add(mbook);
                mbook= new BookEntity();
                update();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        update();

    }
}
