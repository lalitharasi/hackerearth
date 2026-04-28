import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int k = sc.nextInt();
            int x = sc.nextInt();

            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();

            out.println(solve(n, k, x, a));
        }
        out.flush();
    }

    private static int solve(int n, int k, int x, int[] a) {
        int[] freq = new int[100001];
        int distinctCount = 0;
        int lastX = -1;
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            int val = a[right];
            if (val == x) lastX = right;

            // Add element to window
            if (freq[val] == 0) distinctCount++;
            freq[val]++;

            // Shrink window if distinct elements > K
            while (distinctCount > k) {
                int leftVal = a[left];
                freq[leftVal]--;
                if (freq[leftVal] == 0) distinctCount--;
                left++;
            }

            // If we have exactly K distinct and X is in the window
            if (distinctCount == k && lastX >= left) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }
        return maxLen;
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    String s = br.readLine();
                    if (s == null) return null;
                    st = new StringTokenizer(s);
                } catch (IOException e) {
                    return null;
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }
    }
}
