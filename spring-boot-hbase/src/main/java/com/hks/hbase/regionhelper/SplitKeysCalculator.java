package com.hks.hbase.regionhelper;

public interface SplitKeysCalculator {
    public byte[][] calcSplitKeys();
}
