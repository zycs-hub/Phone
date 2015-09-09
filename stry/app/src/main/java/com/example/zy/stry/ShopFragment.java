package com.example.zy.stry;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zy.stry.lib.Book;
import com.example.zy.stry.lib.DatabaseHandler;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wendy on 15-8-30.
 */
public class ShopFragment extends Fragment implements PullToRefreshBase.OnRefreshListener<ListView> {
    private LinkedList<String> mListItems;
    private ArrayAdapter<String> mAdapter;
    FragmentActivity listener;
    List mStrings = new ArrayList();
    private PullToRefreshListFragment mPullRefreshListFragment;
    private PullToRefreshListView mPullRefreshListView;

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
//        Intent shop = new Intent(getActivity(), Shop.class);
//////        getActivity().startActivity(myIntent)
////        startActivity(shop);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

//        mPullRefreshListFragment = (PullToRefreshListFragment) rootView.findFragmentById(
//                R.id.frag_ptr_list);

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

        new GetData().execute();

//        DatabaseHandler db = new DatabaseHandler(getActivity());
//        ArrayList<SellBook> list =  db.getShopData();
//        for(int i = 0; i < list.size(); i++)
//        {
//            mStrings.add(list.get(i).bookname);
//        }

        mListItems.addAll(mStrings);

        mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mListItems);

        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
        soundListener.addSoundEvent(State.PULL_TO_REFRESH,R.raw.pull_event);
        soundListener.addSoundEvent(State.RESET,R.raw.reset_sound);
        soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);

        // You can also just use setListAdapter(mAdapter) or
        mPullRefreshListView.setAdapter(mAdapter);
        actualListView.setAdapter(mAdapter);

            return rootView;
        };


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

                Book book = new Book();
                JSONObject json = book.getAllSell();
                mStrings.removeAll(mStrings);

                if (json.getString(KEY_SUCCESS) != null) {
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    JSONArray json_books = json.getJSONArray("goods");
                    int len = json_books.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject oj = json_books.getJSONObject(i);
                        db.addSell(oj.getString(KEY_USERNAME), oj.getString(KEY_BOOKNAME),
                                oj.getInt(KEY_COURSEID), oj.getString(KEY_COURSENAME),
                                oj.getInt(KEY_PRICE), oj.getString(KEY_PRESSS), oj.getBoolean(KEY_IS_SELLING),
                                oj.getBoolean(KEY_IS_SOLD), oj.getString(KEY_ADD_TIME),
                                oj.getString(KEY_UPDATE_TIME), oj.getBoolean(KEY_IS_DEL), oj.getInt(KEY_BID));
                        mStrings.add(oj.getString(KEY_BOOKNAME));
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

}
