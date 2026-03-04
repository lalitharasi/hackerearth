import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        String line = br.readLine();
        if (line == null) return;
        int T = Integer.parseInt(line.trim());
        for (int t_i = 0; t_i < T; t_i++) {
            int N = Integer.parseInt(br.readLine().trim());
            String[] arr_A = br.readLine().trim().split("\\s+");
            int[] A = new int[N];
            for (int i_A = 0; i_A < N; i_A++) {
                A[i_A] = Integer.parseInt(arr_A[i_A]);
            }

            int out_ = solve(N, A);
            System.out.println(out_);
        }
        wr.close();
        br.close();
    }

    static int solve(int N, int[] A) {
        // Special case for N=1
        if (N == 1) {
            return (A[0] == 0) ? 0 : -1;
        }

        long totalSum = 0;
        int maxElement = -1;

        for (int i = 0; i < N; i++) {
            totalSum += (long) A[i];
            if (A[i] > maxElement) {
                maxElement = A[i];
            }
        }

        // Total sum must be divisible by N - 1
        long divisor = (long) N - 1;
        if (totalSum % divisor != 0) {
            return -1;
        }

        // Total operations K
        long K = totalSum / divisor;

        // k_i = K - A[i] must be >= 0 for all i
        if (K < (long) maxElement) {
            return -1;
        }

        // K is guaranteed to be within int range based on constraints
        return (int) K;
    }
}