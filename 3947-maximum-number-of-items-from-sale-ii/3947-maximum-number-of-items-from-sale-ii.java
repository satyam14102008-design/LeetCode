class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxFactor = 0;
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
        }
        
        // Count items by factor
        int[] count = new int[maxFactor + 1];
        for (int[] item : items) {
            count[item[0]]++;
        }
        
        // Compute divisorsCount[f] = number of items j with f | factor_j
        int[] divisorsCount = new int[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            for (int m = f; m <= maxFactor; m += f) {
                divisorsCount[f] += count[m];
            }
        }
        
        // Compute freeCount for each item
        // freeCount[i] = divisorsCount[factor_i] - 1
        // We'll store items as (price, freeCount)
        
        // Find p_min
        int p_min = Integer.MAX_VALUE;
        for (int[] item : items) {
            p_min = Math.min(p_min, item[1]);
        }
        
        // Sort items by price
        Arrays.sort(items, (a, b) -> Integer.compare(a[1], b[1]));
        
        long totalItems = 0;
        long remainingBudget = budget;
        
        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];
            int freeCount = divisorsCount[factor] - 1;
            
            if (price >= 2 * p_min) {
                break;
            }
            
            if (freeCount <= 0) continue;
            
            long maxAffordable = remainingBudget / price;
            long take = Math.min(freeCount, maxAffordable);
            totalItems += 2 * take;
            remainingBudget -= take * price;
        }
        
        // Regular copies at p_min
        totalItems += remainingBudget / p_min;
        
        return (int) totalItems;
    }
}