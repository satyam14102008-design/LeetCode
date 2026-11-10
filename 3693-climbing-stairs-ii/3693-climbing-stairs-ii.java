class Solution {
    public int climbStairs(int n, int[] costs) {
        int INF = Integer.MAX_VALUE / 2;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = INF;
        }
        
        for (int j = 1; j <= n; j++) {
            int costJ = costs[j - 1]; // costs is 1-indexed, array is 0-indexed
            for (int k = 1; k <= 3; k++) {
                int i = j - k;
                if (i >= 0) {
                    dp[j] = Math.min(dp[j], dp[i] + costJ + k * k);
                }
            }
        }
        
        return dp[n];
    }
}