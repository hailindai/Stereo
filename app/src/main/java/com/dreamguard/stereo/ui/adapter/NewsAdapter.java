package com.dreamguard.stereo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamguard.stereo.R;
import com.dreamguard.stereo.bean.weixin.WeixinNews;
import com.dreamguard.stereo.ui.activity.NewsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by hailin
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 10000;
    private static final int TYPE_ONE_PIC = 10001;
    private static final int TYPE_THREE_PIC = 10002;
    private static final int TYPE_AD = 10003;


    public ArrayList<WeixinNews> weixinNewses;
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<WeixinNews> weixinNewses) {
        this.weixinNewses = weixinNewses;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_HEADER:
                break;
            case TYPE_ONE_PIC:
                viewHolder = new OnePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_one_pic_item, parent, false));
                break;
            case TYPE_THREE_PIC:
                viewHolder = new ThreePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_three_pic_item, parent, false));
                break;
            case TYPE_AD:
                viewHolder = new ADViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_ad_item, parent, false));
                break;
            default:
                viewHolder = new ThreePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_one_pic_item, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    final public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return TYPE_ONE_PIC;
        } else if (position % 3 == 1) {
            return TYPE_THREE_PIC;
        }else if(position % 3 == 2){
            return TYPE_AD;
        }else {
            return super.getItemViewType(position);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final WeixinNews weixinNews = weixinNewses.get(position);

        switch (getItemViewType(position)){
            case TYPE_HEADER:
                break;
            case TYPE_ONE_PIC:
                ((OnePicViewHolder)holder).tvDescription.setText(weixinNews.getDescription());
                ((OnePicViewHolder)holder).tvTitle.setText(weixinNews.getTitle());
                ((OnePicViewHolder)holder).tvTime.setText(weixinNews.getHottime());
                if (!TextUtils.isEmpty(weixinNews.getPicUrl())) {
                    Glide.with(mContext).load(weixinNews.getPicUrl()).into(((OnePicViewHolder)holder).ivWeixin);
                } else {
                    ((OnePicViewHolder)holder).ivWeixin.setImageResource(R.mipmap.ic_launcher);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, NewsActivity.class);
                        intent.putExtra("url", weixinNews.getUrl());
                        intent.putExtra("title", weixinNews.getTitle());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case TYPE_THREE_PIC:
                ((ThreePicViewHolder)holder).tvDescription.setText(weixinNews.getDescription());
                ((ThreePicViewHolder)holder).tvTitle.setText(weixinNews.getTitle());
                ((ThreePicViewHolder)holder).tvTime.setText(weixinNews.getHottime());
                if (!TextUtils.isEmpty(weixinNews.getPicUrl())) {
                    Glide.with(mContext).load(weixinNews.getPicUrl()).into(((ThreePicViewHolder)holder).ivImage1);
                    Glide.with(mContext).load(weixinNews.getPicUrl()).into(((ThreePicViewHolder)holder).ivImage2);
                    Glide.with(mContext).load(weixinNews.getPicUrl()).into(((ThreePicViewHolder)holder).ivImage3);
                } else {
                    ((ThreePicViewHolder)holder).ivImage1.setImageResource(R.mipmap.ic_launcher);
                    ((ThreePicViewHolder)holder).ivImage2.setImageResource(R.mipmap.ic_launcher);
                    ((ThreePicViewHolder)holder).ivImage3.setImageResource(R.mipmap.ic_launcher);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, NewsActivity.class);
                        intent.putExtra("url", weixinNews.getUrl());
                        intent.putExtra("title", weixinNews.getTitle());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case TYPE_AD:
                if (!TextUtils.isEmpty(weixinNews.getPicUrl())) {
                    Glide.with(mContext).load(weixinNews.getPicUrl()).into(((ADViewHolder)holder).ivAD);
                } else {
                    ((ADViewHolder)holder).ivAD.setImageResource(R.mipmap.ic_launcher);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, NewsActivity.class);
                        intent.putExtra("url", weixinNews.getUrl());
                        intent.putExtra("title", weixinNews.getTitle());
                        mContext.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemCount() {
        return weixinNewses.size();
    }

    class OnePicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_weixin)
        ImageView ivWeixin;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        OnePicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ThreePicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image1)
        ImageView ivImage1;
        @BindView(R.id.iv_image2)
        ImageView ivImage2;
        @BindView(R.id.iv_image3)
        ImageView ivImage3;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        ThreePicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ADViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_ad)
        ImageView ivAD;

        ADViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
