package com.project.upload.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.upload.R;
import com.project.upload.adapter.MyAdapter;
import com.project.upload.bean.GiftBean;
import com.project.upload.mvp.presenter.IUserPresenter;
import com.project.upload.mvp.presenter.impl.UserPresenter;
import com.project.upload.mvp.view.IUserView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IUserView {

    private Button btnUpload;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private IUserPresenter userPresenter;
    private int pager;
    private LinearLayoutManager mLayoutManager;
    private List<String> dataList;
    private MyAdapter mAdapter;
    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        userPresenter = new UserPresenter(this);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UploadActivity.class));
            }
        });
        initData();
        initView();
        getData();
    }

    /**
     * 最初的数据
     */
    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("这是第" + (i + 1) + "个子项");
        }
    }

    /**
     * 上拉加载更多的数据
     */
    private void loadMoreData() {
        List<String> moreList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            moreList.add("加载更多的数据" + (i + 1));
        }
        mAdapter.addMoreData(moreList);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        //上面是对srl的各种设置
        //圆圈背景色
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //加载变幻色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //如果确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        //
        mAdapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(mAdapter);
        //srl下拉刷新,设置setOnRefreshListener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> newDatas = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            int index = i + 1;
                            newDatas.add("new item" + index);
                        }
                        //这里获取刷新的数据，传给adapter
                        mAdapter.refreshItem(newDatas);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "更新了3条数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        //rv要使用上啦加载更多。需要在activity中像下面这样做就行了。
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //设置foot view 文字变成正在加载中
                            mAdapter.changeMoreStatus(MyAdapter.LOADING_MORE);
                            loadMoreData();
                        }
                    }, 1500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void getData() {
        pager = 1;
        userPresenter.startLoad(pager);
    }

    @Override
    public void dataResult(GiftBean data) {
        List<GiftBean.ListBean> dataList = data.getList();
        Log.i("TAG", "dataResult: " + dataList.toString());
    }

}
