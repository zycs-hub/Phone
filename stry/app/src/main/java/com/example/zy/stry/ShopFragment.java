package com.example.zy.stry;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.Book;
import com.example.zy.stry.entity.SellEntity;
import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.DatabaseHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wendy on 15-9-27.
 */
public class ShopFragment extends Fragment implements PullToRefreshBase.OnRefreshListener<ListView> {
    private LinkedList<String> mListItems;
    private ShopAdapter  mAdapter;
    private FragmentActivity listener;
    private ArrayList<SellEntity.SellBook> tmp = new ArrayList<>();
    private ArrayList<SellEntity.SellBook> mData = new ArrayList<>();
    private List mStrings;
    private PullToRefreshListView mPullRefreshListView;
    DatabaseHandler db;

    private static String KEY_SUCCESS = "success";
    public static String KEY_USERNAME = "username";
    public static String KEY_BOOKNAME = "bookname";
    public static String KEY_COURSEID = "courseid";
    public static String KEY_COURSENAME = "coursename";
    public static String KEY_PRICE = "price";
    public static String KEY_PRESSS = "press";
    public static String KEY_IS_SELLING = "is_selling";
    public static String KEY_IS_SOLD = "is_sold";
    public static String KEY_ADD_TIME = "add_time";
    public static String KEY_UPDATE_TIME = "update_time";
    public static String KEY_IS_DEL = "is_del";
    public static String KEY_BID = "bid";
    public static String KEY_BUYER = "buyer";



    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (FragmentActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = MainActivity.db;
        setHasOptionsMenu(true);
        mStrings = new ArrayList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);
        //Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        //set toolbar appearance
        //toolbar.setBackground(R.color.material_blue_grey_800);

        //for crate home button
        //AppCompatActivity activity = (AppCompatActivity) getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        //activity.setSupportActionBar(toolbar);
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get PullToRefreshListView from Fragment
        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(this);




        // You can also just use mPullRefreshListFragment.getListView()
        ListView actualListView = mPullRefreshListView.getRefreshableView();

        registerForContextMenu(actualListView);

        mListItems = new LinkedList<String>();


        //如果连网就更新否则直接查询本地
        Toast.makeText(getActivity(), "加载中...", Toast.LENGTH_LONG).show();
        if(MainActivity.hvNetwork) {
            //new GetData().execute();
        } else {
            tmp =  db.getShopData();
            mStrings.removeAll(mStrings);
            for(int i = 0; i < tmp.size(); i++) {
                if(tmp.get(i).is_selling == 1 && tmp.get(i).is_sold == 0) {
                    mStrings.add(tmp.get(i).bookname);
                    mData.add(tmp.get(i));
                    mListItems.addAll(mStrings);
                }
            }
            if(mData.size() == 0) {
                Toast.makeText(getActivity(), "本地数据库空，请联网", Toast.LENGTH_SHORT).show();
            }

        }
//        mAdapter=new ListAdapter(getActivity(),mData);
        mAdapter = new ShopAdapter(getActivity());
        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH,R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET,R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);
        mPullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Book book =new Book();
                Intent intent1 = new Intent(getActivity(), BookDetailActivity.class);
                if(mData.size() > 0) {
                    intent1.putExtra("book", mStrings.get(position - 1).toString());
                    intent1.putExtra("_id", mData.get(position - 1)._id);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("data", mData.get(position - 1));
                    intent1.putExtras(mBundle);
                }
                startActivity(intent1);
            }
        });


        // You can also just use setListAdapter(mAdapter) or
//        mPullRefreshListView.setAdapter(mAdapter);
        actualListView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("搜索商品...");
        searchView.setSubmitButtonEnabled(true);
//

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

        // Update the LastUpdatedLabel
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

        // Do work to refresh the list here.

        //new GetData().execute();
//        mAdapter.notifyDataSetChanged();
//        mPullRefreshListView.onRefreshComplete();

    }


    private class GetData extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);

            } catch (InterruptedException e) {
            }


            try {

                BookOperarion book = new BookOperarion();
                JSONObject json = book.getAllSell();
                mStrings.removeAll(mStrings);
                mData.clear();

                if (json.getString(KEY_SUCCESS) != null) {
                    // Store user details in SQLite Database
                    db.deleteShopData();
                    JSONArray json_books = json.getJSONArray("goods");
                    int len = json_books.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject oj = json_books.getJSONObject(i);
                        SellEntity.SellBook data = new SellEntity.SellBook();
                        data.setData(i + 1, oj.getString(KEY_USERNAME), oj.getString(KEY_BOOKNAME),
                                oj.getInt(KEY_COURSEID), oj.getString(KEY_COURSENAME),
                                oj.getInt(KEY_PRICE), oj.getString(KEY_PRESSS), oj.getInt(KEY_IS_SELLING),
                                oj.getInt(KEY_IS_SOLD), oj.getString(KEY_ADD_TIME),
                                oj.getString(KEY_UPDATE_TIME), oj.getInt(KEY_IS_DEL), oj.getInt(KEY_BID),oj.getString(KEY_BUYER));
                        db.addSell(data.username, data.bookname, data.courseid, data.coursename,
                                data.price, data.press, data.is_selling, data.is_sold, data.add_time,
                                data.update_time, data.is_del, data.bid, data.buyer);
                        if(data.is_selling == 1 && data.is_sold == 0) {
                            mData.add(data);
                            mStrings.add(data.bookname);
                        }
                    }
                    db.close();
                } else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final int len = mStrings.size();
            return (String[])mStrings.toArray(new String[len]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            mListItems.clear();
            mListItems.addAll(mStrings);

            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class ShopAdapter extends ArrayAdapter<SellEntity.SellBook>{
        private LayoutInflater inflater;
        private Context context;


        public ShopAdapter(Context context) {
            super(context, R.layout.item_book);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
            this.context = context;
        }


        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public SellEntity.SellBook getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            SellEntity.SellBook item = mData.get(position);


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
            viewHolder.s_owner.setText("卖家："+item.username);
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
