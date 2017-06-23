package lhc.highlightview.position;

import android.graphics.RectF;

import lhc.highlightview.HighLight;

/**
 * 作者：LHC on 2017/6/20 10:52
 * 描述：
 */
public class leftPosStrategy extends BasePosStrategy {

    public leftPosStrategy(float offSet) {
        super(offSet);
    }

    @Override
    public void getPos(float rightMargin, float bottomMargin, RectF viewRectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin + viewRectF.width() + offSet;
        marginInfo.topMargin = viewRectF.top;
    }
}
