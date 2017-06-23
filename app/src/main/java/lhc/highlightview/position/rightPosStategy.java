package lhc.highlightview.position;

import android.graphics.RectF;

import lhc.highlightview.HighLight;

/**
 * 作者：LHC on 2017/6/20 10:56
 * 描述：
 */
public class rightPosStategy extends BasePosStrategy {

    public rightPosStategy(float offSet) {
        super(offSet);
    }

    @Override
    public void getPos(float rightMargin, float bottomMargin, RectF viewRectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = viewRectF.right + offSet;
        marginInfo.topMargin = viewRectF.top;
    }
}
