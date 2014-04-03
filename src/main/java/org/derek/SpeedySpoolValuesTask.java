package org.derek;

import java.util.concurrent.RecursiveAction;

final class SpeedySpoolValuesTask extends RecursiveAction {
    private static final int THRESHOLD = 100000;
    private final int[] source;
    private final int startPos;
    private final int endPos;
    private final int[] counts;

    public SpeedySpoolValuesTask(final int[] source, final int[] counts, final int startPos, final int endPos) {
        this.source = source;
        this.startPos = startPos;
        this.endPos = endPos;
        this.counts = counts;
    }

    @Override
    public final void compute() {

        if (THRESHOLD > (endPos - startPos)) {
            for (int i = startPos; i < endPos; ++i) {
                source[i] = computeVirtualSortedValue(i);
            }
        } else {
            final int midPos = (startPos + endPos) >>> 1;
            invokeAll(new SpeedySpoolValuesTask(source, counts, startPos, midPos), new SpeedySpoolValuesTask(source, counts, midPos, endPos));
        }


    }

    private int computeVirtualSortedValue(final int currentLocation) {
        int counter = 0;
        for (int i = 0; i < counts.length; i++) {
            counter += counts[i];
            if (counter > currentLocation) {
                return i;
            }
        }
        return counts.length - 1;
    }


}
