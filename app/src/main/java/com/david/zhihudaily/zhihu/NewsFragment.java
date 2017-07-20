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
import android.widget.Toast;

import com.david.zhihudaily.R;
import com.david.zhihudaily.adapter.NewsListAdapter;
import com.david.zhihudaily.adapter.OnItemClickListener;
import com.david.zhihudaily.details.DetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;

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
    FloatingActionButton mDatetimePicker;
    private Unbinder unbinder;
    private NewsContract.Presenter mPresenter;
    private NewsListAdapter mAdapter;
    private ArrayList<NewsModel> mNewsItems;
    private DatePickerDialog dpd;

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
        initRefreshLayout();
        initRecyclerView();
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
                        String time = "You picked the following time: " + year + "年" + (month + 1) + "月"
                                + day + "月";
                        Toast.makeText(getContext(), time, Toast.LENGTH_SHORT).show();
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.vibrate(false);
        dpd.setAccentColor("#009788");
        return root;
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getNewsList();
                refreshlayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000);
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
                        startActivity(new Intent(getContext(), DetailActivity.class));
                        EventBus.getDefault().postSticky(newsModel);
                    }
                }
        );
    }

    @Override
    public void loadRecyclerViewItems(ArrayList<NewsModel> newslist) {
        mAdapter.addAllItems(newslist);
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
        dpd.show(getActivity().getFragmentManager(), NewsFragment.class.getSimpleName());
    }

}
