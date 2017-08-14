package com.lhc.highlight.position;

import android.graphics.RectF;

import com.lhc.highlight.HighLight;

/**
 * 作者：LHC on 2017/6/20 10:54
 * 描述：
 */
public class topCenterPosStrategy extends BasePosStrategy {

    public topCenterPosStrategy(float offSet) {
        super(offSet);
    }

    @Override
    public void getPos(float rightMargin, float bottomMargin, RectF viewRectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = (viewRectF.right + rightMargin) / 2;
        marginInfo.bottomMargin = bottomMargin + viewRectF.height() + offSet;
    }
}
