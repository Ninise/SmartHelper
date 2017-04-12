package com.ninise.smarthelper.core;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

import android.util.Log;

/**
 * p - original pixels
 * xc - XOR counter
 * MA - match accuracy
 *
 * MA = ((p - xc) / p) * 100
 *
 * */

public class XORRunner {

    private final String XOR = "XOR";

    private static XORRunner runner = new XORRunner();
    private int mLength;

    private int[][] mOriginalPixels;
    private int[][] mSamplePixels;

    public static XORRunner build() {return runner;};

    public XORRunner setOriginalPixels(int[][] pixels) {
        this.mOriginalPixels = pixels;
        this.mLength = pixels.length;
        return this;
    }

    public XORRunner setSamplePixels(int[][] pixels) {
        this.mSamplePixels = pixels;
        return this;
    }

    public int getMatchAccuracy() {
        double accuracy;
        int xc = 0;
        int p = mLength * mLength;

        for (int i = 0;  i < mLength; i++) {
            for (int j = 0; j < mLength; j++) {
                xc += xor(mOriginalPixels[i][j], mSamplePixels[i][j]);
            }
        }

        accuracy = ((double) (p - xc) / p) * 100;

        Log.d(XOR, "p: " + p + "; xc: " + xc + "; accuracy: " + accuracy + ";");

        return (int) accuracy;
    }

    private int xor(int a, int b) {
        return a == b ? 0 : 1;
    }

}
