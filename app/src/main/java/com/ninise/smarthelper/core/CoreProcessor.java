package com.ninise.smarthelper.core;

import android.graphics.Bitmap;
import android.support.v4.util.Pair;

import com.ninise.smarthelper.model.BitmapMatrix;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class CoreProcessor implements IUtils {


    private Bitmap originalBitmap;
    private int[][] matrix;
    private int[][] submatrix;
    private int[][] compressedSubMatrix;
    private byte[] compressedSubByteVector;
    private int[] compressedSubVector;
    private int compressValue = 64;

    public CoreProcessor addCompressValue(int value) {
        compressValue = value;
        return this;
    }

    public CoreProcessor addBitmap(Bitmap bitmap) {
        originalBitmap = bitmap;
        return this;
    }

    public void run() {
        if (originalBitmap == null) throw new IllegalArgumentException("bitmap isn't be null");

        // GET FROM VIEW BITMAP AND CONVERT TO INT[][]
        BitmapMatrix bitmapMatrix = arrayFromBitmap(originalBitmap);
        matrix = bitmapMatrix.getMatrix();

        // GET TWO VECTORS X and Y - SUMS OF MATRIX PIXELS
        Pair<int[], int[]> pair = MatrixUtils.getInstance().getArr(matrix, bitmapMatrix.getWidth(), bitmapMatrix.getHeight());
        int[] xArr = pair.first;
        int[] yArr = pair.second;

        // CUT SUB-MATRIX FROM MATRIX
        submatrix = MatrixUtils.getInstance().getSubMatrix(matrix, xArr, yArr);

        int width = 0;
        int height = 0;

        // GET WIDTH AND HEIGHT OF SUB-MATRIX
        for (int i = 0; i < submatrix.length; i++) {
            for (int j = 0; j < submatrix[i].length; j++) {
                width = j;
            }
            height = i;
        }

        // SHRINK SUB-MATRIX BY ALGORITHM FROM FACTORY
        Processor processor = ImageProccesorFactory.getFactory(ImageProccesorFactory.BILINEAR);
        compressedSubVector = processor.process(convert2DtoVector(submatrix), width, height, compressValue, compressValue);

        //   FIRST       SECOND
        // 0 0 0 0 0    1 1 1 1 1
        // 0 1 0 1 0    1 0 0 0 1
        // 0 1 1 1 0    1 0 0 0 1
        // 0 1 0 1 0    1 0 0 0 1
        // 0 0 0 0 0    1 1 1 1 1

        // FIRST
        // 0 0 0 0 0 0 1 0 1 0 0 1 1 1 0 0 1 0 1 0 0 0 0 0 0

        // SECOND
        // 1 1 1 1 1 1 0 0 0 1 1 0 0 0 1 1 0 0 0 1 1 1 1 1 1

        // xor
        // 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1
        // xc - 23 p - 25 ma - 2 / 25 = 0.08 = 8%



        compressedSubMatrix = convert1Dto2D(compressedSubVector, compressValue);

        // CONVERT VECTOR TO BYTE VECTOR
        compressedSubByteVector = toByteArray(compressedSubVector);
        System.out.println("RUN FINISH");
    }

    @Override
    public BitmapMatrix arrayFromBitmap(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int[][] result = new int[height][width];
        int[] pixels = new int[width * height];
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        int pixelsIndex = 0;
        System.out.println("arrayFromBitmap START " + " - height: " + height + "; width: " + width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[pixelsIndex] != 0 ? 1 : 0;
                result[i][j] = pixel;
                pixelsIndex++;
            }
        }

        int[] px = new int[pixels.length];
        for (int i = 0; i < px.length; i++) {
            int pixel = pixels[i] != 0 ? 1 : 0;
            px[i] = pixel;
        }

        return new BitmapMatrix(px, result, width, height);
    }

    @Override
    public int[] convert2DtoVector(int[][] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                list.add(arr[i][j]);
            }
        }

        int[] vector = new int[list.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = list.get(i);
        }

        return vector;
    }

    @Override
    public int[][] convert1Dto2D(int[] array, int length) {
        if (array.length != (length * length))
            throw new IllegalArgumentException("Invalid array length");

        int[][] bidi = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                bidi[j][i] = array[i * length + j];
            }
            System.out.println();
        }

        return bidi;
    }

    @Override
    public byte[] toByteArray(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);

        return byteBuffer.array();
    }

    @Override
    public int[] toIntArray(byte[] data) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        IntBuffer intb = buffer.asIntBuffer();

        int[] ints = new int[intb.limit()];
        intb.get(ints);

        return ints;
    }

    public Bitmap getOriginalBitmap() {
        return originalBitmap;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int[][] getSubMatrix() {
        return submatrix;
    }

    public int[][] getCompressedSubMatrix() {
        return compressedSubMatrix;
    }

    public byte[] getCompressedSubByteVector() {
        return compressedSubByteVector;
    }

    public int getCompressValue() {
        return compressValue;
    }

    public int[] getCompressedSubVector() {
        return compressedSubVector;
    }
}
