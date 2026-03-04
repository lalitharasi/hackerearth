import java.io.*;
import java.util.*;

public class Oddbitarray {
    public static void main(String[] args) throws IOException {
        // Fast I/O using BufferedReader and StringTokenizer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String firstLine = br.readLine();
        if (firstLine == null) return;
        
        int t = Integer.parseInt(firstLine.trim());
        long MOD = 1_000_000_007L;

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            // sum0 is the sum of ways for prefixes with even parity
            // sum1 is the sum of ways for prefixes with odd parity
            // We start with sum0 = 1 because the initial prefix (empty, dp[0]) 
            // has an XOR sum of 0, which has an even number of set bits (0).
            long sum0 = 1; 
            long sum1 = 0;
            long currentDP = 0;
            int currentPrefixParity = 0;

            for (int i = 0; i < n; i++) {
                int val = Integer.parseInt(st.nextToken());
                
                // Calculate parity of current element's bits
                // Integer.bitCount returns the number of set bits
                int elementParity = Integer.bitCount(val) % 2;
                
                // Update prefix XOR parity
                currentPrefixParity ^= elementParity;

                if (currentPrefixParity == 0) {
                    // Current prefix is even, must pair with odd previous prefixes
                    currentDP = sum1;
                    sum0 = (sum0 + currentDP) % MOD;
                } else {
                    // Current prefix is odd, must pair with even previous prefixes
                    currentDP = sum0;
                    sum1 = (sum1 + currentDP) % MOD;
                }
            }

            out.println(currentDP);
        }
        out.flush();
        out.close();
    }
}