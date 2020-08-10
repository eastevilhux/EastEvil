package com.good.framework.model.camera;

import android.graphics.ImageFormat;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;

import java.util.Arrays;
import java.util.Collections;

public class Fuck {

    public static Size getSize(StreamConfigurationMap map ){
        return Collections.max(
                Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                new CompareSizesByArea());
    }
}
