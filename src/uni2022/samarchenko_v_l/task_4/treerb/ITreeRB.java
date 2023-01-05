package uni2022.samarchenko_v_l.task_4.treerb;

public interface ITreeRB<T extends Comparable<T>> {

    T get(T value);
    T put(T value);
    T remove(T value);

    boolean contains(T value);

    boolean isEmpty();
    int size();





}