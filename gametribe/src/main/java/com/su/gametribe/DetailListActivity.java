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
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
public class DetailListActivity extends Activity implements OptionItemAdapter.OnBindListener, View.OnFocusChangeListener {

    private MetroViewBorderImpl mMetroViewBorderImpl;
    private static final String TAG = DetailListActivity.class.getSimpleName();

    private List<Map<String,String>> mDatas = new ArrayList<>();
    private MyAdapter mAdapter;
    private List<Map<String,Integer>> list = new ArrayList<>();



    private void initBinfenData(){
        Map<String,String> map = new HashMap<>();
        map.put("com.closelamp","CloseLamp.apk");
        map.put("com.sf","Sf.apk");
        map.put("org.cocos2d.PuzzleBobble","PuzzleBobble-release-signed.apk");
        map.put("org.cocos2d.Dungeon","Dungeon-release-signed.apk");
        map.put("org.cocos2d.Kitchen","Kitchen-release-signed.apk");
        map.put("org.cocos2d.Quickspot","Quickspot-release-signed.apk");
        map.put("org.cocos2d.Eliminate","Eliminate-release-signed.apk");
        mDatas.add(map);


        Map<String,Integer> iconMap = new HashMap<>();
        iconMap.put("com.closelamp",R.drawable.xxxg);
        iconMap.put("com.sf",R.drawable.gtfh);
        iconMap.put("org.cocos2d.PuzzleBobble",R.drawable.xcpp);
        iconMap.put("org.cocos2d.Dungeon",R.drawable.jsdl);
        iconMap.put("org.cocos2d.Kitchen",R.drawable.jlsyy);
        iconMap.put("org.cocos2d.Quickspot",R.drawable.jljdg);
        iconMap.put("org.cocos2d.Eliminate",R.drawable.ckppp);
        list.add(iconMap);
    }

    private void initDongwuData(){
        Map<String,String> map = new HashMap<>();
        map.put("blackman.com.blackman","BlkMan.apk");
        map.put("com.base","TheJeepAndroid.apk");
        map.put("org.cocos2d.JumpingRat","JumpingRat-release-signed.apk");
        map.put("org.cocos2d.ClimbBuild","ClimbBuild-release-signed.apk");
        map.put("org.cocos2d.Butterfly","Butterfly-release-signed.apk");
        map.put("org.cocos2d.RescueBaby","RescueBaby-release-signed.apk");
        map.put("org.cocos2d.Flower","Flower-release-signed.apk");
        mDatas.add(map);

        Map<String,Integer> iconMap = new HashMap<>();
        iconMap.put("blackman.com.blackman",R.drawable.bpxd);
        iconMap.put("com.base",R.drawable.fkxd);
        iconMap.put("org.cocos2d.JumpingRat",R.drawable.ttslxj);
        iconMap.put("org.cocos2d.ClimbBuild",R.drawable.bbdt);
        iconMap.put("org.cocos2d.Butterfly",R.drawable.hjcd);
        iconMap.put("org.cocos2d.RescueBaby",R.drawable.yjbb);
        iconMap.put("org.cocos2d.Flower",R.drawable.jsth);
        list.add(iconMap);
    }

    private void initYizhiData(){
        Map<String,String> map = new HashMap<>();
        map.put("com.intelligencepuzzle","IntelligencePuzzle.apk");
        map.put("com.interlocking","Interlocking.apk");
        map.put("com.matrixpuzzle","MatrixPuzzle.apk");
        map.put("com.parking","Parking.apk");
        map.put("com.puzzlegame","PuzzleGame.apk");
        map.put("org.cocos2d.PushBox","PushBox-release-signed.apk");
        map.put("org.cocos2d.IceCube","IceCube-release-signed.apk");
        mDatas.add(map);

        Map<String,Integer> iconMap = new HashMap<>();
        iconMap.put("com.intelligencepuzzle",R.drawable.zlpt);
        iconMap.put("com.interlocking",R.drawable.hhxk);
        iconMap.put("com.matrixpuzzle",R.drawable.jzpt);
        iconMap.put("com.parking",R.drawable.nltcc);
        iconMap.put("com.puzzlegame",R.drawable.jlpt);
        iconMap.put("org.cocos2d.PushBox",R.drawable.txz);
        iconMap.put("org.cocos2d.IceCube",R.drawable.bfk);
        list.add(iconMap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaillist);

        initBinfenData();
        initDongwuData();
        initYizhiData();

        mMetroViewBorderImpl = new MetroViewBorderImpl(this);
        mMetroViewBorderImpl.setBackgroundResource(R.drawable.framework_focus_border);
        loadRecyclerViewMenuItem();
        loadDataForRecyclerViewGridLayout();
    }

    private void loadRecyclerViewMenuItem() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ry_menu_item);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        createOptionItemData(recyclerView, R.layout.detail_menu_item);
    }

    private void loadDataForRecyclerViewGridLayout() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ry_detail_list);
        GridLayoutManager gridlayoutManager = new AutoLayoutManager(this, 4);
        gridlayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridlayoutManager);
        recyclerView.setFocusable(false);
        mMetroViewBorderImpl.attachTo(recyclerView);
        createData(recyclerView, R.layout.detail_list_item);
    }

    private void createData(RecyclerView recyclerView, int id) {
        mAdapter = new MyAdapter(this, id);
        mAdapter.setIconIds(list.get(0));
        mAdapter.setKeys(new ArrayList<String>(mDatas.get(0).keySet()));
        mAdapter.setMaps(mDatas.get(0));

        recyclerView.setAdapter(mAdapter);
        recyclerView.scrollToPosition(0);
    }

    private void createOptionItemData(RecyclerView recyclerView, int id) {
        OptionItemAdapter adapter = new OptionItemAdapter(this, id);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
        adapter.setOnFocusChangeListener(this);
        adapter.setOnBindListener(this);
    }

    @Override
    public void onBind(View view, int i) {
        if (i==3){
            return;
        }
        mAdapter.setIconIds(list.get(i));
        mAdapter.setKeys(new ArrayList<String>(mDatas.get(i).keySet()));
        mAdapter.setMaps(mDatas.get(i));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
    }
}
