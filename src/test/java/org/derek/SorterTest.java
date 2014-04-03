package org.derek;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;


@RunWith(JUnit4.class)
public class SorterTest {


    @Test
    public void sort_verifyAccuracy() {

        final int[] input = Sorter.getLargeIntArray();

        final int[] input2 = new int[input.length];
        System.arraycopy(input, 0, input2, 0, input.length);

        Sorter.informedSort(input);

        Arrays.sort(input2);

        assertTrue(Arrays.equals(input, input2));

    }


    @Test
    public void threadedSort_verifyAccuracy() {

        final int[] input = Sorter.getLargeIntArray();

        final int[] input2 = new int[input.length];
        System.arraycopy(input, 0, input2, 0, input.length);

        Sorter.informedThreadedSort(input);

        Arrays.sort(input2);

        assertTrue(Arrays.equals(input, input2));

    }
}
