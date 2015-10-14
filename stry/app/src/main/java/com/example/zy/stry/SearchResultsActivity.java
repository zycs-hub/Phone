package com.example.zy.stry;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zy.stry.entity.SellBook;
import com.example.zy.stry.lib.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-9-17.
 */
public class SearchResultsActivity extends Activity {
    ListView mlistView;
    private ArrayAdapter<SellBook> mAdapter;
    List<SellBook> lt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        mlistView = (ListView) findViewById(R.id.listView);

        lt =  new ArrayList<>();

        // get the action bar
//        ActionBar actionBar = getActionBar(); //这里是null
//
//        // Enabling Back navigation on Action Bar icon
//        actionBar.setDisplayHomeAsUpEnabled(true);


        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
            //use the query to search your data somehow
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            lt = db.search(query);
            mAdapter = new SearchAdapter(getApplicationContext());
            mlistView.setAdapter(mAdapter);

        }
    }

    private class SearchAdapter extends ArrayAdapter<SellBook> {
        private LayoutInflater inflater;
        private Context context;


        public SearchAdapter(Context context) {
            super(context, R.layout.item_book);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
            this.context = context;
        }


        @Override
        public int getCount() {
            return lt.size();
        }

        @Override
        public SellBook getItem(int position) {
            return lt.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            SellBook item = lt.get(position);


            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_shop, null);
                viewHolder.s_image = (ImageView) convertView.findViewById(R.id.s_image);
                viewHolder.s_title = (TextView) convertView.findViewById(R.id.s_title);
//                viewHolder.book = (TextView) convertView.findViewById(R.id.book);
//                viewHolder.author = (TextView) convertView.findViewById(R.id.author);
                viewHolder.s_price = (TextView) convertView.findViewById(R.id.s_price);
//                viewHolder.message = (TextView) convertView.findViewById(R.id.message);
                viewHolder.s_owner = (TextView) convertView.findViewById(R.id.s_owner);
                viewHolder.s_major = (TextView) convertView.findViewById(R.id.s_major);
                viewHolder.s_course = (TextView) convertView.findViewById(R.id.s_course);




                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }


//            if (item.image!=null)
//                Glide.with(viewHolder.s_image.getContext())
//                    .load(item.image)
//                    .fitCenter()
//                    .into(viewHolder.s_image);
            if (item.bookname==null){
                viewHolder.s_title.setText("课程名："+item.coursename);
                viewHolder.s_course.setVisibility(View.GONE);
            }else{
                viewHolder.s_title.setText(item.bookname);
                viewHolder.s_course.setText("课程名："+item.coursename);
            }

//            viewHolder.book.setText(item.bookname);
            viewHolder.s_price.setText("价格："+Integer.toString(item.price));
            viewHolder.s_owner.setText("卖家：" + item.username);
//            if (item.major!=null)
//                viewHolder.s_major.setText(item.major);
//            else
            viewHolder.s_major.setVisibility(View.GONE);

            return convertView;
        }

        class ViewHolder{
            ImageView s_image;
            TextView s_title;
            TextView book;
            //            TextView author;
            TextView s_price;
            //            TextView message;
            TextView s_owner;
            TextView s_major;
            TextView s_course;

            public ImageView getS_Image() {
                return s_image;
            }

            public TextView gets_title() {
                return s_title;
            }
        }
    }
}

