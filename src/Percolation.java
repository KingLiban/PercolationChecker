import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n = 0;
    private int[][] arr;
    private WeightedQuickUnionUF uf;
    private int count;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        arr = new int[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);

        // connect first row to virtual top site
        for (int col = 1; col <= n; col++) {
            uf.union(0, xyTo1D(1, col));
        }

        // connect last row to virtual bottom site
        for (int col = 1; col <= n; col++) {
            uf.union(n * n + 1, xyTo1D(n, col));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        }
        if (!isOpen(row, col)) {
            arr[row - 1][col - 1] = 1;
            count++;

            // connect to adjacent open sites
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row < n && isOpen(row + 1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (col < n && isOpen(row, col + 1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IndexOutOfBoundsException("row or column index out of bounds");
        }
        return arr[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        } else {
            return uf.find(xyTo1D(row, col)) == uf.find(0);
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() { // use count
        return count;
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * n + (col - 1) + 1;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find((n * n) + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(3, 2);
        percolation.open(4, 2);
        percolation.open(4, 3);
        percolation.open(5, 3);
        System.out.println(percolation.percolates());
    }
}

