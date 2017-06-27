package com.lhc.highlight.shape;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

import com.lhc.highlight.HighLight;

/**
 * 作者：LHC on 2017/6/20 11:13
 * 描述：矩形形状
 */
public class RectLightShape extends BaseHighLight {
    private int offSet = 10;
    private float radius;

    public RectLightShape(int offSet, float radius) {
        this(offSet, radius, 0);
    }

    public RectLightShape(int offSet, float radius, float blurRadius) {
        this(offSet, radius, blurRadius, 0, 0);
    }

    public RectLightShape(int offSet, float radius, float blurRadius, float offSetX, float offSetY) {
        super(offSetX, offSetY);
        this.offSet = offSet;
        this.radius = radius;
        this.blurRadius = blurRadius;
    }


    @Override
    protected void drawShape(Bitmap bitmap, HighLight.ViewInfo viewInfo) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        if (blurRadius > 0) {
            paint.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.SOLID));
        }
        viewInfo.rectF.left -= offSet;
        viewInfo.rectF.top -= offSet;
        viewInfo.rectF.right += offSet;
        viewInfo.rectF.bottom += offSet;

        canvas.drawRoundRect(viewInfo.rectF, radius, radius, paint);

        RectF rectF = new RectF(viewInfo.rectF);
        rectF.left -= offSet;
        rectF.top -= offSet;
        rectF.right += offSet;
        rectF.bottom += offSet;

        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 3}, 0));
        canvas.drawRoundRect(rectF, radius, radius, paint);
    }
}
