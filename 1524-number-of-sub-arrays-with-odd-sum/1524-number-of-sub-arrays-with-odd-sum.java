class Solution {
    public int numOfSubarrays(int[] arr) {
        final int MOD = 1_000_000_007;
        long oddCount = 0;
        long evenCount = 1;
        long prefix = 0;
        long result = 0;

        for (int num : arr) {
            prefix += num;
            if (prefix % 2 == 0) {
                result = (result + oddCount) % MOD;
                evenCount++;
            } else {
                result = (result + evenCount) % MOD;
                oddCount++;
            }
        }

        return (int) result;
    }
}