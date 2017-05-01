public class PercolationStats {
  private double sitesPerTrial[];
  private int percolationTrials;

  public PercolationStats(int n, int trials) {
    sitesPerTrial = new double[trials];
    percolationTrials = trials;
    for (int i = 0; i < trials; i++) {
      Percolation perc = new Percolation(n);
      int opened = 0;
      while (!perc.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        if (!perc.isOpen(row, col)) {
          perc.open(row, col);
          opened++;
        }
      }
      sitesPerTrial[i] = ((double) opened) / (n * n);
    }
  }

  public double mean() {
    return StdStats.mean(sitesPerTrial);
  }

  public double stddev() {
    return StdStats.stddev(sitesPerTrial);
  }

  public double confidenceLo() {
    return mean() - (((1.96 * stddev()) / Math.sqrt(percolationTrials)));
  }

  public double confidenceHi() {
    return mean() + (((1.96 * stddev()) / Math.sqrt(percolationTrials)));
  }

  public static void main(String[] args) {
    int latticeSize = Integer.parseInt(args[0]);
    int numTrials = Integer.parseInt(args[1]);
    PercolationStats pStats = new PercolationStats(latticeSize, numTrials);
    StdOut.printf("mean                    = %f\n", pStats.mean());
    StdOut.printf("stddev                  = %f\n", pStats.stddev());
    StdOut.printf("95%% confidence interval = [%f, %f]\n",
      pStats.confidenceLo(), pStats.confidenceHi());
  }
}
