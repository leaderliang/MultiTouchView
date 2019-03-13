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
 *
 * 单指触控
 * @author liangyanqiao
 */
public class MultiTouchView extends View {

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

    public MultiTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;

                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = originalOffsetX + event.getX() - downX;
                offsetY = originalOffsetY + event.getY() - downY;
                invalidate();
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