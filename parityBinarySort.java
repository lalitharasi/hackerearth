import java.io.*;
import java.util.*;
//mid
public class TestClass {
    public static void main(String args[]) throws Exception {
        FastReader sc = new FastReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        // Read number of test cases
        String tStr = sc.next();
        if (tStr == null) return;
        int t = Integer.parseInt(tStr);

        while (t-- > 0) {
            int n = Integer.parseInt(sc.next());
            
            List<Integer> evenParity = new ArrayList<>();
            List<Integer> oddParity = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int val = Integer.parseInt(sc.next());
                // Integer.bitCount returns the number of set bits (1s)
                if (Integer.bitCount(val) % 2 == 0) {
                    evenParity.add(val);
                } else {
                    oddParity.add(val);
                }
            }

            // Sort both lists in ascending order
            Collections.sort(evenParity);
            Collections.sort(oddParity);

            // Print Even parity numbers first, then Odd
            StringBuilder sb = new StringBuilder();
            for (int x : evenParity) {
                sb.append(x).append(" ");
            }
            for (int x : oddParity) {
                sb.append(x).append(" ");
            }

            out.println(sb.toString().trim());
        }
        out.flush();
        out.close();
    }

    // FastReader for efficient input processing
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    String line = br.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    return null;
                }
            }
            return st.nextToken();
        }
    }
}
