class Solution {
    static class Node {
        int mx = 0;
        int[] pref = new int[0];
        long[] ps = new long[0];
    }

    private int n;
    private Node[] tr;
    private int[] a;

    private void build(int p, int l, int r) {
        if (l == r) {
            tr[p] = new Node();
            tr[p].mx = a[l];
            tr[p].pref = new int[]{a[l]};
            tr[p].ps = new long[]{a[l]};
            return;
        }
        int m = (l + r) >>> 1;
        build(p << 1, l, m);
        build(p << 1 | 1, m + 1, r);

        Node L = tr[p << 1], R = tr[p << 1 | 1], C = new Node();
        C.mx = Math.max(L.mx, R.mx);

        int len = r - l + 1;
        C.pref = new int[len];
        int idx = 0;
        for (int v : L.pref) C.pref[idx++] = v;
        for (int v : R.pref) C.pref[idx++] = Math.max(L.mx, v);

        C.ps = new long[len];
        long sum = 0;
        for (int i = 0; i < len; i++) {
            sum += C.pref[i];
            C.ps[i] = sum;
        }
        tr[p] = C;
    }

    private void query(int p, int l, int r, int ql, int qr, int[] curMax, long[] sum) {
        if (ql <= l && r <= qr) {
            int[] pref = tr[p].pref;
            int idx = upperBound(pref, curMax[0]);

            long rightSum = tr[p].ps[tr[p].ps.length - 1];
            if (idx > 0) rightSum -= tr[p].ps[idx - 1];

            sum[0] += (long) curMax[0] * idx + rightSum;
            curMax[0] = Math.max(curMax[0], tr[p].mx);
            return;
        }
        int m = (l + r) >>> 1;
        if (ql <= m) query(p << 1, l, m, ql, qr, curMax, sum);
        if (qr > m) query(p << 1 | 1, m + 1, r, ql, qr, curMax, sum);
    }

    private int upperBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private long runningMaxSum(int L, int R) {
        int[] curMax = {0};
        long[] sum = {0};
        query(1, 0, n - 1, L, R, curMax, sum);
        return sum[0];
    }

    public long countNonDecreasingSubarrays(int[] nums, int k) {
        n = nums.length;
        a = nums;
        tr = new Node[4 * n];
        build(1, 0, n - 1);

        long[] pref = new long[n + 1];
        for (int i = 0; i < n; i++) pref[i + 1] = pref[i] + nums[i];

        long ans = 0;
        int L = 0;

        for (int R = 0; R < n; R++) {
            while (L <= R) {
                long cost = runningMaxSum(L, R) - (pref[R + 1] - pref[L]);
                if (cost <= k) break;
                L++;
            }
            ans += (long) (R - L + 1);
        }
        return ans;
    }
}