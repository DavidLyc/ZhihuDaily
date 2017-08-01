package com.david.zhihudaily.module;

import android.app.Application;

import com.letv.sarrsdesktop.blockcanaryex.jrt.BlockCanaryEx;
import com.letv.sarrsdesktop.blockcanaryex.jrt.Config;
import com.squareup.leakcanary.LeakCanary;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        boolean isInSamplerProcess = BlockCanaryEx.isInSamplerProcess(this);
        if(!isInSamplerProcess) {
            BlockCanaryEx.install(new Config(this));
        }

        super.onCreate();

        LeakCanary.install(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

    }

}

