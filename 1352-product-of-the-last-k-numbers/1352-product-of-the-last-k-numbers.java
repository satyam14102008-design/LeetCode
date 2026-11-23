import java.util.*;

class ProductOfNumbers {
    private List<Integer> prefixProducts;
    private int lastZeroIndex; // index in prefixProducts where last zero occurred
    
    public ProductOfNumbers() {
        prefixProducts = new ArrayList<>();
        prefixProducts.add(1); // sentinel value
        lastZeroIndex = -1; // no zero encountered yet
    }
    
    public void add(int num) {
        if (num == 0) {
            // Reset: clear all prefix products since any product including this zero is 0
            prefixProducts.clear();
            prefixProducts.add(1); // reset sentinel
            lastZeroIndex = 0; // now everything before this index is "invalid"
        } else {
            // Add new prefix product
            int lastProduct = prefixProducts.get(prefixProducts.size() - 1);
            prefixProducts.add(lastProduct * num);
        }
    }
    
    public int getProduct(int k) {
        int size = prefixProducts.size();
        
        // If k is larger than the numbers since last zero, product is 0
        if (k >= size) {
            return 0;
        }
        
        // Product of last k numbers = prefix[n] / prefix[n-k]
        int n = size - 1;
        return prefixProducts.get(n) / prefixProducts.get(n - k);
    }
}

/**
 * Your ProductOfNumbers object will be instantiated and called as such:
 * ProductOfNumbers obj = new ProductOfNumbers();
 * obj.add(num);
 * int param_2 = obj.getProduct(k);
 */