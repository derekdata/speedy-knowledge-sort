package org.derek;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class SpeedySpoolValuesTaskTest {

    @Test
    public void compute() {

        final int arrayMultiplier = 1000;

        //build array to be sorted
        int[] a = new int[Sorter.VALUE_RANGE * arrayMultiplier];
        for (int i = 0; i < Sorter.VALUE_RANGE; i++) {
            for (int z = arrayMultiplier * i; z < (arrayMultiplier + (arrayMultiplier * i)); z++) {
                a[z] = i;
            }
        }
        //reverse array because it is already sorted
        ArrayUtils.reverse(a);

        final int[] a2 = new int[a.length];
        System.arraycopy(a, 0, a2, 0, a.length);

        //build count array
        int[] b = new int[Sorter.VALUE_RANGE];
        for (int i = 0; i < Sorter.VALUE_RANGE; i++) {
            b[i] = arrayMultiplier;
        }

        final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool pool = new ForkJoinPool(AVAILABLE_PROCESSORS);

        final SpeedySpoolValuesTask task2 = new SpeedySpoolValuesTask(a, b, 0, a.length);
        pool.invoke(task2);

        Sorter.informedThreadedSort(a);

        Arrays.sort(a2);

        assertTrue(Arrays.equals(a, a2));

    }
}
