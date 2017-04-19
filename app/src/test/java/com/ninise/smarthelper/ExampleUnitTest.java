package com.ninise.smarthelper;

import com.ninise.smarthelper.core.CoreProcessor;
import com.ninise.smarthelper.core.XORRunner;
import com.ninise.smarthelper.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;


public class ExampleUnitTest {
    @Test
    public void convertingIsCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        int [] first = {0,1,1,1,1,0,1,0,1};

        CoreProcessor coreProcessor = new CoreProcessor();

        byte [] bytes = coreProcessor.toByteArray(first);

        int [] second = coreProcessor.toIntArray(bytes);

        assertArrayEquals(first, second);
    }

    @Test
    public void matrixIsCorrect() throws Exception {
        int[][] a = {
                {0,0,0,0,0},
                {0,1,0,1,0},
                {0,1,1,1,0},
                {0,1,0,1,0},
                {0,0,0,0,0}
        };

        int[][] b = {
                {0,0,0,0,0},
                {0,1,0,1,0},
                {0,1,1,1,0},
                {0,1,0,1,0},
                {0,0,0,0,0}
        };


        int ma = XORRunner
                .build()
                .setOriginalPixels(a)
                .setSamplePixels(b)
                .getMatchAccuracy();

        assertEquals(100, ma);
    }
}