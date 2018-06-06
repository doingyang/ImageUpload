package com.project.upload.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * @author ydy
 */
public class DragScrollView extends ScrollView implements View.OnTouchListener {

    /**
     * 记录首次按下位置
     */
    private float mFirstPosition = 0;
    /**
     * 是否正在放大
     */
    private Boolean mScaling = false;

    private View dropZoomView;
    private int dropZoomViewWidth;
    private int dropZoomViewHeight;

    public DragScrollView(Context context) {
        super(context);
    }

    public DragScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        if (getChildAt(0) != null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                dropZoomView = vg.getChildAt(0);
                setOnTouchListener(this);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (dropZoomViewWidth <= 0 || dropZoomViewHeight <= 0) {
            dropZoomViewWidth = dropZoomView.getMeasuredWidth();
            dropZoomViewHeight = dropZoomView.getMeasuredHeight();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //手指离开后恢复图片
                mScaling = false;
                replyImage();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScaling) {
                    if (getScrollY() == 0) {
                        //滑动到顶部
                        // 滚动到顶部时记录位置，否则正常返回
                        mFirstPosition = event.getY();
                    } else if (getChildAt(0).getHeight() - getHeight() == getScrollY()) {
                        //滑动到底部的时候,回弹效果暂时没加进去
                        break;
                    } else {

                        break;
                    }
                }
                // 滚动距离乘以一个系数
                int distance = (int) ((event.getY() - mFirstPosition) * 0.6);
                if (distance < 0) {
                    // 当前位置比记录位置要小，正常返回
                    break;
                }
                // 处理放大
                mScaling = true;
                setZoom(1 + distance);
                return true;
                // 返回true表示已经完成触摸事件，不再处理
            default:
                break;
        }
        return false;
    }

    /**
     * 回弹动画 (属性动画)
     */
    public void replyImage() {
        final float distance = dropZoomView.getMeasuredWidth() - dropZoomViewWidth;

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration((long) (distance * 0.7));

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                setZoom(distance - ((distance) * cVal));
            }
        });
        anim.start();

    }

    /**
     * 缩放
     * @param s s
     */
    public void setZoom(float s) {
        if (dropZoomViewHeight <= 0 || dropZoomViewWidth <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = dropZoomView.getLayoutParams();
        lp.width = (int) (dropZoomViewWidth + s);
        lp.height = (int) (dropZoomViewHeight * ((dropZoomViewWidth + s) / dropZoomViewWidth));
        dropZoomView.setLayoutParams(lp);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(DragScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    private ScrollViewListener scrollViewListener = null;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

}
