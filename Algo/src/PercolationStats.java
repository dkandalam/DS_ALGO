import edu.princeton.cs.algs4.StdRandom;
import java.util.*;

public class PercolationStats {
	// perform trials independent experiments on an n-by-n grid
	private int n;
	private int trails;
	private List<Double> openedSites = new ArrayList<Double>(10);
	final double constant = 1.96d;
	private double mean = 0.0d;
	private double stddev = 0.0d;

	public PercolationStats(int n, int trails) throws IllegalArgumentException {
		if (n <= 0 && trails <= 0)
			throw new IllegalArgumentException("Both n and trails should be greaterthan 0");
		this.n = n;
		this.trails = trails;
	}

	private int getRandomNumber() {
		int randomNumber = StdRandom.uniform(n);
		while (randomNumber > n) {
			randomNumber = StdRandom.uniform(n);
		}
		return randomNumber;
	}

	public void simulate() {

		Percolation percolationSimulator = new Percolation(n);
		int run = trails;
		while (run > 0) {

			while (!percolationSimulator.percolates()) {
				percolationSimulator.open(getRandomNumber(), getRandomNumber());
			}

			openedSites.add(((double) percolationSimulator.numberOfOpenSites() / percolationSimulator.getTotalSize()));
			/*System.out.println(percolationSimulator.numberOfOpenSites() + " openedSite For total sites "
					+ percolationSimulator.getTotalSize() + " fraction "
					+ ((double) percolationSimulator.numberOfOpenSites() / percolationSimulator.getTotalSize())
					+ " trail::" + run);
			*/
			run--;
			percolationSimulator.reset();
		}
		mean = calculateMean();
		stddev = calculateStdDev();
	}

	private double calculateMean() {
		double totalPercolationThershold = 0d;
		for (double openedSite : openedSites) {
			totalPercolationThershold += (double) (openedSite);
		}
		//System.out.println("Before Mean " + totalPercolationThershold + " trails:" + trails);
		return (totalPercolationThershold / trails);
	}
	
	private double calculateStdDev() {
		double mean = mean();
		double totalDiff = 0d;
		for (double openedSite : openedSites) {
			double temp = openedSite - mean;
			totalDiff += (temp * temp);
		}
		return Math.sqrt(totalDiff / (trails - 1));
	}

	public double mean() {
		return mean;
	}

	public double stddev() {
		return stddev;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {

		return mean() - ((constant * stddev()) / Math.sqrt(trails));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + ((constant * stddev()) / Math.sqrt(trails));
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("mean 			: ").append(mean()).append("\n");
		sb.append("stddev			: ").append(stddev()).append("\n");
		sb.append("95% confidence interval	: ").append("[").append(confidenceLo()).append(",").append(confidenceHi())
				.append("]");
		return sb.toString();
	}

	// test client (described below)
	public static void main(String[] args) {
		int size = Integer.parseInt(args[0]);
		int trails = Integer.parseInt(args[1]);
		PercolationStats percolationStats = new PercolationStats(size, trails);
		percolationStats.simulate();
		System.out.println(percolationStats);
	}
}
