/*
 * Copyright (C) 2016 hejunlin <hejunlin2013@gmail.com>
 * Github:https://github.com/hejunlin2013/TVSample
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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hejunlin on 2015/10/16.
 * blog: http://blog.csdn.net/hejjunlin
 */
public class OptionItemAdapter extends RecyclerView.Adapter<OptionItemAdapter.ViewHolder> {

    private int[] resIds = {R.drawable.second_menu_01_selector,R.drawable.second_menu_02_selector,
        R.drawable.second_menu_03_selector,R.drawable.second_menu_04_selector};
    private Context mContext;
    private int id;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnBindListener onBindListener;
    private static final String TAG = OptionItemAdapter.class.getSimpleName();

    public interface OnBindListener {
        void onBind(View view, int i);
    }

    public OptionItemAdapter(Context context) {
        super();
        mContext = context;
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    public OptionItemAdapter(Context context, int id) {
        super();
        mContext = context;
        this.id = id;
    }

    public void setOnBindListener(OnBindListener onBindListener) {
        this.onBindListener = onBindListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int resId = R.layout.detail_menu_item;
        if (this.id > 0) {
            resId = this.id;
        }
        View view = LayoutInflater.from(mContext).inflate(resId, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if (resIds.length == 0) {
            return;
        }
        viewHolder.mMenuView.setImageResource(resIds[i]);
        viewHolder.itemView.setTag(i);
        viewHolder.itemView.setOnFocusChangeListener(mOnFocusChangeListener);
        viewHolder.mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onBindListener != null) {
                    onBindListener.onBind(viewHolder.itemView, i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return resIds.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMenuView;

        public ViewHolder(View itemView) {
            super(itemView);
            mMenuView = (ImageView) itemView.findViewById(R.id.menu);
        }
    }

}
