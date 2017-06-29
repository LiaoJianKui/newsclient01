package com.itheima.news01;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends BaseActivity {
    private ImageView iv01;
    private Button btnGo;
    private int count=0;
    private MediaPlayer mMediaPlayer;
    private int[] imagesArray=new int[]{
            R.drawable.ad_new_version1_img1,
            R.drawable.ad_new_version1_img2,
            R.drawable.ad_new_version1_img3,
            R.drawable.ad_new_version1_img4,
            R.drawable.ad_new_version1_img5,
            R.drawable.ad_new_version1_img6,
            R.drawable.ad_new_version1_img7};
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    startAnimation();
                    break;
            }
        }
    };



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_guide;
    }

    @Override
    public void initViews() {
        iv01= (ImageView) findViewById(R.id.iv_01);
        btnGo= (Button) findViewById(R.id.btn_go);
    }

    @Override
    public void initListener() {
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMainActivity();
            }
        });

    }

    private void enterMainActivity() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initData() {
        startAnimation();

    }

    private void startAnimation() {
        count++;
        count=count%imagesArray.length;
        iv01.setBackgroundResource(imagesArray[count]);
        iv01.setScaleX(1.0f);
        iv01.setScaleY(1.0f);

        iv01.animate().scaleX(1.2f).scaleY(1.2f).setDuration(3500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 延迟1秒后发消息，发消息后，会调用mHandler的handleMessage方法， 此处what为0，handleMessage会根据0作判断。
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }
    // 在GuideActivity.java类中添加如下代码：
    private void playBackgroundMusic(){
        mMediaPlayer=MediaPlayer.create(this,R.raw.new_version);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(1.0f,1.0f);
        mMediaPlayer.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        playBackgroundMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }
}
