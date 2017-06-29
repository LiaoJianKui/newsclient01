package com.itheima.news01.Fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.itheima.news01.Adapter.NewsAdapter;
import com.itheima.news01.Bean.NewsEntity;
import com.itheima.news01.NewsDetailActivity;
import com.itheima.news01.R;
import com.itheima.news01.Util.URLManager;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by yls on 2017/6/27.
 */

public class NewsFragment extends BaseFragment {
    private ListView mListView;
    private String channelId;
    private SpringView springView;
    private int[] pullAnimSrcs = new int[]{R.drawable.mt_pull,R.drawable.mt_pull01,R.drawable.mt_pull02,R.drawable.mt_pull03,R.drawable.mt_pull04,R.drawable.mt_pull05};
    private int[] refreshAnimSrcs = new int[]{R.drawable.mt_refreshing01,R.drawable.mt_refreshing02,R.drawable.mt_refreshing03,R.drawable.mt_refreshing04,R.drawable.mt_refreshing05,R.drawable.mt_refreshing06};
    private int[] loadingAnimSrcs = new int[]{R.drawable.mt_loading01,R.drawable.mt_loading02};
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
//        TextView textView = (TextView) mRoot.findViewById(R.id.tv_01);
//        textView.setText(channelId);    // 显示新闻类别id，以作区分
        mListView = (ListView) mRoot.findViewById(R.id.list_view);
        springView= (SpringView) mRoot.findViewById(R.id.springView);
        springView.setType(SpringView.Type.FOLLOW);
    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 用户点击的新闻
                NewsEntity.ResultBean newsBean = (NewsEntity.ResultBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("news", newsBean);
                startActivity(intent);
            }
        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
        springView.setHeader(new MeituanHeader(mActivity,pullAnimSrcs,refreshAnimSrcs));
        springView.setFooter(new MeituanFooter(mActivity,loadingAnimSrcs));
    }

    @Override
    public void initData() {
        getDataFromServer();

    }

    // 请求服务器获取页签详细数据
    private void getDataFromServer() {
        String url = URLManager.getUrl(channelId);

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                System.out.println("----服务器返回的json数据:" + json);
                json = json.replace(channelId, "result");
                Gson gson = new Gson();
                NewsEntity newsDatas = gson.fromJson(json, NewsEntity.class);
                System.out.println("----解析json:" + newsDatas.getResult().size());

                // 显示数据到列表中
                showDatas(newsDatas);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });
    }

    // 显示服务器数据
    private void showDatas(NewsEntity newsDatas) {
        //显示服务器数据
        if (newsDatas == null
                || newsDatas.getResult() == null
                || newsDatas.getResult().size() == 0) {
            System.out.println("----没有获取到服务器的新闻数据");
            return;
        }
//显示轮播图广告
        List<NewsEntity.ResultBean.AdsBean> ads = newsDatas.getResult().get(0).getAds();
//有轮播图广告
        if (ads != null && ads.size() > 0) {
            View headerView = View.inflate(mActivity, R.layout.list_header, null);
            SliderLayout sliderLayout = (SliderLayout)
                    headerView.findViewById(R.id.slider_layout);

            for (int i = 0; i < ads.size(); i++) {
                // 一则广告数据
                NewsEntity.ResultBean.AdsBean adBean = ads.get(i);

                TextSliderView sliderView = new TextSliderView(mActivity);
                sliderView.description(adBean.getTitle()).image(adBean.getImgsrc());
                // 添加子界面
                sliderLayout.addSlider(sliderView);
                // 设置点击事件
                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        showToast(slider.getDescription());
                    }
                });
            }
            // 添加列表头部布局
            mListView.addHeaderView(headerView);


        }
        //显示新闻列表
        NewsAdapter newsAdapter = new NewsAdapter(mActivity, newsDatas.getResult());
        mListView.setAdapter(newsAdapter);

    }
}
