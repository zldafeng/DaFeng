package com.zl.dafeng.ui.widgetview.navbarview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.ObjectAnimator;
import com.zl.dafeng.R;
import com.zl.dafeng.ui.widgetview.navbarview.impl.INavBarItemView;
import com.zl.dafeng.ui.widgetview.navbarview.impl.INavBarPopupView;
import com.zl.dafeng.ui.widgetview.navbarview.util.AnimationUtil;
import com.zl.dafeng.ui.widgetview.navbarview.util.LogUtil;
import com.zl.dafeng.ui.widgetview.navbarview.util.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉导航菜单（仿美团导航下拉菜单）。</br>
 * 使用注意：
 * Created by moguangjian on 15/8/3 15:04.
 */
public class MDNavBarView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = MDNavBarView.class.getSimpleName();
    public static final int NAV_BAR_POPUP_VIEW_HEIGHT_DEFAULT = 210;

    private List<INavBarItemView> listItemView;
    private List<INavBarPopupView> listPopupView;

    private LinearLayout navBarView;
    private View navBarViewLine;
    private RelativeLayout navBarContentView;
    private FrameLayout navBarPopupView;
    private RelativeLayout navBarPopupOperateView;
    private View navBarPopupShadeView;
    private int navBarViewHeight;
    private int navBarPopupViewHeight = NAV_BAR_POPUP_VIEW_HEIGHT_DEFAULT;

    public MDNavBarView(Context context) {
        super(context);
        init();
    }

    public MDNavBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        moveChildViewToResultView();
    }

    private void moveChildViewToResultView() {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            removeView(view);
            navBarContentView.addView(view);
        }

        navBarContentView.addView(navBarPopupView);

        removeAllViews();
        addView(navBarView);
        addView(navBarViewLine);
        addView(navBarContentView);
    }

    private void init() {
        setOrientation(VERTICAL);
        listItemView = new ArrayList<>();
        listPopupView = new ArrayList<>();

        setNavBarViewHeight(UnitConversionUtil.dpToPx(getContext(), 48));

        initNavBarView();
        initNavBarContent();
        initNavBarPopupView();
    }

    private void initNavBarPopupView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        navBarPopupView = new FrameLayout(getContext());
        navBarPopupView.setLayoutParams(params);

        navBarPopupShadeView = new View(getContext());
        navBarPopupShadeView.setLayoutParams(params);
        navBarPopupShadeView.setBackgroundColor(Color.BLACK);
        navBarPopupView.addView(navBarPopupShadeView);
        ObjectAnimator.ofFloat(navBarPopupShadeView, AnimationUtil.ALPHA, 0.4f).setDuration(1).start();
        navBarPopupShadeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

//        TODO
//        RelativeLayout.LayoutParams paramsOperate = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams paramsOperate = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, UnitConversionUtil.dpToPx(getContext(), 200));
        navBarPopupOperateView = new RelativeLayout(getContext());
        navBarPopupOperateView.setLayoutParams(paramsOperate);
        navBarPopupOperateView.setBackgroundColor(Color.WHITE);
        navBarPopupView.addView(navBarPopupOperateView);

        hide(false);
    }

    private void initNavBarContent() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        navBarContentView = new RelativeLayout(getContext());
        navBarContentView.setLayoutParams(params);
    }

    private void initNavBarView() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, getNavBarViewHeight());
        navBarView = new LinearLayout(getContext());
        navBarView.setLayoutParams(params);
        navBarView.setOrientation(LinearLayout.HORIZONTAL);
        navBarView.setGravity(Gravity.CENTER_VERTICAL);

        navBarViewLine = new View(getContext());
        LayoutParams paramsLine = new LayoutParams(LayoutParams.MATCH_PARENT, UnitConversionUtil.dpToPx(getContext(), 0.5f));
        navBarViewLine.setLayoutParams(paramsLine);
        setNavBarViewLineColor(getResources().getColor(R.color.md_c_a7a7a7));

    }

    public void show() {
        navBarPopupView.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(navBarPopupShadeView, AnimationUtil.ALPHA, 0, 0.4f).setDuration(AnimationUtil.ANIMATION_DURATION).start();

        int height = getNavBarPopupViewHeight();
        if (height <= 0) {
            height = NAV_BAR_POPUP_VIEW_HEIGHT_DEFAULT;
        }
        AnimationUtil.stretchAnimate(getContext(), navBarPopupOperateView, AnimationUtil.ANIMATION_DURATION, height);

    }

    public void hide() {
        hide(true);
    }

    public void hide(boolean isAnimate) {
        setNoSelectItem();
       if (isAnimate) {
           ObjectAnimator.ofFloat(navBarPopupShadeView, AnimationUtil.ALPHA, 0.4f, 0).setDuration(AnimationUtil.ANIMATION_DURATION).start();

           int height = getNavBarPopupViewHeight();
           if (height <= 0) {
               height = NAV_BAR_POPUP_VIEW_HEIGHT_DEFAULT;
           }
           AnimationUtil.shrinkAnimate(getContext(), navBarPopupOperateView, AnimationUtil.ANIMATION_DURATION, height);

           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   navBarPopupView.setVisibility(View.GONE);
               }
           }, AnimationUtil.ANIMATION_DURATION);
       } else {
           navBarPopupView.setVisibility(View.GONE);
       }
    }

    public void setNavBarItemView(List<INavBarItemView> list) {
        if (list == null) {
            return;
        }

        listItemView.clear();
        listItemView.addAll(list);

        navBarView.removeAllViews();
        View line;
        int height  = getNavBarViewHeight();
        LayoutParams params = new LayoutParams(UnitConversionUtil.dpToPx(getContext(), 0.5f), height);
        for (int i = 0; i < list.size(); i++) {
            View itemView = (View) list.get(i);
            itemView.setTag(i);
            itemView.setOnClickListener(this);
            navBarView.addView(itemView);

            line = new View(getContext());
            line.setLayoutParams(params);
            line.setBackgroundColor(getResources().getColor(R.color.md_c_e6e6e6));
            navBarView.addView(line);
        }
    }

    public void setNavBarViewBG(int resId) {
        navBarView.setBackgroundResource(resId);
    }

    public void setNavBarViewBGColor(int colorId) {
        navBarView.setBackgroundColor(colorId);
    }

    private void setNavBarViewLineColor(int color) {
        navBarViewLine.setBackgroundColor(color);
    }

    @Override
    public void onClick(View v) {
        MDNavBarItemTitleView item = (MDNavBarItemTitleView) v;
        LogUtil.i(TAG, "title:" + item.getText() + " " + item.isSelected());
        setSelectItem(v);
    }

    private void setSelectItem(int index) {
        if (listItemView == null && listItemView.isEmpty()) {
            LogUtil.e(TAG, "listItemView is null. no item");
            return;
        }

        if (index > listItemView.size()) {
            index = listItemView.size()-1;
            LogUtil.e(TAG, "index 大于 listItemView.size()");
        }

        if (index < 0) {
            index = 0;
            LogUtil.e(TAG, "index 小于 0");
        }

        setSelectItem((View) listItemView.get(index));
    }

    private void setSelectItem(View view) {
        for (int i = 0; i < listItemView.size(); i++) {
            MDNavBarItemTitleView itemView = (MDNavBarItemTitleView) listItemView.get(i);

            if (itemView.isSelected()) {//之前有选中
                if (view.equals(itemView)) {//选中同一个item
                    LogUtil.i(TAG, "选中同一个item");
                    hide();
                    break;

                } else {//切换不同的item
                    LogUtil.i(TAG, "切换不同的item");
                    itemView.setSelected(false);
                    view.setSelected(true);
                    int tag = (int) view.getTag();
                    INavBarPopupView iNavBarPopupView = setSelectPopupOperatView(tag);
                    setNavBarPopupViewHeight(iNavBarPopupView.getNavBarPopupViewHeight());
                    show();
                    break;
                }
            } else {

                if (i == (listItemView.size() - 1)) {//没有显示
                    LogUtil.i(TAG, "全部没有显示过，直接显示");
                    view.setSelected(true);
                    int tag = (int) view.getTag();
                    INavBarPopupView iNavBarPopupView = setSelectPopupOperatView(tag);
                    setNavBarPopupViewHeight(iNavBarPopupView.getNavBarPopupViewHeight());
                    show();
                    break;
                }

            }
        }
    }

    private void setNoSelectItem() {
        for (int i = 0; i < listItemView.size(); i++) {
            View itemView = (View) listItemView.get(i);
            itemView.setSelected(false);
        }
    }

    public void setNavBarPopupOperateView(List<INavBarPopupView> list) {
        if (list == null) {
            return;
        }

        listPopupView.clear();
        listPopupView.addAll(list);

        for (int i = 0; i < list.size(); i++) {
            View itemView = (View) list.get(i);
            itemView.setOnClickListener(this);
            navBarPopupOperateView.addView(itemView);
        }
    }

    private INavBarPopupView setSelectPopupOperatView(int index) {
        LogUtil.i(TAG, "setSelectPopupOperatView is "+index);
        if (listPopupView == null && listPopupView.isEmpty()) {
            LogUtil.e(TAG, "listPopupView is null. no item");
            return null;
        }

        if (index > listPopupView.size()) {
            index = listPopupView.size()-1;
            LogUtil.e(TAG, "index 大于 listPopupView.size()");
        }

        if (index < 0) {
            index = 0;
            LogUtil.e(TAG, "index 小于 0");
        }

        View operateView = (View) listPopupView.get(index);
        operateView.setTag(index);
        navBarPopupOperateView.removeAllViews();
        navBarPopupOperateView.addView(operateView);
        return (INavBarPopupView) operateView;
    }

    public void setNavBarViewHeight(int navBarViewHeight) {
        this.navBarViewHeight = navBarViewHeight;
    }
    private int getNavBarViewHeight() {
        return navBarViewHeight;
    }

    public void setNavBarItemTitle(String title, int index) {
        if (title == null) {
            title = "";
        }
        try {
            INavBarItemView itemView = listItemView.get(index);
            itemView.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNavBarItemIcon(int resId, int index) {
        try {
            INavBarItemView itemView = listItemView.get(index);
            itemView.setIcon(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isShowNavBarItemIcon(boolean isShow, int index) {
        try {
            INavBarItemView itemView = listItemView.get(index);
            itemView.isShowIcon(isShow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNavBarPopupViewHeight(int height) {
        this.navBarPopupViewHeight = height;
    }

    public int getNavBarPopupViewHeight() {
        return navBarPopupViewHeight;
    }
}
