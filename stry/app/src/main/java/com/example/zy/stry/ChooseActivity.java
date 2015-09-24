package com.example.zy.stry;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.zy.stry.BooksFragment;
import com.example.zy.stry.entity.Book;
import com.example.zy.stry.entity.BookEntity;
import com.example.zy.stry.lib.DatabaseHandler;
import com.example.zy.stry.util.UserbookGlobla;
import com.example.zy.stry.widget.BackHandledFragment;
import com.example.zy.stry.widget.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity  {

    private Toolbar mToolbar;

    private static final int ANIM_DURATION_TOOLBAR = 300;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFabButton;

    String bookname;
    int UBposition;
    //private static final int ANIM_DURATION_FAB = 400;



    private static final int ANIM_DURATION_FAB = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Intent intent = getIntent();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("点击选择匹配的书");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        　∧__∧
//        　( ●ω●)
//        　｜つ／(＿＿＿
//        ／└-(＿＿＿_／

        bookname =  getIntent().getStringExtra("book");
        UBposition = getIntent().getIntExtra("position",-1);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        setUpFAB();














        mProgressBar.setVisibility(View.GONE);

        mFabButton.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        new MaterialDialog.Builder(this)
                .title(R.string.search)
                        //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        if (!TextUtils.isEmpty(input)) {
                            doSearch(input.toString());
                        }
                    }
                }).show();
        startFABAnimation();
        doSearch(bookname);

    }


    private void switchToBook() {
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new BooksFragment()).commit();
        //mToolbar.setTitle(R.string.navigation_book);
    }


    private void doSearch(String keyword) {
        mProgressBar.setVisibility(View.VISIBLE);
        mAdapter.clearItems();
        Book.searchBooks(keyword, new Book.IBookResponse<List<Book>>() {
            @Override
            public void onData(List<Book> books) {
                mProgressBar.setVisibility(View.GONE);
                startFABAnimation();
                mAdapter.updateItems(books, true);
            }
        });
    }


    private void setUpFAB( ) {
        mFabButton = (FloatingActionButton) findViewById(R.id.fab_normal);

        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(ChooseActivity.this)
                        .title(R.string.search)
                                //.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    doSearch(input.toString());
                                }
                            }
                        }).show();
            }
        });
    }


    private void startFABAnimation() {
        mFabButton.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(500)
                .setDuration(ANIM_DURATION_FAB)
                .start();
    }


    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view,final int position) {
            new AlertDialogWrapper.Builder(ChooseActivity.this)
                    .setTitle("选择这本书")
                    .setMessage("存入")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "已存", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Book book = mAdapter.getBook(position);
            /*
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            intent.putExtra("book", book);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            view.findViewById(R.id.ivBook), getString(R.string.transition_book_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            */



//            　∧__∧
//            　( ●ω●)
//            　｜つ／(＿＿＿
//            ／└-(＿＿＿_／

                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            SQLiteDatabase job=db.getReadableDatabase();
                            //这里应该有个确定对话框

                            //应该是下面一条但是存image 但好像不能存网址，因为有 冒号 ：，待解决
                            //job.execSQL("update courses set  image="+book.getImages().getLarge()+" where book=?", new String[]{bookname});
                            job.execSQL("update courses set  image=? where book=?", new String[]{book.getImage(), bookname});
                            db.close();
                            UserbookGlobla.lts.get(UBposition).image=book.getImages().getLarge();
                            UserbookGlobla.lts.get(UBposition).bookname=book.getTitle();
                            if (book.getAuthor().length>0)
                                UserbookGlobla.lts.get(UBposition).author=book.getAuthor()[0];
                            UserbookGlobla.lts.get(UBposition).publisher=book.getPublisher();
                            UserbookGlobla.lts.get(UBposition).origprice=book.getPrice();
                            UserbookGlobla.lts.get(UBposition).pages=book.getPages();

                        }
                    }).show();
        }
    };

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final int mBackground;
        private List<Book> mBooks = new ArrayList<Book>();
        private final TypedValue mTypedValue = new TypedValue();

        private static final int ANIMATED_ITEMS_COUNT = 4;

        private boolean animateItems = false;
        private int lastAnimatedPosition = -1;
        Context context;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context) {
            this.context =context;
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView ivBook;
            public TextView tvTitle;
            public TextView tvDesc;

            public int position;

            public ViewHolder(View v) {
                super(v);
                ivBook = (ImageView) v.findViewById(R.id.ivBook);
                tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                tvDesc = (TextView) v.findViewById(R.id.tvDesc);
            }
        }


        private void runEnterAnimation(View view, int position) {
            if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
                return;
            }

            if (position > lastAnimatedPosition) {
                lastAnimatedPosition = position;
                view.setTranslationY(Utils.getScreenHeight(context));
                view.animate()
                        .translationY(0)
                        .setStartDelay(100 * position)
                        .setInterpolator(new DecelerateInterpolator(3.f))
                        .setDuration(700)
                        .start();
            }
        }


        public void updateItems(List<Book> books, boolean animated) {
            animateItems = animated;
            lastAnimatedPosition = -1;
            mBooks.addAll(books);
            notifyDataSetChanged();
        }

        public void clearItems() {
            mBooks.clear();
            notifyDataSetChanged();
        }


        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_item, parent, false);
            //v.setBackgroundResource(mBackground);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            runEnterAnimation(holder.itemView, position);
            Book book = mBooks.get(position);
            holder.tvTitle.setText(book.getTitle());
            String desc ;
            if (book.getAuthor().length==0) desc =   "\n副标题: " + book.getSubtitle()
                    + "\n出版年: " + book.getPubdate() + "\n页数: " + book.getPages() + "\n定价:" + book.getPrice();
            else desc = "作者: " + book.getAuthor()[0] + "\n副标题: " + book.getSubtitle()
                    + "\n出版年: " + book.getPubdate() + "\n页数: " + book.getPages() + "\n定价:" + book.getPrice();
            holder.tvDesc.setText(desc);
            Glide.with(holder.ivBook.getContext())
                    .load(book.getImage())
                    .fitCenter()
                    .into(holder.ivBook);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mBooks.size();
        }


        public Book getBook(int pos) {
            return mBooks.get(pos);
        }
    }

















}
