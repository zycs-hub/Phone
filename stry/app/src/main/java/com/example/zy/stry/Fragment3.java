package com.example.zy.stry;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.UserbookGlobla;

/**
 * Created by wendy on 15-8-26.
 */
public class Fragment3 extends Fragment {
    private View rootView;
    SharedPreferences shared_preferences;
    MyAdapter mAdapter;
    public static CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView;
    int mutedColor = R.attr.colorPrimary;
    //SimpleRecyclerAdapter simpleRecyclerAdapter;

    public static final String PREFS_NAME = "MyPrefs";
    private String username = null;
    private String name;
    private ImageButton head;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_3, container, false);
        }

        FragmentTransaction ft = MainActivity.fmg.beginTransaction();

        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);
        name = shared_preferences.getString("name", null);
        head = (ImageButton) rootView.findViewById(R.id.fab_head);

        if(username == null) {
            ft.replace(R.id.fragment_3, new Login());
            collapsingToolbar = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle("登录");
            head.setVisibility(View.GONE);
        } else if (name==null){
            ft.replace(R.id.fragment_3, new AccountFragment());
            head.setVisibility(View.GONE);
        }else {
            head.setVisibility(View.VISIBLE);
            ft.replace(R.id.fragment_3, new AccountFragment());
            //ft.replace(R.id.fragment_3, new UserInfFragment());
            collapsingToolbar = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(username);
        }

        ft.commit();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.scrollableview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        try {
            mAdapter= new MyAdapter(getActivity());

        }catch (Exception e){
            e.printStackTrace();
        }

        recyclerView.setAdapter(mAdapter);
        head.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(),SetUserInfActivity.class);
                startActivity(intent);

            }
        });

        return rootView;
    }
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
            BookEntity book = CartFragment.mListItems.get(position);
            //BookEntity book = book_for_sell.get(position);
            holder.tvTitle.setText(book.getBook());
            if (book.getBook()!=null)
                holder.tvTitle.setText(book.getBook());
            else
                holder.tvTitle.setText(book.bookname);
//            if (book.image!=null)
//                Glide.with(holder.ivBook.getContext())
//                        .load(book.image)
//                        .fitCenter()
//                        .into(holder.ivBook);
//            else
                holder.ivBook.setVisibility(View.GONE);
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
            holder.message.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
            holder.dot.setVisibility(View.GONE);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return CartFragment.mListItems==null ? 0: CartFragment.mListItems.size();
        }
        public BookEntity getBook(int pos) {
            return CartFragment.mListItems.get(pos);
        }
        public void update() {
            notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            mAdapter= new MyAdapter(getActivity());
            recyclerView.setAdapter(mAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
