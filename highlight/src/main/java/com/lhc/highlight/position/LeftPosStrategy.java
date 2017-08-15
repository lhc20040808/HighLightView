package com.lhc.highlight.position;

import android.graphics.RectF;

import com.lhc.highlight.HighLight;

/**
 * 作者：LHC on 2017/6/20 10:52
 * 描述：
 */
public class LeftPosStrategy extends BasePosStrategy {

    public LeftPosStrategy(float offSet) {
        super(offSet);
    }

    @Override
    public void getPos(float rightMargin, float bottomMargin, RectF viewRectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin + viewRectF.width() + offSet;
        marginInfo.topMargin = viewRectF.top;
    }
}
