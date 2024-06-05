package com.aizone.blockchain;

import java.util.Random;

public class Setup {

    public static void main(String[] args) {
        int[] setup = Setup();
        int n = setup[0];
        int m = setup[1];
        int d = setup[2];
        int q = setup[3];
        int[][] A = generateMatrix(n, d, m, q);
        int[] f = constructIrreduciblePolynomial(d);
    }

    public static int[] Setup() {
        int n = 4;
        int m = 6;
        int d = 128;
        int q = 8380417;
        return new int[]{n, m, d, q};
    }

    public static int[][] generateMatrix(int n, int d, int m, int q) {
        int[][] A = new int[n][d * m];
        Random rand = new Random();
        int range = (q - 1) / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < d * m; j++) {
                A[i][j] = rand.nextInt(range * 2 + 1) - range;
            }
        }
        return A;
    }

    public static int[] constructIrreduciblePolynomial(int d) {
        int[] f = new int[d + 1];
        f[0] = 1;
        f[d] = 1;
        return f;
    }
}
