class Solution {
    public boolean canAliceWin(int n) {
        int stonesNeeded = 10;
        boolean aliceTurn = true;
        
        while (n >= stonesNeeded) {
            n -= stonesNeeded;
            stonesNeeded--;
            aliceTurn = !aliceTurn;
        }
        
        return !aliceTurn;
    }
}