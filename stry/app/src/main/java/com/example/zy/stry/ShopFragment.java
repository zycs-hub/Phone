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
import android.widget.ListView;
import android.widget.Toast;

import com.example.zy.stry.adapter.ListAdapter;
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
    private ListAdapter mAdapter;
    private FragmentActivity listener;
    private ArrayList<SellEntity.SellBook> mData = new ArrayList<>();
    private List mStrings = new ArrayList();
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

        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
            }
        });


        // You can also just use mPullRefreshListFragment.getListView()
        ListView actualListView = mPullRefreshListView.getRefreshableView();

        registerForContextMenu(actualListView);

        mListItems = new LinkedList<String>();


        //如果连网就更新否则直接查询本地
        Toast.makeText(getActivity(), "加载中...", Toast.LENGTH_SHORT).show();
        if(MainActivity.hvNetwork) {
            new GetData().execute();
        } else {
            mData =  db.getShopData();
            for(int i = 0; i < mData.size(); i++) {
                mStrings.add(mData.get(i).bookname);
            }
        }
        mListItems.addAll(mStrings);
        mAdapter=new ListAdapter(getActivity(),mData);

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
                intent1.putExtra("book", mStrings.get(position - 1).toString());
                intent1.putExtra("_id", mData.get(position - 1)._id);
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
        searchView.setQueryHint("");
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
        new GetData().execute();
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
                                oj.getInt(KEY_PRICE), oj.getString(KEY_PRESSS), oj.getBoolean(KEY_IS_SELLING),
                                oj.getBoolean(KEY_IS_SOLD), oj.getString(KEY_ADD_TIME),
                                oj.getString(KEY_UPDATE_TIME), oj.getBoolean(KEY_IS_DEL), oj.getInt(KEY_BID));
                        db.addSell(data.username, data.bookname, data.courseid, data.coursename,
                                data.price, data.press, data.is_selling, data.is_sold, data.add_time,
                                data.update_time, data.is_del, data.bid);
                        if(data.is_selling == true && data.is_sold == false) {
                            mData.add(data);
                            mStrings.add(data.bookname);
                        }
                    }
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
//            mListItems.addAll(mStrings);

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


}
