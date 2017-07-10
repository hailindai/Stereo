package com.dreamguard.stereo.ui.adapter;

import android.content.Context;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by hailin
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.WeixinViewHolder> {

    public ArrayList<WeixinNews> weixinNewses;
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<WeixinNews> weixinNewses) {
        this.weixinNewses = weixinNewses;
        this.mContext = context;
    }

    @Override
    public WeixinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeixinViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final WeixinViewHolder holder, int position) {
        final WeixinNews weixinNews = weixinNewses.get(position);

        holder.tvDescription.setText(weixinNews.getDescription());
        holder.tvTitle.setText(weixinNews.getTitle());
        holder.tvTime.setText(weixinNews.getHottime());
        if (!TextUtils.isEmpty(weixinNews.getPicUrl())) {
            Glide.with(mContext).load(weixinNews.getPicUrl()).into(holder.ivWeixin);
        } else {
            holder.ivWeixin.setImageResource(R.mipmap.ic_launcher);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.tvTitle.setTextColor(Color.GRAY);
//                Intent intent = new Intent(mContext, WeixinNewsActivity.class);
//                intent.putExtra("url", weixinNews.getUrl());
//                intent.putExtra("title", weixinNews.getTitle());
//                mContext.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return weixinNewses.size();
    }

    class WeixinViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_weixin)
        ImageView ivWeixin;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        WeixinViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
