package org.derek;

import java.util.concurrent.RecursiveTask;

final class SpeedySortCountValuesTask extends RecursiveTask<int[]> {
    private static final int THRESHOLD = 100000;
    private final int[] source;
    private final int startPos;
    private final int endPos;
    private final int valueRange;

    public SpeedySortCountValuesTask(final int[] source, final int startPos, final int endPos, final int valueRange) {
        this.source = source;
        this.startPos = startPos;
        this.endPos = endPos;
        this.valueRange = valueRange;
    }

    private static int[] sumArrays(final int[] x, final int[] y) {
        for (int i = 0; i < x.length; i++) {
            x[i] += y[i];
        }
        return x;
    }

    @Override
    public final int[] compute() {

        if (THRESHOLD > (endPos - startPos)) {
            final int[] b = new int[valueRange];
            for (int i = startPos; i < endPos; ++i) {
                b[source[i]] += 1;
            }
            return b;
        } else {

            final int midPos = (startPos + endPos) >>> 1;
            final SpeedySortCountValuesTask speedySortCountValuesTask1 = new SpeedySortCountValuesTask(source, startPos, midPos, valueRange);
            speedySortCountValuesTask1.fork();
            final SpeedySortCountValuesTask speedySortCountValuesTask2 = new SpeedySortCountValuesTask(source, midPos, endPos, valueRange);
            return sumArrays(speedySortCountValuesTask2.compute(), speedySortCountValuesTask1.join());
        }

    }

}
