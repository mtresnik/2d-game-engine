package util.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;

public final class matrices {

    private matrices() {

    }

    public static final class mat2 {
    }

    public static final class mat3 {
    }

    public static final class mat4 {
    }
    
    
    public static int[] frequenciesFromData(double[][] data, double min, double max, int bins){
        int[] frequency = new int[bins];
        double binSize = (max - min) / bins;
        for (int ROW = 0; ROW < data.length; ROW++) {
            for (int COL = 0; COL < data[0].length; COL++) {
                for (int BIN_INDEX = 0; BIN_INDEX < bins; BIN_INDEX++) {
                    if(data[ROW][COL] >= min){
                       if(data[ROW][COL] < min + (BIN_INDEX+1)*binSize){
                           frequency[BIN_INDEX]++;
                           break;
                       }
                    }
                }
            }
        }
        return frequency;
    }
    
    public static String[] toString(double[][] outputMatrix){
        String[] toWrite = new String[outputMatrix.length];
        for (int ROW = 0; ROW < outputMatrix.length; ROW++) {
            String buffer = "";
            for (int COL = 0; COL < outputMatrix[ROW].length; COL++) {
                buffer += outputMatrix[ROW][COL];
                if(COL != outputMatrix[ROW].length - 1){
                    buffer += " ";
                }
            }
            toWrite[ROW] = buffer;
        }
        return toWrite;
    }
    
    public static String dimString(double[][] A) {
        return "(" + A.length + "x" + A[0].length + ")";
    }

    public static double[][] randMatrix(int m, int n) {
        double[][] retMatrix = new double[m][n];
        for (int ROW = 0; ROW < m; ROW++) {
            for (int COL = 0; COL < n; COL++) {
                retMatrix[ROW][COL] = Math.random();
            }
        }
        return retMatrix;
    }

    public static double[][] transpose(double[][] A) {
        int m = A.length, n = A[0].length;
        double[][] retArray = new double[n][m];
        for (int ROW = 0; ROW < m; ROW++) {
            for (int COL = 0; COL < n; COL++) {
                retArray[COL][ROW] = A[ROW][COL];
            }
        }
        return retArray;
    }

    public static void printMatrix(double[][]... matrices) {
        for (double[][] matrix : matrices) {
            printMatrix(matrix, "");
        }
    }

    public static void printMatrix(double[][] A, String name) {
        if (name.length() > 0) {
            System.out.println("Matrix " + name + ":" + dimString(A));
        }
        System.out.println("[");
        for (int ROW = 0; ROW < A.length; ROW++) {
            System.out.println(Arrays.toString(A[ROW]));
            if (ROW == A.length - 1) {
                System.out.println("]");
            }
        }

    }

    public static double[][] dot(double[][] A, double[][] B) {
        int m = A.length, n = A[0].length, o = B.length, p = B[0].length;
        if ((n == 1 && p == 1 || m == 1 && o == 1) && (m > 1 || n > 1) && (o > 1 || p > 1)) {
            B = transpose(B);
            o = B.length;
            p = B[0].length;
        }
        if (n != o) {
            throw new IllegalArgumentException("Matrices are of improper lengths: \n(" + m + "x" + n + ")\t(" + o + "x" + p + ")");
        }
        double[][] retMatrix = new double[m][p];
        for (int ROW_C = 0; ROW_C < m; ROW_C++) {
            for (int COL_C = 0; COL_C < p; COL_C++) {
                double result = 0.0;
                for (int i = 0; i < n; i++) {
                    double A_elem = A[ROW_C][i];
                    double B_elem = B[i][COL_C];
                    result += A_elem * B_elem;
                }
                retMatrix[ROW_C][COL_C] = result;
            }
        }
        return retMatrix;

    }

    public static double[][] hadamard(double[][] A, double[][] B) {
        int m = A.length, n = A[0].length, o = B.length, p = B[0].length;
        if (m != o || n != p) {
            throw new IllegalArgumentException("Matrices are of improper lengths: \n(" + m + "x" + n + ")\t(" + o + "x" + p + ")");
        }
        double[][] retMatrix = new double[m][n];
        for (int ROW = 0; ROW < m; ROW++) {
            for (int COL = 0; COL < n; COL++) {
                retMatrix[ROW][COL] = A[ROW][COL] * B[ROW][COL];
            }
        }
        return retMatrix;
    }

    public static double[][] add(double[][] A, double[][] B) {
        int m = A.length, n = A[0].length, o = B.length, p = B[0].length;
        if (m != o || n != p) {
            throw new IllegalArgumentException("Matrices are of improper lengths: \n(" + m + "x" + n + ")\t(" + o + "x" + p + ")");
        }
        double[][] retMatrix = new double[m][n];
        for (int ROW = 0; ROW < m; ROW++) {
            for (int COL = 0; COL < n; COL++) {
                retMatrix[ROW][COL] = A[ROW][COL] + B[ROW][COL];
            }
        }
        return retMatrix;
    }

    public static double[][] subtract(double[][] A, double[][] B) {
        int m = A.length, n = A[0].length, o = B.length, p = B[0].length;
        if (m != o || n != p) {
            throw new IllegalArgumentException("Matrices are of improper lengths: \n(" + m + "x" + n + ")\t(" + o + "x" + p + ")");
        }
        double[][] retMatrix = new double[m][n];
        for (int ROW = 0; ROW < m; ROW++) {
            for (int COL = 0; COL < n; COL++) {
                retMatrix[ROW][COL] = A[ROW][COL] - B[ROW][COL];
            }
        }
        return retMatrix;
    }

    public static double[][] scale(double[][] A, double scalar) {
        int m = A.length, n = A[0].length;
        double[][] retMatrix = new double[m][n];
        for (int ROW = 0; ROW < m; ROW++) {
            for (int COL = 0; COL < n; COL++) {
                retMatrix[ROW][COL] = A[ROW][COL] * scalar;
            }
        }
        return retMatrix;
    }

    public static double sigma(double[][] A) {
        double total = 0.0;
        for (int ROW = 0; ROW < A.length; ROW++) {
            for (int COL = 0; COL < A[0].length; COL++) {
                total += A[ROW][COL];
            }
        }
        return total;
    }

    public static double sigmoid(double x) {
        return 1.0 / (1 + Math.exp(-1.0 * x));
    }

    public static double sigmoidPrime(double x) {
        return Math.exp(-1.0 * x) / Math.pow((1 + Math.exp(-1.0 * x)), 2);
    }

    public static double[][] zeroMatrix(int m, int n) {
        double[][] retMatrix = new double[m][n];
        for (int ROW = 0; ROW < m; ROW++) {
            for (int COL = 0; COL < n; COL++) {
                retMatrix[ROW][COL] = 0.0;
            }
        }
        return retMatrix;
    }

    public static double justifyDouble(Double d) {
        if (d > 1.0) {
            d = 1.0;
        }
        if (d < 0.0) {
            d = 0.0;
        }
        return d;

    }

    public static byte[] getColorSliding(double d) {
        if (d > 1.0) {
            d = 1.0;
        }
        if (d < 0.0) {
            d = 0.0;
        }
        double val = 120;
        return getColor(d * val + 360.0 - val);
    }

    public static byte[] getColor(double theta) {
        List<Pair<Double, int[]>> pairlist = new ArrayList();
        pairlist.add(new Pair(0.0, new int[]{255, 0, 0}));
        pairlist.add(new Pair(60.0, new int[]{255, 0, 255}));
        pairlist.add(new Pair(120.0, new int[]{0, 0, 255}));
        pairlist.add(new Pair(180.0, new int[]{0, 255, 255}));
        pairlist.add(new Pair(240.0, new int[]{0, 255, 0}));
        pairlist.add(new Pair(300.0, new int[]{255, 255, 0}));
        pairlist.add(new Pair(360.0, new int[]{255, 0, 0}));
        if (theta > 360.0) {
            theta -= 360.0;
        }
        if (theta < 0) {
            theta += 360.0;
        }
        for (int i = 0; i < pairlist.size() - 1; i++) {
            Pair<Double, int[]> pair1 = pairlist.get(i), pair2 = pairlist.get(i + 1);
            if (theta >= pair1.getKey() && theta <= pair2.getKey()) {
                // System.out.println(pair1.toString() + " \t" + pair2.toString());
                int[] final_color = pair2.getValue();
                int[] initial_color = pair1.getValue();
                double percentage = (theta - pair1.getKey()) / (pair2.getKey() - pair1.getKey());
                int[] new_color = new int[3];
                byte[] ret_color = new byte[3];
                for (int j = 0; j < 3; j++) {
                    new_color[j] = (int) (percentage * (final_color[j] - initial_color[j]) + initial_color[j]);
                    ret_color[j] = (byte) new_color[j];
                }
                return ret_color;
            }
        }
        return new byte[]{(byte) 255, 0, 0};

    }
    

    

}
