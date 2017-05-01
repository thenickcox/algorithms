public class Percolation {
  public int latticeSize;
  public WeightedQuickUnionUF uf;

	private int id[][];
	private int opens[];
	private int numOpens;
  // n = 3
  // [[ 0 ]
  //  [ 0, 1, 2, 3 ]
  //  [ 0, 4, 5, 6 ],
  //  [ 0, 7, 8, 9 ],
  //  [ 0 ]]

  public Percolation(int n) {

		id = new int[n + 2][n + 1];
		opens = new int[(n + 1) * (n + 1)];
		numOpens = 0;

    if (n <= 0)
      throw new java.lang.IllegalArgumentException("i must be > 0");
    latticeSize = n;
    uf = new WeightedQuickUnionUF(opens.length);
    for (int i = 0; i <= n + 1; i++) {
      for (int j = 0; j <= n + 1; j++) {
        // top site
        if (i == 0 && j == 0) {
          id[0][0] = 0;
        }
        // bottom site
        else if (j == n + 1) {
          id[n + 1][0] = 0;
        }
        else {
          id[i][j] = i * n + j;
          opens[xyTo1D(i, j)] = 0;
        }
      }
    }
    connectTopSite();
    connectBottomSite();
  }

  // n = 3
  // [[ 0 ]
  //  [ 0, 1, 2, 3 ]
  //  [ 0, 4, 5, 6 ],
  //  [ 0, 7, 8, 9 ],
  //  [ 0 ]]
  public void open(int row, int col) {
    validateArgs(row, col);
    int opened = xyTo1D(row, col);
    opens[opened] = 1;
		numOpens++;
    // union top unless row == 1
    if (!(row == 1) && isOpen(row - 1, col)) uf.union(opened, xyTo1D(row - 1, col));
    // union right unless col == latticeSize
    if (!(col == latticeSize) && isOpen(row, col + 1)) uf.union(opened, xyTo1D(row, col + 1));
    // union bottom unless row == latticeSize
    if (!(row == latticeSize) && isOpen(row + 1, col)) uf.union(opened, xyTo1D(row + 1, col));
    // union left unless col == 1
    if (!(col == 1) && isOpen(row, col - 1)) uf.union(opened, xyTo1D(row, col - 1));
  }

  public boolean isOpen(int row, int col) {
    validateArgs(row, col);
    return opens[xyTo1D(row, col)] == 1;
  }

  public boolean isFull(int row, int col) {
    validateArgs(row, col);
    // is connected to top virtual site?
    return uf.connected(0, xyTo1D(row, col));
  }

  public int numberOfOpenSites() {
		return numOpens;
  }

  public boolean percolates() {
    // check if bottom "virtual site" is connected to top virtual site
    return uf.connected(0, xyTo1D(latticeSize + 1, 0));
  }

  private void connectTopSite() {
    for (int i = 0; i <= latticeSize + 1; i++) {
      uf.union(0, xyTo1D(1, i + 1));
    }
  }

  private void connectBottomSite() {
    for (int i = 0; i <= latticeSize + 1; i++) {
      uf.union(xyTo1D(latticeSize + 1, 0), xyTo1D(latticeSize, i + 1));
    }
  }

  private void validateArgs(int row, int col) {
    if (
      row <= 0 ||
      col <= 0 ||
      row > latticeSize + 1 ||
      col > latticeSize + 1
    ) {
			StdOut.printf("row %d and column %d", row, col);
			throw new java.lang.IllegalArgumentException("row or column out of range");
		}
  }

  private int xyTo1D(int x, int y) {
    return x == 1 ? x + y + 1 : x * latticeSize + y;
  }

  // public static void main(String[] args) {
  //   int n = StdIn.readInt();
  //   Percolation percolation = new Percolation(n);
		// StdOut.println(n);
  //   while (!percolation.percolates()) {
  //     int row = StdRandom.uniform(1, n + 1);
  //     int col = StdRandom.uniform(1, n + 1);
			// StdOut.printf("%d  %d\n", row, col);
  //     if (!percolation.isOpen(row, col)) percolation.open(row, col);
  //   }
  // }
}
