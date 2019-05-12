package main.java.utilities.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public final class math {

    public static int fib_recurr(int n) {
        checkfib(n);
        if (n <= 1) {
            return n;
        }
        int m = fib_recurr(n - 1) + fib_recurr(n - 2);
        return m;
    }


    public static int fib_loop(int n) {
        checkfib(n);
        if (n <= 1) {
            return n;
        }
        int curr = 1, prev = 0, next = 0;
        for (int index = 0; index < n - 1; index++) {
            next = curr + prev;
            prev = curr;
            curr = next;
        }
        return curr;
    }


    public static BigInteger fib_linear(BigInteger bn) {
        return null;
    }


    public static BigDecimal pow(BigDecimal input, BigInteger power) {
        BigDecimal retvalue = BigDecimal.ONE;
        for (BigInteger index = new BigInteger("0"); index.compareTo(power) < 0; index = index.add(BigInteger.ONE)) {
            retvalue = retvalue.multiply(input);
        }
        return retvalue;
    }


    private static abstract class telemetryTestObject<T> {

        private T[] params;


        private telemetryTestObject() {

        }


        private telemetryTestObject(T... params) {
            this.params = params;
        }


        public long[][] testFunction(int n) {
            long[][] times = new long[n + 1][2];
            for (int index = 0; index <= n; index++) {
                int factor = 10 * index;
                times[index][0] = factor;
                long start = System.currentTimeMillis();
                this.method(factor);
                long finish = System.currentTimeMillis();
                times[index][1] = start - finish;
                System.out.println(finish - start);
            }

            return times;
        }


        public abstract Object method(int n);


    }


    public static void main(String[] args) {
        int[] numbers = new int[]{0, 1, 2, 3, 4};
        int[] clone = new int[numbers.length];
        int sum = 0;

        for (int index = 0; index < numbers.length;) {
            if (clone[index] < numbers.length) {
                clone[index]++;
            } else {
                index++;
            }
            sum++;
        }

        System.out.println("sum:" + sum);

    }


    private static void checkfib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be greater than zero");
        }
    }


    private static void checkfib(BigInteger bn) {
        if (bn.compareTo(BigInteger.ZERO) < 0) {
            checkfib(-1);
        }
    }


    private math() {

    }


}
