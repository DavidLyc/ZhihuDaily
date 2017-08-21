package com.david.zhihudaily.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.david.zhihudaily.R;
import com.david.zhihudaily.util.ActivityUtils;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    DetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_frame);
        ButterKnife.bind(this);

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.web_container);
        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    detailFragment, R.id.web_container);
        }

        mPresenter = new DetailPresenter(detailFragment);

    }

}
