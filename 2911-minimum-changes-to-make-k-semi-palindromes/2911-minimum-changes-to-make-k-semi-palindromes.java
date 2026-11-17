class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = Integer.MAX_VALUE / 2;
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int L = j - i + 1;
                if (L == 1) { cost[i][j] = INF; continue; }
                int best = INF;
                for (int d = 1; d < L; d++) {
                    if (L % d != 0) continue;
                    int total = 0;
                    for (int r = 0; r < d; r++) {
                        int g = L / d;
                        for (int t = 0; t < g / 2; t++) {
                            if (s.charAt(i + r + t * d) != s.charAt(i + r + (g - 1 - t) * d)) total++;
                        }
                    }
                    best = Math.min(best, total);
                }
                cost[i][j] = best;
            }
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int[] row : dp) java.util.Arrays.fill(row, INF);
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int p = 1; p <= k; p++) {
                for (int m = p - 1; m < i; m++) {
                    if (dp[m][p - 1] == INF || cost[m][i - 1] == INF) continue;
                    dp[i][p] = Math.min(dp[i][p], dp[m][p - 1] + cost[m][i - 1]);
                }
            }
        }
        return dp[n][k];
    }
}