import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class TestClass {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Read the number of test cases
        String tLine = br.readLine();
        if (tLine == null) return;
        int t = Integer.parseInt(tLine.trim());
        
        while (t-- > 0) {
            // Read array size
            String nLine = br.readLine();
            if (nLine == null) break;
            int n = Integer.parseInt(nLine.trim());
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            long totalSum = 0;
            long minOdd = Long.MAX_VALUE;
            boolean hasOdd = false;
            
            for (int i = 0; i < n; i++) {
                long val = Long.parseLong(st.nextToken());
                totalSum += val;
                
                // Track the smallest odd number
                if (val % 2 != 0) {
                    hasOdd = true;
                    if (val < minOdd) {
                        minOdd = val;
                    }
                }
            }
            
            // Check the parity of the total sum
            if (totalSum % 2 != 0) {
                // If the sum is already odd, it's the maximum possible
                System.out.println(totalSum);
            } else {
                // If sum is even, remove the smallest odd number to make it odd
                if (hasOdd) {
                    System.out.println(totalSum - minOdd);
                } else {
                    // No odd numbers found, cannot form an odd sum
                    System.out.println(0);
                }
            }
        }
    }
}