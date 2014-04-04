speedy-knowledge-sort [![Build Status](https://travis-ci.org/derekdata/speedy-knowledge-sort.svg)](https://travis-ci.org/derekdata/speedy-knowledge-sort)
=====================

Demo sort algorithm in Java for data sets of a known range of values.  It is called "knowledge sort" becuase in order for high performance to be reached, it must have a knowledge of the range of included values.

This "count sort" outperforms Array.sort() as implemented in OpenJDK for datasets with little variation in their values

Example timings for a very large array of random integers between 0-9:
* "Count Sort" - 192 ms without threads
* "Count Sort" - 204 ms with threads
* Control group - 1795 ms without threads using standard Arrays.sort
* Control group - 1486 ms with threads using slightly modified Arrays.sort
