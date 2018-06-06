public interface Stack<T>{
	public void push(T object);
	public T    pop();
	public T    peek();
	public int  size();
}