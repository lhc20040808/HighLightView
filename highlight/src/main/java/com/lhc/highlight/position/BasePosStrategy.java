package com.lhc.highlight.position;

/**
 * 作者：LHC on 2017/6/20 10:50
 * 描述：
 */
public abstract class BasePosStrategy implements IPositionStrategy {
    public float offSet;

    public BasePosStrategy(float offSet) {
        this.offSet = offSet;
    }
}
