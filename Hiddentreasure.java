import java.io.*;
import java.util.*;

public class Hiddentreasure {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Reading N
        String line1 = br.readLine();
        if (line1 == null) return;
        int n = Integer.parseInt(line1.trim());
        
        // Reading nums
        String line2 = br.readLine();
        if (line2 == null) return;
        String[] arr_nums = line2.trim().split("\\s+");
        
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(arr_nums[i]);
        }

        long out_ = solve(n, nums);
        System.out.println(out_);

        br.close();
    }

    static long solve(int n, int[] nums) {
        // Max possible digit sum for an int (up to 2*10^9) is around 82.
        // Using an array of size 200 is more than enough and faster than a HashMap.
        long[] countMap = new long[200];
        long totalPairs = 0;

        for (int i = 0; i < n; i++) {
            int sum = getDigitSum(nums[i]);
            
            // The number of pairs this element forms is equal to how many
            // times we've seen this digit sum previously.
            totalPairs += countMap[sum];
            
            // Increment the occurrence count for this digit sum
            countMap[sum]++;
        }

        return totalPairs;
    }

    // Helper method to calculate sum of digits
    private static int getDigitSum(int num) {
        int sum = 0;
        // Handle negative numbers if any (though constraints usually imply non-negative)
        num = Math.abs(num);
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}