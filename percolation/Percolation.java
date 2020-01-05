/* *****************************************************************************
 *  Name:              wfb
 *  Coursera User ID:  FubinSama@qq.com
 *  Last modified:     5/1/2020
 **************************************************************************** */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    private boolean[][] grid;
    private int cnt;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        cnt = 0;
        uf = new WeightedQuickUnionUF(n*n+2);
        uf2 = new WeightedQuickUnionUF(n*n+1);
        grid = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!checkRange(row, col)) throw new IllegalArgumentException();
        if (grid[row-1][col-1]) return;
        grid[row-1][col-1] = true;
        cnt++;
        if (row == 1) {
            uf.union(getIndex(row, col), getTopIndex());
            uf2.union(getIndex(row, col), getTopIndex());
        }
        if (row == grid.length) {
            uf.union(getIndex(row, col), getBottomIndex());
        }
        if (row-1 >= 1 && isOpen(row-1, col)) {
            uf.union(getIndex(row, col), getIndex(row-1, col));
            uf2.union(getIndex(row, col), getIndex(row-1, col));
        }
        if (row+1 <= grid.length && isOpen(row+1, col)) {
            uf.union(getIndex(row, col), getIndex(row+1, col));
            uf2.union(getIndex(row, col), getIndex(row+1, col));
        }
        if (col-1 >= 1 && isOpen(row, col-1)) {
            uf.union(getIndex(row, col), getIndex(row, col-1));
            uf2.union(getIndex(row, col), getIndex(row, col-1));
        }
        if (col+1 <= grid.length && isOpen(row, col+1)) {
            uf.union(getIndex(row, col), getIndex(row, col+1));
            uf2.union(getIndex(row, col), getIndex(row, col+1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!checkRange(row, col)) throw new IllegalArgumentException();
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!checkRange(row, col)) throw new IllegalArgumentException();
        return uf2.connected(getIndex(row,col), getTopIndex());
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return cnt;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(getTopIndex(), getBottomIndex());
    }

    private int getIndex(int row, int col) {
        return (row-1)*grid.length + col - 1;
    }

    private int getTopIndex() {
        return grid.length * grid.length;
    }

    private int getBottomIndex() {
        return getTopIndex() + 1;
    }

    private boolean checkRange(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length)
            return false;
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(20);
        for (int i = 1; i <= 20; i++) {
            perc.open(i, 1);
        }
        System.out.println(perc.numberOfOpenSites());
        System.out.println(perc.isOpen(2, 2));
        System.out.println(perc.percolates());
    }
}
