package com.ninise.smarthelper.model;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class BitmapMatrix {

    private int[][] matrix;
    private int width;
    private int height;
    private int[] vector;

    public BitmapMatrix(int[] vector, int[][] matrix, int width, int height) {
        this.matrix = matrix;
        this.width = width;
        this.height = height;
        this.vector = vector;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }
}
