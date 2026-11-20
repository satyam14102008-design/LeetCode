class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> dp = new HashMap<>();
        int res = 0;
        
        for (int x : nums) {
            // Option 1: keep x as is, extends sequence ending at x-1
            int keep = dp.getOrDefault(x - 1, 0) + 1;
            // Option 2: increase x to x+1, extends sequence ending at x
            int incr = dp.getOrDefault(x, 0) + 1;
            
            // Update dp — take max in case multiple elements map here
            dp.put(x, Math.max(dp.getOrDefault(x, 0), keep));
            dp.put(x + 1, Math.max(dp.getOrDefault(x + 1, 0), incr));
            
            res = Math.max(res, Math.max(keep, incr));
        }
        
        return res;
    }
}