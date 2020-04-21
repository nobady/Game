package com.su.gametribe;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecondMenuActivity extends AppCompatActivity implements FocusViewMonitor.FocusBorderProvider{

    private FocusViewMonitor mMonitor;
    private List<Map<String,String>> mDatas = new ArrayList<>();
    private List<Map<String,Integer>> list = new ArrayList<>();
    private int index = 0;
    private List<ImageView> mImageViewList;
    private AddNewPackageBroadcast mAddNewPackageBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_menu);
        mMonitor = new FocusViewMonitor(this);
        mMonitor.setFocusBorderProvider(this);
        mMonitor.start();

        int currentIndex = getIntent().getIntExtra("index", 0);
        LinearLayout view = findViewById(R.id.ll);
        view.getChildAt(currentIndex).requestFocus();
        if (currentIndex==3){
            currentIndex=1;
        }
        index = currentIndex;

        initBinfenData();
        initDongwuData();
        initYizhiData();

        findViewById(R.id.menu_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondMenuActivity.this.index = 0;
                setImage();
            }
        });
        findViewById(R.id.menu_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondMenuActivity.this.index = 1;
                setImage();
            }
        });
        findViewById(R.id.menu_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondMenuActivity.this.index = 2;
                setImage();
            }
        });
        findViewById(R.id.menu_04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondMenuActivity.this.index = 1;
                setImage();
            }
        });
        ImageView item1 = (ImageView) findViewById(R.id.item_01);
        ImageView item2 = (ImageView) findViewById(R.id.item_02);
        ImageView item3 = (ImageView) findViewById(R.id.item_03);
        ImageView item4 = (ImageView) findViewById(R.id.item_04);
        ImageView item5 = (ImageView) findViewById(R.id.item_05);
        ImageView item6 = (ImageView) findViewById(R.id.item_06);
        ImageView item7 = (ImageView) findViewById(R.id.item_07);
        mImageViewList = new ArrayList<>();
        mImageViewList.add(item1);
        mImageViewList.add(item2);
        mImageViewList.add(item3);
        mImageViewList.add(item4);
        mImageViewList.add(item5);
        mImageViewList.add(item6);
        mImageViewList.add(item7);
        setImage();


        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        mAddNewPackageBroadcast = new AddNewPackageBroadcast();
        registerReceiver(mAddNewPackageBroadcast,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mAddNewPackageBroadcast);

    }

    private void setImage(){

        for (int i = 0; i < mImageViewList.size(); i++) {
            List<String> keyString  = new ArrayList<>(mDatas.get(index).keySet());
            final String key = keyString.get(i);
            mImageViewList.get(i).setImageResource(list.get(index).get(key));
            mImageViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.startApp(SecondMenuActivity.this,key,mDatas.get(index).get(key));
                }
            });
        }
    }


    @Override
    public int borderStrokeWidth(View focusedView) {
        return 0;
    }

    @Override
    public int borderFocusViewOverSize(View focusedView) {
        return 1;
    }

    @Override
    public int borderDrawableId(View focusedView) {
        return R.drawable.framework_focus_border;
    }

    @Override
    public boolean borderCustom(View focusedView) {

        boolean custom = focusedView == findViewById(R.id.item_01)
            || focusedView == findViewById(R.id.item_02)
            || focusedView == findViewById(R.id.item_03)
            || focusedView == findViewById(R.id.item_04)
            || focusedView == findViewById(R.id.item_05)
            || focusedView == findViewById(R.id.item_06)
            || focusedView == findViewById(R.id.item_07);
        return custom;
    }

    @Override
    public boolean borderEnable(View focusedView) {

        boolean custom = focusedView == findViewById(R.id.item_01)
            || focusedView == findViewById(R.id.item_02)
            || focusedView == findViewById(R.id.item_03)
            || focusedView == findViewById(R.id.item_04)
            || focusedView == findViewById(R.id.item_05)
            || focusedView == findViewById(R.id.item_06)
            || focusedView == findViewById(R.id.item_07);
        return custom;
    }

    private void initBinfenData(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("com.closelamp","CloseLamp.apk");
        map.put("com.sf","Sf.apk");
        map.put("org.cocos2d.PuzzleBobble","PuzzleBobble-release-signed.apk");
        map.put("org.cocos2d.Dungeon","Dungeon-release-signed.apk");
        map.put("org.cocos2d.Kitchen","Kitchen-release-signed.apk");
        map.put("org.cocos2d.Quickspot","Quickspot-release-signed.apk");
        map.put("org.cocos2d.Eliminate","Eliminate-release-signed.apk");
        mDatas.add(map);


        Map<String,Integer> iconMap = new LinkedHashMap<>();
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
        Map<String,String> map = new LinkedHashMap<>();
//        map.put("com.parking","Parking.apk");
        map.put("com.blackman","BlkMan.apk");
        map.put("com.base","TheJeepAndroid.apk");
        map.put("org.cocos2d.JumpingRat","JumpingRat-release-signed.apk");
        map.put("org.cocos2d.ClimbBuild","ClimbBuild-release-signed.apk");
        map.put("org.cocos2d.Butterfly","Butterfly-release-signed.apk");
        map.put("org.cocos2d.RescueBaby","RescueBaby-release-signed.apk");
        map.put("org.cocos2d.Flower","Flower-release-signed.apk");
        mDatas.add(map);

        Map<String,Integer> iconMap = new LinkedHashMap<>();
//        iconMap.put("com.parking",R.drawable.nltcc);
        iconMap.put("com.blackman",R.drawable.bpxd);
        iconMap.put("com.base",R.drawable.fkxd);
        iconMap.put("org.cocos2d.JumpingRat",R.drawable.ttslxj);
        iconMap.put("org.cocos2d.ClimbBuild",R.drawable.bbdt);
        iconMap.put("org.cocos2d.Butterfly",R.drawable.hjcd);
        iconMap.put("org.cocos2d.RescueBaby",R.drawable.yjbb);
        iconMap.put("org.cocos2d.Flower",R.drawable.jsth);
        list.add(iconMap);
    }

    private void initYizhiData(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("com.intelligencepuzzle","IntelligencePuzzle.apk");
        map.put("com.interlocking","Interlocking.apk");
        map.put("com.matrixpuzzle","MatrixPuzzle.apk");
        map.put("com.parking","Parking.apk");
        map.put("com.puzzlegame","PuzzleGame.apk");
        map.put("org.cocos2d.PushBox","PushBox-release-signed.apk");
        map.put("org.cocos2d.IceCube","IceCube-release-signed.apk");
        mDatas.add(map);

        Map<String,Integer> iconMap = new LinkedHashMap<>();
        iconMap.put("com.intelligencepuzzle",R.drawable.zlpt);
        iconMap.put("com.interlocking",R.drawable.hhxk);
        iconMap.put("com.matrixpuzzle",R.drawable.jzpt);
        iconMap.put("com.parking",R.drawable.nltcc);
        iconMap.put("com.puzzlegame",R.drawable.jlpt);
        iconMap.put("org.cocos2d.PushBox",R.drawable.txz);
        iconMap.put("org.cocos2d.IceCube",R.drawable.bfk);
        list.add(iconMap);
    }

    class AddNewPackageBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (TextUtils.equals(Utils.mPackageName,packageName)){
                Utils.gotoApp(context,packageName);
            }
        }
    }
}
