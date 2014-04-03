package org.derek;

/**
 * User: derek
 * Date: 4/1/14
 * Time: 9:03 PM
 */

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * Sort demonstration
 */
public final class Sorter {


    final static int VALUE_RANGE = 10;

    private Sorter() {

    }

    public static void main(final String[] args) {

        final Sorter sorter = new Sorter();

        final int[] largeIntArray1 = getLargeIntArray();

        final int[] largeIntArray2 = new int[largeIntArray1.length];
        System.arraycopy(largeIntArray1, 0, largeIntArray2, 0, largeIntArray1.length);

        final int[] largeIntArray3 = new int[largeIntArray1.length];
        System.arraycopy(largeIntArray1, 0, largeIntArray3, 0, largeIntArray1.length);

        final int[] largeIntArray4 = new int[largeIntArray1.length];
        System.arraycopy(largeIntArray1, 0, largeIntArray4, 0, largeIntArray1.length);

        final long t0 = System.nanoTime();
        sorter.informedSort(largeIntArray1);
        System.out.println("Sorting took " + ((System.nanoTime() - t0) / 1000000) + " millis without threads");

        final long t1 = System.nanoTime();
        sorter.informedThreadedSort(largeIntArray2);
        System.out.println("Sorting took " + ((System.nanoTime() - t1) / 1000000) + " millis with threads");

        final long t2 = System.nanoTime();
        sorter.sort(largeIntArray3);
        System.out.println("Control group: Sorting took " + ((System.nanoTime() - t2) / 1000000) + " millis without threads using standard Arrays.sort");

        final long t3 = System.nanoTime();
        sorter.sortWithThreads(largeIntArray4);
        System.out.println("Control group: Sorting took " + ((System.nanoTime() - t3) / 1000000) + " millis with threads using slightly modified Arrays.sort");


    }

    static int[] getLargeIntArray() {
        final Random rand = new Random();
        final int[] input = new int[1 << 27];
        for (int i = 0; i < input.length; i++) {
            input[i] = Math.abs(rand.nextInt()) % 10;
        }
        return input;
    }

    /**
     * Sort array
     * @param a
     */
    static void sortWithThreads(final int[] a) {
        final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool pool = new ForkJoinPool(AVAILABLE_PROCESSORS);
        DualPivotQuickSortTask dualPivotQuickSortTask = new DualPivotQuickSortTask(a,0,a.length-1,true);
        pool.invoke(dualPivotQuickSortTask);
    }

    static void sort(final int[] a) {
        Arrays.sort(a);
    }

    /**
     * Sort array with range of known values.
     * This method is optimized for the problem at-hand
     *
     * @param a
     */
    static void informedSort(final int[] a) {
        final int[] countTracker = new int[VALUE_RANGE];
        for (final int anA : a) {
            countTracker[anA] += 1;
        }
        int start = 0;
        for (int i = 0; i < countTracker.length; i++) {
            for (int z = start; z < (countTracker[i] + start); z++) {
                a[z] = i;
            }
            start += countTracker[i];
        }
    }


    /**
     * Sort array with range of known values using fork/join framework.
     * This method is optimized for the problem at-hand
     *
     * @param a
     */
    static final void informedThreadedSort(final int[] a) {
        final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

        final ForkJoinPool pool = new ForkJoinPool(AVAILABLE_PROCESSORS);

        final SpeedySortCountValuesTask task = new SpeedySortCountValuesTask(a, 0, a.length, VALUE_RANGE);
        final int[] b = pool.invoke(task);

        final SpeedySpoolValuesTask task2 = new SpeedySpoolValuesTask(a, b, 0, a.length);
        pool.invoke(task2);
    }

}