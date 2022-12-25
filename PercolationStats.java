/* *****************************************************************************
 *  Name: Noah Meng
 *  Date: 24 Dec.
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshold;
    private double[] confidence_interval;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0)
            throw new IllegalArgumentException("Trials must > 0!");
        if (n <= 0)
            throw new IllegalArgumentException("n must > 0!");

        T = trials;
        threshold = new double[T];
        int times = 0;

        while (times < T) {
            // each pocolation experimental
            Percolation perc = new Percolation(n);
            int count = 0;
            // generate the x,y(less than n) to open sites
            while (true) {
                count++;
                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;

                if (perc.isOpen(x, y)) {
                    continue;
                } else
                    perc.open(x, y);

                if (perc.percolates()) break;

            }
            threshold[times] = (double) perc.numberOfOpenSites() / (n * n);

            times++;

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (1.96 * stddev()) / (Math.sqrt(T)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (1.96 * stddev()) / (Math.sqrt(T)));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(200, -1);
        StdOut.println(percolationStats.mean());
        StdOut.println(percolationStats.stddev());
        StdOut.println(percolationStats.confidenceLo());
        StdOut.println(percolationStats.confidenceHi());
    }
}
