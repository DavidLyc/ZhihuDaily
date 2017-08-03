package com.david.zhihudaily.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.david.zhihudaily.R;
import com.david.zhihudaily.zhihu.NewsModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zackratos.ultimatebar.UltimateBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;

public class DetailFragment extends Fragment implements DetailContract.View {

    @BindView(R.id.detail_coverImage)
    ImageView mCoverImage;
    Unbinder unbinder;
    @BindView(R.id.detail_collapsing_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.share_fab)
    FloatingActionButton mShareFab;
    @BindView(R.id.webview)
    WebView mWebView;
    private DetailContract.Presenter mPresenter;
    private ZhihuContent mZhihuContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(getActivity());
        ultimateBar.setImmersionBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.web_content, container, false);
        unbinder = ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                builder.build().launchUrl(getContext(), Uri.parse(url));
                return true;
            }
        });
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void showZhihuWebContent(@NonNull ZhihuContent content) {
        mZhihuContent = content;
        loadCoverImage(content.getImage());
        mToolbar.setTitle(content.getTitle());
        String result = content.getBody();
        result = result.replace("<div class=\"img-place-holder\">", "");
        result = result.replace("<div class=\"headline\">", "");
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        result = "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />"
                + css
                + "\n</head>\n"
                + theme
                + result
                + "</body></html>";
        mWebView.loadDataWithBaseURL("x-data://base", result, "text/html", "utf-8", null);
    }

    @Override
    public void loadCoverImage(String imageUrl) {
        Glide.with(getContext())
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .into(mCoverImage);
    }

    @Override
    public void share() {
        try {
            Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
            String shareText = mZhihuContent.getTitle() + mZhihuContent.getShareUrl()
                    + getString(R.string.share_tail);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NewsModel news) {
        mPresenter.loadZhihuContent(news.getId());
        EventBus.getDefault().removeStickyEvent(news);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

    @OnClick(R.id.share_fab)
    public void onViewClicked() {
        share();
    }
}