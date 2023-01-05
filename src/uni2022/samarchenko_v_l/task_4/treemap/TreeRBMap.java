package uni2022.samarchenko_v_l.task_4.treemap;


import uni2022.samarchenko_v_l.task_4.treerb.TreeRB;

public class TreeRBMap<K extends Comparable<K>, V> implements ITreeRBMap<K, V> {

    class EntryTreeMapRB<K extends Comparable<K>, V> implements Comparable<EntryTreeMapRB<K, V>> {
        private K key;
        private V value;

        public EntryTreeMapRB(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(EntryTreeMapRB<K, V> o) {
            return this.key.compareTo(o.key);
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public String toString() {
            return key + "=" + value;
        }
    }

    private TreeRB<EntryTreeMapRB<K, V>> tree = new TreeRB<>();

    @Override
    public V put(K key, V value) {
        EntryTreeMapRB<K, V> entry = tree.put(new EntryTreeMapRB<>(key, value));
        return (entry == null) ? null : entry.value;

    }

    @Override
    public V get(K key) {
        EntryTreeMapRB<K, V> entry = tree.get(new EntryTreeMapRB<>(key, null));
        return (entry == null) ? null : entry.value;
    }

    @Override
    public V remove(K key) {
        EntryTreeMapRB<K, V> entry = tree.get(new EntryTreeMapRB<>(key, null));
        return (entry == null) ? null : entry.value;
    }

    @Override
    public boolean containsKey(K key) {
        return tree.contains(new EntryTreeMapRB<>(key, null));
    }

    @Override
    public boolean isEmpty() {
        return size() <= 0;
    }

    @Override
    public int size() {
        return tree.size();
    }




}
