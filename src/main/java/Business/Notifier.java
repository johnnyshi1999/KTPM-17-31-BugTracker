package Business;

public interface Notifier<T> {
    public void notifyChange(T t);
}
