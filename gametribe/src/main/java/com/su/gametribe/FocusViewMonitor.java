package com.su.gametribe;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangshuai on 2017/8/24.
 */

public class FocusViewMonitor {

    Activity activity;

    private ImageView borderView;

    private View focusView;

    private float lastFocusViewZ = 1;

    private int defaultBorderWidth = 0;

    private Drawable borderDrawable;

    private int defaultOverSize = 0;

    private FocusBorderProvider focusBorderProvider;

    private AnimatorSet animatorSet;

    private Rect focusRect = new Rect();

    private int[] focusLocation = new int[2];

    private ViewTreeObserver.OnGlobalFocusChangeListener onGlobalFocusChangeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {


        @Override
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {

            logger("FocusViewMonitor - onGlobalFocusChangeListener oldFocus =   " + oldFocus + " , newFocus = " + newFocus);
            if (newFocus == null) {
                return;
            }

            if (!borderEnable(newFocus)) {
                if (borderView != null) {
                    borderView.setVisibility(View.GONE);
                }
                resetFocusView();
                focusView = null;
                return;
            }

            logger("FocusViewMonitor - onGlobalFocusChangeListener  " + newFocus);
            logger("FocusViewMonitor - onGlobalFocusChangeListener start");
            if (animatorSet != null && animatorSet.isRunning()) {
                logger("FocusViewMonitor - onGlobalFocusChangeListener animatorSet.end start");
                animatorSet.end();
                logger("FocusViewMonitor - onGlobalFocusChangeListener animatorSet.end end");
            }

            changeFocus(newFocus);

        }
    };

    private ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {

//            final View focusView = FocusViewMonitor.this.focusView;

            if (focusView != null && borderEnable(focusView)) {
                final int[] location = new int[2];
                final int[] locationWindow = new int[2];
                final Rect rect = new Rect();
                focusView.getLocalVisibleRect(rect);

                focusView.getLocationOnScreen(location);
                focusView.getLocationInWindow(locationWindow);

                if (focusViewHasUpdate(location, rect)) {
                    logger("FocusViewMonitor - onScrollChangedListener  " + focusView);
                    logger("FocusViewMonitor - onScrollChangedListener start");
                    if (animatorSet != null && animatorSet.isRunning()) {
                        logger("FocusViewMonitor - onScrollChangedListener animatorSet.end start");
                        animatorSet.end();
                        logger("FocusViewMonitor - onScrollChangedListener animatorSet.end end");
                    }
                } else {
                    return;
                }

                if (borderView == null) {
                    addBorderView();
                }

                borderFocusView(location, rect);

            }
        }
    };

    private boolean focusViewHasUpdate(int[] location, Rect rect) {
        logger("FocusViewMonitor - focusViewHasUpdate  " + location[0] + " , " + focusLocation[0] + " , " + location[1] + " , " + focusLocation[1]);
        logger("FocusViewMonitor - focusViewHasUpdate  " + rect + " , " + rect);
        boolean noUpdate = location[0] == focusLocation[0] && location[1] == focusLocation[1] && focusRect.equals(rect);
        logger("FocusViewMonitor - focusViewHasUpdate  " + noUpdate);
        return !noUpdate;
    }

    private List<View> searchFocusViews(View view, int direction1, int direction2, int direction3, int direction4) {
        final List<View> views = new ArrayList<>();
        if (view.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            views.addAll(viewGroup.getFocusables(direction1));
            views.addAll(viewGroup.getFocusables(direction2));
            views.addAll(viewGroup.getFocusables(direction3));
            views.addAll(viewGroup.getFocusables(direction4));
            if (view.getParent().getParent() != null) {
                ViewGroup group = (ViewGroup) view.getParent().getParent();
                views.addAll(group.getFocusables(direction1));
                views.addAll(group.getFocusables(direction2));
                views.addAll(group.getFocusables(direction3));
                views.addAll(group.getFocusables(direction4));
            }
        }
        views.addAll(activity.getWindow().getDecorView().getFocusables(direction1));
        views.addAll(activity.getWindow().getDecorView().getFocusables(direction2));
        views.addAll(activity.getWindow().getDecorView().getFocusables(direction3));
        views.addAll(activity.getWindow().getDecorView().getFocusables(direction4));
        return views;
    }

    private View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            if (v == focusView && (left != oldLeft || right != oldRight || bottom != oldBottom || top != oldTop)) {

                logger("FocusViewMonitor - onLayoutChangeListener = " + focusView);
                if (animatorSet != null && animatorSet.isRunning()) {
                    animatorSet.end();
                }
                if (borderView != null) {
                    borderView.setVisibility(View.GONE);
                }
                v.removeCallbacks(requestFocusRun);
                v.postDelayed(requestFocusRun, 100);
            }
        }
    };

    private Runnable requestFocusRun = new Runnable() {
        @Override
        public void run() {
            start();
        }
    };


    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            logger("FocusViewMonitor - animatorListener = " + focusView);
            logger("FocusViewMonitor - animatorListener end");

            if (focusView == null) {
                resetFocusView();
                return;
            }
            if (borderView == null) {
                addBorderView();
            }


            int[] location = new int[2];
            focusView.getLocationOnScreen(location);
            Rect rect = new Rect();
            focusView.getLocalVisibleRect(rect);

            borderFocusView(location, rect);

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void borderFocusView(int[] location, Rect rect) {
        logger("FocusViewMonitor - borderFocusView = " + focusView);
        int borderWidth = defaultBorderWidth;
        if (focusBorderProvider != null && focusBorderProvider.borderCustom(focusView)) {
            borderWidth = focusBorderProvider.borderStrokeWidth(focusView);
        }
        int focusCenterX = location[0] + rect.left + rect.width() / 2;
        int focusCenterY = location[1] + rect.top + rect.height() / 2;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) borderView.getLayoutParams();
        layoutParams.width = rect.width() + borderWidth;
        layoutParams.height = rect.height() + borderWidth;
        layoutParams.leftMargin = focusCenterX - layoutParams.width / 2;
        layoutParams.topMargin = focusCenterY - layoutParams.height / 2;
        borderView.requestLayout();
    }

    private void changeFocus(View focus) {

        logger("FocusViewMonitor - changeFocus  " + focus);
        int borderWidth = defaultBorderWidth;
        int overSize = defaultOverSize;
        if (borderView == null) {
            addBorderView();
        }
//        final View focusView = FocusViewMonitor.this.focusView;
        if (focusBorderProvider != null && focusBorderProvider.borderCustom(focus)) {
            borderWidth = focusBorderProvider.borderStrokeWidth(focus);
            overSize = focusBorderProvider.borderFocusViewOverSize(focus);
            borderView.setImageResource(focusBorderProvider.borderDrawableId(focus));
        } else {
            borderView.setImageDrawable(borderDrawable);
        }

        if (focus != focusView) {
            focus.addOnLayoutChangeListener(onLayoutChangeListener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                lastFocusViewZ = focus.getZ();
                focus.setZ(100);
            }
            if (borderView != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    borderView.setZ(Integer.MAX_VALUE);
                }
                int width = focus.getWidth();
                int height = focus.getHeight();
                if (focus.getWidth() > focus.getHeight()) {
                    width += overSize;
                    height = (int) ((overSize * 1f / focus.getWidth() + 1) * height);
                } else {
                    height += overSize;
                    width = (int) ((overSize * 1f / focus.getHeight() + 1) * width);
                }

                int[] location = new int[2];
                focus.getLocationOnScreen(location);
                Rect rect = new Rect();
                focus.getLocalVisibleRect(rect);
//                int focusCenterX = location[0] + focus.getWidth() / 2;
//                int focusCenterY = location[1] + focus.getHeight() / 2;
                int focusCenterX = location[0] + rect.width() / 2;
                int focusCenterY = location[1] + rect.height() / 2;

                if (borderView.getVisibility() == View.GONE) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) borderView.getLayoutParams();
                    layoutParams.width = (int) (width + borderWidth);
                    layoutParams.height = (int) (height + borderWidth);
                    layoutParams.leftMargin = focusCenterX - layoutParams.width / 2;
                    layoutParams.topMargin = focusCenterY - layoutParams.height / 2;
                    borderView.setVisibility(View.VISIBLE);
                    borderView.setTranslationX(0);
                    borderView.setTranslationY(0);
                    focus.setScaleX((width) * 1f / focus.getWidth());
                    focus.setScaleY((height) * 1f / focus.getHeight());

                    logger("FocusViewMonitor - changeFocus borderView  leftMargin = " + layoutParams.leftMargin + " , topMargin = " + layoutParams.topMargin);
                } else {

                    if (FocusViewMonitor.this.animatorSet != null && FocusViewMonitor.this.animatorSet.isRunning()) {
                        animatorSet.end();
                    }

                    animatorSet = new AnimatorSet();

                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) borderView.getLayoutParams();
                    int bouderCenterX = (layoutParams.leftMargin + (width + borderWidth) / 2);
                    int bouderCenterY = layoutParams.topMargin + (int) (height * 1f + borderWidth) / 2;

                    animatorSet.playTogether(
                            ObjectAnimator.ofInt(new MarginLayoutLTDecorate(borderView), "left", layoutParams.leftMargin, layoutParams.leftMargin + focusCenterX - bouderCenterX).setDuration(200),
                            ObjectAnimator.ofInt(new MarginLayoutLTDecorate(borderView), "top", layoutParams.topMargin, layoutParams.topMargin + focusCenterY - bouderCenterY).setDuration(200),
//                            ObjectAnimator.ofFloat(borderView, "translationX", borderView.getTranslationX(), focusCenterX - bouderCenterX),
//                            ObjectAnimator.ofFloat(borderView, "translationY", borderView.getTranslationY(), focusCenterY - bouderCenterY),
                            ObjectAnimator.ofInt(new MarginLayoutWHDecorate(borderView), "w", borderView.getWidth(), (int) (width + borderWidth)).setDuration(200),
                            ObjectAnimator.ofInt(new MarginLayoutWHDecorate(borderView), "h", borderView.getHeight(), (int) (height * 1f + borderWidth)).setDuration(200),
//                            ObjectAnimator.ofFloat(focus, "scaleX", 1, width * 1f / focus.getWidth()),
//                            ObjectAnimator.ofFloat(focus, "scaleY", 1, height * 1f / focus.getHeight()),
                            ObjectAnimator.ofFloat(new ViewScaleDecorate(focus), "scale", 1, width * 1f / focus.getWidth()).setDuration(200)

                    );
                    animatorSet.setDuration(200);
                    animatorSet.removeAllListeners();
                    animatorSet.addListener(animatorListener);
                    animatorSet.start();
                    logger("FocusViewMonitor - changeFocus animatorSet.start() ");
                }
            }
            resetFocusView();
            this.focusView = focus;
            this.focusView.getLocalVisibleRect(focusRect);
            this.focusView.getLocationInWindow(focusLocation);

            logger("FocusViewMonitor - this.focusView = focus  " + focusView);
            logger("FocusViewMonitor - changeFocus this.focusView = focus;");
        }
    }

    public FocusViewMonitor(Activity activity) {
        logger("FocusViewMonitor - FocusViewMonitor  " + focusView);
        this.activity = activity;
        borderDrawable = activity.getResources().getDrawable(R.drawable.framework_shape_border);
        setBorderStroke(DisplayUtils.dip2px(24));
        setScaleSize((int) activity.getResources().getDimension(R.dimen.y18));
        setBorderDrawable(activity.getResources().getDrawable(R.drawable.framework_focus_border));
    }

    private void resetFocusView() {
        logger("FocusViewMonitor - resetFocusView  " + focusView);
        if (focusView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                focusView.setZ(lastFocusViewZ);
            }
            focusView.setScaleX(1);
            focusView.setScaleY(1);
            focusView.removeOnLayoutChangeListener(onLayoutChangeListener);
        }
    }

    private void addBorderView() {

        logger("FocusViewMonitor - addBorderView  " + focusView);
        if (activity != null) {
            ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
            if (borderView != null) {
                viewGroup.removeView(borderView);
            }
            borderView = new ImageView(activity);
            borderView.setImageDrawable(borderDrawable);
            borderView.setScaleType(ImageView.ScaleType.FIT_XY);
            borderView.setVisibility(View.GONE);
            viewGroup.addView(borderView);
        }
    }


    public void setBorderStroke(int borderWidth) {
        this.defaultBorderWidth = borderWidth;
    }

    public void setBorderDrawable(Drawable drawable) {
        this.borderDrawable = drawable;
    }

    public void setScaleSize(int size) {
        this.defaultOverSize = size;
    }

    public void setFocusBorderProvider(FocusBorderProvider focusBorderProvider) {
        this.focusBorderProvider = focusBorderProvider;
    }

    public void start() {
        if (activity != null) {

            logger("FocusViewMonitor - start  " + focusView);
            activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalFocusChangeListener(onGlobalFocusChangeListener);
            activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalFocusChangeListener(onGlobalFocusChangeListener);
            activity.getWindow().getDecorView().getViewTreeObserver().addOnScrollChangedListener(onScrollChangedListener);
            View focus = activity.getCurrentFocus();
            if (focus == null) {
                resetFocusView();
                focusView = null;
                return;
            }
            if (!borderEnable(focus)) {
                resetFocusView();
                focusView = null;
                return;
            }
//            if (focus == focusView) {
//                resetFocusView();
//                focusView = null;
//            }
            changeFocus(focus);
        }
    }

    private boolean borderEnable(View focusView) {

        return focusView != null && focusView.getTag(Integer.MAX_VALUE) == null && (focusBorderProvider == null || focusBorderProvider.borderEnable(focusView));
    }

    public void stop() {
        if (activity != null) {
            activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalFocusChangeListener(onGlobalFocusChangeListener);
        }
    }

    public void destroy() {
        stop();
        removeBorderView();
        activity = null;
        borderDrawable = null;
        borderView = null;
        focusView = null;
    }

    private void removeBorderView() {
        logger("FocusViewMonitor - removeBorderView  ");
        if (activity != null) {
            ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
            if (borderView != null) {
                viewGroup.removeView(borderView);
            }
        }
    }

    static void logger(String msg) {
//        System.out.println(msg);
    }


    private class MarginLayoutWHDecorate {

        ViewGroup.MarginLayoutParams layoutParams;

        View view;

        MarginLayoutWHDecorate(View view) {
            this.layoutParams = layoutParams;
            this.view = view;
        }

        public void setW(int w) {
            layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            logger("FocusViewMonitor - setW  " + "[" + layoutParams.leftMargin + "," + layoutParams.topMargin + "]");
            layoutParams.width = w;
            view.setLayoutParams(layoutParams);
        }

        public void setH(int h) {
            layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            logger("FocusViewMonitor - setH  " + "[" + layoutParams.leftMargin + "," + layoutParams.topMargin + "]");
            layoutParams.height = h;
            view.setLayoutParams(layoutParams);
        }
    }


    private class MarginLayoutLTDecorate {

        ViewGroup.MarginLayoutParams layoutParams;

        View view;

        MarginLayoutLTDecorate(View view) {
            this.view = view;
        }

        public void setLeft(int left) {
            layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            logger("FocusViewMonitor - setLeft  " + "[" + layoutParams.leftMargin + "," + layoutParams.topMargin + "]");
            layoutParams.leftMargin = left;
            view.setLayoutParams(layoutParams);
        }

        public void setTop(int top) {
            layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            logger("FocusViewMonitor - setLeft  " + "[" + layoutParams.leftMargin + "," + layoutParams.topMargin + "]");
            layoutParams.topMargin = top;
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * 同步x、y的scale变化的装饰器
     */
    private class ViewScaleDecorate {

        View view;

        ViewScaleDecorate(View view) {
            this.view = view;
        }

        void setScale(float scale) {
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    }

    /**
     * 焦点View的获取到焦点时的状态的变化的资源提供者
     */
    public interface FocusBorderProvider {

        int borderStrokeWidth(View focusedView);

        int borderFocusViewOverSize(View focusedView);

        int borderDrawableId(View focusedView);

        boolean borderCustom(View focusedView);

        boolean borderEnable(View focusedView);

    }
}
