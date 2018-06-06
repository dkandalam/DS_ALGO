

import java.util.Arrays;

public class WeightedQuickUnionWithPathCompression {

	int[] data;
	int[] size;
	
	public WeightedQuickUnionWithPathCompression(int n) {
		// TODO Auto-generated constructor stub
		data = new int[n];
		size = new int[n];
		init();
	}

	private void init(){
		for(int i=0;i<data.length;i++) {
			size[i]=1;
			data[i] = -1;
		}
	}
	
	public void reset(){
		init();
	}

	public void setSize(int index,int sz) {
		size[index] = sz;
	}
	
	private int getRoot(int a) {
		//System.out.print("root of "+a+"=");
		while (a != data[a]) {
			if (data[a] == -1)
				break;
			else {
				if (data[data[a]] > -1) {
					data[a] = data[data[a]];
				}
				a = data[a];
			}
		}
		//System.out.print(a);
		//System.out.println();
		return a;
	}
	
	public void open(int index) {
		data[index] = index;
	}
	
	public boolean isOpen(int index) {
		return data[index] > -1;
	}
	
	private boolean isConnected(int a,int b) {
		return getRoot(a) == getRoot(b);
	}
	
	
	public void union(int a, int b) {
		if(!isConnected(a, b)) {
			int aRoot = getRoot(a);
			int bRoot = getRoot(b);
			if(size[aRoot] > size[bRoot]) {
				data[bRoot] = aRoot;
				size[aRoot]+=size[bRoot];
			}else {
				data[aRoot] = bRoot;
				size[bRoot]+=size[aRoot];
			}
			//System.out.println(a+","+b+" connected");
		}
	}
	
	public boolean connected(int a, int b) {
		// TODO Auto-generated method stub
		return isConnected(a, b);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Array::").append(Arrays.toString(data)).append("\n");
		stringBuffer.append(" Size::").append(Arrays.toString(size));
		return stringBuffer.toString();
		
	}

	
}
