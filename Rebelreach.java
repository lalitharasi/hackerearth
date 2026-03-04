import java.io.*;
import java.util.*;

public class Rebelreach {
    // FastReader class to handle high volume input efficiently in Java 17
    static class FastReader {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
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

        public int nextInt() {
            String s = next();
            return s == null ? -1 : Integer.parseInt(s);
        }

        public long nextLong() {
            String s = next();
            return s == null ? -1L : Long.parseLong(s);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        String tInput = fr.next();
        if (tInput == null) return;
        int T = Integer.parseInt(tInput);

        while (T-- > 0) {
            int N = fr.nextInt();
            if (N <= 0) continue;

            // Static array adjacency list representation
            int[] head = new int[N + 1];
            int[] nextEdge = new int[2 * N];
            int[] to = new int[2 * N];
            Arrays.fill(head, -1);
            int edgeCount = 0;

            for (int i = 0; i < N - 1; i++) {
                int u = fr.nextInt();
                int v = fr.nextInt();
                if (u == -1 || v == -1) break;
                // Add undirected edge u-v
                to[edgeCount] = v;
                nextEdge[edgeCount] = head[u];
                head[u] = edgeCount++;
                to[edgeCount] = u;
                nextEdge[edgeCount] = head[v];
                head[v] = edgeCount++;
            }

            long[] guards = new long[N + 1];
            for (int i = 1; i <= N; i++) {
                guards[i] = fr.nextLong();
            }

            int LOG = 18; // Enough for N = 10^5
            int[][] up = new int[N + 1][LOG];
            long[] prefixSum = new long[N + 1];
            
            // Iterative BFS to compute depth/parent/prefixSum
            int[] q = new int[N];
            int qHead = 0, qTail = 0;
            q[qTail++] = 1;
            boolean[] visited = new boolean[N + 1];
            visited[1] = true;
            prefixSum[1] = guards[1];

            while (qHead < qTail) {
                int u = q[qHead++];
                for (int e = head[u]; e != -1; e = nextEdge[e]) {
                    int v = to[e];
                    if (!visited[v]) {
                        visited[v] = true;
                        up[v][0] = u;
                        prefixSum[v] = prefixSum[u] + guards[v];
                        q[qTail++] = v;
                    }
                }
            }

            // Construct Binary Lifting table
            for (int j = 1; j < LOG; j++) {
                for (int i = 1; i <= N; i++) {
                    int mid = up[i][j - 1];
                    if (mid != 0) {
                        up[i][j] = up[mid][j - 1];
                    }
                }
            }

            int Q = fr.nextInt();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Q; i++) {
                int u = fr.nextInt();
                long k = fr.nextLong();
                
                // Rebels reach an ancestor V if K > Sum of guards from U to child(V)
                // S(V) > S(U) - K
                long target = prefixSum[u] - k;

                if (prefixSum[1] > target) {
                    sb.append(1).append("\n");
                } else {
                    int curr = u;
                    for (int j = LOG - 1; j >= 0; j--) {
                        int anc = up[curr][j];
                        if (anc != 0 && prefixSum[anc] > target) {
                            curr = anc;
                        }
                    }
                    sb.append(curr).append("\n");
                }
                
                // Periodic buffer management
                if (sb.length() > 8192) {
                    out.print(sb.toString());
                    sb.setLength(0);
                }
            }
            out.print(sb.toString());
        }
        out.flush();
        out.close();
    }
}