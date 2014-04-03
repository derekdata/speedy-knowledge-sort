package org.derek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ForkJoinPool;

@RunWith(JUnit4.class)
public class SpeedySortCountValuesTaskTest {

    @Test
    public void compute() {

        final int arrayMultiplier = 1000;

        int[] a = new int[Sorter.VALUE_RANGE * arrayMultiplier];

        for (int i=0; i < Sorter.VALUE_RANGE; i++) {
            for (int z=arrayMultiplier*i; z < (arrayMultiplier+(arrayMultiplier*i)); z++) {
                a[z]=i;
            }
        }

        final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool pool = new ForkJoinPool(AVAILABLE_PROCESSORS);

        final SpeedySortCountValuesTask task = new SpeedySortCountValuesTask(a, 0, a.length, Sorter.VALUE_RANGE);
        final int[] b = pool.invoke(task);

        for (int i=0; i < Sorter.VALUE_RANGE; i++) {
            assertTrue(arrayMultiplier == b[i]);
        }

    }

}
