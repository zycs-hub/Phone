package com.example.zy.stry;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.zy.stry.entity.Book;
import com.example.zy.stry.widget.RecyclerItemClickListener;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 15/9/9.
 */
public class BooksFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFabButton;

    private static final int ANIM_DURATION_FAB = 400;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_books, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), onItemClickListener));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mAdapter = new MyAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setUpFAB(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mProgressBar.setVisibility(View.GONE);

        mFabButton.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        new MaterialDialog.Builder(getActivity())
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


    private void setUpFAB(View view) {
        mFabButton = (FloatingActionButton) view.findViewById(R.id.fab_normal);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
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
        public void onItemClick(View view, int position) {
            /*
            Book book = mAdapter.getBook(position);
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            intent.putExtra("book", book);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            view.findViewById(R.id.ivBook), getString(R.string.transition_book_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            */
            Toast.makeText(getActivity(), "待完成", Toast.LENGTH_SHORT).show();

        }
    };

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final int mBackground;
        private List<Book> mBooks = new ArrayList<Book>();
        private final TypedValue mTypedValue = new TypedValue();

        private static final int ANIMATED_ITEMS_COUNT = 4;

        private boolean animateItems = false;
        private int lastAnimatedPosition = -1;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context) {
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
                view.setTranslationY(Utils.getScreenHeight(getActivity()));
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
                    .inflate(R.layout.item_book, parent, false);
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
