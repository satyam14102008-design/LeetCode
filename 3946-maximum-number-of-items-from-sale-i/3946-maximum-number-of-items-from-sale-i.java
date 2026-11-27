class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] factor = new int[n];
        int[] price = new int[n];
        int minPrice = Integer.MAX_VALUE;
        int c = 0;
        for (int i = 0; i < n; i++) {
            factor[i] = items[i][0];
            price[i] = items[i][1];
            if (price[i] < minPrice) {
                minPrice = price[i];
                c = i;
            }
        }

        // freeFrom[i] = count of j != i with factor[i] | factor[j]
        int[] freeFrom = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && factor[j] % factor[i] == 0) {
                    freeFrom[i]++;
                }
            }
        }

        // Case B: c not activated, no extra copies
        int ans2 = 0;
        {
            int[] dp = new int[budget + 1];
            java.util.Arrays.fill(dp, -1);
            dp[0] = 0;
            for (int i = 0; i < n; i++) {
                if (i == c) continue;
                int cost = price[i], val = 1 + freeFrom[i];
                for (int b = budget; b >= cost; b--) {
                    if (dp[b - cost] != -1) {
                        dp[b] = Math.max(dp[b], dp[b - cost] + val);
                    }
                }
            }
            for (int b = 0; b <= budget; b++) {
                if (dp[b] != -1) ans2 = Math.max(ans2, dp[b]);
            }
        }

        // Case A: c activated, buy extra copies of c with leftover
        int ans1 = 0;
        if (budget >= price[c]) {
            int R = budget - price[c];
            int[] dp = new int[R + 1];
            java.util.Arrays.fill(dp, -1);
            dp[0] = 0;
            for (int i = 0; i < n; i++) {
                if (i == c) continue;
                int cost = price[i], val = 1 + freeFrom[i];
                for (int b = R; b >= cost; b--) {
                    if (dp[b - cost] != -1) {
                        dp[b] = Math.max(dp[b], dp[b - cost] + val);
                    }
                }
            }
            int base = 1 + freeFrom[c];
            for (int b = 0; b <= R; b++) {
                if (dp[b] != -1) {
                    ans1 = Math.max(ans1, base + dp[b] + (R - b) / minPrice);
                }
            }
        }

        return Math.max(ans1, ans2);
    }
}