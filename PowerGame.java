import java.io.*;
import java.util.*;
//hard
public class TestClass {
    static int n, m;
    static int[] head, to, next, edgeId, edgePower;
    static int edgeCount;
    static int[] tin, low;
    static int timer;
    static int[] bridgePowers;
    static int bridgeCount;

    public static void main(String[] args) throws Exception {
        // Increase stack size to 64MB for deep graphs
        Thread thread = new Thread(null, () -> {
            try {
                solve();
            } catch (Exception e) {}
        }, "solve", 1 << 26);
        thread.start();
        thread.join();
    }

    static void solve() throws IOException {
        FastReader sc = new FastReader(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        String sN = sc.next();
        if (sN == null) return;
        n = Integer.parseInt(sN);
        m = Integer.parseInt(sc.next());

        int[] powers = new int[m];
        for (int i = 0; i < m; i++) powers[i] = Integer.parseInt(sc.next());

        // Using adjacency list with primitive arrays for speed
        head = new int[n + 1];
        Arrays.fill(head, -1);
        to = new int[2 * m];
        next = new int[2 * m];
        edgeId = new int[2 * m];
        edgePower = new int[2 * m];
        edgeCount = 0;

        for (int i = 0; i < m; i++) {
            int u = Integer.parseInt(sc.next());
            int v = Integer.parseInt(sc.next());
            addEdge(u, v, i, powers[i]);
            addEdge(v, u, i, powers[i]);
        }

        tin = new int[n + 1];
        low = new int[n + 1];
        Arrays.fill(tin, -1);
        timer = 0;
        bridgePowers = new int[m];
        bridgeCount = 0;

        for (int i = 1; i <= n; i++) {
            if (tin[i] == -1) dfs(i, -1);
        }

        // Sort bridge powers using primitive array (much faster than List)
        Arrays.sort(bridgePowers, 0, bridgeCount);

        long maxSam = 0;
        long minSam = 0;
        
        // Sorting is ascending, so iterate from the end (largest powers)
        int turn = 0;
        for (int i = bridgeCount - 1; i >= 0; i--) {
            if (turn % 2 == 0) maxSam += bridgePowers[i];
            else minSam += bridgePowers[i];
            turn++;
        }

        out.println(maxSam + " " + minSam);
        out.flush();
        out.close();
    }

    static void addEdge(int u, int v, int id, int p) {
        to[edgeCount] = v;
        edgeId[edgeCount] = id;
        edgePower[edgeCount] = p;
        next[edgeCount] = head[u];
        head[u] = edgeCount++;
    }

    static void dfs(int v, int pId) {
        tin[v] = low[v] = timer++;
        for (int i = head[v]; i != -1; i = next[i]) {
            int neighbor = to[i];
            int id = edgeId[i];
            if (id == pId) continue;
            if (tin[neighbor] != -1) {
                low[v] = Math.min(low[v], tin[neighbor]);
            } else {
                dfs(neighbor, id);
                low[v] = Math.min(low[v], low[neighbor]);
                if (low[neighbor] > tin[v]) {
                    bridgePowers[bridgeCount++] = edgePower[i];
                }
            }
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
        public FastReader(InputStream in) { br = new BufferedReader(new InputStreamReader(in)); }
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    String s = br.readLine();
                    if (s == null) return null;
                    st = new StringTokenizer(s);
                } catch (IOException e) { return null; }
            }
            return st.nextToken();
        }
    }
}
