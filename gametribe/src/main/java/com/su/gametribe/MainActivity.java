package com.su.gametribe;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FocusViewMonitor.FocusBorderProvider {

    private Button tab1;
    private Button tab2;
    private Button tab3;
    private Button tab4;
    private Button tab5;

    private ImageView topleft;
    private ImageView leftbottom1;
    private ImageView leftbottom2;

    private ImageView mid1;
    private ImageView mid2;
    private ImageView right;

    private ImageView cent01;
    private ImageView cent02;
    private ImageView cent03;
    private ImageView cent04;
    private ImageView cent05;
    private ImageView cent06;
    private ImageView cent07;

    private Context context;
    private FocusViewMonitor monitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        initView();
        context = this;
        monitor = new FocusViewMonitor(this);
        monitor.setFocusBorderProvider(this);
        monitor.start();

    }

    private void checkPermission(){
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "请同意权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        //顶部分五个tab
        tab1 = (Button) findViewById(R.id.main_top_tab1);
        tab2 = (Button) findViewById(R.id.main_top_tab2);
        tab3 = (Button) findViewById(R.id.main_top_tab3);
        tab4 = (Button) findViewById(R.id.main_top_tab4);
        tab5 = (Button) findViewById(R.id.main_top_tab5);
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DetailListActivity.class);
                startActivity(intent);
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSeconActivity();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSeconActivity();
            }
        });
        tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSeconActivity();
            }
        });
        tab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PersonInfoActivity.class);
                startActivity(intent);
            }
        });

        //中间方框部分
        topleft = (ImageView) findViewById(R.id.top_left);
        leftbottom1 = (ImageView) findViewById(R.id.left_bottom1);
        leftbottom2 = (ImageView) findViewById(R.id.left_bottom2);
        mid1 = (ImageView) findViewById(R.id.top_mid1);
        mid2 = (ImageView) findViewById(R.id.top_mid2);
        right = (ImageView) findViewById(R.id.top_right);
        topleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startApp(MainActivity.this,"org.cocos2d.Kitchen","Kitchen-release-signed.apk");
            }
        });
        leftbottom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startApp(MainActivity.this,"org.cocos2d.Butterfly","Butterfly-release-signed.apk");
            }
        });
        leftbottom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startApp(MainActivity.this,"org.cocos2d.RescueBaby","RescueBaby-release-signed.apk");
            }
        });
        mid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startApp(MainActivity.this,"com.base","TheJeepAndroid.apk");

            }
        });
        mid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startApp(MainActivity.this,"blackman.com.blackman","BlkMan.apk");
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startApp(MainActivity.this,"org.cocos2d.Dungeon","Dungeon-release-signed.apk");
            }
        });

        //一排的六边形tab，超级玛丽、斗地主、植物大战僵尸……
        cent01 = (ImageView)findViewById(R.id.cent_01);
        cent02 = (ImageView)findViewById(R.id.cent_02);
        cent03 = (ImageView)findViewById(R.id.cent_03);
        cent04 = (ImageView)findViewById(R.id.cent_04);
        cent05 = (ImageView)findViewById(R.id.cent_05);
        cent06 = (ImageView)findViewById(R.id.cent_06);
        cent07 = (ImageView) findViewById(R.id.cent_07);

        cent01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.startApp(MainActivity.this,"com.base","TheJeepAndroid.apk");
            }
        });
        cent02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.startApp(MainActivity.this,"com.sf","Sf.apk");
            }
        });
        cent03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.startApp(MainActivity.this,"org.cocos2d.Flower","Flower-release-signed.apk");
            }
        });
        cent04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.startApp(MainActivity.this,"org.cocos2d.Quickspot","Quickspot-release-signed.apk");
            }
        });
        cent05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.startApp(MainActivity.this,"com.puzzlegame","PuzzleGame.apk");
            }
        });
        cent06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.startApp(MainActivity.this,"org.cocos2d.JumpingRat","JumpingRat-release-signed.apk");
            }
        });
        cent07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.startApp(MainActivity.this,"com.closelamp","CloseLamp.apk");
            }
        });
    }

    private void gotoSeconActivity() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, DetailListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3&&resultCode==RESULT_OK){
            Utils.gotoApp(this,Utils.mPackageName);
        }
    }

    @Override
    public int borderStrokeWidth(View focusedView) {
        return 1;
    }

    @Override
    public int borderFocusViewOverSize(View focusedView) {
        return 0;
    }

    @Override
    public int borderDrawableId(View focusedView) {
        return R.drawable.framework_focus_border;
    }

    @Override
    public boolean borderCustom(View focusedView) {
        boolean custom = focusedView == findViewById(R.id.top_left)
                || focusedView == findViewById(R.id.left_bottom1)
                || focusedView == findViewById(R.id.left_bottom2)
                || focusedView == findViewById(R.id.top_mid1)
                || focusedView == findViewById(R.id.top_mid2)
                || focusedView == findViewById(R.id.ll_01)
                || focusedView == findViewById(R.id.ll_02)
                || focusedView == findViewById(R.id.ll_03)
                || focusedView == findViewById(R.id.ll_04)
                || focusedView == findViewById(R.id.ll_05)
                || focusedView == findViewById(R.id.ll_021)
                || focusedView == findViewById(R.id.ll_022)
                || focusedView == findViewById(R.id.ll_023)
                || focusedView == findViewById(R.id.ll_024)
                || focusedView == findViewById(R.id.ll_025)
                || focusedView == findViewById(R.id.ll_031)
                || focusedView == findViewById(R.id.ll_032)
                || focusedView == findViewById(R.id.ll_033)
                || focusedView == findViewById(R.id.ll_034)
                || focusedView == findViewById(R.id.ll_035)
                || focusedView == findViewById(R.id.top_right);
        return custom;
    }

    @Override
    public boolean borderEnable(View focusedView) {
        boolean custom = focusedView == findViewById(R.id.top_left)
                || focusedView == findViewById(R.id.left_bottom1)
                || focusedView == findViewById(R.id.left_bottom2)
                || focusedView == findViewById(R.id.top_mid1)
                || focusedView == findViewById(R.id.top_mid2)
                || focusedView == findViewById(R.id.top_right)
            || focusedView == findViewById(R.id.ll_01)
            || focusedView == findViewById(R.id.ll_02)
            || focusedView == findViewById(R.id.ll_03)
            || focusedView == findViewById(R.id.ll_04)
            || focusedView == findViewById(R.id.ll_05)
            || focusedView == findViewById(R.id.ll_021)
            || focusedView == findViewById(R.id.ll_022)
            || focusedView == findViewById(R.id.ll_023)
            || focusedView == findViewById(R.id.ll_024)
            || focusedView == findViewById(R.id.ll_025)
            || focusedView == findViewById(R.id.ll_031)
            || focusedView == findViewById(R.id.ll_032)
            || focusedView == findViewById(R.id.ll_033)
            || focusedView == findViewById(R.id.ll_034)
            || focusedView == findViewById(R.id.ll_035);
        return custom;
    }
}
