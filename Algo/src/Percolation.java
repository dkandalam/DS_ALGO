import java.util.function.IntSupplier;

public class Percolation {

	int n;
	int virtualTopRoot;
	int virtualBottomNode;
	int totalSize;
	int opened;
	WeightedQuickUnionWithPathCompression quickUnion;
	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		this.n = n;
		totalSize = n*n;
		virtualTopRoot = totalSize;
		virtualBottomNode = virtualTopRoot+1;
		quickUnion = new WeightedQuickUnionWithPathCompression(virtualBottomNode+1);
		quickUnion.setSize(virtualTopRoot, virtualTopRoot);
		quickUnion.setSize(virtualBottomNode, virtualBottomNode);
	}

	private int convertToIndex(int row,int col) {
		return row*n+col;
	}

	public void reset(){
		opened = 0;
		quickUnion.reset();
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		
		final int index = convertToIndex(row, col);
		
		if(quickUnion.isOpen(index)) return;
		
		quickUnion.open(index);
		
		opened++;
		
		//System.out.println("In Data "+index);
		if (row == 0) {
			quickUnion.union(index, virtualTopRoot);
		} else if (row == n - 1) {
			quickUnion.union(index, virtualBottomNode);
		}
		// botton
		if (row >= 0 && row < n - 1 && quickUnion.isOpen(index + n)) {
			quickUnion.union(index, index + n);
		}
		// above
		if (row >= 1 && row < n && quickUnion.isOpen(index - n)) {
			quickUnion.union(index, index - n);
		}
		// before
		if ((col > 0 && col < n) && quickUnion.isOpen(index - 1)) {
			quickUnion.union(index, index - 1);
		}
		// after
		if ((col >= 0 && col < n) && quickUnion.isOpen(index + 1)) {
			quickUnion.union(index, index + 1);
		}
		
	}
	
		// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		return quickUnion.isOpen(convertToIndex(row, col));
	}
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		return false;
	}

	// number of open sites
	public int numberOfOpenSites() {
		return opened;
	}
	
	public int getTotalSize() {
		return totalSize;
	}

	// does the system percolate?
	public boolean percolates() {
		return quickUnion.connected(virtualTopRoot, virtualBottomNode);
	}

	// test client (optional)
	public static void main(String[] args) {
		Percolation percolation = new Percolation(4);
		percolation.open(1, 1);
		percolation.open(1, 2);
		percolation.open(0, 0);
		percolation.open(0, 1);
		percolation.open(2, 2);
		percolation.open(3, 2);
		System.out.println();
		System.out.println(percolation.percolates());
	}
}