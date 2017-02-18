package com.zl.dafeng.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zl.dafeng.R;
import com.zl.dafeng.bo.model.BelleModel;
import com.zl.dafeng.dafeng.Constant;
import com.zl.dafeng.novate.BaseSubscriber;
import com.zl.dafeng.novate.Novate;
import com.zl.dafeng.novate.Throwable;
import com.zl.dafeng.ui.adapter.CommentAdapter;
import com.zl.dafeng.ui.adapter.GirlIconAdapter;
import com.zl.dafeng.ui.base.BaseActivity;
import com.zl.dafeng.ui.widgetview.RatingBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class GirlDetailActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.Rv_icon)
    RecyclerView RvIcon;
    @BindView(R.id.img_sex)
    SimpleDraweeView imgSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_constellation)
    TextView tvConstellation;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.swipe_target)
    RecyclerView RvComment;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
//    @BindView(R.id.tv_nickname)
//    TextView tvNickname;
//    @BindView(R.id.tv_adress)
//    TextView tvAdress;
//    @BindView(R.id.tv_time)
//    TextView tvTime;
//    @BindView(R.id.tv_bardian_sign)
//    TextView tvBardianSign;

    private GirlIconAdapter girlIconAdapter;
    private CommentAdapter commentAdapter;
    private List<BelleModel.ShowapiResBodyBean.NewslistBean> NewslistBeanList = new ArrayList<BelleModel.ShowapiResBodyBean.NewslistBean>();
    private int PAGE_INDEX = 1;

    private int PAGE_SIZE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_girl_detail;
    }

    @Override
    protected void initialize() {
        // 标题设置
        leftText.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(R.mipmap.back);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        leftText.setCompoundDrawables(drawable, null, null, null);
        leftText.setText("小乔");
        // 头像加载
        RvIcon.setLayoutManager(new GridLayoutManager(GirlDetailActivity.this, 4));
        girlIconAdapter = new GirlIconAdapter(GirlDetailActivity.this, NewslistBeanList);
        View emptyImg = getLayoutInflater().inflate(R.layout.empty_img, (ViewGroup) RvIcon.getParent(), false);
        girlIconAdapter.setEmptyView(emptyImg);
        RvIcon.setAdapter(girlIconAdapter);

        getBellePicList();
        // 评论加载
        RvComment.setLayoutManager(new LinearLayoutManager(GirlDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        commentAdapter = new CommentAdapter(GirlDetailActivity.this, NewslistBeanList);
        // 设置下拉加载监听
        swipeToLoadLayout.setOnLoadMoreListener(GirlDetailActivity.this);
        //添加分割线
        RvComment.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(GirlDetailActivity.this)
                        .color(GirlDetailActivity.this.getResources().getColor(R.color.screen_background_color))
                        .sizeResId(R.dimen.divider)
                        .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                        .build());
        // 加载空布局
        View emptyComment = getLayoutInflater().inflate(R.layout.empty_text, (ViewGroup) RvIcon.getParent(), false);
        commentAdapter.setEmptyView(emptyComment);
        // 添加头部
        View headerView = getLayoutInflater().inflate(R.layout.recycview_head_girl_detail, (ViewGroup) RvComment.getParent(), false);
//        View headerView = getHeaderView(0, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                commentAdapter.addHeaderView(getHeaderView(1, getRemoveHeaderListener()), 0);
//            }
//        });
        commentAdapter.addHeaderView(headerView);

        RvComment.setAdapter(commentAdapter);
        RvComment.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        ViewHolder viewHolder = new ViewHolder(headerView);
        viewHolder.tvNickname.setText("大乔");
    }
    private void refreshOrLoad() {
        if (swipeToLoadLayout == null) {
            return;
        }
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onLoadMore() {
//        PAGE_INDEX += 1;
        swipeToLoadLayout.setLoadingMore(true);
//        getBellePicList();
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 2000);
    }
    @Override
    public void onRefresh() {

    }
    @OnClick(R.id.left_text)
    public void onClick() {
        finish();
    }

    /**
     * 获取美女图片
     */
    private void getBellePicList() {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("showapi_appid", "31566");
        parameters.put("showapi_sign", "3f16db6f4f82413ba878e772f8788145");
        parameters.put("showapi_timestamp", "");
        parameters.put("showapi_sign_method", "");
        parameters.put("showapi_res_gzip", "");
        parameters.put("num", PAGE_SIZE + "");
        parameters.put("page", PAGE_INDEX + "");
        parameters.put("rand", "1");

        Novate novate = new Novate.Builder(GirlDetailActivity.this)
                .connectTimeout(8)
                .baseUrl(Constant.COMMONURL)
                .addParameters(parameters)
                .addLog(true)
                .build();
        novate.post(Constant.get_bellePicList, parameters, new BaseSubscriber<ResponseBody>(GirlDetailActivity.this) {

            @Override
            public void onError(Throwable e) {
                Log.e("OkHttp", e.getMessage());
                Toast.makeText(GirlDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String jstr = new String(responseBody.bytes());
                    BelleModel belleModel = new BelleModel();
                    belleModel = new Gson().fromJson(jstr, BelleModel.class);
                    for (int i = 0; i < belleModel.getShowapi_res_body().getNewslist().size(); ++i) {
                        NewslistBeanList.add(belleModel.getShowapi_res_body().getNewslist().get(i));
                    }
                    girlIconAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    static class ViewHolder {
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_adress)
        TextView tvAdress;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_bardian_sign)
        TextView tvBardianSign;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
