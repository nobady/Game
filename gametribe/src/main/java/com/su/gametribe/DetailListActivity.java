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

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.owen.adapter.CommonRecyclerViewAdapter;
import com.owen.adapter.CommonRecyclerViewHolder;
import com.owen.focus.AbsFocusBorder;
import com.owen.focus.FocusBorder;
import com.owen.tvrecyclerview.TwoWayLayoutManager;
import com.owen.tvrecyclerview.widget.GridLayoutManager;
import com.owen.tvrecyclerview.widget.MetroGridLayoutManager;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.su.gametribe.widget.AutoLayoutManager;
import com.su.gametribe.widget.MetroViewBorderImpl;
import com.xuexiang.xhttp2.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hejunlin on 2015/10/16.
 * blog: http://blog.csdn.net/hejjunlin
 */
public class DetailListActivity extends Activity implements OptionItemAdapter.OnBindListener {

    private MetroViewBorderImpl mMetroViewBorderImpl;
    private static final String TAG = DetailListActivity.class.getSimpleName();

    private List<Map<String, String>> mDatas = new ArrayList<>();
    private MyAdapter mAdapter;
    private MenuAdapter mMenuAdapter;
    private List<Map<String, Integer>> list = new ArrayList<>();
    protected FocusBorder mFocusBorder;
    private TvRecyclerView mMenuView;
    private TvRecyclerView mRecyclerView;

    private void initBinfenData() {
        Map<String, String> map = new HashMap<>();
        map.put("com.closelamp", "CloseLamp.apk");
        map.put("com.sf", "Sf.apk");
        map.put("org.cocos2d.PuzzleBobble", "PuzzleBobble-release-signed.apk");
        map.put("org.cocos2d.Dungeon", "Dungeon-release-signed.apk");
        map.put("org.cocos2d.Kitchen", "Kitchen-release-signed.apk");
        map.put("org.cocos2d.Quickspot", "Quickspot-release-signed.apk");
        map.put("org.cocos2d.Eliminate", "Eliminate-release-signed.apk");
        mDatas.add(map);


        Map<String, Integer> iconMap = new HashMap<>();
        iconMap.put("com.closelamp", R.drawable.xxxg);
        iconMap.put("com.sf", R.drawable.gtfh);
        iconMap.put("org.cocos2d.PuzzleBobble", R.drawable.xcpp);
        iconMap.put("org.cocos2d.Dungeon", R.drawable.jsdl);
        iconMap.put("org.cocos2d.Kitchen", R.drawable.jlsyy);
        iconMap.put("org.cocos2d.Quickspot", R.drawable.jljdg);
        iconMap.put("org.cocos2d.Eliminate", R.drawable.ckppp);
        list.add(iconMap);
    }

    private void initDongwuData() {
        Map<String, String> map = new HashMap<>();
        map.put("blackman.com.blackman", "BlkMan.apk");
        map.put("com.base", "TheJeepAndroid.apk");
        map.put("org.cocos2d.JumpingRat", "JumpingRat-release-signed.apk");
        map.put("org.cocos2d.ClimbBuild", "ClimbBuild-release-signed.apk");
        map.put("org.cocos2d.Butterfly", "Butterfly-release-signed.apk");
        map.put("org.cocos2d.RescueBaby", "RescueBaby-release-signed.apk");
        map.put("org.cocos2d.Flower", "Flower-release-signed.apk");
        mDatas.add(map);

        Map<String, Integer> iconMap = new HashMap<>();
        iconMap.put("blackman.com.blackman", R.drawable.bpxd);
        iconMap.put("com.base", R.drawable.fkxd);
        iconMap.put("org.cocos2d.JumpingRat", R.drawable.ttslxj);
        iconMap.put("org.cocos2d.ClimbBuild", R.drawable.bbdt);
        iconMap.put("org.cocos2d.Butterfly", R.drawable.hjcd);
        iconMap.put("org.cocos2d.RescueBaby", R.drawable.yjbb);
        iconMap.put("org.cocos2d.Flower", R.drawable.jsth);
        list.add(iconMap);
    }

    private void initYizhiData() {
        Map<String, String> map = new HashMap<>();
        map.put("com.intelligencepuzzle", "IntelligencePuzzle.apk");
        map.put("com.interlocking", "Interlocking.apk");
        map.put("com.matrixpuzzle", "MatrixPuzzle.apk");
        map.put("com.parking", "Parking.apk");
        map.put("com.puzzlegame", "PuzzleGame.apk");
        map.put("org.cocos2d.PushBox", "PushBox-release-signed.apk");
        map.put("org.cocos2d.IceCube", "IceCube-release-signed.apk");
        mDatas.add(map);

        Map<String, Integer> iconMap = new HashMap<>();
        iconMap.put("com.intelligencepuzzle", R.drawable.zlpt);
        iconMap.put("com.interlocking", R.drawable.hhxk);
        iconMap.put("com.matrixpuzzle", R.drawable.jzpt);
        iconMap.put("com.parking", R.drawable.nltcc);
        iconMap.put("com.puzzlegame", R.drawable.jlpt);
        iconMap.put("org.cocos2d.PushBox", R.drawable.txz);
        iconMap.put("org.cocos2d.IceCube", R.drawable.bfk);
        list.add(iconMap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaillist);

        initBinfenData();
        initDongwuData();
        initYizhiData();
        initBordor();
        loadRecyclerViewMenuItem();
        loadDataForRecyclerViewGridLayout();
        setListener();
    }

    private void initBordor() {
        mFocusBorder = new FocusBorder.Builder().asColor()
            //阴影宽度(方法shadowWidth(18f)也可以设置阴影宽度)
            .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 20f)
            //阴影颜色
            .shadowColor(Color.parseColor("#3FBB66"))
            //边框宽度(方法borderWidth(2f)也可以设置边框宽度)
            .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3.2f)
            //边框颜色
            .borderColor(Color.parseColor("#00FF00"))
            //padding值
            .padding(2f)
            //动画时长
            .animDuration(300)
            //不要闪光动画
            //.noShimmer()
            //闪光颜色
            .shimmerColor(Color.parseColor("#66FFFFFF"))
            //闪光动画时长
            .shimmerDuration(1000)
            //不要呼吸灯效果
            //.noBreathing()
            //呼吸灯效果时长
            .breathingDuration(3000)
            //边框动画模式
            .animMode(AbsFocusBorder.Mode.SEQUENTIALLY)
            .build(this);

    }

    private void loadRecyclerViewMenuItem() {
        mMenuView = (TvRecyclerView) findViewById(R.id.list_menu);
        mMenuView.setLayoutManager(new LinearLayoutManager(this));
        mMenuView.setSpacingWithMargins(14, 0);
//        mMenuAdapter = new MenuAdapter(this, new ArrayList(4));
//        mMenuView.setAdapter(mMenuAdapter);
        createOptionItemData(mMenuView, R.layout.detail_menu_item);
    }

    private void loadDataForRecyclerViewGridLayout() {

        mRecyclerView = (TvRecyclerView) findViewById(R.id.list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GridLayoutManager.Orientation.HORIZONTAL);
        gridLayoutManager.setNumColumns(4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setSpacingWithMargins(10, 20);//设置行列间距
        createData(mRecyclerView, R.layout.detail_list_item);
    }

    private void createData(RecyclerView recyclerView, int id) {
        mAdapter = new MyAdapter(this, id);
        mAdapter.setIconIds(list.get(0));
        mAdapter.setKeys(new ArrayList<String>(mDatas.get(0).keySet()));
        mAdapter.setMaps(mDatas.get(0));

        recyclerView.setAdapter(mAdapter);
    }

    private void createOptionItemData(RecyclerView recyclerView, int id) {
        OptionItemAdapter adapter = new OptionItemAdapter(this, id);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
        adapter.setOnBindListener(this);
    }

    @Override
    public void onBind(View view, int i) {
        if (i == 3) {
            return;
        }
        mAdapter.setIconIds(list.get(i));
        mAdapter.setKeys(new ArrayList<String>(mDatas.get(i).keySet()));
        mAdapter.setMaps(mDatas.get(i));
        mAdapter.notifyDataSetChanged();
    }

    private void setListener() {

        mMenuView.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                onMoveFocusBorder(itemView, 1.1f, 0);
            }
        });

        mRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, 10);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
//                Utils.startApp(DetailListActivity.this,keys.get(i),maps.get(keys.get(i)));
            }
        });

        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mMenuView.hasFocus() && !hasFocus) {
                    return;
                }
                mFocusBorder.setVisible(hasFocus);
            }
        });

        mMenuView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mRecyclerView.hasFocus() && !hasFocus) {
                    return;
                }
                mFocusBorder.setVisible(hasFocus);
            }
        });

    }


    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }



    private class MenuAdapter extends CommonRecyclerViewAdapter {

        public MenuAdapter(Context context, List datas) {
            super(context, datas);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.detail_menu_item;
        }

        @Override
        public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        }
    }
}
