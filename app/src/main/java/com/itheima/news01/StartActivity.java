package com.itheima.news01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;

import com.itheima.news01.Util.SharedPrefUtil;

public class StartActivity extends BaseActivity {




    @Override
    protected int getLayoutRes() {
        return R.layout.activity_start;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(1500);
                boolean firstRun= SharedPrefUtil.getBoolean(getApplicationContext(),"firstRun",true);
                if (firstRun){
                    SharedPrefUtil.saveBoolean(StartActivity.this,
                            "firstRun", false);
                    enterGuideActivity();
                }else {
                    enterMainActivity();
                }

            }
        }.start();

    }

    private void enterMainActivity() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void enterGuideActivity() {
        Intent intent=new Intent(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

}
