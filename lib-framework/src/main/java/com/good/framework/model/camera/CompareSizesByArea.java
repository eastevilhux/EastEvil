package com.good.framework.model.camera;

import android.util.Size;

import java.util.Comparator;


public class CompareSizesByArea implements Comparator<Size> {
    @Override
    public int compare(Size lhs, Size rhs)
    {
        // 强转为long保证不会发生溢出
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                (long) rhs.getWidth() * rhs.getHeight());
    }
}
