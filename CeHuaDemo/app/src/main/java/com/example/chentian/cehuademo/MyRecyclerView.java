package com.example.chentian.cehuademo;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by tangyangkai on 16/6/12.
 */
public class MyRecyclerView extends RecyclerView {


    private int maxLength;
    private int mStartX = 0;
    private LinearLayout itemLayout;
    private int pos;
    private Rect mTouchFrame;
    private int xDown, xMove, yDown, yMove,mTouchSlop;
    private CeHuaView ceHuaView;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //滑动的最大距离
        maxLength = dipToPx(context,100);
        //滑动到最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                xDown = x;
                yDown = y;
                //通过点击的坐标计算当前的position
                int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                Rect frame = mTouchFrame;
                if (frame == null) {
                    mTouchFrame = new Rect();
                    frame = mTouchFrame;
                }
                int count = getChildCount();
                for (int i = count - 1; i >= 0; i--) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(frame);
                        if (frame.contains(x, y)) {
                            pos = mFirstPosition + i;
                        }
                    }
                }
                //通过position得到item的viewHolder
                View view = getChildAt(pos - mFirstPosition);
                MyViewHolder viewHolder = (MyViewHolder) getChildViewHolder(view);
                itemLayout = viewHolder.layout;
                ceHuaView = (CeHuaView) itemLayout.findViewById(R.id.item_cehua);
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                if (Math.abs(dy) < mTouchSlop * 2 && Math.abs(dx) > mTouchSlop) {
                    int scrollX = itemLayout.getScrollX();
                    int newScrollX = mStartX - x;
                    if (newScrollX < 0 && scrollX <= 0) {
                        newScrollX = 0;
                    } else if (newScrollX > 0 && scrollX >= maxLength) {
                        newScrollX = 0;
                    }

                    ceHuaView.controllAnimation(scrollX,maxLength);
                    itemLayout.scrollBy(newScrollX, 0);

                }
            }
            break;
            case MotionEvent.ACTION_UP:{

            }
            break;

        }
        mStartX = x;
        return super.onTouchEvent(event);
    }

}
