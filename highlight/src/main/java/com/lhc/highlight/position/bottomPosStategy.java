package com.lhc.highlight.position;

import android.graphics.RectF;

import com.lhc.highlight.HighLight;

/**
 * 作者：LHC on 2017/6/20 10:57
 * 描述：
 */
public class bottomPosStategy extends BasePosStrategy {

    public bottomPosStategy(float offSet) {
        super(offSet);
    }

    @Override
    public void getPos(float rightMargin, float bottomMargin, RectF viewRectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin;
        marginInfo.topMargin = viewRectF.top + viewRectF.height() + offSet;
    }
}
