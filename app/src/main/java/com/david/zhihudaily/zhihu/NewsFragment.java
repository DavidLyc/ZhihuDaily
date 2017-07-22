package com.david.zhihudaily.zhihu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.david.zhihudaily.R;
import com.david.zhihudaily.adapter.NewsListAdapter;
import com.david.zhihudaily.adapter.OnItemClickListener;
import com.david.zhihudaily.details.DetailActivity;
import com.david.zhihudaily.util.TimeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NewsFragment extends Fragment implements NewsContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.datetime_picker)
    FloatingActionButton mFab;
    private Unbinder unbinder;
    private NewsContract.Presenter mPresenter;
    private NewsListAdapter mAdapter;
    private ArrayList<NewsModel> mNewsItems;
    private DatePickerDialog mDpd;
    private Calendar mNow;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_list, container, false);
        unbinder = ButterKnife.bind(this, root);
        initTimePicker();
        initRefreshLayout();
        initRecyclerView();
        return root;
    }

    private void initTimePicker() {
        mNow = Calendar.getInstance();
        mNow.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        mDpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
                        String date = TimeUtils.TransformDateToTimeString(year, month, day);
                        mPresenter.getBeforeNews(date);
                        mNow = Calendar.getInstance();
                    }
                },
                mNow.get(Calendar.YEAR),
                mNow.get(Calendar.MONTH),
                mNow.get(Calendar.DAY_OF_MONTH)
        );
        Calendar minDate = Calendar.getInstance();
        minDate.set(2013, 4, 20);
        mDpd.setMinDate(minDate);
        mDpd.setVersion(DatePickerDialog.Version.VERSION_1);
        mDpd.vibrate(false);
        mDpd.setAccentColor("#009788");
        mDpd.setMaxDate(mNow);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getNewsList();
                mNow = Calendar.getInstance();
                refreshlayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mNow.set(Calendar.DATE, mNow.get(Calendar.DATE) - 1);
                mPresenter.getBeforeNews(TimeUtils.TransformDateToTimeString(mNow));
                refreshlayout.finishLoadmore();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mNewsItems = new ArrayList<>();
        mAdapter = new NewsListAdapter(mNewsItems, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onClick(View view, NewsModel newsModel) {
                        EventBus.getDefault().postSticky(newsModel.getId());
                        startActivity(new Intent(getContext(), DetailActivity.class));
                    }
                }
        );
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }
        });
    }

    @Override
    public void loadRecyclerViewItems(ArrayList<NewsModel> newslist) {
        mAdapter.resetAllItems(newslist);
    }

    @Override
    public void loadMoreRecyclerViewItems(ArrayList<NewsModel> newslist) {
        mAdapter.appendItems(newslist);
    }

    @Override
    public void setPresenter(@NonNull NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.datetime_picker)
    public void onViewClicked() {
        mDpd.show(getActivity().getFragmentManager(), NewsFragment.class.getSimpleName());
    }

}
