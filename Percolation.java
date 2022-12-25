/* *****************************************************************************
 *  Name: Noah Meng
 *  Date: 23 Dec.
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF perc;
    private boolean[] flag;
    private int sqrt;
    private int number;
    private int numberOfOpenSites;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must > 0!");
        sqrt = n;
        number = n * n + 2;
        perc = new WeightedQuickUnionUF(number);
        flag = new boolean[number];
        for (int i = 1; i < number - 1; i++) flag[i] = false;
        flag[0] = true;
        flag[number - 1] = true;
        // union the virtual top site and first n sites
        for (int i = 1; i <= n; i++)
            perc.union(0, i);
        // union the virtual bottom site and last n sites
        for (int i = n * n; i > n * n - n; i--)
            perc.union(n * n + 1, i);
    }

    // index = (row - 1) * sqrt + col

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0)
            throw new IllegalArgumentException("row must > 0!");
        if (col <= 0)
            throw new IllegalArgumentException("row must > 0!");
        if (flag[(row - 1) * sqrt + col] == false) {
            // open sites
            // up site
            if (row != 1 && flag[(row - 1) * sqrt + col - sqrt])
                perc.union((row - 1) * sqrt + col,
                        (row - 1) * sqrt + col - sqrt);
            // down site
            if (row != sqrt && flag[(row - 1) * sqrt + col + sqrt])
                perc.union((row - 1) * sqrt + col,
                        (row - 1) * sqrt + col + sqrt);
            // left site
            if (flag[(row - 1) * sqrt + col - 1] == true && col != 1)
                perc.union((row - 1) * sqrt + col,
                        (row - 1) * sqrt + col - 1);
            // right site
            if (flag[(row - 1) * sqrt + col + 1] == true && col != sqrt)
                perc.union((row - 1) * sqrt + col,
                        (row - 1) * sqrt + col + 1);
            // change flag
            flag[(row - 1) * sqrt + col] = true;
            // add numberofopensites
            numberOfOpenSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0)
            throw new IllegalArgumentException("row must > 0!");
        if (col <= 0)
            throw new IllegalArgumentException("row must > 0!");
        return flag[(row - 1) * sqrt + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0)
            throw new IllegalArgumentException("row must > 0!");
        if (col <= 0)
            throw new IllegalArgumentException("row must > 0!");
        if (isOpen(row, col)) {
            // connected(self, 0)
            return perc.connected(0, (row - 1) * sqrt + col);
        } else return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return perc.connected(0, sqrt * sqrt + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation pe = new Percolation(N);
        pe.open(1, 1);
        pe.open(2, 1);
        System.out.println(pe.percolates());
    }
}
