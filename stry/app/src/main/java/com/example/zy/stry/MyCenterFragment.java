package com.example.zy.stry;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.Book;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.entity.UserEntity;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.util.MyAdapter;
import com.example.zy.stry.util.MySellAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


import com.example.zy.stry.util.My_DB;
import com.example.zy.stry.util.ThreadBooksMessage;
import com.example.zy.stry.util.UserbookGlobla;
import com.example.zy.stry.widget.DividerItemDecoration;
import com.example.zy.stry.widget.RecyclerItemClickListener;

/**
 * Created by wendy on 15-8-26.
 */
public class MyCenterFragment extends Fragment {
    static class IncomingHandler extends Handler {
        private final WeakReference<MyCenterFragment> mService;
        private int status;


        IncomingHandler(MyCenterFragment service,int status) {
            mService = new WeakReference<MyCenterFragment>(service);
            this.status=status;
        }

        @Override
        public void handleMessage(Message msg) {
            MyCenterFragment service = mService.get();
            if (service != null) {
                switch (msg.what) {
                    case -1:
                        Toast.makeText(service.getActivity(), "no_web", Toast.LENGTH_LONG).show();
                        service.cancelProgress();
                        break;
                    case -2:
                        service.cancelProgress();
                        service.input();
                        Toast.makeText(service.getActivity(), R.string.TLogErr1, Toast.LENGTH_LONG).show();
                        break;
                    case -3:
                        service.cancelProgress();
                        service.input();
                        Toast.makeText(service.getActivity(), R.string.TLogErr2, Toast.LENGTH_LONG).show();
                        break;
                    case 1: {
                        service.getCourse(status);
                        if(BookGlobla.lts==null)
                            switch (status){
                                case 1:
                                    Toast.makeText(service.getActivity(), "无新书", Toast.LENGTH_LONG).show();
                                    return;
                                case 0:
                                    Toast.makeText(service.getActivity(), "无课程", Toast.LENGTH_LONG).show();
                                    return;
                            }
                        Intent intent = new Intent(service.getActivity(), SelectFromT.class);
                        service.startActivity(intent);
                        service.cancelProgress();
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }
    private  MaterialDialog dialog=null;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


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
    /*
    *
     *
      *
      * menu
    *
    *
    *
    *
    * */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mycenter, menu);
        MenuItem readd = menu.findItem(R.id.readd);
        MenuItem addnew = menu.findItem(R.id.addnew);
        MenuItem rechoice = menu.findItem(R.id.rechoice);
        MenuItem self_defined = menu.findItem(R.id.self_defined);


//

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.readd:
                new AlertDialogWrapper.Builder(getActivity())
                        .setTitle("教务处重选")
                        .setMessage("选择会失去所有已添加图书信息，谨慎选择")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showProgress();
                                login(0);
                            }
                        }).show();
                break;
            case R.id.addnew:
                showProgress();
                login(1);
                break;
            case R.id.rechoice:
                showProgress();
                getCourse(2);
                break;
            case R.id.self_defined:
                showProgress();
                Intent intent = new Intent(getActivity(), SelfDefineActivity.class);
                startActivity(intent);
                cancelProgress();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getCourse(int status) {
        DatabaseHandler db;
        List<BookEntity> lt;
        db = new DatabaseHandler(getActivity());
        lt = db.getCoursesAll();
        switch (status) {
            case 0:
                db.deleteCourses();
                break;
            case 1: {

                for (int i = 0, len = UserbookGlobla.lts.size(); i < len; i++)
                    for (int j = 0, leng = lt.size(); i < leng; i++)
                        if (UserbookGlobla.lts.get(i).coursenum == lt.get(j).coursenum) {
                            lt.remove(j);
                            --leng;
                            --j;
                        }
                BookGlobla.lts=lt;
            }
            break;
            case 2:
                for (int i = 0, len = lt.size(); i < len; i++) {
                    if (lt.get(i).isSelected() == 1)
                        lt.remove(i);
                    --len;
                    --i;
                }
                if (lt == null) {
                    Toast.makeText(getActivity(), "无未选择书", Toast.LENGTH_LONG).show();
                    cancelProgress();
                    return;
                } else {
                    BookGlobla.lts=lt;
                    Intent intent = new Intent(getActivity(), SelectFromT.class);
                    startActivity(intent);
                    cancelProgress();
                }
                db.close();
        }
    }
    private void login(int status){
        IncomingHandler myHandler = new IncomingHandler(this,status);
        ThreadBooksMessage str = new ThreadBooksMessage(myHandler, UserbookGlobla.user.student_ID, UserbookGlobla.user.password, 0);
        new Thread(str).start();
    }
    private void showProgress(){
        dialog=new MaterialDialog.Builder(getActivity())
                .title("网络连接中")
                .content("请稍后")
                .progress(true, 0)
                .show();
    }
    private  void cancelProgress(){
        if (dialog==null) return;
        dialog.cancel();
    }
    private void input(){
        new MaterialDialog.Builder(getActivity())
                .title("密码错误")
                .content("请输入密码")
                .inputType(InputType.TYPE_CLASS_NUMBER )
                .input("教务处账号", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        if (!TextUtils.isEmpty(input)) {
                            if(UserbookGlobla.user.student_ID==null) return;
                            UserbookGlobla.user.student_ID=input.toString();
                            login(0);
                        }
                    }
                }).show();
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
            public TextView book;
            public TextView price;
            public TextView author;
            public TextView message;


            public int position;

            public ViewHolder(View v) {
                super(v);
                ivBook = (ImageView) v.findViewById(R.id.vBook);
                tvTitle = (TextView) v.findViewById(R.id.vTitle);
                book = (TextView) v.findViewById(R.id.book);
                price = (TextView) v.findViewById(R.id.price);
                message = (TextView) v.findViewById(R.id.message);
                author = (TextView) v.findViewById(R.id.author);
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
            if (book.getBook()!=null)
                holder.tvTitle.setText(book.getBook());
            else
                holder.tvTitle.setText(book.bookname);
            if (book.image!=null)
                Glide.with(holder.ivBook.getContext())
                        .load(book.image)
                        .fitCenter()
                        .into(holder.ivBook);
            else holder.ivBook.setImageDrawable(null);
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
    public void onResume() {
        super.onResume();
        mAdapter= new MyAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

    }
}
