import java.io.BufferedReader;
import java.io.InputStreamReader;

class Palindromesplit {
    public static void main(String args[]) throws Exception {
        // Use BufferedReader for fast input reading
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String tLine = br.readLine();
        if (tLine == null) return;
        int t = Integer.parseInt(tLine.trim());
        
        while (t-- > 0) {
            String s = br.readLine();
            if (s == null) break;
            
            // Array to store frequency of each lowercase English letter
            int[] counts = new int[26];
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                // Ensure we only process lowercase letters as per constraints
                if (c >= 'a' && c <= 'z') {
                    counts[c - 'a']++;
                }
            }
            
            int totalPalindromes = 0;
            // For each character, the number of pairs determines the number of palindromes
            for (int count : counts) {
                totalPalindromes += count / 2;
            }
            
            // Print the result for the current test case
            System.out.println(totalPalindromes);
        }
    }
}