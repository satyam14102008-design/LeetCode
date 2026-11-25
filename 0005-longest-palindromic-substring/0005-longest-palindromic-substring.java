class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int start = 0, maxLen = 1;
        
        // All single characters are palindromes
        for (int i = 0; i < n; i++) dp[i][i] = true;
        
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    // Length 2, or inner substring is palindrome
                    dp[i][j] = (len == 2) || dp[i + 1][j - 1];
                    if (dp[i][j] && len > maxLen) {
                        maxLen = len;
                        start = i;
                    }
                }
            }
        }
        
        return s.substring(start, start + maxLen);
    }
}