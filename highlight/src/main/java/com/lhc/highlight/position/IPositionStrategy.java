package com.lhc.highlight.position;

import android.graphics.RectF;

import com.lhc.highlight.HighLight;

/**
 * 作者：LHC on 2017/6/20 10:48
 * 描述：
 */
public interface IPositionStrategy {
    void getPos(float rightMargin, float bottomMargin, RectF viewRectF, HighLight.MarginInfo marginInfo);
}
