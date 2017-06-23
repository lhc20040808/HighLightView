package lhc.highlightview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * 作者：LHC on 2017/6/20 09:52
 * 描述：
 */
public class HighLightView extends FrameLayout {

    private List<HighLight.ViewInfo> mHighLightViews;
    private int maskColor;
    private Paint mPaint;
    private Bitmap mMaskBitmap;
    private Bitmap mLightBitmap;
    private boolean isNext;
    private int nowPos;
    private HighLight highLight;

    public HighLightView(Context context, HighLight highLight, List<HighLight.ViewInfo> mHighLightViews, int maskColor, boolean isNext) {
        super(context);
        this.mHighLightViews = mHighLightViews;
        this.maskColor = maskColor;
        this.isNext = isNext;
        this.highLight = highLight;
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),//
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed || isNext) {
            buildMask();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            canvas.drawBitmap(mMaskBitmap, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recyleBitmap(mMaskBitmap);
        recyleBitmap(mLightBitmap);
    }

    private void buildMask() {
        recyleBitmap(mMaskBitmap);
        mMaskBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(mMaskBitmap);
        canvas.drawColor(maskColor);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        recyleBitmap(mLightBitmap);
        mLightBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_4444);

        if (isNext) {
            HighLight.ViewInfo viewInfo = mHighLightViews.get(nowPos);
            addTipShape(mLightBitmap,viewInfo);
            nowPos++;
        } else {
            for (HighLight.ViewInfo viewInfo : mHighLightViews) {
                addTipShape(mLightBitmap, viewInfo);
            }
        }

        canvas.drawBitmap(mLightBitmap, 0, 0, mPaint);
    }

    private void updateTipPos() {
        if (isNext) {
            if (nowPos < 0 || nowPos > mHighLightViews.size()) {
                nowPos = 0;
            } else if (nowPos == mHighLightViews.size() - 1) {

            }
        } else {
            for (int i = 0, n = getChildCount(); i < n; i++) {
                View view = getChildAt(i);
                HighLight.ViewInfo viewPosInfo = mHighLightViews.get(i);

                LayoutParams lp = (LayoutParams) buildLayoutParams(view, viewPosInfo);
                if (lp == null) continue;
                view.setLayoutParams(lp);
            }
        }
    }

    private void recyleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    private void addTipShape(Bitmap bitmap, HighLight.ViewInfo viewInfo) {
        viewInfo.highLight.shape(bitmap, viewInfo);
    }

    public void addViewForTips() {
        if (isNext) {
            addNextView();
        } else {
            for (HighLight.ViewInfo viewInfo : mHighLightViews) {
                addViewForEveryTip(viewInfo);
            }
        }
    }

    private void addNextView() {
        if (nowPos < 0 || nowPos > mHighLightViews.size()) {
            nowPos = 0;
        } else if (nowPos == mHighLightViews.size() - 1) {
            highLight.remove();
            highLight = null;
            return;
        }
        HighLight.ViewInfo viewInfo = mHighLightViews.get(nowPos);
        removeAllViews();
        addViewForEveryTip(viewInfo);
//        nowPos++;
    }

    private void addViewForEveryTip(HighLight.ViewInfo viewInfo) {
        View view = LayoutInflater.from(getContext()).inflate(viewInfo.decorLayoutId, this, false);
        view.setId(viewInfo.decorLayoutId);

        ViewGroup.LayoutParams params = buildLayoutParams(view, viewInfo);
        addView(view, params);
    }

    private ViewGroup.LayoutParams buildLayoutParams(View view, HighLight.ViewInfo viewInfo) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();

        if (lp.leftMargin == (int) viewInfo.marginInfo.leftMargin &&
                lp.topMargin == (int) viewInfo.marginInfo.topMargin &&
                lp.rightMargin == (int) viewInfo.marginInfo.rightMargin &&
                lp.bottomMargin == (int) viewInfo.marginInfo.bottomMargin) return null;

        lp.leftMargin = (int) viewInfo.marginInfo.leftMargin;
        lp.topMargin = (int) viewInfo.marginInfo.topMargin;
        lp.rightMargin = (int) viewInfo.marginInfo.rightMargin;
        lp.bottomMargin = (int) viewInfo.marginInfo.bottomMargin;

        if (lp.rightMargin != 0) {
            lp.gravity = Gravity.RIGHT;
        } else {
            lp.gravity = Gravity.LEFT;
        }

        if (lp.bottomMargin != 0) {
            lp.gravity |= Gravity.BOTTOM;
        } else {
            lp.gravity |= Gravity.TOP;
        }

        return lp;
    }


}
