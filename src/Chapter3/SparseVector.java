package Chapter3;

import edu.princeton.cs.algs4.In;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 实现系数向量及其乘法，
 * 第一行输入空格分隔的三个整数n，a和b
 * 其中n表示向量的维度，a和b表示两个向量非零的个数
 * 第二行到a + 1行是向量u的稀疏表示index, value
 * a + 2到a + b + 1行输入v的稀疏表示
 */
public class SparseVector {
    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        String str = scanner.readLine();
        String[] vars = str.trim().split(" ");
        int n = Integer.parseInt(vars[0]);
        int a = Integer.parseInt(vars[1]);
        int b = Integer.parseInt(vars[2]);
        HashMap<Integer, Integer> uMap = new HashMap<>();
        long s = 0;
        int k = 0, v = 0;
        for (int i = 0; i < a; i++) {
            str = scanner.readLine();
            vars = str.trim().split(" ");
            int k1 = Integer.parseInt(vars[0]);
            int v2 = Integer.parseInt(vars[1]);
            uMap.put(k1, v2);
        }

        for (int i = 0; i < b; i++) {
            str = scanner.readLine();
            vars = str.trim().split(" ");
            k = Integer.parseInt(vars[0]);
            v = Integer.parseInt(vars[1]);
            if (uMap.containsKey(k))
                s += uMap.get(k) * v;
        }
        System.out.println(s);
    }
}
