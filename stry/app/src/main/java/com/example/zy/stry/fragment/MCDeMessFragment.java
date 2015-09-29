package com.example.zy.stry.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zy.stry.R;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.UserbookGlobla;
import com.example.zy.stry.widget.DividerItemDecoration;
import com.example.zy.stry.widget.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by zy on 15/9/28.
 */
public class MCDeMessFragment extends Fragment {
    private int mposition;
    private MyAdapter mAdapter;
    RecyclerView list;
    private LinearLayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        final String book = bundle.getString("book");
        mposition = bundle.getInt("position");
        View view = inflater.inflate(R.layout.fragment_mcd_message, container, false);
        list = (RecyclerView) view.findViewById(R.id.mcd_mess_list);
        list.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(mLayoutManager);
        list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        list.setItemAnimator(new DefaultItemAnimator());
        upd();
        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final int mBackground;
        private List<String > mess;
        private final TypedValue mTypedValue = new TypedValue();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            public TextView value;



            public ViewHolder(View v) {
                super(v);

                value = (TextView) v.findViewById(R.id.mcd_m_value);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context,List<String> a) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mess=a;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mcd_item, parent, false);
            //v.setBackgroundResource(mBackground);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            if (mess.get(position).substring(0,1).equals("t"))
                holder.value.setBackgroundColor(getResources().getColor(R.color.white));
            else
                holder.value.setBackgroundColor(getResources().getColor(R.color.mess));
            holder.value.setText(mess.get(position).substring(1));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mess.size();
        }
        public String getBook(int pos) {
            return mess.get(pos);
        }
        public void update() {
            notifyDataSetChanged();
        }
    }
    public void upd(){
        List<String> mess = new ArrayList<>();
        if (UserbookGlobla.lts.get(mposition).message.size() == 0)
            mess.add("m无消息");
        else
            mess = UserbookGlobla.lts.get(mposition).message;
        mAdapter = new MyAdapter(getActivity(),mess);
        list.setAdapter(mAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        upd();

    }

}
