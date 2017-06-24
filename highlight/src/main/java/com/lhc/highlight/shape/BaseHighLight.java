package com.lhc.highlight.shape;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.lhc.highlight.HighLight;

/**
 * 作者：LHC on 2017/6/20 11:11
 * 描述：
 */
public abstract class BaseHighLight implements IHighLight {
    protected float dx;
    protected float dy;
    protected float blurRadius = 15;//模糊半径

    public BaseHighLight(float offSetX, float offSetY) {
        this.dx = offSetX;
        this.dy = offSetY;
    }

    public BaseHighLight(float offSetX, float offSetY, float blurRadius) {
        this.dx = offSetX;
        this.dy = offSetY;
        this.blurRadius = blurRadius;
    }

    @Override
    public void shape(Bitmap bitmap, HighLight.ViewInfo viewInfo) {
        resetRectF4Shape(viewInfo.rectF, dx, dy);
        drawShape(bitmap, viewInfo);
    }

    private void resetRectF4Shape(RectF viewPosInfoRectF, float dx, float dy) {
        viewPosInfoRectF.inset(dx, dy);
    }

    protected abstract void drawShape(Bitmap bitmap, HighLight.ViewInfo viewInfo);

}
