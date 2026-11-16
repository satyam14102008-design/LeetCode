class Solution {
    public boolean isPalindrome(int x) {
        Scanner sc = new Scanner(System.in);

        if (x < 0){
            System.out.println("false");
        }
         int original = x;
         int reverse = 0;

        while (x > 0){
            int digit = x % 10;
            reverse = reverse * 10 + digit;
            x = x/10;
        }
        return original == reverse;
    }
        
}