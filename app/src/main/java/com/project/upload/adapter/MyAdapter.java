package com.project.upload.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.upload.R;

import java.util.List;

/**
 * Author: ydy
 * Created: 2017/4/1 11:15
 * Description:
 */

/**
 * 用到多个holder：使用RecyclerView.ViewHolder
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //普通ItemView
    private static final int TYPE_ITEM = 0;
    //底部FootView
    private static final int TYPE_FOOTER = 1;

    //上拉加载更多状态-默认为0
    private int load_more_status = 0;
    //上拉加载更多
    public static final int PULL_UP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;

    private Context mContext;
    private List<String> data;

    public MyAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.data = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            View item_view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(item_view);
            return itemViewHolder;

        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_footview, parent, false);
            //可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //根据holder的不同设置不同的数据
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvItem.setText(data.get(position));
            holder.itemView.setTag(position);
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                //这个可以使用2个也可使用1个
                case PULL_UP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    load_more_status = PULL_UP_LOAD_MORE;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 底部FooterView布局
     */
    public class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.foot_view_item_tv);
        }
    }

    /**
     * 正常Item布局
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    @Override
    public int getItemCount() {
        if (data == null || data.size() < 1) {
            return 1;
        }
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {//当前位置再加1为总长度--->footer
            return TYPE_FOOTER;
        } else {//否则返回正常的item
            return TYPE_ITEM;
        }
    }

    public void refreshItem(List<String> newData) {
        //下拉刷新的数据：加到头部
        data.addAll(0, newData);
        notifyDataSetChanged();
    }

    public void addMoreData(List<String> moreList) {
        //上拉加载更多的数据：加载到末尾
        data.addAll(moreList);
        notifyDataSetChanged();
    }

    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
