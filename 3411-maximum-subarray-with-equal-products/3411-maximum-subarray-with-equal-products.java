class Solution {
    public int maxLength(int[] nums) {
        int[] primes = {2, 3, 5, 7};
        int n = nums.length;
        int best = 1;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isProductEquivalent(nums, i, j, primes)) {
                    best = Math.max(best, j - i + 1);
                }
            }
        }
        return best;
    }

    private boolean isProductEquivalent(int[] nums, int l, int r, int[] primes) {
        for (int pr : primes) {
            int sum = 0, mx = 0, mn = Integer.MAX_VALUE;
            for (int k = l; k <= r; k++) {
                int e = 0, v = nums[k];
                while (v % pr == 0) { v /= pr; e++; }
                sum += e;
                mx = Math.max(mx, e);
                mn = Math.min(mn, e);
            }
            if (sum != mx + mn) return false;
        }
        return true;
    }
}