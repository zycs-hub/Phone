package com.example.zy.stry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.Book;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.entity.UserEntity;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.MySellAdapter;

import java.util.ArrayList;
import java.util.List;


import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.UserbookGlobla;
import com.example.zy.stry.widget.DividerItemDecoration;
import com.example.zy.stry.widget.RecyclerItemClickListener;

/**
 * Created by wendy on 15-8-26.
 */
public class MyCenterFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String[] myDataset;
    private MyAdapter mAdapter;
    DatabaseHandler db = null;
    SharedPreferences shared_preferences;


    public static final String PREFS_NAME = "MyPrefs";
    private String username;


    ListView my_center_list =null;
    TextView my_center_list_value = null;
    MySellAdapter my_center_adapt = null;
    List<BookEntity> book_for_sell =new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_center, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);

        // specify an adapter (see also next example)
        if(username != null) {
            db = new DatabaseHandler(getActivity());
            List<BookEntity> lt = db.getUserBooks(username);
            if (lt==null){
                Intent intent = new Intent(getActivity(), LogForT.class);
                startActivity(intent);
            }
            if (lt!=null)
                for (BookEntity book : lt){
                    UserbookGlobla.lts=lt;
                    //book_for_sell.add(book);
                }
            lt=null;
        }


        mAdapter = new MyAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        /*
        my_center_list =(ListView) rootView.findViewById(R.id.my_center_list);
        my_center_list_value =(TextView) rootView.findViewById(R.id.my_center_list_value);
        for (BookEntity book : BookGlobla.lts)
            book_for_sell.add(book);
        my_center_adapt=new MySellAdapter(book_for_sell,getActivity());
        my_center_list.setAdapter(my_center_adapt);
        my_center_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getActivity(),ShowMessageActivity.class);
                intent.putExtra("bookName", book_for_sell.get(i).toString());
                intent.putExtra("position", i);
                startActivity(intent);

            }
        });
        */
        return view;
    }
    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

//            　 ∧__∧
//            　( ●ω●)
//            　｜つ／(＿＿＿
//            ／└-(＿＿＿_／

            String book ;//mAdapter.getBook(position);
            book = UserbookGlobla.lts.get(position).getBook();
            //book = book_for_sell.get(position).getBook();
            Intent intent1 = new Intent(getActivity(), MyCenterBookDetailActivity.class);
            intent1.putExtra("book", book);
            intent1.putExtra("position", position);
            startActivity(intent1);

            //ActivityOptionsCompat options =
            //        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
            //                view.findViewById(R.id.ivBook), getString(R.string.transition_book_img));

            //ActivityCompat.startActivity(getActivity(), intent1, null/*options.toBundle()*/);


            /*
            switch (position) {
                case 0:
                    intent = new Intent(getActivity(), RecycleViewActivity.class);
                    startActivity(intent);
                    break;

                case 1:
                    intent = new Intent(getActivity(), EditTextFLActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getActivity(), CardViewActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(getActivity(), AppBarDetailActivity.class);
                    startActivity(intent);
                    break;

                case 4:
                    intent = new Intent(getActivity(), BottomTabActivity.class);
                    startActivity(intent);
                    break;
            }*/


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
            public TextView tvDesc;


            public int position;

            public ViewHolder(View v) {
                super(v);
                ivBook = (ImageView) v.findViewById(R.id.vBook);
                tvTitle = (TextView) v.findViewById(R.id.vTitle);
                tvDesc = (TextView) v.findViewById(R.id.vDesc);
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
                    .inflate(R.layout.courses_item, parent, false);
            //v.setBackgroundResource(mBackground);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            BookEntity book = UserbookGlobla.lts.get(position);
            //BookEntity book = book_for_sell.get(position);
            holder.tvTitle.setText(book.getBook());
            holder.tvDesc.setText(new Integer(book.courseid).toString());
            if (UserbookGlobla.lts.get(position).image!=null)
            Glide.with(holder.ivBook.getContext())
                    .load(UserbookGlobla.lts.get(position).image)
                    .fitCenter()
                    .into(holder.ivBook);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return UserbookGlobla.lts.size();
        }
        public BookEntity getBook(int pos) {
            return UserbookGlobla.lts.get(pos);
        }
        public void update() {
            notifyDataSetChanged();
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        mAdapter.update();

    }
}
