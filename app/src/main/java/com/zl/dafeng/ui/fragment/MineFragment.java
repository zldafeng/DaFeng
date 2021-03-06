package com.zl.dafeng.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.bugly.beta.Beta;
import com.zl.dafeng.R;
import com.zl.dafeng.service.interfaces.AppBarStateChangeListener;
import com.zl.dafeng.ui.activity.LoginActivity;
import com.zl.dafeng.ui.activity.MineActivity;
import com.zl.dafeng.ui.base.BaseFragment;
import com.zl.dafeng.ui.widgetview.WindSnowView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@SuppressLint("ValidFragment")
public class MineFragment extends BaseFragment {

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.my_head)
    SimpleDraweeView myHead;
    @BindView(R.id.collapsing_layout_fragment_mine)
    CollapsingToolbarLayout collapsingLayoutFragmentMine;
    @BindView(R.id.fragment_mine_toolbar)
    Toolbar fragmentMineToolbar;
    @BindView(R.id.fragment_mine_toolBar_title)
    TextView fragmentMineToolBarTitle;
    @BindView(R.id.fragment_mine_right_text)
    TextView fragmentMineRightText;
    @BindView(R.id.windsnow_fly)
    WindSnowView windsnowFly;
    @BindView(R.id.text_collect)
    TextView textCollect;
    @BindView(R.id.text_recommend)
    TextView textRecommend;
    @BindView(R.id.text_prefecture)
    TextView textPrefecture;
    @BindView(R.id.text_update)
    TextView textUpdate;
    @BindView(R.id.text_aboutme)
    TextView textAboutme;

    private String mShareLink = "github";
    private boolean isSnowOrMoney = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
//
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingLayoutFragmentMine.setExpandedTitleColor(getResources().getColor(R.color.toolbar_color));//设置还没收缩时状态下字体颜色
        collapsingLayoutFragmentMine.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        // CoordinatorLayout监听展开与折叠
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {

                    //展开状态
                    collapsingLayoutFragmentMine.setTitle("展开");
                    fragmentMineToolBarTitle.setAlpha(0);
                    fragmentMineRightText.setAlpha(0);
                    windsnowFly.setImages(R.mipmap.snowflake);

                    windsnowFly.startAnimation();
                } else if (state == State.COLLAPSED) {

                    //折叠状态
//                    collapsingLayoutFragmentMine.setTitle("折叠");
                    fragmentMineToolBarTitle.setAlpha(1);
                    fragmentMineRightText.setAlpha(1);
                    windsnowFly.stopAnimationDely();
                } else {

                    //中间状态
//                    collapsingLayoutFragmentMine.setTitle("中间");
                    fragmentMineToolBarTitle.setAlpha(0.2f);
                    fragmentMineRightText.setAlpha(0.2f);
                }
            }
        });
        /**
         * CollapsingToolbarLayout 与 Toolbar 的设置会有冲突？
         * Toolbar.setTitle() 之后 CollapsingToolbarLayout.setTitle() 没效果。
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.my_head, R.id.fragment_mine_right_text, R.id.windsnow_fly, R.id.text_collect, R.id.text_recommend, R.id.text_prefecture,
            R.id.text_update, R.id.text_aboutme})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_head:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.fragment_mine_right_text:
                startActivity(new Intent(getActivity(), MineActivity.class));
                break;
            case R.id.windsnow_fly:
                if (isSnowOrMoney == false) {
                    windsnowFly.setImages(R.mipmap.ico_gold_money);
                    isSnowOrMoney = true;
                } else {
                    windsnowFly.setImages(R.mipmap.snowflake);
                    isSnowOrMoney = false;
                }
                break;
            case R.id.text_collect:

//                CrashReport.testJavaCrash();
                break;
            case R.id.text_recommend:
//                CrashReport.testANRCrash();
                break;
            case R.id.text_prefecture:
//                CrashReport.testNativeCrash();
                break;
            case R.id.text_update:
                Beta.checkUpgrade();
                break;
            case R.id.text_aboutme:
                startActivity(new Intent(getActivity(), MineActivity.class));
                break;
        }
    }
}