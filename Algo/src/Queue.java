
public interface Queue<T> {
	public void enqueue(T object);
	public T dequeue();
	public int size();
}
