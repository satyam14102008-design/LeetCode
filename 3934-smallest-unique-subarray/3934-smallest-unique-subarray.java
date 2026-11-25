class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int[] sa = buildSA(nums);
        int[] lcp = buildLCP(nums, sa);
        
        int ans = n + 1;
        for (int p = 0; p < n; p++) {
            int maxLCP = Math.max(lcp[p], p+1 < n ? lcp[p+1] : 0);
            int i = sa[p];
            int suffixLen = n - i;
            if (maxLCP < suffixLen) {
                ans = Math.min(ans, maxLCP + 1);
            }
        }
        return ans;
    }
    
    private int[] buildSA(int[] s) {
        int n = s.length;
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] tmp = new int[n];
        
        int maxVal = 0;
        for (int x : s) maxVal = Math.max(maxVal, x);
        int[] cnt = new int[Math.max(maxVal, n) + 2];
        
        for (int x : s) cnt[x]++;
        for (int i = 1; i < cnt.length; i++) cnt[i] += cnt[i-1];
        for (int i = n-1; i >= 0; i--) sa[--cnt[s[i]]] = i;
        
        rank[sa[0]] = 0;
        for (int i = 1; i < n; i++) {
            rank[sa[i]] = rank[sa[i-1]] + (s[sa[i]] != s[sa[i-1]] ? 1 : 0);
        }
        
        for (int k = 1; k < n; k <<= 1) {
            int p = 0;
            for (int i = n-k; i < n; i++) tmp[p++] = i;
            for (int i = 0; i < n; i++) {
                if (sa[i] >= k) tmp[p++] = sa[i] - k;
            }
            
            int maxRank = rank[sa[n-1]];
            Arrays.fill(cnt, 0, maxRank+1, 0);
            for (int i = 0; i < n; i++) cnt[rank[i]]++;
            for (int i = 1; i <= maxRank; i++) cnt[i] += cnt[i-1];
            for (int i = n-1; i >= 0; i--) {
                sa[--cnt[rank[tmp[i]]]] = tmp[i];
            }
            
            tmp[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                int prev = sa[i-1], cur = sa[i];
                int rp2 = (prev+k < n) ? rank[prev+k] : -1;
                int rc2 = (cur+k < n) ? rank[cur+k] : -1;
                boolean same = rank[prev] == rank[cur] && rp2 == rc2;
                tmp[cur] = tmp[prev] + (same ? 0 : 1);
            }
            System.arraycopy(tmp, 0, rank, 0, n);
            if (rank[sa[n-1]] == n-1) break;
        }
        return sa;
    }
    
    private int[] buildLCP(int[] s, int[] sa) {
        int n = s.length;
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) rank[sa[i]] = i;
        int[] lcp = new int[n];
        int h = 0;
        for (int i = 0; i < n; i++) {
            if (rank[i] > 0) {
                int j = sa[rank[i]-1];
                while (i+h < n && j+h < n && s[i+h] == s[j+h]) h++;
                lcp[rank[i]] = h;
                if (h > 0) h--;
            } else {
                h = 0;
            }
        }
        return lcp;
    }
}