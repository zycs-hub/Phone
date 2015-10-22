package com.example.zy.stry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.util.SoldGlobal;
import com.example.zy.stry.util.UserbookGlobla;
import com.example.zy.stry.widget.DividerItemDecoration;
import com.example.zy.stry.widget.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by wendy on 15-10-14.
 */
public class BuyingListActivity extends AppCompatActivity {

    private  MaterialDialog dialog=null;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    DatabaseHandler db = null;
    SharedPreferences shared_preferences;
    int flag=1;


    public static final String PREFS_NAME = "MyPrefs";
    private String username;
    private String student_ID;
    private String password_T;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("购买中");
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
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shared_preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, getApplicationContext().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);

        // specify an adapter (see also next example)
        if(username != null) {
            db = new DatabaseHandler(getApplicationContext());
            List<BookEntity> lt = db.getBuyingBooks(username);
            if (lt==null){
                Intent intent = new Intent(getApplicationContext(), LogForT.class);
                startActivity(intent);
            }
            if (lt!=null)
                for (BookEntity book : lt){
                    SoldGlobal.lts=lt;
                    //book_for_sell.add(book);
                }
            if (flag==1){
                flag++;
                DatabaseHandler db;
                List<BookEntity> lts;
                db = new DatabaseHandler(getApplicationContext());
                lts = db.getCoursesAll();
                for (int i = 0, len = SoldGlobal.lts.size(); i < len; i++)
                    for (int j = 0, leng = lts.size(); j < leng; j++)
                        if (SoldGlobal.lts.get(i).courseid == lts.get(j).courseid) {
                            SoldGlobal.lts.get(i).image=lts.get(j).image;
                            SoldGlobal.lts.get(i).author=lts.get(j).author;
                            SoldGlobal.lts.get(i).publisher=lts.get(j).publisher;
                            SoldGlobal.lts.get(i).pages=lts.get(j).pages;
                            lts.remove(j);
                            --leng;
                            --j;

                            break;
                        }
            }
            lt=null;
        }


        mAdapter = new MyAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

    }


    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

//            　 ∧__∧
//            　( ●ω●)
//            　｜つ／(＿＿＿
//            ／└-(＿＿＿_／

            String book ;//mAdapter.getBook(position);
            book = SoldGlobal.lts.get(position).getBook();
            //book = book_for_sell.get(position).getBook();
            Intent intent1 = new Intent(getApplicationContext(), MyCenterBookDetailActivity.class);
            intent1.putExtra("book", book);
            intent1.putExtra("bid", SoldGlobal.lts.get(position).bid);
            intent1.putExtra("position", position);
            startActivity(intent1);



        }
    };


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final int mBackground;
        private final TypedValue mTypedValue = new TypedValue();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView ivBook;
            public TextView tvTitle;
            public TextView book;
            public TextView price;
            public TextView author;
            public TextView message;
            public TextView dot;
            public TextView date;



            public ViewHolder(View v) {
                super(v);
                ivBook = (ImageView) v.findViewById(R.id.vBook);
                tvTitle = (TextView) v.findViewById(R.id.vTitle);
                book = (TextView) v.findViewById(R.id.book);
                price = (TextView) v.findViewById(R.id.price);
                message = (TextView) v.findViewById(R.id.message);
                author = (TextView) v.findViewById(R.id.author);
                dot = (TextView) v.findViewById(R.id.dot);

                date = (TextView) v.findViewById(R.id.data);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_courses, parent, false);
            //v.setBackgroundResource(mBackground);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            BookEntity book = SoldGlobal.lts.get(position);
            //BookEntity book = book_for_sell.get(position);
            holder.tvTitle.setText(book.getBook());
            if (book.getBook()!=null)
                holder.tvTitle.setText(book.getBook());
            else
                holder.tvTitle.setText(book.bookname);
            if (book.image!=null)
                Glide.with(holder.ivBook.getContext())
                        .load(book.image)
                        .fitCenter()
                        .into(holder.ivBook);
            else holder.ivBook.setImageDrawable(null);
            if (book.bookname!=null)
                holder.book.setText("书 名：" + book.bookname);
            else
                holder.book.setText("书 名：");
            if (book.author!=null)
                holder.author.setText("作 者："+book.author);
            else
                holder.author.setText("作 者：");
            if (book.price!=null)
                holder.price.setText("价 格："+book.price+"元");
            else
                holder.price.setText("价 格：");
            //if (book.price!=null)
            if (book.message.size()!=0&& !book.message.get(0).equals("m无消息")) {
                holder.message.setVisibility(View.VISIBLE);
                holder.message.setText(book.message.get(1).substring(1));
                holder.dot.setVisibility(View.VISIBLE);
                holder.date.setVisibility(View.VISIBLE);
                holder.date.setText(book.message.get(0).substring(1));
            }
            else {
                holder.message.setVisibility(View.GONE);

                holder.dot.setVisibility(View.GONE);
                holder.date.setVisibility(View.GONE);}

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return SoldGlobal.lts.size();
        }
        public BookEntity getBook(int pos) {
            return SoldGlobal.lts.get(pos);
        }
        public void update() {
            notifyDataSetChanged();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapter= new MyAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

    }

}
