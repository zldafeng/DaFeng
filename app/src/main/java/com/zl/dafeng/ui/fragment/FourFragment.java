package com.zl.dafeng.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.zl.dafeng.R;
import com.zl.dafeng.ui.base.BaseFragment;


@SuppressLint("ValidFragment")
public class FourFragment extends BaseFragment {
    private String mTitle;

    public static FourFragment getInstance(String title) {
        FourFragment sf = new FourFragment();
        sf.mTitle = title;
        return sf;
    }

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

    }
}