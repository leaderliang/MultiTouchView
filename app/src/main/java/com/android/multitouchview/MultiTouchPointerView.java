package com.android.multitouchview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * MultiTouchPointerView
 *
 * 多指触控场景
 *
 * 类似于接力棒，列表滑动的 场景
 *
 * 规则：
 * 在这里 默认把抬起手指的 index 交给 剩下里面最大 的一个
 *
 * @author liangyanqiao
 */
public class MultiTouchPointerView extends View {

    private static final float IMAGE_WIDTH = Utils.dpToPx(300);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    /**
     * 初始坐标
     */
    private float downX;
    private float downY;
    /**
     * 偏移
     */
    float offsetX;
    float offsetY;
    /**
     * 记录图片的初始位置
     */
    float originalOffsetX;
    float originalOffsetY;
    /**
     * 追踪的 id
     */
    int trackingPointerId;

    public MultiTouchPointerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //根据 index 获取 id
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;

                break;
            case MotionEvent.ACTION_MOVE:
                // 移动时候知道追踪的 id ，不知道此刻的 index 是多少，需要转换
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = originalOffsetX + event.getX(index) - downX;
                offsetY = originalOffsetY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // 只记录 pointer_down 的话 会在下次移动时候找不到手指 崩溃掉
                int actionIndex = event.getActionIndex();
                // 对某个 id 进行追踪了
                // 获取新按下手指的 id
                trackingPointerId = event.getPointerId(actionIndex);
                // 重新记录一下初始值，保证新手指操作时 不跳  闪
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                //获取抬起手指 id
                int actionId = event.getPointerId(actionIndex);
                // 如果抬起手指 id 就是我们追踪的 手指 id（是不是活跃的 id   ）
                if(actionId == trackingPointerId){
                    int newIndex;
                    // 在这里 默认把抬起手指的 index 交给 剩下里面最大 的一个。 找到新目标
                    // 如果抬起的手指是最大一个的时候
                    if(actionIndex == event.getPointerCount() - 1){
                        newIndex = event.getPointerCount() - 2;
                    }else{
                        newIndex = event.getPointerCount() - 1;
                    }
                    // 新的 trackingPointerId
                    trackingPointerId = event.getPointerId(newIndex);
                    // 重新记录一下初始值，保证新手指操作时 不跳  闪
                    downX = event.getX(actionIndex);
                    downY = event.getY(actionIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }

                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, offsetX, offsetY, mPaint);
    }


}