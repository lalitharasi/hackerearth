import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Specialsubarray {
    public static void main(String[] args) throws Exception {
        // Fast I/O using BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        String mLine = br.readLine();
        if (mLine == null) return;
        
        int M;
        try {
            M = Integer.parseInt(mLine.trim());
        } catch (Exception e) {
            return;
        }

        // Initialize Trie with a reasonable capacity. 
        // It will resize automatically if total characters exceed this.
        Trie trie = new Trie(1000005);

        for (int i = 0; i < M; i++) {
            String s = br.readLine();
            if (s != null && !s.isEmpty()) {
                trie.insert(s.trim());
            }
        }

        // Calculate and output the result
        out.println(trie.getSpecialSubarraySum());
        out.flush();
        out.close();
    }

    static class Trie {
        int[] child0;
        int[] child1;
        int[] zeroCounts;
        int nodeCounter;
        static final int MOD = 1000000007;

        Trie(int initialCapacity) {
            child0 = new int[initialCapacity];
            child1 = new int[initialCapacity];
            zeroCounts = new int[initialCapacity];
            nodeCounter = 1; // Root node is index 1
        }

        // Insert string into Trie and track zero counts for each unique prefix
        void insert(String s) {
            int curr = 1;
            int z = 0;
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch != '0' && ch != '1') continue; 
                int bit = ch - '0';
                if (bit == 0) z++;
                
                if (bit == 0) {
                    if (child0[curr] == 0) {
                        nodeCounter++;
                        if (nodeCounter >= child0.length) resize();
                        child0[curr] = nodeCounter;
                        zeroCounts[nodeCounter] = z;
                    }
                    curr = child0[curr];
                } else {
                    if (child1[curr] == 0) {
                        nodeCounter++;
                        if (nodeCounter >= child1.length) resize();
                        child1[curr] = nodeCounter;
                        zeroCounts[nodeCounter] = z;
                    }
                    curr = child1[curr];
                }
            }
        }

        private void resize() {
            int oldSize = child0.length;
            int newSize = oldSize * 2;
            int[] newChild0 = new int[newSize];
            int[] newChild1 = new int[newSize];
            int[] newZeroCounts = new int[newSize];
            System.arraycopy(child0, 0, newChild0, 0, oldSize);
            System.arraycopy(child1, 0, newChild1, 0, oldSize);
            System.arraycopy(zeroCounts, 0, newZeroCounts, 0, oldSize);
            child0 = newChild0;
            child1 = newChild1;
            zeroCounts = newZeroCounts;
        }

        long getSpecialSubarraySum() {
            long totalSum = 0;
            // Iterate through every unique prefix (Trie nodes 2 to nodeCounter)
            for (int i = 2; i <= nodeCounter; i++) {
                if (zeroCounts[i] > 1) {
                    // Count of special subarrays in this prefix is (number of 0s) - 1
                    totalSum = (totalSum + (zeroCounts[i] - 1)) % MOD;
                }
            }
            return totalSum;
        }
    }
}