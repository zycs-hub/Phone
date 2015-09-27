package com.example.zy.stry;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zy.stry.entity.CartEntity;
import com.example.zy.stry.entity.SellEntity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wendy on 15-9-25.
 */
public class CartFragment extends Fragment  {
    private View rootView;
    TextView textName;

    SharedPreferences shared_preferences;

    private LinkedList<String> mListItems;
    private PullToRefreshListView mPullRefreshListView;
    private ArrayAdapter<String> mAdapter;
    private List<SellEntity.SellBook> lst;
    private List mStrings;

    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mStrings = new ArrayList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        rootView = new PullToRefreshExpandableListView(getActivity());
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);

//        FragmentTransaction ft = MainActivity.fmg.beginTransaction();

        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);
        mPullRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.pull_refresh_list);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });

        // Add an end-of-list listener
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
            }
        });

        ListView actualListView = mPullRefreshListView.getRefreshableView();

        textName = (TextView) rootView.findViewById(R.id.bookname);


        new GetDataTask().execute();

        mListItems = new LinkedList<String>();

        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mListItems);

        actualListView.setAdapter(mAdapter);

        return rootView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
                mStrings.clear();

                List<SellEntity.SellBook> It = new ArrayList<>();
                SQLiteDatabase db = MainActivity.db.getReadableDatabase();
                SellEntity.SellBook tmp = null;
                String formattedDate = MainActivity.df.format(MainActivity.c.getTime());

                String selectQuery = "SELECT  * FROM " + CartEntity.Cart.TABLE_NAME + " WHERE " + CartEntity.Cart.KEY_ADD_TIME + " LIKE '" + formattedDate + "%'";
                Cursor cr=db.rawQuery(selectQuery, null);
//                String KEY_ADD_TIME = CartEntity.Cart.KEY_ADD_TIME;
                String KEY_SELLID = CartEntity.Cart.KEY_SELLID;
//                Cursor cr= db.query(CartEntity.Cart.TABLE_NAME, new String[]{KEY_SELLID}, "add_time LIKE '?'", new String[]{formattedDate + "%"}, null, null, null);
//                Cursor cr= db.query(CartEntity.Cart.TABLE_NAME, null, KEY_ADD_TIME + " LIKE '?'", new String[]{formattedDate + "%"}, null, null, null);

                if (cr.moveToFirst()) {
                    do {
                        tmp = new SellEntity.SellBook();
                        int a = cr.getColumnIndex(KEY_SELLID);
                        int sid = cr.getInt(a);
                        String tmpQuery = "SELECT  * FROM " + SellEntity.Sell.TABLE_NAME + " Where " + SellEntity.Sell.KEY_ID + " = ?";
                        Cursor cr2 = db.rawQuery(tmpQuery,new String[]{Integer.toString(sid)});
                        if(cr2.moveToFirst()) {
                            tmp.setData(cr2.getInt(0), cr2.getString(1), cr2.getString(2), cr2.getInt(3), cr2.getString(4),
                                    cr2.getInt(5), cr2.getString(6), (cr2.getString(7) == "true" )? true : false,
                                    (cr2.getString(8) == "true" )? true : false, cr2.getString(9),cr2.getString(10),
                                    (cr2.getString(11) == "true" )? true : false, cr2.getInt(12));
                            mStrings.add(tmp.bookname);
                            It.add(tmp);
                        }
                    } while (cr.moveToNext());
                }
            } catch (InterruptedException e) {
            }
            return (String[])mStrings.toArray(new String[mStrings.size()]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            mListItems.addAll(mStrings);
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }





}

