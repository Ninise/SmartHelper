package com.ninise.smarthelper.core;

import android.graphics.Bitmap;

import com.ninise.smarthelper.model.BitmapMatrix;

import java.io.IOException;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public interface IUtils {

    BitmapMatrix arrayFromBitmap(Bitmap source);
    int[] convert2DtoVector(int[][] arr);
    int[][] convert1Dto2D(int[] array, int length);
    byte[] toByteArray(int[] data);
    int[] toIntArray(byte[] data) throws IOException;

}
