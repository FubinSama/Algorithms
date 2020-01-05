/* *****************************************************************************
 *  Name:              wfb
 *  Coursera User ID:  FubinSama@qq.com
 *  Last modified:     5/1/2020
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double tmp;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        double[] thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int row, col;
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);
                if (perc.isOpen(row, col)) continue;
                perc.open(row, col);
            }
            thresholds[i] = 1.0 * perc.numberOfOpenSites() / (n*n);
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        tmp = 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - tmp;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + tmp;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trial);
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() +
                                   ", " + percolationStats.confidenceHi() + "]");
    }
}
