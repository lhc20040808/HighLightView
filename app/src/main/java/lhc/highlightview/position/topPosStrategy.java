package lhc.highlightview.position;

import android.graphics.RectF;

import lhc.highlightview.HighLight;

/**
 * 作者：LHC on 2017/6/20 10:54
 * 描述：
 */
public class topPosStrategy extends BasePosStrategy {

    public topPosStrategy(float offSet) {
        super(offSet);
    }

    @Override
    public void getPos(float rightMargin, float bottomMargin, RectF viewRectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = viewRectF.right - viewRectF.width() / 2;
        marginInfo.bottomMargin = bottomMargin + viewRectF.height() + offSet;
    }
}
