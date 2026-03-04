import java.io.*;
import java.util.*;

public class Treeinversion {
    static int[] color, depth, first, last, euler;
    static int[][] up;
    static int[] nodeFreq, colorFreq;
    static long currentPairs;
    static int timer, LOG;
    static List<Integer>[] adj;

    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        int t = fr.nextInt();
        while (t-- > 0) {
            int n = fr.nextInt();
            int q = fr.nextInt();
            color = new int[n + 1];
            // Coordinate compression for colors if needed, but constraints allow direct indexing if within range
            for (int i = 1; i <= n; i++) color[i] = fr.nextInt();
            adj = new ArrayList[n + 1];
            for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                int u = fr.nextInt();
                int v = fr.nextInt();
                adj[u].add(v);
                adj[v].add(u);
            }

            LOG = (int) (Math.log(n) / Math.log(2)) + 1;
            up = new int[n + 1][LOG];
            first = new int[n + 1];
            last = new int[n + 1];
            depth = new int[n + 1];
            euler = new int[2 * n];
            timer = 0;
            dfs(1, 1, 0);

            Query[] queries = new Query[q];
            for (int i = 0; i < q; i++) {
                int u = fr.nextInt();
                int v = fr.nextInt();
                int lca = getLCA(u, v);
                if (first[u] > first[v]) { int temp = u; u = v; v = temp; }
                if (lca == u) queries[i] = new Query(first[u], first[v], i, -1);
                else queries[i] = new Query(last[u], first[v], i, lca);
            }

            int blockSize = (int) Math.sqrt(2 * n);
            Arrays.sort(queries, (a, b) -> {
                int b1 = a.l / blockSize;
                int b2 = b.l / blockSize;
                if (b1 != b2) return b1 - b2;
                return (b1 % 2 == 0) ? a.r - b.r : b.r - a.r;
            });

            long[] results = new long[q];
            nodeFreq = new int[n + 1];
            colorFreq = new int[100005]; // Adjust based on color range
            currentPairs = 0;
            int L = queries[0].l, R = L - 1;

            for (Query query : queries) {
                while (L > query.l) update(euler[--L]);
                while (R < query.r) update(euler[++R]);
                while (L < query.l) update(euler[L++]);
                while (R > query.r) update(euler[R--]);

                int uNode = euler[L], vNode = euler[R];
                if (query.lca != -1) update(query.lca);
                
                long k = depth[uNode] + depth[vNode] - 2L * depth[getLCA(uNode, vNode)] + 1;
                if(query.lca == -1) k = Math.abs(depth[uNode] - depth[vNode]) + 1;
                
                results[query.id] = (k * (k - 1) / 2) - currentPairs;
                if (query.lca != -1) update(query.lca);
            }
            for (long res : results) out.println(res);
        }
        out.flush();
    }

    static void dfs(int u, int p, int d) {
        first[u] = timer;
        euler[timer++] = u;
        depth[u] = d;
        up[u][0] = p;
        for (int i = 1; i < LOG; i++) up[u][i] = up[up[u][i - 1]][i - 1];
        for (int v : adj[u]) if (v != p) dfs(v, u, d + 1);
        last[u] = timer;
        euler[timer++] = u;
    }

    static void update(int node) {
        int c = color[node];
        if (nodeFreq[node] == 0) {
            currentPairs += colorFreq[c];
            colorFreq[c]++;
            nodeFreq[node]++;
        } else {
            colorFreq[c]--;
            currentPairs -= colorFreq[c];
            nodeFreq[node]--;
        }
    }

    static int getLCA(int u, int v) {
        if (depth[u] < depth[v]) { int temp = u; u = v; v = temp; }
        for (int i = LOG - 1; i >= 0; i--) if (depth[u] - (1 << i) >= depth[v]) u = up[u][i];
        if (u == v) return u;
        for (int i = LOG - 1; i >= 0; i--) if (up[u][i] != up[v][i]) { u = up[u][i]; v = up[v][i]; }
        return up[u][0];
    }

    static class Query {
        int l, r, id, lca;
        Query(int l, int r, int id, int lca) { this.l = l; this.r = r; this.id = id; this.lca = lca; }
    }

    static class FastReader {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String next() {
            while (st == null || !st.hasMoreElements()) try { st = new StringTokenizer(br.readLine()); } catch (IOException e) {}
            return st.nextToken();
        }
        int nextInt() { return Integer.parseInt(next()); }
    }
}