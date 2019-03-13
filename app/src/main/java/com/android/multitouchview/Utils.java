package com.android.multitouchview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

public class Utils {

    /**
     * 把Android系统中的非标准度量尺寸转变为标准度量尺寸 (Android系统中的标准尺寸是px, 即像素)
     *
     *  Android系统中的尺寸单位有: 
     * 标准单位: px (px是安卓系统内部使用的单位, dp是与设备无关的尺寸单位 )
     * 非标准单位: dp, in, mm, pt, sp
     *
     * TypedValue.applyDimension()方法的功能就是把非标准尺寸转换成标准尺寸, 如: 
     *
     * dp->px:  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
     * in->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 20, context.getResources().getDisplayMetrics());
     * mm->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 20, context.getResources().getDisplayMetrics());
     * pt->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 20, context.getResources().getDisplayMetrics());
     * sp->px: TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics());
     *
     * @param dp
     * @return
     */
    public static float dpToPx(float dp) {
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getAvatar(Resources res, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable.xiyou, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(res, R.drawable.xiyou, options);
    }
}
