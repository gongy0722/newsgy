package com.crc.news.utils;

import android.content.Context;

/**
 * Created by gongyuan on 2017/3/30.
 */

public class DensityUtils {

    // dpi转px
    public static int dpi2px(Context ctx, int dpi){
        return (int)(ctx.getResources().getDisplayMetrics().density * dpi);
    }

    public static int px2dpi(Context ctx,int px){
        return (int)(px / ctx.getResources().getDisplayMetrics().density);
    }
}
