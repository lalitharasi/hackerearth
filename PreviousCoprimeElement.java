import java.io.*;
import java.util.*;
//hard
public class TestClass {
    // Fast GCD function
    static int gcd(int a, int b) {
        while (b != 0) {
            a %= b;
            int temp = a;
            a = b;
            b = temp;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String tStr = sc.next();
        if (tStr == null) return;
        int t = Integer.parseInt(tStr);

        while (t-- > 0) {
            String nStr = sc.next();
            if (nStr == null) break;
            int n = Integer.parseInt(nStr);
            
            // Map to store: Value -> Its most recent index
            Map<Integer, Integer> lastSeen = new HashMap<>();
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                int current = Integer.parseInt(sc.next());
                int maxCoprimeIdx = -1;

                // Iterate through all UNIQUE values seen so far in this test case
                for (Map.Entry<Integer, Integer> entry : lastSeen.entrySet()) {
                    if (gcd(current, entry.getKey()) == 1) {
                        maxCoprimeIdx = Math.max(maxCoprimeIdx, entry.getValue());
                    }
                }
                
                sb.append(maxCoprimeIdx);
                if (i < n - 1) sb.append(" ");
                
                // Update the last seen index for the current value
                lastSeen.put(current, i);
            }
            out.println(sb.toString());
        }
        out.flush();
        out.close();
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
