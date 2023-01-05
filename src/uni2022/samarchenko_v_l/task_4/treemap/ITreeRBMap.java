package uni2022.samarchenko_v_l.task_4.treemap;

public interface ITreeRBMap<K extends Comparable<K>, V> {

    V put(K key, V value);
    V get(K key);
    V remove(K key);

    boolean containsKey(K key);

    boolean isEmpty();
    int size();

}