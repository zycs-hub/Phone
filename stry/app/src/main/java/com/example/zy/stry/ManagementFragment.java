package com.example.zy.stry;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.entity.MessageEntity;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.util.BookGlobla;
import com.example.zy.stry.adapter.MySellAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.example.zy.stry.util.ThreadBooksMessage;
import com.example.zy.stry.util.UserbookGlobla;
import com.example.zy.stry.widget.DividerItemDecoration;
import com.example.zy.stry.widget.RecyclerItemClickListener;

/**
 * Created by wendy on 15-8-26.
 */
public class ManagementFragment extends Fragment {
    static class IncomingHandler extends Handler {
        private final WeakReference<ManagementFragment> mService;
        private int status;


        IncomingHandler(ManagementFragment service,int status) {
            mService = new WeakReference<ManagementFragment>(service);
            this.status=status;
        }

        @Override
        public void handleMessage(Message msg) {
            ManagementFragment service = mService.get();
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
                        if(BookGlobla.lts==null||BookGlobla.lts.size()==0)
                            switch (status){
                                case 1:
                                    Toast.makeText(service.getActivity(), "无新书", Toast.LENGTH_LONG).show();
                                    service.cancelProgress();
                                    return;
                                case 0:
                                    Toast.makeText(service.getActivity(), "无课程", Toast.LENGTH_LONG).show();
                                    service.cancelProgress();
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
    private MyAdapter mAdapter;
    DatabaseHandler db = null;
    SharedPreferences shared_preferences;
    int flag=1;


    public static final String PREFS_NAME = "MyPrefs";
    private String username;
    private String student_ID;
    private String password_T;



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

        View view = inflater.inflate(R.layout.fragment_managment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shared_preferences = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
        username = shared_preferences.getString("username", null);
        student_ID=shared_preferences.getString("student_ID", null);
        password_T=shared_preferences.getString("password_T", null);

        // specify an adapter (see also next example)
        if(username != null) {
            db = new DatabaseHandler(getActivity());
            List<BookEntity> lt = db.getUserBooks(username);
//            if (lt!=null)
//                for (BookEntity book : lt){
                    UserbookGlobla.lts=lt;
                    //book_for_sell.add(book);
                    //}
//            if (flag==1){
//                flag++;
//                DatabaseHandler db;
//                List<BookEntity> lts;
//                db = new DatabaseHandler(getActivity());
//                lts = db.getCoursesAll();
//                for (int i = 0, len = UserbookGlobla.lts.size(); i < len; i++)
//                    for (int j = 0, leng = lts.size(); j < leng; j++)
//                        if (UserbookGlobla.lts.get(i).courseid == lts.get(j).courseid) {
//                            UserbookGlobla.lts.get(i).image=lts.get(j).image;
//                            UserbookGlobla.lts.get(i).author=lts.get(j).author;
//                            UserbookGlobla.lts.get(i).publisher=lts.get(j).publisher;
//                            UserbookGlobla.lts.get(i).pages=lts.get(j).pages;
//                            lts.remove(j);
//                            --leng;
//                            --j;
////            if (lt==null){
////                Intent intent = new Intent(getActivity(), LogForT.class);
////                startActivity(intent);
//            }
//            if (lt!=null)
//                UserbookGlobla.lts=lt;
//
////            for (BookEntity book : lt){
////                    UserbookGlobla.lts=lt;
////                    //book_for_sell.add(book);
////                }
////            if (flag==1){
////                flag++;
////                DatabaseHandler db;
////                List<BookEntity> lts;
////                db = new DatabaseHandler(getActivity());
////                lts = db.getCoursesAll();
////                for (int i = 0, len = UserbookGlobla.lts.size(); i < len; i++)
////                    for (int j = 0, leng = lts.size(); j < leng; j++)
////                        if (UserbookGlobla.lts.get(i).courseid == lts.get(j).courseid) {
////                            UserbookGlobla.lts.get(i).image=lts.get(j).image;
////                            UserbookGlobla.lts.get(i).author=lts.get(j).author;
////                            UserbookGlobla.lts.get(i).publisher=lts.get(j).publisher;
////                            UserbookGlobla.lts.get(i).pages=lts.get(j).pages;
////                            lts.remove(j);
////                            --leng;
////                            --j;
////
////                            break;
////                        }
//            }
            lt=null;
        }

//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(final Message msgs) {
//                //write your code hear which give error
//                switch (msgs.what) {
//                    case 1:
//                        //mAdapter= new MyAdapter(getActivity());
//                        mAdapter.notifyDataSetChanged();
//                        //Toast.makeText(getActivity(), "w已存", Toast.LENGTH_SHORT).show();
//                        break;
////                    case -1:
////                        Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
                    if(UserbookGlobla.lts.size()>0) {
                        for (int i= 0;i<UserbookGlobla.lts.size();i++)
                            com(i);
                        //handler.sendEmptyMessage(1);
                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };

        //thread.start();




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
    private void com(int pos){
        Calendar c = Calendar.getInstance();
        int y=c.get(Calendar.YEAR);
        int m=c.get(Calendar.MONTH);
        int d=c.get(Calendar.DAY_OF_MONTH);
        int h=c.get(Calendar.HOUR);
        int mi=c.get(Calendar.MINUTE);
        String data;
        List<MessageEntity> messageEntities = new ArrayList<>();
        DatabaseHandler db1 = new DatabaseHandler(getActivity());
        //db1.deleteMessages();
        messageEntities =db1.getMessage(UserbookGlobla.lts.get(pos).bid);
        MessageEntity messageEntity =new MessageEntity();
        if (messageEntities.size()==0&&UserbookGlobla.lts.get(pos).messageEntities.size()==0){
            messageEntity.data="";
            messageEntity.message="无消息";
            messageEntity.isRead=1;
            UserbookGlobla.lts.get(pos).messageEntities.add(messageEntity);
            return;
        }
        //List<com.example.zy.stry.entity.Message> messages= UserbookGlobla.lts.get(pos).messages;
        for (int i=0;i< messageEntities.size();i++) {
            if (y == messageEntities.get(i).year){
                if (m == messageEntities.get(i).moon) {
                    if (d == messageEntities.get(i).day)
                        data = messageEntities.get(i).hour + ":" + messageEntities.get(i).min;
                    else
                        switch (d - messageEntities.get(i).day) {
                            case 1:
                                data = "昨天";
                                break;
                            case 2:
                                data = "前天";
                                break;
                            default:
                                data = messageEntities.get(i).day + "日";
                                break;
                        }
                } else {
                    data = messageEntities.get(i) + "月 " + messageEntities.get(i) + "日 ";
                }
            }else{
                data= messageEntities.get(i).year+"年 "
                        + messageEntities.get(i).moon+"月 ";
            }
            messageEntity = messageEntities.get(i);
            messageEntity.data=data;
            UserbookGlobla.lts.get(pos).messageEntities.add(messageEntity);
    }
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
        if (student_ID!=null) {
            inflater.inflate(R.menu.mycenter, menu);
            MenuItem readd = menu.findItem(R.id.readd);
            MenuItem addnew = menu.findItem(R.id.addnew);
            MenuItem rechoice = menu.findItem(R.id.rechoice);
            MenuItem self_defined = menu.findItem(R.id.self_defined);
        }
        else if (username!=null){
            inflater.inflate(R.menu.log_t, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.logt:
                showProgress();
                Intent intent_t = new Intent(getActivity(), LogForT.class);
                startActivity(intent_t);
                cancelProgress();

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
                long param = db.addData(BookGlobla.lts);
                break;
            case 1:
                for (int i = 0, len = BookGlobla.lts.size(); i < len; i++)
                    for (int j = 0, leng = lt.size(); j < leng; j++)
                        if (BookGlobla.lts.get(i).courseid == lt.get(j).courseid) {
                            lt.remove(j);
                            BookGlobla.lts.remove(i);
                            --i;
                            --len;
                            --leng;
                            --j;
                            break;
                        }
                break;
            case 2:
                for (int i = 0, len = lt.size(); i < len; i++) {
                    if (lt.get(i).isSelected() == 1)
                        lt.remove(i);
                    --len;
                    --i;
                }
                if (lt.size()==0) {
                    Toast.makeText(getActivity(), "无未选择书", Toast.LENGTH_LONG).show();
                    cancelProgress();
                    return;
                } else {
                    BookGlobla.lts=lt;
                    Intent intent = new Intent(getActivity(), SelectFromT.class);
                    startActivity(intent);
                    cancelProgress();
                }
        }
        db.close();
    }
    private void login(int status){
        IncomingHandler myHandler = new IncomingHandler(this,status);
        ThreadBooksMessage str = new ThreadBooksMessage(myHandler, student_ID, password_T, 0);
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
                            UserbookGlobla.user.password=input.toString();
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
            book = UserbookGlobla.lts.get(position).bookname;
            //book = book_for_sell.get(position).getBook();
            Intent intent1 = new Intent(getActivity(), MyCenterBookDetailActivity.class);
            intent1.putExtra("book", book);
            intent1.putExtra("bid", UserbookGlobla.lts.get(position).bid);
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
            BookEntity book = UserbookGlobla.lts.get(position);
            //BookEntity book = book_for_sell.get(position);
            holder.tvTitle.setText(book.bookname);
            if (book.bookname!=null)
                holder.tvTitle.setText(book.coursename);
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
            if (book.message.size()!=0&& !book.message.get(0).equals("m无消息")) {
                holder.message.setVisibility(View.VISIBLE);
                holder.message.setText(book.message.get(1).substring(1));
                holder.dot.setVisibility(View.VISIBLE);
                holder.date.setVisibility(View.VISIBLE);
                holder.date.setText(book.message.get(0).substring(1));
            }
            else {
            holder.message.setVisibility(View.GONE);

            holder.dot.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);}

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
