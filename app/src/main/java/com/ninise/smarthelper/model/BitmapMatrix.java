package com.ninise.smarthelper.model;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class BitmapMatrix {

    private int[][] matrix;
    private int width;
    private int height;

    public BitmapMatrix(int[][] matrix, int width, int height) {
        this.matrix = matrix;
        this.width = width;
        this.height = height;
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
}
