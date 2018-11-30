package com.hks.hbase.model;

public class KeyValue<K,V> {
    private K k;
    private V v;
    public KeyValue(K key,V value){
        this.k = key;
        this.v = value;
    }

    public K getK() {
        return k;
    }

    public V getV() {
        return v;
    }
}
