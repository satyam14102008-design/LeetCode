class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int ans = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = i; j < n; j++) {
                currentSum += nums.get(j);
                int length = j - i + 1;
                if (length > r) {
                    break;
                }
                if (length >= l && currentSum > 0) {
                    ans = Math.min(ans, currentSum);
                }
            }
        }
        
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}