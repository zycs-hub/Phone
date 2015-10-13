package com.example.zy.stry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.entity.CartEntity;
import com.example.zy.stry.entity.SellEntity;
import com.example.zy.stry.lib.BookOperarion;
import com.example.zy.stry.lib.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 15-9-25.
 */
public class CartActivity extends Activity {
    ListView mlistView;
    Button bntConfirm;


    SharedPreferences shared_preferences;

    public static ArrayList<BookEntity> mListItems;
    private ArrayAdapter<BookEntity> mAdapter;
    private String lst;
    private List mStrings;

    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mStrings = new ArrayList<>();
        mListItems = new ArrayList();
        setContentView(R.layout.fragment_cart);
        lst = "";
        //FragmentManager fm = getFragmentManager();
        bntConfirm = (Button) findViewById(R.id.bnt_confirm);


//        FragmentTransaction ft = MainActivity.fmg.beginTransaction();

        shared_preferences = this.getSharedPreferences(PREFS_NAME, this.MODE_PRIVATE);
        username = shared_preferences.getString("username", null);

        // Set a listener to be invoked when the list should be refreshed.

        mlistView = (ListView) findViewById(R.id.listView);
//        actualListView.setTextFilterEnabled(true);

        this.GetData();

//        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_cart, mListItems);

        mAdapter = new CartAdapter(this, mListItems);



        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item,
                                    int position, long id) {
                BookEntity cart_item = mAdapter.getItem(position);
                cart_item.toggleChecked();
                ViewHolder viewHolder = (ViewHolder) item.getTag();
                boolean check = (cart_item.isSelected() == 0 ? false : true);
                viewHolder.getCheckBox().setChecked(check);
            }
        });



        ArrayList<BookEntity> planetList = new ArrayList<BookEntity>();
        planetList.addAll(mListItems);

        // Set our custom array adapter as the ListView's adapter.
        mlistView.setAdapter(mAdapter);
//        mlistView.setChoiceMode(mlistView.CHOICE_MODE_MULTIPLE);
        bntConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                lst = "";
                for (BookEntity item : mListItems) {
                    if (item.isSelected() != 0) {
                        lst += item.getSellid() + '\n';
                    }

                }

                if (lst != "") {

                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("books", lst.substring(0, lst.length() - 1));
                    startActivity(intent);

                }
            }
        });
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public void onItemClick(AdapterView arg0, View v, int position, long arg3) {
//        // TODO Auto-generated method stub
//        CheckBox cb = (CheckBox) v.findViewById(R.id.item_cart);
////        TextView tv = (TextView) v.findViewById(R.id.textView1);
//        cb.performClick();
////        if (cb.isChecked()) {
////            Toast.makeText(getActivity(),mListItems.get(position) ,Toast.LENGTH_LONG).show();
////
////        } else if (!cb.isChecked()) {
////        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void GetData() {
        mStrings.clear();
        mListItems.clear();

        SQLiteDatabase db = MainActivity.db.getReadableDatabase();
        BookEntity tmp = null;
//        String formattedDate = MainActivity.df.format(MainActivity.c.getTime());

        String selectQuery = "SELECT  * FROM " + CartEntity.Cart.TABLE_NAME;

//        String selectQuery = "SELECT  * FROM " + CartEntity.Cart.TABLE_NAME + " WHERE " + CartEntity.Cart.KEY_ADD_TIME + " LIKE '" + formattedDate + "%'";
        Cursor cr=db.rawQuery(selectQuery, null);
//                String KEY_ADD_TIME = CartEntity.Cart.KEY_ADD_TIME;
        String KEY_SELLID = CartEntity.Cart.KEY_SELLID;
//                Cursor cr= db.query(CartEntity.Cart.TABLE_NAME, new String[]{KEY_SELLID}, "add_time LIKE '?'", new String[]{formattedDate + "%"}, null, null, null);
//                Cursor cr= db.query(CartEntity.Cart.TABLE_NAME, null, KEY_ADD_TIME + " LIKE '?'", new String[]{formattedDate + "%"}, null, null, null);

        if (cr.moveToFirst()) {
            do {
                tmp = new BookEntity();
                int a = cr.getColumnIndex(KEY_SELLID);
                int sid = cr.getInt(a);
                String tmpQuery = "SELECT  * FROM " + SellEntity.Sell.TABLE_NAME + " Where " + SellEntity.Sell.KEY_ID + " = ?";
                Cursor cr2 = db.rawQuery(tmpQuery,new String[]{Integer.toString(sid)});
                if(cr2.moveToFirst()) {
                    tmp.setData(cr2.getInt(0), cr2.getString(1), cr2.getString(2), cr2.getInt(3), cr2.getString(4),
                            cr2.getInt(5), cr2.getString(6), (cr2.getString(7) == "true") ? true : false,
                            (cr2.getString(8) == "true") ? true : false, cr2.getString(9), cr2.getString(10),
                            (cr2.getString(11) == "true") ? true : false, cr2.getInt(12));
                    mStrings.add(tmp.bookname);
                    mListItems.add(tmp);
                }
            } while (cr.moveToNext());
        }
    }

    private static class ViewHolder {
        private CheckBox checkBox ;
        ImageView s_image;
        TextView s_title;
        TextView book;
        //            TextView author;
        TextView s_price;
        //            TextView message;
        TextView s_owner;
        TextView s_major;
        TextView s_course;
        public ViewHolder() {}

        public CheckBox getCheckBox() {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }
    }

    public class CartAdapter extends ArrayAdapter<BookEntity> {

        private LayoutInflater inflater;
        private List<BookEntity> mList;
        private Context context;


        public CartAdapter(Context context,List<BookEntity> mList) {
            super(context, R.layout.item_cart, mList);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
            this.mList = mList;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CheckBox checkBox ;
            ViewHolder viewHolder = null;


            BookEntity item =  mList.get(position);
//            BookEntity item;
//            item =  new BookEntity();
//
            if(convertView == null) {
                viewHolder = new ViewHolder();

                convertView = inflater.inflate(R.layout.item_cart, null);

                checkBox = (CheckBox) convertView.findViewById( R.id.check_box );
                viewHolder.checkBox = checkBox;
                viewHolder.s_image = (ImageView) convertView.findViewById(R.id.s_image);
                viewHolder.s_title = (TextView) convertView.findViewById(R.id.s_title);
//                viewHolder.book = (TextView) convertView.findViewById(R.id.book);
//                viewHolder.author = (TextView) convertView.findViewById(R.id.author);
                viewHolder.s_price = (TextView) convertView.findViewById(R.id.s_price);
//                viewHolder.message = (TextView) convertView.findViewById(R.id.message);
                viewHolder.s_owner = (TextView) convertView.findViewById(R.id.s_owner);
                viewHolder.s_major = (TextView) convertView.findViewById(R.id.s_major);
                viewHolder.s_course = (TextView) convertView.findViewById(R.id.s_course);

                convertView.setTag( viewHolder );

                // If CheckBox is toggled, update the planet it is tagged with.
                viewHolder.checkBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        BookEntity item = (BookEntity) cb.getTag();
                        int tmp = (cb.isChecked() == false ? 0 : 1);
                        item.setSelected(tmp);
                    }
                });

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.checkBox = viewHolder.getCheckBox() ;
            }

            // Tag the CheckBox with the Planet it is displaying, so that we can
            // access the planet in onClick() when the CheckBox is toggled.
            viewHolder.checkBox.setTag(item);

            // Display planet data
            boolean tmp = (item.isSelected() == 0 ? false : true);
            viewHolder.checkBox.setChecked(tmp);
            viewHolder.s_title.setText("课程名：" + mStrings.get(position));

            return convertView;
        }
    }




}

