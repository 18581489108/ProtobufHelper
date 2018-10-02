package cn.kurisu9.data;

/**
 * @author zhaoxin_m
 * @description 键值对
 * @date 2018/10/2 19:42
 **/
public class KeyValue<K, V> {
    private K key;
    private V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public static <K, V> KeyValue<K, V> of(K key, V value) {
        return new KeyValue<>(key, value);
    }
}
