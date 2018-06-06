import java.util.stream.Stream;

public class IntegerStack extends StackArrayImpl<Integer>{
	
	static int x = 0;
	public static void main(String[] args) {
		IntegerStack integerStack = new IntegerStack();
		Stream.generate(()->x++).limit(3000).forEach((a)->integerStack.push(a));
		System.out.println(integerStack.size());
		Stream.generate(()->integerStack.pop()).limit(200).forEach((x)->System.out.println(x));
	}
}