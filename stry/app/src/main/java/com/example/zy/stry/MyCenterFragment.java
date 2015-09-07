package com.example.zy.stry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.util.MySellAdapter;

import java.util.List;

/**
 * Created by wendy on 15-8-26.
 */
public class MyCenterFragment extends Fragment {
    ListView my_center_list =null;
    TextView my_center_list_value = null;
    MySellAdapter my_center_adapt = null;
    List<BookEntity> book_for_sell =null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_center, container, false);
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
        return rootView;
    }
}
