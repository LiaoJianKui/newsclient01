package com.itheima.news01.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.news01.Bean.NewsEntity;
import com.itheima.news01.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yls on 2017/6/27.
 */

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    /** 列表显示的新闻数据 */
    private List<NewsEntity.ResultBean> listDatas;
    private static final int ITEM_TYPE_WITH_1_IMAGE=0;
    private  static final int ITEM_TYPE_WITH_3_IMAGE=1;

    public NewsAdapter(Context context,List<NewsEntity.ResultBean> listDatas){
        this.mContext = context;
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return (listDatas == null) ? 0 : listDatas.size();
    }

    @Override
    public NewsEntity.ResultBean getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    // 返回列表项视图，只要显示列表项时，就会调用此方法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 3. 获取列表项对应的数据（javabean）
       NewsEntity.ResultBean  info=getItem(position);
        int itemViewType=getItemViewType(position);
        if (itemViewType==ITEM_TYPE_WITH_1_IMAGE) { // 显示1张图片的item
            // 1. 创建列表项item视图
            if (convertView == null) {// 为空时才创建列表项，提高列表效率
                convertView = View.inflate(mContext, R.layout.item_news_1, null);
            }
            // 2. 查找列表项中的子控件
            ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvSource = (TextView) convertView.findViewById(R.id.tv_source);
            TextView tvComment = (TextView) convertView.findViewById(R.id.tv_comment);
            tvTitle.setText(info.getTitle());
            tvSource.setText(info.getSource());
            tvComment.setText(info.getReplyCount() + "跟帖");
            Picasso.with(mContext).load(info.getImgsrc()).into(ivIcon);
        }else if (itemViewType==ITEM_TYPE_WITH_3_IMAGE){
            if (convertView==null){
                convertView= View.inflate(mContext,R.layout.item_news_2,null);
            }
            //查找列表子控件
            TextView tvTitle= (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvComment= (TextView) convertView.findViewById(R.id.tv_comment);
            ImageView iv01= (ImageView) convertView.findViewById(R.id.iv_01);
            ImageView iv02= (ImageView) convertView.findViewById(R.id.iv_02);
            ImageView iv03= (ImageView) convertView.findViewById(R.id.iv_03);

            //显示列表item中的子控件
            tvTitle.setText(info.getTitle());
            tvComment.setText(info.getReplyCount()+"跟帖");
            try {
            Picasso.with(mContext).load(info.getImgsrc()).into(iv01);
            Picasso.with(mContext).load(info.getImgextra().get(0).getImgsrc()).into(iv02);
            Picasso.with(mContext).load(info.getImgextra().get(1).getImgsrc()).into(iv03);
            } catch(Exception e) {
                e.printStackTrace();
            }
            }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        NewsEntity.ResultBean item=getItem(position);
        if (item.getImgextra()==null||item.getImgextra().size()==0){
            return ITEM_TYPE_WITH_1_IMAGE;
        }else{
            return ITEM_TYPE_WITH_3_IMAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
