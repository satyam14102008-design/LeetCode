class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long n1 = need1, n2 = need2;
        long maxNeed = Math.max(n1, n2);

        // The cost function is piecewise linear in x (number of type-3 items),
        // so its minimum is attained at one of these breakpoints.
        long[] candidates = {0, n1, n2, maxNeed};

        long ans = Long.MAX_VALUE;

        for (long x : candidates) {
            if (x < 0 || x > maxNeed) continue;

            long cost = x * costBoth;
            cost += Math.max(0L, n1 - x) * cost1;
            cost += Math.max(0L, n2 - x) * cost2;

            ans = Math.min(ans, cost);
        }

        return ans;
    }
}