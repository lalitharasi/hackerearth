import java.io.*;
import java.util.*;
//mid

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        int T = Integer.parseInt(br.readLine().trim());
        for(int t_i = 0; t_i < T; t_i++)
        {
            int N = Integer.parseInt(br.readLine().trim());
            String[] arr_A = br.readLine().split(" ");
            int[] A = new int[N];
            for(int i_A = 0; i_A < arr_A.length; i_A++)
            {
            	A[i_A] = Integer.parseInt(arr_A[i_A]);
            }

            long out_ = solve(N, A);
            System.out.println(out_);
            
         }

         wr.close();
         br.close();
    }
static long solve(int N, int[] A) {
    long sum = 0;
    for (int x : A) {
        sum += x;
    }

    // If sum is positive, we can never reach 0 because each operation increases sum by 1
    if (sum > 0) {
        return -1;
    }

    long k = -sum; // Total operations needed
    long minIncrementsRequired = 0;

    for (int x : A) {
        if (x < 0) {
            // We need 2*xi + x >= 0 => 2*xi >= -x
            // xi >= ceil(-x / 2.0)
            long val = -x;
            minIncrementsRequired += (val + 1) / 2;
        }
    }

    // Total increments available (k) must be at least the sum of minimum required increments
    if (minIncrementsRequired <= k) {
        return k;
    } else {
        return -1;
    }
}

}