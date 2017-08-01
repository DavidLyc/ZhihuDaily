package com.david.zhihudaily.zhihu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.david.zhihudaily.R;
import com.david.zhihudaily.util.ActivityUtils;
import com.david.zhihudaily.util.CleanUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    NewsPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        if (newsFragment == null) {
            newsFragment = NewsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    newsFragment, R.id.container);
        }

        mPresenter = new NewsPresenter(newsFragment, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, "开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cleanCache:
                if (CleanUtils.cleanInternalCache(this)) {
                    Toast.makeText(this, "清理成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "清理失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
