package com.lhc.highlight.shape;

import android.graphics.Bitmap;

import com.lhc.highlight.HighLight;

/**
 * 作者：LHC on 2017/6/20 11:07
 * 描述：
 */
public interface IHighLight {
    void shape(Bitmap bitmap, HighLight.ViewInfo viewInfo);
}
