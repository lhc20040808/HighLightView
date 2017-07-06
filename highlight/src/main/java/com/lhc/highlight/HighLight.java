package com.lhc.highlight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.lhc.highlight.position.IPositionStrategy;
import com.lhc.highlight.shape.IHighLight;
import com.lhc.highlight.shape.RectLightShape;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LHC on 2017/6/20 09:52
 * 描述：高亮布局辅助类
 */
public class HighLight implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final int DISMISS = 0;
    public static final int SHOW = 1;

    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";
    private boolean isShowAfterInit = false;
    private View mAnchor;
    private Context mContext;
    private List<ViewInfo> mHighLightViews = new ArrayList<>();
    private int maskColor = 0xCC000000;
    private boolean isNext;
    private HighLightView mHighLightView;
    private OnCloseListener onCloseListener;
    private int status = DISMISS;


    public HighLight(Context context) {
        this.mContext = context;
        if (mContext instanceof Activity) {
            mAnchor = ((Activity) mContext).findViewById(android.R.id.content);
            registerGlobalLayoutListener();
        }
    }

    @Override
    public void onGlobalLayout() {
        unRegisterGlobalLayoutListener();
        calViewLocation();
        if (isShowAfterInit) {
            showHighLight();
        }
    }

    private void calViewLocation() {
        for (ViewInfo viewInfo : mHighLightViews) {
            View parent = viewInfo.parent;
            viewInfo.rectF = new RectF(getLocationInWindow(parent, viewInfo.highLightView));
            MarginInfo marginInfo = new MarginInfo();
            viewInfo.posStrategy.getPos(parent.getWidth() - viewInfo.rectF.right, parent.getHeight() - viewInfo.rectF.height()
                    , viewInfo.rectF, marginInfo);
            viewInfo.marginInfo = marginInfo;
        }
    }

    public HighLight addHighLight(int viewId, int decorLayoutId, IPositionStrategy posStrategy, IHighLight highLightShape) {
        checkAnchor();
        View parent = mAnchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, posStrategy, highLightShape);
        return this;
    }

    public HighLight addHighLight(View view, int decorLayoutId, IPositionStrategy posStrategy, IHighLight highLightShape) {
        checkAnchor();
        View parent = mAnchor;

        ViewInfo viewInfo = new ViewInfo();
        viewInfo.parent = parent;
        viewInfo.decorLayoutId = decorLayoutId;
        viewInfo.highLightView = view;
        viewInfo.posStrategy = posStrategy;
        viewInfo.highLight = highLightShape == null ? new RectLightShape(0, 0) : highLightShape;

        mHighLightViews.add(viewInfo);
        return this;
    }

    public HighLight anchor(View anchor) {
        this.mAnchor = anchor;
        registerGlobalLayoutListener();
        return this;
    }

    public HighLight maskColor(int color) {
        this.maskColor = color;
        return this;
    }

    public HighLight show() {
        this.isShowAfterInit = true;
        return this;
    }

    public HighLight showInSequence() {
        this.isNext = true;
        return this;
    }

    private void registerGlobalLayoutListener() {
        mAnchor.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void unRegisterGlobalLayoutListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mAnchor.getViewTreeObserver()
                    .removeOnGlobalLayoutListener(this);
        }
    }

    public void showHighLight() {
        if (mHighLightViews.isEmpty()) {
            return;
        }

        status = SHOW;

        HighLightView highLightView = new HighLightView(mContext, this, mHighLightViews, maskColor, isNext);
        highLightView.setId(R.id.high_light_view);
        if (mAnchor instanceof FrameLayout) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) mAnchor).addView(highLightView, lp);
        } else {
            FrameLayout frameLayout = new FrameLayout(mContext);
            ViewGroup parent = (ViewGroup) mAnchor.getParent();
            parent.removeView(mAnchor);
            parent.addView(frameLayout, mAnchor.getLayoutParams());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mAnchor, lp);
            frameLayout.addView(highLightView);
        }

        highLightView.addViewForTips();

        highLightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNext) {
                    mHighLightView.addViewForTips();
                } else {
                    remove();
                }
            }
        });

        this.mHighLightView = highLightView;
    }

    public void remove() {
        if (mHighLightView == null) {
            return;
        }

        ViewGroup viewGroup = (ViewGroup) mHighLightView.getParent();
        viewGroup.removeView(mHighLightView);
        mHighLightView = null;

        status = DISMISS;

        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
    }

    private void checkAnchor() {
        if (mAnchor == null) {
            throw new IllegalArgumentException("anchor can not be null.");
        }
    }

    private Rect getLocationInWindow(View parent, View child) {
        if (parent == null || child == null) {
            throw new IllegalArgumentException("parent or child cannot be null.");
        }

        View decorView = null;
        Context context = child.getContext();
        if (context instanceof Activity) {
            decorView = ((Activity) context).getWindow().getDecorView();
        }

        Rect result = new Rect();
        Rect tmpRect = new Rect();

        View tmp = child;

        if (child == parent) {
            child.getHitRect(result);
            return result;
        }
        while (tmp != decorView && tmp != parent) {
            tmp.getHitRect(tmpRect);
            if (!tmp.getClass().equals(FRAGMENT_CON)) {
                result.left += tmpRect.left;
                result.top += tmpRect.top;
            }
            tmp = (View) tmp.getParent();

            if (tmp != null && tmp.getParent() != null && (tmp.getParent() instanceof ViewPager)) {
                tmp = (View) tmp.getParent();
            }
        }
        result.right = result.left + child.getMeasuredWidth();
        result.bottom = result.top + child.getMeasuredHeight();
        return result;
    }

    public static class ViewInfo {
        public View parent;
        public View highLightView;
        public int decorLayoutId;
        public RectF rectF;
        public IPositionStrategy posStrategy;
        public MarginInfo marginInfo;
        public IHighLight highLight;
    }

    public static class MarginInfo {
        public float topMargin;
        public float leftMargin;
        public float rightMargin;
        public float bottomMargin;
    }

    interface OnCloseListener {
        void onClose();
    }

    public void setOnCloseListener(OnCloseListener listener) {
        this.onCloseListener = listener;
    }

    public int getStatus() {
        return status;
    }
}
