import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] arr;
    private int trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IndexOutOfBoundsException("grid or trials out of bounds");
        }
        arr = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
            int row = ((int) StdRandom.uniformDouble(1, n+1));
            int col = ((int) StdRandom.uniformDouble(1, n+1));
            percolation.open(row, col);
            }
            arr[i] = (double) percolation.numberOfOpenSites() / Math.pow(n, 2);

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arr);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double std = stddev();
        double z = 1.96;
        return mean - (z * std / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double std = stddev();
        double z = 1.96;
        return mean + (z * std / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats perc = new PercolationStats(5, 20);
        System.out.println(perc.mean());
        System.out.println(perc.stddev());
        System.out.println(perc.confidenceHi());
        System.out.println(perc.confidenceLo());
    }

}