/*
 * Copyright (C) 2016 hejunlin <hejunlin2013@gmail.com>
 * github:https://github.com/hejunlin2013/TVSample
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.su.gametribe;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by hejunlin on 2015/10/16.
 * blog: http://blog.csdn.net/hejjunlin
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    // 数据集
    private Context mContext;
    private int id;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnBindListener onBindListener;
    private static final String TAG = MyAdapter.class.getSimpleName();

    private Map<String,Integer> iconIds;

    private Map<String,String> maps;

    private List<String> keys;

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public void setIconIds(Map<String, Integer> iconIds) {
        this.iconIds = iconIds;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public interface OnBindListener {
        void onBind(View view, int i);
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    public MyAdapter(Context context) {
        super();
        mContext = context;
    }

    public MyAdapter(Context context, int id) {
        super();
        mContext = context;
        this.id = id;
    }


    public void setOnBindListener(OnBindListener onBindListener) {
        this.onBindListener = onBindListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int resId = R.layout.detail_list_item;
        if (this.id > 0) {
            resId = this.id;
        }
        View view = LayoutInflater.from(mContext).inflate(resId, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.mPostImg.setImageResource(iconIds.get(keys.get(i)));
        if (onBindListener != null) {
            onBindListener.onBind(viewHolder.itemView, i);
        }
        viewHolder.itemView.setOnFocusChangeListener(mOnFocusChangeListener);
        viewHolder.mPostImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startApp(view.getContext(),keys.get(i),maps.get(keys.get(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return maps==null?0:maps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPostImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mPostImg = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }


}
