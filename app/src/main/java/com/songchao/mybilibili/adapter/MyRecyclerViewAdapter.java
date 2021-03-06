package com.songchao.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.songchao.mybilibili.R;
import com.songchao.mybilibili.model.DoubleNews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: SongCHao
 * Date: 2017/8/31/17:30
 * Email: 15704762346@163.com
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHodler>{
    private List<DoubleNews> mNewses;
    private Context mContext;
    //接口回调
    public interface onItemOnClickListener{
        void onItemClick(View view,int position);
    }
    private onItemOnClickListener mOnItemOnClickListener;

    public void setOnItemOnClickListener(onItemOnClickListener onItemOnClickListener) {
        mOnItemOnClickListener = onItemOnClickListener;
    }

    public MyRecyclerViewAdapter(List<DoubleNews> newses, Context context) {
        mNewses = newses;
        mContext = context;
    }

    // 这个方法里面只创建viewHolder
    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item,parent,false);
        MyViewHodler hodler = new MyViewHodler(view);
        return hodler;
    }
    // 这个方法里面只bind 绑定数据
    @Override
    public void onBindViewHolder(final MyViewHodler holder, int position) {
        DoubleNews news = mNewses.get(position);
        String id = news.id;
        String title = news.title;
        String digest = news.digest;
        String time = news.time;
        String source = news.source;
        String image = news.image;
        if (!TextUtils.isEmpty(title)){
            holder.titleTextView.setText(title);
        }
        if(!TextUtils.isEmpty(time)){
            //json接口里的是秒，Java里是毫秒，所以要乘以1000
            Date date = new Date(Long.parseLong(time)*1000);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String times = format.format(date);
            holder.timeTextView.setText(times);
        }
        if (!TextUtils.isEmpty(source)){
            holder.sourceTextView.setText(source);
        }
        //这块直接load参数填image就可以，好好理解
            Glide.with(mContext).load(image).into(holder.mImageView);
        if (mOnItemOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    mOnItemOnClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }
    // 获取条目总数
    @Override
    public int getItemCount() {
        return mNewses==null?0:mNewses.size();
    }
    // viewHolder里面只查找控件
    public static class MyViewHodler extends RecyclerView.ViewHolder{
        public TextView titleTextView,timeTextView,sourceTextView;
        public ImageView mImageView;
        public MyViewHodler(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_doublenews);
            timeTextView = (TextView) itemView.findViewById(R.id.time_doublenews);
            sourceTextView = (TextView) itemView.findViewById(R.id.source_doublenews);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_doublenews);
        }
    }
}
