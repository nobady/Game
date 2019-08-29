package com.su.gametribe;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondMenuActivity extends AppCompatActivity implements FocusViewMonitor.FocusBorderProvider{

    private FocusViewMonitor mMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_menu);
        mMonitor = new FocusViewMonitor(this);
        mMonitor.setFocusBorderProvider(this);
        mMonitor.start();

        findViewById(R.id.menu_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.menu_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.menu_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.menu_04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

        boolean custom = focusedView == findViewById(R.id.item_01)
            || focusedView == findViewById(R.id.item_02)
            || focusedView == findViewById(R.id.item_03)
            || focusedView == findViewById(R.id.item_04)
            || focusedView == findViewById(R.id.item_05)
            || focusedView == findViewById(R.id.item_06)
            || focusedView == findViewById(R.id.item_07)
            || focusedView == findViewById(R.id.item_08);
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
            || focusedView == findViewById(R.id.item_07)
            || focusedView == findViewById(R.id.item_08);
        return custom;
    }
}
