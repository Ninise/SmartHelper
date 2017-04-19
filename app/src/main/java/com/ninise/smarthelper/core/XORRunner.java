package com.ninise.smarthelper.core;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

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


    public static class XORVector {
        private int mLength;

        private int[] mOriginalPixels;
        private int[] mSamplePixels;

        public XORVector setOriginalPixels(int[] pixels) {
            this.mOriginalPixels = pixels;
            this.mLength = pixels.length;
            return this;
        }

        public XORVector setSamplePixels(int[] pixels) {
            this.mSamplePixels = pixels;
            return this;
        }

        public int getMatchAccuracy() {
            double accuracy;
            int xc = 0;
            int p = mLength;

            int c_or = 0;

            for (int i = 0;  i < mLength - 1; i++) {
                c_or += mSamplePixels[i] == 1 ? 1 : 0;


                xc += and(mOriginalPixels[i], mSamplePixels[i]);
            }

            System.out.println("ORIGINAL");

            for (int i = 0; i < mLength - 1; i++) {
                System.out.print("" + mOriginalPixels[i]);
            }

            System.out.println("SAMPLE");

            for (int i = 0; i < mLength - 1; i++) {
                System.out.print("" + mSamplePixels[i]);
            }

//            accuracy = ((double) (p - xc) / p) * 100;
            accuracy = ((double) xc / c_or) * 100;

            System.out.println("accuracy = " + ((double) xc / c_or));
            System.out.println("c_or = " + c_or);
            System.out.println("p = " + p + "; xc = " + xc);

            return (int) accuracy;
        }

    }

    public static class XORMatrix {

        private int mLength;

        private int[][] mOriginalPixels;
        private int[][] mSamplePixels;

        public XORMatrix setOriginalPixels(int[][] pixels) {
            this.mOriginalPixels = pixels;
            this.mLength = pixels.length;
            return this;
        }

        public XORMatrix setSamplePixels(int[][] pixels) {
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

            // p = 4000
            // xc = 400
            // mp = 3600
            // ma = 3600 / 4000 = 0.9

            accuracy = ((double) (p - xc) / p) * 100;

            System.out.println("accuracy = " + ((double) (p - xc) / p));
            System.out.println("p = " + p + "; xc = " + xc);
            System.out.println("accuracy = " + ((double) (p - xc) / p));

            return (int) accuracy;
        }

    }

    public static XORVector vector() {return new XORVector();};

    public static XORMatrix matrix() {return new XORMatrix();};

    private static int xor(int a, int b) {
        return a == b ? 0 : 1;
    }

    private static int and(int a, int b) {
        return (a & b);
    }
}
