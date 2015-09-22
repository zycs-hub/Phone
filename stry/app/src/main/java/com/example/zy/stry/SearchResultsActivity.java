package com.example.zy.stry;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zy.stry.entity.SellEntity;
import com.example.zy.stry.lib.DatabaseHandler;

import java.util.List;

/**
 * Created by wendy on 15-9-17.
 */
public class SearchResultsActivity extends Activity {
    private TextView txtQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        // get the action bar
        ActionBar actionBar = getActionBar();

        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtQuery = (TextView) findViewById(R.id.txtQuery);

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
//            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//            List<SellEntity.SellBook> lt = db.search(query);

        }
    }

}

