
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // Always perform binary search on the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int low = 0;
        int high = m;

        while (low <= high) {

            int partition1 = (low + high) / 2;
            int partition2 = (m + n + 1) / 2 - partition1;

            int maxLeft1 = (partition1 == 0)
                    ? Integer.MIN_VALUE : nums1[partition1 - 1];

            int minRight1 = (partition1 == m)
                    ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0)
                    ? Integer.MIN_VALUE : nums2[partition2 - 1];

            int minRight2 = (partition2 == n)
                    ? Integer.MAX_VALUE : nums2[partition2];

            // Correct partition found
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {

                // Odd total number of elements
                if ((m + n) % 2 == 1) {
                    return Math.max(maxLeft1, maxLeft2);
                }

                // Even total number of elements
                return (Math.max(maxLeft1, maxLeft2)
                        + Math.min(minRight1, minRight2)) / 2.0;
            }

            // Move binary search towards left
            else if (maxLeft1 > minRight2) {
                high = partition1 - 1;
            }

            // Move binary search towards right
            else {
                low = partition1 + 1;
            }
        }

        return 0.0;
    }
}
