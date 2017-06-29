package com.itheima.news01.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.itheima.news01.Adapter.VideoAdapter;
import com.itheima.news01.Bean.VideoEntity;
import com.itheima.news01.R;
import com.itheima.news01.Util.URLManager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by yls on 2017/6/27.
 */

public class MainFragment2 extends BaseFragment {

    private RecyclerView mRecyclerView;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_02;
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.recycler_view);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getViewDatas();

    }
//获取服务器视频列表数据
    public void getViewDatas() {
        new HttpUtils().send(HttpRequest.HttpMethod.GET, URLManager.VideoURL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.println("-----视频数据：" + json);
                json =  json.replace("V9LG4B3A0", "result");

                Gson gson = new Gson();
                VideoEntity newsDatas = gson.fromJson(json, VideoEntity.class);
                System.out.println("----解析视频json:"
                        + newsDatas.getResult().size());

                // 显示数据
                showDatas(newsDatas);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });
    }

    private void showDatas(VideoEntity newsDatas) {

        // 设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        // 设置适配器，显示列表
        mRecyclerView.setAdapter(new VideoAdapter(mActivity, newsDatas.getResult()));
    }
}
