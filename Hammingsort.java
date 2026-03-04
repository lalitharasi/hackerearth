import java.io.*;
import java.util.*;

public class Hammingsort {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        
        String tLine = br.readLine();
        if (tLine == null) return;
        int T = Integer.parseInt(tLine.trim());
        
        for (int t_i = 0; t_i < T; t_i++) {
            String[] custom_input_1 = br.readLine().trim().split("\\s+");
            int N = Integer.parseInt(custom_input_1[0]);
            int K = Integer.parseInt(custom_input_1[1]);
            
            String[] arr_A = br.readLine().trim().split("\\s+");
            int[] A = new int[N];
            for (int i_A = 0; i_A < N; i_A++) {
                A[i_A] = Integer.parseInt(arr_A[i_A]);
            }

            int[] out_ = solve(N, K, A);
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < out_.length; i++) {
                sb.append(out_[i]).append(i == out_.length - 1 ? "" : " ");
            }
            wr.println(sb.toString());
        }

        wr.close();
        br.close();
    }

    static int[] solve(int N, int K, int[] A) {
        // We need an Integer array to use a custom Comparator
        Integer[] boxedArray = new Integer[N];
        for (int i = 0; i < N; i++) {
            boxedArray[i] = A[i];
        }

        // Sort using the specified logic
        Arrays.sort(boxedArray, (a, b) -> {
            // Calculate Hamming Distances
            int distA = Integer.bitCount(a ^ K);
            int distB = Integer.bitCount(b ^ K);

            if (distA != distB) {
                // Ascending order of Hamming Distance
                return Integer.compare(distA, distB);
            } else {
                // Tie-breaker: Ascending order of value
                return Integer.compare(a, b);
            }
        });

        // Convert back to primitive int array
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            result[i] = boxedArray[i];
        }

        return result;
    }
}